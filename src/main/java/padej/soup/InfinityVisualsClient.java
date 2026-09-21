package padej.soup;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import padej.soup.gui.ClickGuiScreen;
import padej.soup.module.ModuleManager;
import padej.soup.hud.VisualHud;

public final class InfinityVisualsClient implements ClientModInitializer {
    private static KeyBinding clickGui;

    @Override
    public void onInitializeClient() {
        ModuleManager.init();
        VisualHud.register();

        clickGui = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.infinityvisuals.clickgui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "InfinityVisuals"
        ));

        System.out.println("[InfinityVisuals] InfinityVisuals 1.0.0 loaded for 1.21.4");
    }

    public static void tick() {
        while (clickGui.wasPressed()) {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.currentScreen == null) {
                mc.setScreen(new ClickGuiScreen());
            } else if (mc.currentScreen instanceof ClickGuiScreen) {
                mc.setScreen(null);
            }
        }

        ModuleManager.tick();
    }
}