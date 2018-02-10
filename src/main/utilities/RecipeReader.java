package main.utilities;

import food.Dish;
import food.SimpleIngredient;
import food.dishes.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RecipeReader {
    private static int fileCounter;
    final private static String FILE_DIRECTORY;
    final private static int MAX_FILES;
    final private static String[] DISH_TYPES = {"sniadanie", "II sniadanie", "obiad", "podwieczorek", "kolacja"};
    private ArrayList<Dish> loadedDishes;


    static
    {
        fileCounter = 0;
        FILE_DIRECTORY = "db/recipes/";
        MAX_FILES = 10;             //  ile kolejnych plików próbuje wczytać
    }

    public RecipeReader(ArrayList<Dish> dishes)
    {
        loadedDishes = dishes;
    }

    public void loadAllRecipes()
    {
        int exceptionCounter = 0;
        String[] input;
        while (fileCounter < MAX_FILES && exceptionCounter < 5)
        {
            input = new String[200];
            File recipeFile = new File(FILE_DIRECTORY + ++fileCounter + ".txt");
            try (Scanner scan = new Scanner(recipeFile))
            {
                exceptionCounter = 0;
                int i = 0;
                while (scan.hasNextLine())
                {
                    input[i++] = scan.nextLine();
                }
                input = Arrays.copyOf(input, i);
                System.out.println("File " + fileCounter + ".txt was successfully found and read!");
            }
            catch (IOException ioe)
            {
                System.out.println("File " + fileCounter + ".txt wasn't found!");
                input = null;
                exceptionCounter++;
            }

            if (input != null)
                analyzeInput(input);
        }
    }

    private void analyzeInput(String[] input)
    {
        int dishNr = -1;
        LineCounter lc = new LineCounter();

        for (int i = 0; i < input.length; i++)
        {
            lc.count = i;
            for (int j = 0; j < 5; j++)
                if (input[i].matches("(?i)(.)*" + DISH_TYPES[j] + "(.)*"))
                {
                    dishNr = j;
                    lc.count++;
                    loadedDishes.add(createDish(lc, input, dishNr));
                    i = lc.count;
                }
        }
    }

    private Dish createDish(LineCounter lc, String[] input, int dishNr)
    {
        Dish newDish = null;
        while(input[lc.count].equals(""))
            lc.count++; // pomijamy pustą linię
        String dishName = input[lc.count++];
        HashSet<SimpleIngredient> ingredients = getIngredients(lc, input);
        String instructionContent = getInstruction(lc, input, dishNr);
        switch (dishNr)
        {
            case 0:
                newDish = new Breakfast(dishName, instructionContent);
                break;
            case 1:
                newDish = new Lunch(dishName, instructionContent);
                break;
            case 2:
                newDish = new Dinner(dishName, instructionContent);
                break;
            case 3:
                newDish = new AfternoonSnack(dishName, instructionContent);
                break;
            case 4:
                newDish = new Supper(dishName, instructionContent);
                break;
        }
        if(newDish != null)
            newDish.setIngredients(ingredients);

        return newDish;
    }

    private HashSet<SimpleIngredient> getIngredients(LineCounter lc, String[] input)
    {
        HashSet<SimpleIngredient> ingredients = new HashSet<>();
        while(!input[lc.count].matches("[ ]*[A-Z](.)+") && lc.count < input.length)
        {
            if(input[lc.count].matches("(.)*[-–](.)*") && input[lc.count].length() < 50)
            {
                String[] ingredient_parts = input[lc.count].split("[-–]");
                SimpleIngredient newIngredient = new SimpleIngredient(ingredient_parts[0], ingredient_parts[1]);
                ingredients.add(newIngredient);
            }
            lc.count++;
        }
    //    ingredients.forEach(ingredient -> System.out.println(ingredient.toString()));
        return ingredients;
    }

    private String getInstruction(LineCounter lc, String[] input, int dishNr) {
        String instructionContent = "";
        while (lc.count < input.length && !input[lc.count].matches("(?i)(.)*(" + DISH_TYPES[(dishNr + 1) % 5] + ")(.)*")) {
            instructionContent += input[lc.count];
            lc.count++;
        }
        return instructionContent;
    }

    private class LineCounter
    {
        private int count;

        public LineCounter()
        {
            count = 0;
        }
    }
}
