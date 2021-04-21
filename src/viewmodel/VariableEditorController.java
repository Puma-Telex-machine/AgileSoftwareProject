package viewmodel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class VariableEditorController {


    @FXML
    private TextField nameField; //The text field for the name of the variable

    @FXML
    private ComboBox accessComboBox; //The combo box for choosing the access of the variable

    @FXML
    private TextField typeField; //The text field for the type of the variable

    @FXML
    private void deleteVariable() //This method is called when the player wants to delete the variable
    {

    }

    @FXML
    private void confirmVariable() //This method is called when the player wants to confirm and stop editing this variable
    {

    }
}
