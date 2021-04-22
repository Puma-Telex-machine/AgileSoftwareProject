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
