package food.dishes;

import food.Dish;

import java.io.File;

public class Dinner extends Dish {

    public Dinner(File instructionFile)
    {
        super(instructionFile);
    }

    public Dinner(String instructionContent)
    {
        super(instructionContent);
    }
}
