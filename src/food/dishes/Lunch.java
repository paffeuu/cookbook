package food.dishes;

import food.Dish;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Lunch extends Dish {
    final private static String plType;

    public Lunch(String name, String instructionContent)
    {
        super(name, instructionContent);
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
