package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import model.facades.MethodData;
import model.boxes.Visibility;
import model.facades.BoxFacade;
import model.facades.MethodFacade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MethodEditorController extends AnchorPane {

    public MethodEditorController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("view/MethodEditor.fxml")));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    @FXML
    private TextField nameField;

    @FXML
    private ComboBox accessComboBox;

    @FXML
    private TextField returnTypeField;

    @FXML
    public VBox argumentVBox;

    private BoxFacade box;

    private MethodFacade method;

    private MethodArgumentEditorController currentEditArgument;

    private List<MethodArgumentEditorController> arguments = new ArrayList<MethodArgumentEditorController>();

    private void AddArgument()
    {
        //Adds the current edit argument to the arguments
        currentEditArgument.highlightPane.getStyleClass().clear();
        currentEditArgument.highlightPane.getStyleClass().add("box");
        currentEditArgument.argumentTypeField.getStyleClass().clear();
        currentEditArgument.argumentTypeField.getStyleClass().add("highlight");
        currentEditArgument.argumentTypeField.setOnAction(null);
        arguments.add(currentEditArgument);
        MethodArgumentEditorController argument = currentEditArgument;
        argument.argumentTypeField.setOnAction((Action) -> ChangeArgument(argument));
        this.setLayoutY(this.getLayoutY() - argument.getHeight()/2);

        //Creates a new current edit argument
        newCurrentEditArgument();
    }

    private void ChangeArgument(MethodArgumentEditorController argument)
    {
        if(argument.argumentTypeField.getText().equals(""))
        {
            this.setLayoutY(this.getLayoutY() + argument.getHeight()/2);
            arguments.remove(argument);
            argumentVBox.getChildren().remove(argument);
            argument.argumentTypeField.setOnAction(null);
        }
    }

    @FXML
    public void ConfirmMethod()
    {
        method.setName(nameField.getText());

        //Gets the arguments for the method data
        for (int i = 0; i < arguments.size(); i++)
        {
            method.addArgument(arguments.get(i).argumentTypeField.getText());
        }


        Visibility visibility = Visibility.valueOf(accessComboBox.getValue().toString());
        method.setVisibility(visibility);

        method.setType(returnTypeField.getText());

        argumentVBox.getChildren().setAll();
        this.setVisible(false);
    }

    @FXML
    public void DeleteMethod()
    {
        //box.DeleteMethod(methodData.methodName);
        this.setVisible(false);
    }

    public void EditMethod(BoxFacade box)
    {
        EditMethod(box.addMethod(), box);
    }

    public void EditMethod(MethodFacade methodData, BoxFacade box)
    {
        this.box = box;
        method = methodData;

        //Resets the arguments array
        arguments = new ArrayList<MethodArgumentEditorController>(0);
        argumentVBox.getChildren().setAll();

        //Set name
        nameField.setText(methodData.getName());

        //Sets the options for the accessibility combo box
        accessComboBox.getItems().setAll(Visibility.values());

        //Sets the current visibility
        accessComboBox.getSelectionModel().select(methodData.getVisibility().name());

        //Sets the method type field
        returnTypeField.setText(methodData.getType());

        argumentVBox.getChildren().clear();

        //Sets the arguments for this method
        for (int i = 0; i < methodData.getArguments().size(); i++)
        {
            MethodArgumentEditorController argument = new MethodArgumentEditorController();
            argument.argumentTypeField.setText(methodData.getArguments().get(i));
            argument.argumentTypeField.setOnAction((Action) -> ChangeArgument(argument));
            argumentVBox.getChildren().add(argument);
            argument.paramLable.setText("Param " + arguments.size());
            argument.argumentTypeField.getStyleClass().add("highlight");
            this.setLayoutY(this.getLayoutY() - argument.getHeight()/2);
        }

        newCurrentEditArgument();
    }

    private  void newCurrentEditArgument ()
    {
        currentEditArgument = new MethodArgumentEditorController();
        argumentVBox.getChildren().add(currentEditArgument);
        currentEditArgument.highlightPane.getStyleClass().clear();
        currentEditArgument.highlightPane.getStyleClass().add("highlight");
        currentEditArgument.argumentTypeField.getStyleClass().clear();
        currentEditArgument.argumentTypeField.getStyleClass().add("box");
        currentEditArgument.argumentTypeField.setOnAction((Action) -> AddArgument());
        currentEditArgument.paramLable.setText("Param " + arguments.size());
    }
}
