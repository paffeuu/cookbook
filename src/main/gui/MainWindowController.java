package main.gui;

import food.Dish;
import food.SimpleIngredient;
import food.substances_extended.Substantial;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import main.utilities.SubstantialsReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.CookBookApp.observableDishes;

public class MainWindowController
{
    @FXML
    TableView<Dish> dishTable;
    @FXML
    TableColumn<Dish, String> dishNameCol;
    @FXML
    TableColumn<Dish, String> dishTypeCol;

    @FXML
    VBox checkBoxesVBox;

    ObservableList<Dish> filteredDishes;
    HashSet<Substantial> markedSet;

    ArrayList<Substantial> substantialsList;
    HashMap<Substantial, CheckBox> filterCheckBoxes;

    public void initialize()
    {
        fillColumns();
        substantialsList = getSubstantialsList();
        createFilterCheckBoxes();
    }

    private void fillColumns()
    {
        dishTable.setItems(observableDishes);
        dishNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        dishTypeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

    }

    public ArrayList<Substantial> getSubstantialsList() {
        SubstantialsReader substantialsReader = new SubstantialsReader();
        return substantialsReader.getLoadedSubstantials();
    }

    private void createFilterCheckBoxes()
    {
        filterCheckBoxes = new HashMap<>();
        markedSet = new HashSet<>();
        for (Substantial substantial : substantialsList)
        {
            CheckBox newCheckBox = new CheckBox(substantial.getName());
            filterCheckBoxes.put(substantial, newCheckBox);
            newCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue)
                        markedSet.add(substantial);
                    else if(oldValue)
                        markedSet.remove(substantial);
                    prepareFilteredList();
                    if (markedSet.isEmpty())
                        dishTable.setItems(observableDishes);

            });
            checkBoxesVBox.getChildren().add(newCheckBox);
        }

    }

    private void prepareFilteredList()
    {
        filteredDishes = FXCollections.observableArrayList();
        dishTable.setItems(filteredDishes);
        for (Dish dish : observableDishes)
        {
            boolean anySubstantialNotFound = true;
            for (Substantial substantial : markedSet)
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
                filteredDishes.add(dish);
        }
    }
}
