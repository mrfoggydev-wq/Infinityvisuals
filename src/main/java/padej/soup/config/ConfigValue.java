package padej.soup.config;

public interface ConfigValue<T> {
    String getName();
    T get();
    void set(T value);
}