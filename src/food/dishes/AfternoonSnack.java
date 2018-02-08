package food.dishes;

import food.Dish;

import java.io.File;

public class AfternoonSnack extends Dish {

    public AfternoonSnack(File instructionFile)
    {
        super(instructionFile);
    }

    public AfternoonSnack(String instructionContent)
    {
        super(instructionContent);
    }
}
