package viewmodel;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;

public class MethodEditorController {

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

    public MethodEditorController()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("view/MethodEditor.fxml")));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        currentEditArgument = new MethodArgumentEditorController(argumentVBox);
        currentEditArgument.argumentTypeField.setOnAction((Action) -> AddArgument());
    }
}
