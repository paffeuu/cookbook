package food.dishes;

import food.Dish;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AfternoonSnack extends Dish {
    final public static String plType;

    public AfternoonSnack(String name, String cal, String instructionContent)
    {
        super(name, cal, instructionContent);
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
