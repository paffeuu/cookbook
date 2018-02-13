package main.gui;

import food.Dish;
import food.SimpleIngredient;
import food.dishes.*;
import food.substances_extended.Substantial;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.utilities.SubstantialsReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.CookBookApp.observableDishes;

public class MainWindowController
{
    @FXML   TableView<Dish> dishTable;
    @FXML   TableColumn<Dish, String> dishNameCol;
    @FXML   TableColumn<Dish, Integer> calCol;
    @FXML   TableColumn<Dish, String> dishTypeCol;

    @FXML   VBox ingredientsVBox;
    @FXML   VBox dishesTypesVBox;


    private ObservableList<Dish> typeFilteredDishes;
    private ObservableList<Dish> ingrFilteredDishes;

    private HashSet<Substantial> markedIngrSet;
    private HashSet<Class> markedTypesSet;
    private ArrayList<Substantial> substantialsList;

    private HashMap<Substantial, CheckBox> filterIngrCheckBoxes;
    private HashMap<Class, CheckBox> filterDishTypesCheckboxes;

    public void initialize()
    {
        fillColumns();
        substantialsList = getSubstantialsList();
        createFilterCheckBoxes();
        createDishesTypesCheckBoxes();
    }

    private void fillColumns()
    {
        dishTable.setItems(observableDishes);
        dishNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        calCol.setCellValueFactory(cellData -> cellData.getValue().caloriesProperty().asObject());
        dishTypeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        dishTable.setOnKeyPressed(event -> {
            Dish dish = dishTable.getSelectionModel().getSelectedItem();
            if (event.getCode().equals(KeyCode.ENTER) && dish != null)
                openDetailsWindow(dish);
        });
        dishTable.setOnMouseClicked(event -> {
            Dish dish = dishTable.getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2 && dish != null)
                openDetailsWindow(dish);
        });
    }

    public ArrayList<Substantial> getSubstantialsList() {
        SubstantialsReader substantialsReader = new SubstantialsReader();
        return substantialsReader.getLoadedSubstantials();
    }

    private void createFilterCheckBoxes()
    {
        filterIngrCheckBoxes = new HashMap<>();
        markedIngrSet = new HashSet<>();
        for (Substantial substantial : substantialsList)
        {
            CheckBox newCheckBox = new CheckBox(substantial.getName());
            filterIngrCheckBoxes.put(substantial, newCheckBox);
            newCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue)
                        markedIngrSet.add(substantial);
                    else if(oldValue)
                        markedIngrSet.remove(substantial);
                    prepareIngrFilteredList();
                    if (markedIngrSet.isEmpty())
                        dishTable.setItems(typeFilteredDishes);

            });
            ingredientsVBox.getChildren().add(newCheckBox);
        }
    }

    private void createDishesTypesCheckBoxes()
    {
        typeFilteredDishes = observableDishes;
        markedTypesSet = new HashSet<>();

        filterDishTypesCheckboxes = new HashMap<>();
        LinkedHashMap<String, Class> dishTypesPL = new LinkedHashMap<>();

        dishTypesPL.put(Breakfast.plType, Breakfast.class);
        dishTypesPL.put(Lunch.plType, Lunch.class);
        dishTypesPL.put(Dinner.plType, Dinner.class);
        dishTypesPL.put(AfternoonSnack.plType, AfternoonSnack.class);
        dishTypesPL.put(Supper.plType, Supper.class);

        dishTypesPL.forEach( (key, value) -> {
            CheckBox newCheckbox = new CheckBox(key);
            newCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue)
                    markedTypesSet.add(value);
                else if(oldValue)
                    markedTypesSet.remove(value);
                if (markedTypesSet.isEmpty())
                {
                    typeFilteredDishes = observableDishes;
                    prepareIngrFilteredList();
                }
                else
                {
                    prepareTypeFilteredList();
                }
                if (markedIngrSet.isEmpty())
                    dishTable.setItems(typeFilteredDishes);

            });

            dishesTypesVBox.getChildren().add(newCheckbox);
        });
    }

    private void prepareIngrFilteredList()
    {
        ingrFilteredDishes = FXCollections.observableArrayList();
        dishTable.setItems(ingrFilteredDishes);
        for (Dish dish : typeFilteredDishes)
        {
            boolean anySubstantialNotFound = true;
            for (Substantial substantial : markedIngrSet)
            {
                anySubstantialNotFound = true;
                for (SimpleIngredient ingredient : dish.getIngredients())
                {
                    Matcher matcher = Pattern.compile("(.)*" + substantial.getName() + "(.)*").matcher(ingredient.getSubstance());
                    if (matcher.find())
                    {
                        anySubstantialNotFound = false;
                        break;
                    }
                }
                if(anySubstantialNotFound)
                    break;
            }
            if(!anySubstantialNotFound)
                ingrFilteredDishes.add(dish);
        }
    }

    private void prepareTypeFilteredList()
    {
        typeFilteredDishes = FXCollections.observableArrayList();
        for (Dish dish : observableDishes)
        {
            for (Class cl : markedTypesSet)
            {
                if (dish.getClass().equals(cl))
                    typeFilteredDishes.add(dish);
            }
        }
        prepareIngrFilteredList();
    }

    // other windows
    private void openDetailsWindow(Dish dish)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dish_details.fxml"));
            Parent root = fxmlLoader.load();
            Stage detailsStage = new Stage();
            detailsStage.setTitle("Książka kucharska");
            detailsStage.setScene(new Scene(root, 650, 580));
            detailsStage.show();

            DishDetailsController dishDetailsController = fxmlLoader.getController();
            dishDetailsController.showDetails(dish);
        } catch (IOException ioe)
        {ioe.printStackTrace();}
    }
}
