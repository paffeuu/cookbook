package food.dishes;

import food.Dish;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Lunch extends Dish {
    final public static String plType;

    public Lunch(String name, String cal, String instructionContent)
    {
        super(name, cal, instructionContent);
    }

    static
    {
        plType = "drugie śniadanie";
    }

    @Override
    public StringProperty typeProperty() {
        return new SimpleStringProperty(plType);
    }
}
