package food.dishes;

import food.Dish;

import java.io.File;

public class Supper extends Dish {

    public Supper(File instructionFile)
    {
        super(instructionFile);
    }

    public Supper(String instructionContent)
    {
        super(instructionContent);
    }
}
