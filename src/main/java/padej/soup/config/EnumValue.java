package padej.soup.config;

public final class EnumValue<E extends Enum<E>> implements ConfigValue<E> {
    private final String name;
    private final E[] values;
    private E value;

    public EnumValue(String name, E value) {
        this.name = name;
        this.value = value;
        this.values = value.getDeclaringClass().getEnumConstants();
    }

    @Override public String getName() { return name; }
    @Override public E get() { return value; }
    @Override public void set(E value) { this.value = value; }

    public void next() {
        int i = (value.ordinal() + 1) % values.length;
        value = values[i];
    }
}