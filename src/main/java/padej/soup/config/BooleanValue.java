package padej.soup.config;

public final class BooleanValue implements ConfigValue<Boolean> {
    private final String name;
    private boolean value;

    public BooleanValue(String name, boolean value) {
        this.name = name;
        this.value = value;
    }

    @Override public String getName() { return name; }
    @Override public Boolean get() { return value; }
    @Override public void set(Boolean value) { this.value = value; }
    public void toggle() { value = !value; }
}