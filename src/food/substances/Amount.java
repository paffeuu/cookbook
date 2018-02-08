package food.substances;

public class Amount {
    private double size;
    private String unit;

    public Amount()
    {
        this.size = 0.0;
        this.unit = "g";
    }

    public Amount(double size, String unit)
    {
        this.size = size;
        if (unit != null)
            this.unit = unit;
        else
            this.unit = "g";
    }

    public double getSize() {
        return size;
    }

    public String getUnit() {
        return unit;
    }

    public void setSize(double size) {
        if (size > 0.0)
            this.size = size;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
