package padej.soup.config;

public final class ColorValue implements ConfigValue<Integer> {
    private final String name;
    private int value;

    public ColorValue(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override public String getName() { return name; }
    @Override public Integer get() { return value; }
    @Override public void set(Integer value) { this.value = value; }
}