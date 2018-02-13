package main.gui;

import food.Dish;
import food.SimpleIngredient;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import main.utilities.PDFCreator;
import main.utilities.PDFPrinter;

public class DishDetailsController {
    private Dish dish;

    @FXML   Label dishNameLabel;
    @FXML   ListView<SimpleIngredient> dishIngredientsListView;
    @FXML   Label dishInstructionLabel;
    @FXML   Button printButton;

    public void initialize()
    {

    }

    public void showDetails(Dish dish)
    {
        this.dish = dish;
        dishNameLabel.setText(dish.getName());
        dishIngredientsListView.setItems(FXCollections.observableArrayList(dish.getIngredients()));
        dishInstructionLabel.setText(dish.getInstruction().getInstructionContent());
        dishInstructionLabel.setWrapText(true);

        printButton.setOnAction(event -> printDish(dish));
    }

    private void printDish(Dish dish)
    {
        PDFCreator pdfCreator = PDFCreator.create(dish.getFileName());
        String pdfDest = pdfCreator.preparePdf(dish);
        PDFPrinter pdfPrinter;
        if (pdfCreator.isProperlyCreated())
        {
            Alert properlyCreated = new Alert(Alert.AlertType.INFORMATION);
            properlyCreated.setTitle("Sukces!");
            properlyCreated.setHeaderText("");
            properlyCreated.setContentText("Plik PDF z przepisem został pomyślnie utworzony!\n" + pdfDest + "\nDrukowanie...");
            properlyCreated.show();

            pdfPrinter = new PDFPrinter(pdfDest);
            pdfPrinter.print();
        }
        else
        {
            Alert notCreated = new Alert(Alert.AlertType.ERROR);
            notCreated.setTitle("Błąd!");
            notCreated.setHeaderText("");
            notCreated.setContentText("Wystąpił nieoczekiwany błąd! Plik PDF z przepisem nie został utworzony!");
            notCreated.show();
        }
    }
}
