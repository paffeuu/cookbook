package food.dishes;

import food.Dish;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Supper extends Dish {
    final private static String plType;

    public Supper(String name, String instructionContent)
    {
        super(name, instructionContent);
    }

    static
    {
        plType = "kolacja";
    }

    @Override
    public StringProperty typeProperty() {
        return new SimpleStringProperty(plType);
    }
}
