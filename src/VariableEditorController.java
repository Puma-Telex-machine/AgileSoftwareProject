import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
//import model.VariableData;
import model.VariableData;
import model.facades.BoxFacade;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class VariableEditorController extends AnchorPane {


    public VariableEditorController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("view/VariableEditor.fxml")));

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

    //@FXML
    //private ComboBox accessComboBox; //The combo box for choosing the access of the variable

    @FXML
    private TextField typeField; //The text field for the type of the variable

    BoxFacade box;

    @FXML
    private void deleteVariable() //This method is called when the player wants to delete the variable
    {
        box.DeleteVariable(nameField.getText());
    }

    @FXML
    private void confirmVariable() //This method is called when the player wants to confirm and stop editing this variable
    {
        VariableData data = new VariableData();
        data.name = nameField.getText();
        box.EditVariable(data);
    }

    public void EditVariable(BoxFacade box)
    {
        EditVariable(new VariableData(), box);
    }

    public void EditVariable(VariableData variableData, BoxFacade box)
    {
        this.box = box;
    }

}
