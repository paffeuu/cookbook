package main.gui;

import food.Dish;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import static main.CookBookApp.observableDishes;

public class MainWindowController
{
    @FXML
    TableView<Dish> dishTable;
    @FXML
    TableColumn<Dish, String> dishNameCol;
    @FXML
    TableColumn<Dish, String> dishTypeCol;

    public void initialize()
    {
        fillColumns();
    }

    private void fillColumns()
    {
        dishTable.setItems(observableDishes);
        dishNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        dishTypeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

    }
}
