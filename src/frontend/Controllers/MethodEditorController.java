package frontend.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import model.boxes.Visibility;
import model.boxes.BoxFacade;
import model.facades.MethodFacade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MethodEditorController extends AnchorPane {

    public MethodEditorController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("/view/MethodEditor.fxml")));

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

    private MethodFacade methodData;

    private MethodArgumentEditorController currentEditArgument;

    private List<MethodArgumentEditorController> arguments = new ArrayList<MethodArgumentEditorController>();

    private void AddArgument()
    {
        if(currentEditArgument.argumentTypeField.getText().equals("")) return;
        //Adds the current edit argument to the arguments
        currentEditArgument.highlightPane.getStyleClass().clear();
        currentEditArgument.highlightPane.getStyleClass().add("box");
        currentEditArgument.argumentTypeField.getStyleClass().clear();
        currentEditArgument.argumentTypeField.getStyleClass().add("highlight");
        currentEditArgument.argumentTypeField.setOnAction(null);
        arguments.add(currentEditArgument);
        MethodArgumentEditorController argument = currentEditArgument;
        argument.argumentTypeField.setOnAction((Action) -> ChangeArgument(argument));
        argument.argumentTypeField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue){ChangeArgument(argument);}
        });
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
        methodData.confirmMethod();
        methodData.stopUndo();
        methodData.setName(nameField.getText());

        //Gets the arguments for the method data
        String[] argRet = new String[arguments.size()];
        for (int i = 0; i < argRet.length; i++)
        {
            methodData.addArgument(arguments.get(i).argumentTypeField.getText());
        }

        Visibility visibility = (Visibility) accessComboBox.getValue();
        methodData.setVisibility(visibility);

        methodData.setType(returnTypeField.getText());
        methodData.resumeUndo();
        methodData.updateUndo();

        argumentVBox.getChildren().clear();
        methodData =  null;
        this.setVisible(false);
    }

    @FXML
    public void DeleteMethod()
    {
        box.deleteMethod(methodData);
        this.setVisible(false);
        box.updateUndo();
    }

    public void EditMethod(BoxFacade box)
    {
        EditMethod(box.addMethod(), box);
    }

    public void EditMethod(MethodFacade methodData,  BoxFacade box)
    {
        this.box = box;
        this.methodData = methodData;

        //Resets the arguments array
        arguments = new ArrayList<MethodArgumentEditorController>(0);
        argumentVBox.getChildren().setAll();

        //Set name
        nameField.setText(methodData.getName());

        //Sets the options for the accessibility combo box
        accessComboBox.getItems().setAll(Visibility.values());

        //Sets the current visibility
        accessComboBox.getSelectionModel().select(methodData.getVisibility());

        //Sets the method type field
        returnTypeField.setText(methodData.getType());

        argumentVBox.getChildren().clear();

        String[] param = methodData.getArguments().toArray(new String[methodData.getArguments().size()]);
        //Sets the arguments for this method

        double yPos = this.getLayoutY() + this.getHeight()/2;
        for (int i = 0; i < param.length; i++)
        {
            MethodArgumentEditorController argument = new MethodArgumentEditorController();
            argument.argumentTypeField.setText(param[i]);
            argument.argumentTypeField.setOnAction((Action) -> ChangeArgument(argument));
            argument.argumentTypeField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
                if (!newPropertyValue){ChangeArgument(argument);}
            });
            argumentVBox.getChildren().add(argument);
            argument.paramLable.setText("Param " + arguments.size());
            argument.argumentTypeField.getStyleClass().add("highlight");
        }

        this.setLayoutY(this.getLayoutY() - (this.getLayoutY() + getHeight()/2 - yPos));
        newCurrentEditArgument();
    }

    ChangeListener<Boolean> changeListener= (ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
        if (!newValue){AddArgument();}
    };


    private void newCurrentEditArgument ()
    {
        if(currentEditArgument != null) {
            currentEditArgument.argumentTypeField.focusedProperty().removeListener(changeListener);
        }
        currentEditArgument = new MethodArgumentEditorController();
        argumentVBox.getChildren().add(currentEditArgument);
        currentEditArgument.highlightPane.getStyleClass().clear();
        currentEditArgument.highlightPane.getStyleClass().add("highlight");
        currentEditArgument.argumentTypeField.getStyleClass().clear();
        currentEditArgument.argumentTypeField.getStyleClass().add("box");
        currentEditArgument.argumentTypeField.setOnAction((Action) -> AddArgument());
        currentEditArgument.argumentTypeField.focusedProperty().addListener(changeListener);
        currentEditArgument.paramLable.setText("Param " + arguments.size());
    }
}
