package main;

import food.Dish;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.utilities.RecipeReader;

import java.util.ArrayList;

public class CookBookApp extends Application {
    public static ObservableList<Dish> observableDishes;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("gui/main_window.fxml"));
        primaryStage.setTitle("Książka kucharska");
        primaryStage.setScene(new Scene(root, 750, 580));
        primaryStage.show();
    }


    public static void main(String[] args) {
        ArrayList<Dish> dishes = new ArrayList<>();
        RecipeReader rr = new RecipeReader(dishes);
        rr.loadAllRecipes();
        observableDishes = FXCollections.observableArrayList(dishes);

        launch(args);
    }
}
