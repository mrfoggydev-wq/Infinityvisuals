package padej.soup.module;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public abstract class Module {
    private final String name;
    private final String description;
    private final ModuleCategory category;
    @Setter
    private boolean enabled;

    public void toggle() {
        enabled = !enabled;
        if (enabled) onEnable();
        else onDisable();
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}
}