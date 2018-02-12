package main.utilities;

import food.Dish;
import food.SimpleIngredient;
import food.dishes.*;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class RecipeReader extends InputReader{
    private static int fileCounter;
    final private static String FILE_DIRECTORY;
    final private static int MAX_FILES;
    final private static int EXCEPTION_LIMIT;
    final private static String[] DISH_TYPES = {"[śŚsS]niadanie", "II [śŚsS]niadanie", "obiad", "podwieczorek", "kolacja"};
    private ArrayList<Dish> loadedDishes;


    static
    {
        fileCounter = 0;
        FILE_DIRECTORY = "db/recipes/";
        MAX_FILES = 50;             //  ile kolejnych plików próbuje wczytać
        EXCEPTION_LIMIT = 5;
    }

    public RecipeReader(ArrayList<Dish> dishes)
    {
        loadedDishes = dishes;
    }

    public void loadAllRecipes() throws BadFormattedFileException
    {
        int exceptionCounter = 0;
        String[] input;
        final String UTF8_BOM = "\uFEFF";
        while (fileCounter < MAX_FILES && exceptionCounter < EXCEPTION_LIMIT)
        {
            input = new String[200];
            File recipeFile = new File(FILE_DIRECTORY + ++fileCounter + ".txt");
            try (Scanner scan = new Scanner(new FileInputStream(recipeFile)))
            {
                exceptionCounter = 0;
                int i = 0;
                while (scan.hasNextLine())
                {
                    input[i] = scan.nextLine();
                    input[i] = removeUTF8BOM(input[i]);
                    i++;
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

    private void analyzeInput(String[] input) throws BadFormattedFileException
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
                    Dish newDish = createDish(lc, input, dishNr);
                    if (newDish != null)
                        loadedDishes.add(newDish);
                    i = lc.count;
                }
        }
        System.out.println(lc.count + " lines was found in the file.");
    }

    private Dish createDish(LineCounter lc, String[] input, int dishNr) throws BadFormattedFileException
    {
        Dish newDish = null;
        while(input[lc.count].equals(""))
            lc.count++; // pomijamy pustą linię
        String[] nameCal = getNameCal(lc, input);
        String dishName = nameCal[0];
        String dishCal = nameCal[1];
        HashSet<SimpleIngredient> ingredients = getIngredients(lc, input);
        String instructionContent = getInstruction(lc, input, dishNr);

        switch (dishNr)
        {
            case 0:
                newDish = new Breakfast(dishName, dishCal, instructionContent);
                break;
            case 1:
                newDish = new Lunch(dishName, dishCal, instructionContent);
                break;
            case 2:
                newDish = new Dinner(dishName, dishCal, instructionContent);
                break;
            case 3:
                newDish = new AfternoonSnack(dishName, dishCal, instructionContent);
                break;
            case 4:
                newDish = new Supper(dishName, dishCal, instructionContent);
                break;
        }
        if(newDish != null)
            newDish.setIngredients(ingredients);
        for (Dish dish : loadedDishes)
            if(dish.equals(newDish))
                return null;
        return newDish;
    }

    private String[] getNameCal(LineCounter lc, String[] input)
    {
        String[] nameCal = new String[2];
        String temp = input[lc.count];
        int i = 0;
        for (char ch : temp.toCharArray())
        {
            if (ch >= 48 && ch <= 57)
            {
                nameCal[0] = temp.substring(0, i);
                nameCal[1] = temp.substring(i);
                break;
            }
            i++;
        }
        lc.count++;
        return nameCal;
    }

    private HashSet<SimpleIngredient> getIngredients(LineCounter lc, String[] input) throws BadFormattedFileException
    {
        HashSet<SimpleIngredient> ingredients = new HashSet<>();
        while(!(input[lc.count].matches("[ ]*[A-Z](.)+[.]")) && lc.count < input.length)
        {
            if(input[lc.count].matches("(.)*[-–](.)*") && input[lc.count].length() < 50)
            {
                String[] ingredient_parts = input[lc.count].split("[-–]");
                SimpleIngredient newIngredient = new SimpleIngredient(ingredient_parts[0], ingredient_parts[1]);
                ingredients.add(newIngredient);
            }
            lc.count++;
        }
        if (lc.count == input.length)
            throw new BadFormattedFileException();
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

        private LineCounter()
        {
            count = 0;
        }
    }

    public class BadFormattedFileException extends IOException
    {
        public BadFormattedFileException()
        {
            super();
            printStackTrace();
        }
    }
}
