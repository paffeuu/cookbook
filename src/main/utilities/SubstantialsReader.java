package main.utilities;

import food.substances_extended.Substantial;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class SubstantialsReader extends InputReader{
    final private static String FILE_DIRECTORY;
    private ArrayList<Substantial> loadedSubstantials;

    static
    {
        FILE_DIRECTORY = "db/ingredients/substantials.txt";
    }

    public SubstantialsReader()
    {
        loadedSubstantials = new ArrayList<>();
        try (Scanner scan = new Scanner(new File(FILE_DIRECTORY))) // why plik txt jest pusty???????
        {
            while (scan.hasNextLine())
            {
                String line = scan.nextLine();
                line = removeUTF8BOM(line);
                Substantial newSubstantial = new Substantial(line);
                loadedSubstantials.add(newSubstantial);
            }
        }
        catch (IOException ioe)
        {ioe.printStackTrace();}
    }

    public ArrayList<Substantial> getLoadedSubstantials() {
        return loadedSubstantials;
    }
}
