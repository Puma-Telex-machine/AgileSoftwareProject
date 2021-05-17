import frontend.BaseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import viewmodel.frontend.BaseController;

import java.awt.event.KeyEvent;
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setRoot(this);
        BaseController base = new BaseController();
        Scene scene = new Scene(base, 1440, 900);
       
        stage.setTitle("UML 2000");
        stage.setScene(scene);
        stage.show();

        //Sends keypresses to base
        scene.setOnKeyPressed(e -> base.onKeyPressed(e));
        scene.setOnKeyReleased(e -> base.onKeyReleased(e));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}