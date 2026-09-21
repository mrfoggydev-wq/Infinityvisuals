package padej.soup.config;

public final class DoubleValue implements ConfigValue<Double> {
    private final String name;
    private final double min;
    private final double max;
    private double value;

    public DoubleValue(String name, double min, double max, double value) {
        this.name = name;
        this.min = min;
        this.max = max;
        set(value);
    }

    @Override public String getName() { return name; }
    @Override public Double get() { return value; }

    @Override
    public void set(Double value) {
        this.value = Math.max(min, Math.min(max, value));
    }
}