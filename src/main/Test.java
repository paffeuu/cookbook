package main;

import food.Dish;
import main.utilities.RecipeReader;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Test {
    public static Logger logger = Logger.getLogger("RecipeReader");

    public static void main(String[] args)
    {
        ArrayList<Dish> dishes = new ArrayList<>();
        RecipeReader rr = new RecipeReader(dishes);
        rr.loadAllRecipes();
    }
}
