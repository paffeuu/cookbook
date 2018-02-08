package food.substances;

import com.sun.istack.internal.NotNull;
import food.Substance;

public class Ingredient {
    private Substance substance;
    private Amount amount;

    public Ingredient(@NotNull Substance substance, @NotNull Amount amount)
    {
        this.substance = substance;
        this.amount = amount;
    }

    public Amount getAmount() {
        return amount;
    }

    public Substance getSubstance() {
        return substance;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public void setSubstance(Substance substance) {
        this.substance = substance;
    }
}
