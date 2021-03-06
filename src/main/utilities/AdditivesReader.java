package main.utilities;

import food.substances_extended.Additive;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class AdditivesReader extends InputReader{
    final private static String FILE_DIRECTORY;
    private HashSet<Additive> loadedAdditives;

    static
    {
        FILE_DIRECTORY = "db/ingredients/additives.txt";
    }

    public AdditivesReader()
    {
        loadedAdditives = new HashSet<>();
        try (Scanner scan = new Scanner(new File(FILE_DIRECTORY)))
        {
            while (scan.hasNextLine())
            {
                String line = scan.nextLine();
                line = removeUTF8BOM(line);
                Additive newAdditive = new Additive(line);
                loadedAdditives.add(newAdditive);
            }
        }
        catch (IOException ioe)
        {ioe.printStackTrace();}
    }

    public HashSet<Additive> getLoadedAdditives() {
        return loadedAdditives;
    }
}
