package food.dishes;

import food.Dish;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Dinner extends Dish {
    final private static String plType;

    public Dinner(String name, String instructionContent)
    {
        super(name, instructionContent);
    }

    static
    {
        plType = "obiad";
    }

    @Override
    public StringProperty typeProperty() {
        return new SimpleStringProperty(plType);
    }

}
