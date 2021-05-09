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
    private VBox argumentVBox;

    private BoxFacade box;

    private MethodData methodData;

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
        methodData.methodName = nameField.getText();

        //Gets the arguments for the method data
        String[] argRet = new String[arguments.size()];
        for (int i = 0; i < argRet.length; i++)
        {
            argRet[i] = arguments.get(i).argumentTypeField.getText();
        }
        methodData.arguments = argRet;

        Visibility visibility = Visibility.valueOf(accessComboBox.getValue().toString());
        methodData.visibility = visibility;

        methodData.methodReturnType = returnTypeField.getText();

        box.editMethod(methodData);

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
        EditMethod(new MethodData(), box);
    }

    public void EditMethod(MethodData methodData,  BoxFacade box)
    {
        this.box = box;
        this.methodData = methodData;

        //Resets the arguments array
        arguments = new ArrayList<MethodArgumentEditorController>(0);
        argumentVBox.getChildren().setAll();

        //Set name
        nameField.setText(methodData.methodName);

        //Sets the options for the accessibility combo box
        accessComboBox.getItems().setAll(Visibility.values());

        //Sets the current visibility
        accessComboBox.getSelectionModel().select(methodData.visibility.name());

        //Sets the method type field
        returnTypeField.setText(methodData.methodReturnType);

        //Sets the arguments for this method
        for (int i = 0; i < methodData.arguments.length; i++)
        {
            MethodArgumentEditorController argument = new MethodArgumentEditorController();
            argument.argumentTypeField.setText(methodData.arguments[i]);
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
