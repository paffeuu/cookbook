package food;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashSet;

public abstract class Dish
{
    private String name;
    private String fileName;
    private int calories;
    private HashSet<SimpleIngredient> ingredients;
    private Instruction instruction;
    private int number;
    private static int dishCounter;

    protected Dish(String name, String cal, String instructionContent)
    {
        setName(name);
        setFileName();
        setCalories(cal);
        ingredients = new HashSet<>();
        instruction = new Instruction(instructionContent);
        number = ++dishCounter;
    }

    static
    {
        dishCounter = 0;
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public HashSet<SimpleIngredient> getIngredients() {
        return ingredients;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public int getCalories() {
        return calories;
    }

    public StringProperty nameProperty()
    {
        return new SimpleStringProperty(name);
    }

    public StringProperty typeProperty()
    {
        return null;
    }

    public IntegerProperty caloriesProperty()
    {
        return new SimpleIntegerProperty(calories);
    }

    public void setName(String name) {
        String temp;
        temp = name;
        while(temp.charAt(0) == ' ')
            temp = temp.substring(1);       // usuwa spacje na początku
        if(temp.charAt(temp.length()-1) == '.')
            temp = temp.substring(0, temp.length()-1);      // usuwa kropkę na końcu
        this.name = temp;
    }

    private void setFileName() {
        String fileName = name.replace(" ", "");
        if (fileName.length() > 20)
            fileName = fileName.substring(0, 20);
        this.fileName = fileName;
    }

    private void setCalories(String calories) {
        String calNr = null;
        int i = 0;
        for (char ch : calories.toCharArray())
        {
            if (ch < 48 || ch > 57)
            {
                calNr = calories.substring(0, i);
                break;
            }
            i++;
        }
        int cal;
        if (calNr != null)
        {
            cal = Integer.parseInt(calNr);
            this.calories = cal;
        }
    }

    public void setIngredients(HashSet<SimpleIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public void setNumber(int number) {
        if (number > 0)
            this.number = number;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return (name.equals(((Dish) obj).getName()));
    }
}
