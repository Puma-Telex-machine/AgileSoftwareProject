import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/Base.fxml")));
        Scene scene = new Scene(root, 800, 500);
        fxmlLoader.setRoot(this);
        //fxmlLoader.setController(new BaseController());

        stage.setTitle("UML 2000");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
