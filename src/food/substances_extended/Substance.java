package food.substances_extended;

abstract public class Substance {
    private String name;

    protected Substance(String name)
    {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
