import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import model.MethodData;
import model.boxes.Visibility;

import java.io.IOException;
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

    private MethodArgumentEditorController currentEditArgument;

    private List<MethodArgumentEditorController> arguments;

    public void AddArgument()
    {
        currentEditArgument.argumentTypeField.setOnAction(null);
        arguments.add(currentEditArgument);
        currentEditArgument = new MethodArgumentEditorController(argumentVBox);
        currentEditArgument.argumentTypeField.setOnAction((Action) -> AddArgument());
    }

    @FXML
    public void ConfirmMethod()
    {

    }

    @FXML
    public void DeleteMethod()
    {

    }

    public void EditMethod()
    {
        EditMethod(new MethodData());
    }

    public void EditMethod(MethodData methodData)
    {
        System.out.println();
        nameField.setText(methodData.methodName);
        Visibility[] allVisibility = Visibility.values();
        accessComboBox.getItems().addAll(allVisibility.toString());
        accessComboBox.getSelectionModel().select(methodData.visibility);

        for (int i = 0; i < methodData.arguments.length; i++)
        {
            MethodArgumentEditorController argument = new MethodArgumentEditorController(argumentVBox);
            argument.argumentTypeField.setText(methodData.arguments[i]);
        }

        currentEditArgument = new MethodArgumentEditorController(argumentVBox);
        currentEditArgument.argumentTypeField.setOnAction((Action) -> AddArgument());
    }


}
