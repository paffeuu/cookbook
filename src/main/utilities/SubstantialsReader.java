package main.utilities;

import food.substances_extended.Substantial;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
        try (BufferedReader scan = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_DIRECTORY), StandardCharsets.UTF_8))) // why plik txt jest pusty???????
        {
            String line;
            while ((line = scan.readLine()) != null)
            {
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
