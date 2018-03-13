package main.gui;

import food.Dish;
import food.SimpleIngredient;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.utilities.PDFCreator;
import main.utilities.PDFPrinter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingListController {
    @FXML   Button okButton;
    @FXML   Button printButton;

    @FXML   Accordion dishesAccordion;

    private HashMap<Dish, TitledPane> dishTitledPaneMap;

    private ArrayList<Dish> markedDishes;
    private ArrayList<SimpleIngredient> markedIngredients;


    public void initialize()
    {
        markedDishes = new ArrayList<>();
        markedIngredients = new ArrayList<>();
        dishTitledPaneMap = new HashMap<>();
        setButtons();
    }

    private void setButtons()
    {
        // okButton set in MainWindowsController
        printButton.setOnAction(event -> {
            Stage stage = (Stage) printButton.getScene().getWindow();
            stage.setAlwaysOnTop(false);
            printShoppingList(markedIngredients, markedDishes);

        });
    }

    public void addDish(Dish dish)
    {
        markedDishes.add(dish);

        TitledPane dishTitledPane = new TitledPane();
        dishTitledPane.setText(dish.getName());

        VBox ingredientsVBox = new VBox();
        dishTitledPane.setContent(ingredientsVBox);
        for (SimpleIngredient ingredient : dish.getIngredients())
        {
            final SimpleIngredient simpleIngredient = ingredient;
            CheckBox ingredientCheckBox = new CheckBox(simpleIngredient.toString());
            ingredientsVBox.getChildren().add(ingredientCheckBox);
            ingredientCheckBox.selectedProperty().addListener(observable -> {
                if (ingredientCheckBox.isSelected())
                    markedIngredients.add(simpleIngredient);
                else
                    markedIngredients.remove(simpleIngredient);
            });
        }

        dishTitledPaneMap.put(dish, dishTitledPane);
        dishesAccordion.getPanes().add(dishTitledPane);
    }

    private void printShoppingList(ArrayList<SimpleIngredient> markedIngredients, ArrayList<Dish> markedDishes)
    {
        PDFCreator pdfCreator = PDFCreator.create("ListaZakupow" + LocalDate.now().toString());
        String pdfDest = pdfCreator.preparePdf(markedIngredients, markedDishes);
        PDFPrinter pdfPrinter;
        if (pdfCreator.isProperlyCreated())
        {
            pdfPrinter = new PDFPrinter(pdfDest);
            Thread printThread = new Thread(pdfPrinter);
            printThread.start();
            //pdfPrinter.print();
        }
    }


}
