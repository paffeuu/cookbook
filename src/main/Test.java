package main;

import food.Dish;
import main.utilities.RecipeReader;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static Logger logger = Logger.getLogger("RecipeReader");

    public static void main(String[] args)
    {
        /*ArrayList<Dish> dishes = new ArrayList<>();
        RecipeReader rr = new RecipeReader(dishes);
        rr.loadAllRecipes();*/

        Matcher matcher = Pattern.compile("(.)*" + "indyk" + "(.)*").matcher("mięso z podudzia indyka, bez skóry - [120g]");
        if (matcher.find())
        {
            logger.info("found!");
        }
    }
}
