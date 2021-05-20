package frontend.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.boxes.Visibility;
import model.facades.AttributeFacade;
import model.boxes.BoxFacade;

import java.io.IOException;

public class VariableEditorController extends AnchorPane {


    public VariableEditorController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("..//view/VariableEditor.fxml")));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private TextField nameField; //The text field for the name of the variable

    @FXML
    private ComboBox accessComboBox; //The combo box for choosing the access of the variable

    @FXML
    private TextField typeField; //The text field for the type of the variable

    BoxFacade box;
    AttributeFacade variable;

    @FXML
    public void deleteVariable() //This method is called when the player wants to delete the variable
    {
        box.deleteAttribute(variable);
        this.setVisible(false);
    }

    @FXML
    private void confirmVariable() //This method is called when the player wants to confirm and stop editing this variable
    {
        variable.confirmAttribute();
        variable.setName(nameField.getText());

        Visibility visibility = (Visibility) accessComboBox.getValue();
        variable.setVisibility(visibility);

        variable.setType(typeField.getText());

        variable = null;
        this.setVisible(false);
    }

    public void EditVariable(BoxFacade box)
    {
        EditVariable(box.addAttribute(), box);
    }

    public void EditVariable(AttributeFacade variableData, BoxFacade box)
    {
        this.box = box;
        variable = variableData;
        nameField.setText(variableData.getName());
        typeField.setText(variableData.getType());

        //Sets the options for the accessibility combo box
        accessComboBox.getItems().setAll(Visibility.values());

        //Sets the current visibility
        accessComboBox.getSelectionModel().select(variableData.getVisibility());
    }

}
