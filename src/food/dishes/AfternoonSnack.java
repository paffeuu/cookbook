package food.dishes;

import food.Dish;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AfternoonSnack extends Dish {
    final private static String plType;

    public AfternoonSnack(String name, String instructionContent)
    {
        super(name, instructionContent);
    }

    static
    {
        plType = "podwieczorek";
    }

    @Override
    public StringProperty typeProperty() {
        return new SimpleStringProperty(plType);
    }
}
