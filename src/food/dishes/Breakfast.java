package food.dishes;

import food.Dish;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Breakfast extends Dish {
    final public static String plType;

    public Breakfast(String name, String cal, String instructionContent)
    {
        super(name, cal, instructionContent);
    }

    static
    {
        plType = "śniadanie";
    }

    @Override
    public StringProperty typeProperty() {
        return new SimpleStringProperty(plType);
    }
}
