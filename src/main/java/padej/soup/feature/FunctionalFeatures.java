package padej.soup.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import padej.soup.module.ModuleManager;

/** Real client-side feature handlers for Minecraft 1.21.4. */
public final class FunctionalFeatures {
    private static Double originalGamma;
    private static Integer originalFov;
    private static Boolean originalBobView;
    private static boolean movementKeysInjected;

    private FunctionalFeatures() {}

    public static boolean enabled(String name) {
        return ModuleManager.all().stream().anyMatch(m -> m.getName().equals(name) && m.isEnabled());
    }

    public static void tick() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;
        handleFullBright(mc);
        handleFullBrightPulse(mc);
        handleAutoSprint(mc);
        handleZoom(mc);
        handleViewBobbing(mc);
        handleInventoryMove(mc);
    }

    private static void handleFullBright(MinecraftClient mc) {
        boolean enabled = enabled("FullBright") || enabled("NightVision");
        if (enabled) {
            if (originalGamma == null) originalGamma = mc.options.getGamma().getValue();
            if (mc.options.getGamma().getValue() < 16.0) mc.options.getGamma().setValue(16.0);
        } else if (originalGamma != null && !enabled("FullBrightPulse")) {
            mc.options.getGamma().setValue(originalGamma);
            originalGamma = null;
        }
    }

    private static void handleFullBrightPulse(MinecraftClient mc) {
        if (!enabled("FullBrightPulse")) {
            if (originalGamma != null && !enabled("FullBright")) {
                mc.options.getGamma().setValue(originalGamma);
                originalGamma = null;
            }
            return;
        }
        if (originalGamma == null) originalGamma = mc.options.getGamma().getValue();
        double pulse = 12.0 + (Math.sin(System.currentTimeMillis() / 450.0) + 1.0) * 2.0;
        mc.options.getGamma().setValue(Math.min(16.0, pulse));
    }

    private static void handleAutoSprint(MinecraftClient mc) {
        if (!enabled("AutoSprint") && !enabled("SprintLock")) return;
        ClientPlayerEntity player = mc.player;
        boolean movingForward = mc.options.forwardKey.isPressed();
        boolean canSprint = movingForward && !player.isSneaking() && !player.isTouchingWater()
                && player.getHungerManager().getFoodLevel() > 6;
        if (canSprint) player.setSprinting(true);
    }

    private static void handleZoom(MinecraftClient mc) {
        boolean enabled = enabled("Zoom");
        if (enabled) {
            if (originalFov == null) originalFov = mc.options.getFov().getValue();
            int zoomedFov = Math.max(30, (int) Math.round(originalFov * 0.45));
            if (mc.options.getFov().getValue() != zoomedFov) mc.options.getFov().setValue(zoomedFov);
        } else if (originalFov != null) {
            mc.options.getFov().setValue(originalFov);
            originalFov = null;
        }
    }

    private static void handleViewBobbing(MinecraftClient mc) {
        if (enabled("ViewBobbing")) {
            if (originalBobView == null) originalBobView = mc.options.getBobView().getValue();
            mc.options.getBobView().setValue(false);
        } else if (originalBobView != null) {
            mc.options.getBobView().setValue(originalBobView);
            originalBobView = null;
        }
    }

    private static void handleInventoryMove(MinecraftClient mc) {
        if (!enabled("InventoryMove") || mc.currentScreen == null || mc.player == null) {
            if (movementKeysInjected) restoreMovementKeys(mc);
            return;
        }
        if (mc.currentScreen instanceof net.minecraft.client.gui.screen.ChatScreen) {
            if (movementKeysInjected) restoreMovementKeys(mc);
            return;
        }
        syncKey(mc.options.forwardKey, mc.options.forwardKey.isPressed());
        syncKey(mc.options.backKey, mc.options.backKey.isPressed());
        syncKey(mc.options.leftKey, mc.options.leftKey.isPressed());
        syncKey(mc.options.rightKey, mc.options.rightKey.isPressed());
        movementKeysInjected = true;
    }

    private static void syncKey(KeyBinding key, boolean pressed) {
        key.setPressed(pressed);
    }

    private static void restoreMovementKeys(MinecraftClient mc) {
        mc.options.forwardKey.setPressed(false);
        mc.options.backKey.setPressed(false);
        mc.options.leftKey.setPressed(false);
        mc.options.rightKey.setPressed(false);
        movementKeysInjected = false;
    }
}
