import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import model.MethodData;
import model.boxes.Box;
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

    private List<MethodArgumentEditorController> arguments;

    public void AddArgument()
    {
        //Adds the current edit argument to the arguments
        currentEditArgument.highlightPane.getStyleClass().clear();
        currentEditArgument.highlightPane.getStyleClass().add("box");
        currentEditArgument.argumentTypeField.getStyleClass().clear();
        currentEditArgument.argumentTypeField.getStyleClass().add("highlight");
        currentEditArgument.argumentTypeField.setOnAction(null);
        arguments.add(currentEditArgument);

        //Creates a new current edit argument
        currentEditArgument = new MethodArgumentEditorController();
        argumentVBox.getChildren().add(currentEditArgument);
        currentEditArgument.highlightPane.getStyleClass().clear();
        currentEditArgument.highlightPane.getStyleClass().add("highlight");
        currentEditArgument.argumentTypeField.getStyleClass().clear();
        currentEditArgument.argumentTypeField.getStyleClass().add("box");
        currentEditArgument.argumentTypeField.setOnAction((Action) -> AddArgument());
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

        Visibility visibility = (Visibility) accessComboBox.getSelectionModel().getSelectedItem();

        //box.EditMethod(methodData); todo
    }

    @FXML
    public void DeleteMethod()
    {
        box.DeleteMethod(methodData.methodName);
    }

    public void EditMethod()
    {
        EditMethod(new MethodData());
    }

    public void EditMethod(MethodData methodData)
    {
        //this.box = box; todo
        this.methodData = methodData;

        //Resets the arguments array
        arguments = new ArrayList<MethodArgumentEditorController>(0);
        argumentVBox.getChildren().setAll();

        //Set name
        nameField.setText(methodData.methodName);

        //Sets the options for the accessibility combo box
        Visibility[] allVisibility = Visibility.values();
        accessComboBox.getItems().addAll(allVisibility);

        //Sets the current visibility
        accessComboBox.getSelectionModel().select(methodData.visibility.name());

        //Sets the arguments for this method
        for (int i = 0; i < methodData.arguments.length; i++)
        {
            MethodArgumentEditorController argument = new MethodArgumentEditorController();
            argument.argumentTypeField.setText(methodData.arguments[i]);
            argumentVBox.getChildren().add(currentEditArgument);
        }

        currentEditArgument = new MethodArgumentEditorController();
        argumentVBox.getChildren().add(currentEditArgument);
        currentEditArgument.highlightPane.getStyleClass().clear();
        currentEditArgument.highlightPane.getStyleClass().add("highlight");
        currentEditArgument.argumentTypeField.getStyleClass().clear();
        currentEditArgument.argumentTypeField.getStyleClass().add("box");
        currentEditArgument.argumentTypeField.setOnAction((Action) -> AddArgument());
    }


}
