package padej.soup.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ConfigSection {
    private final String name;
    private final List<ConfigValue<?>> values = new ArrayList<>();

    public ConfigSection(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public <T extends ConfigValue<?>> T add(T value) {
        values.add(value);
        return value;
    }

    public List<ConfigValue<?>> values() {
        return Collections.unmodifiableList(values);
    }
}