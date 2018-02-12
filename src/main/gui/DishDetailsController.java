package main.gui;

import food.Dish;
import food.SimpleIngredient;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class DishDetailsController {
    private Dish dish;

    @FXML   Label dishNameLabel;
    @FXML   ListView<SimpleIngredient> dishIngredientsListView;
    @FXML   Label dishInstructionLabel;

    public void initialize()
    {

    }

    public void showDetails(Dish dish)
    {
        this.dish = dish;
        dishNameLabel.setText(dish.getName());
        fillIngredients();
        dishInstructionLabel.setText(dish.getInstruction().getInstructionContent());
        dishInstructionLabel.setWrapText(true);
    }

    private void fillIngredients()
    {
        dishIngredientsListView.setItems(FXCollections.observableArrayList(dish.getIngredients()));
        
    }
}
