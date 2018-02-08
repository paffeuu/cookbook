package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CookBookApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main_window.fxml"));
        primaryStage.setTitle("Książka kucharska");
        primaryStage.setScene(new Scene(root, 750, 580));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
