package padej.soup.module;

import padej.soup.config.ConfigValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModuleFeature {
    private final Module module;
    private final List<ConfigValue<?>> values = new ArrayList<>();

    public ModuleFeature(Module module) {
        this.module = module;
    }

    public Module getModule() { return module; }

    public <T extends ConfigValue<?>> T add(T value) {
        values.add(value);
        return value;
    }

    public List<ConfigValue<?>> values() {
        return Collections.unmodifiableList(values);
    }
}