package main.utilities;

public abstract class InputReader {
    private static final String UTF8_BOM;

    static
    {
        UTF8_BOM = "\uFEFF";
    }

    protected String removeUTF8BOM(String s)
    {
        if (s.startsWith(UTF8_BOM))
        {
            s = s.substring(1);
        }
        return s;
    }
}
