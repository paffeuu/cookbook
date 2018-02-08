package food.dishes;

import food.Dish;

import java.io.File;

public class Lunch extends Dish {

    public Lunch(File instructionFile)
    {
        super(instructionFile);
    }

    public Lunch(String instructionContent)
    {
        super(instructionContent);
    }
}
