package food.dishes;

import food.Dish;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Breakfast extends Dish {
    final private static String plType;

    public Breakfast(String name, String instructionContent)
    {
        super(name, instructionContent);
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
