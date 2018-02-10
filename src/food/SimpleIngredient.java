package food;

import food.substances_extended.Additive;
import main.utilities.AdditivesReader;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleIngredient
{
    private String substance;
    private String amount;
    private boolean additive;
    private static HashSet<Additive> additivesSet;

    public SimpleIngredient(String substance, String amount)
    {
        this.amount = amount;
        setSubstance(substance);
    }

    static
    {
        additivesSet = new HashSet<>();
        AdditivesReader reader = new AdditivesReader();
        additivesSet = reader.getLoadedAdditives();
    }

    @Override
    public String toString() {
        return substance + " - " + amount;
    }

    public String getAmount() {
        return amount;
    }

    public String getSubstance() {
        return substance;
    }

    public boolean isAdditive() {
        return additive;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setSubstance(String substance) {
        this.substance = substance;

        String regex;
        Pattern p;
        Matcher m;

        for (Additive addit : additivesSet)
        {
            regex = "(?i)" + addit.getName();
            p = Pattern.compile(regex);
            m = p.matcher(substance);
            if (m.find())
            {
                additive = true;
                return;
            }

        }
    }

    public void setAdditive(boolean additive) {
        this.additive = additive;
    }
}
