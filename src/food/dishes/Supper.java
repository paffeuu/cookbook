package food.dishes;

import food.Dish;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Supper extends Dish {
    final public static String plType;

    public Supper(String name, String cal, String instructionContent)
    {
        super(name, cal, instructionContent);
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
