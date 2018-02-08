package food.dishes;

import food.Dish;

import java.io.File;

public class Breakfast extends Dish {

    public Breakfast(File instructionFile)
    {
        super(instructionFile);
    }

    public Breakfast(String instructionContent)
    {
        super(instructionContent);
    }
}
