package main.gui;

import food.Dish;
import food.SimpleIngredient;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.Test;
import main.utilities.PDFCreator;
import main.utilities.PDFPrinter;

public class DishDetailsController {
    private Dish dish;

    private Stage stage;

    @FXML   Label dishNameLabel;
    @FXML   ListView<SimpleIngredient> dishIngredientsListView;
    @FXML   Label dishInstructionLabel;

    @FXML   Button printButton;
    @FXML   Button okButton;

    public void initialize()
    {
    }

    public void showDetails(Dish dish)
    {
        this.stage = (Stage)dishNameLabel.getScene().getWindow();

        this.dish = dish;
        dishNameLabel.setText(dish.getName());
        dishIngredientsListView.setItems(FXCollections.observableArrayList(dish.getIngredients()));
        dishInstructionLabel.setText(dish.getInstruction().getInstructionContent());
        dishInstructionLabel.setWrapText(true);

        printButton.setOnAction(event -> printDish(dish));
        okButton.setOnAction(event -> stage.close());

        stage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER)
                stage.close();
        });

    }

    private void printDish(Dish dish)
    {
        PDFCreator pdfCreator = PDFCreator.create(dish.getFileName());
        String pdfDest = pdfCreator.preparePdf(dish);
        PDFPrinter pdfPrinter;
        if (pdfCreator.isProperlyCreated())
        {
            pdfPrinter = new PDFPrinter(pdfDest);
            pdfPrinter.print();
        }
    }
}
