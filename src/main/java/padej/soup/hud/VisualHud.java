package padej.soup.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import padej.soup.module.Module;
import padej.soup.module.ModuleManager;
import padej.soup.module.ModuleCategory;
import padej.soup.util.ColorUtil;

import java.util.List;

public final class VisualHud {
    private VisualHud() {}

    public static void register() {
        HudRenderCallback.EVENT.register(VisualHud::render);
    }

    private static void render(DrawContext ctx, RenderTickCounter tickCounter) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.options.hudHidden) return;

        long now = System.currentTimeMillis();
        if (enabled("Watermark")) drawWatermark(ctx, mc, now);
        if (enabled("FPS")) drawFps(ctx, mc, now);
        if (enabled("Coordinates")) drawCoordinates(ctx, mc, now);
        if (enabled("Ping")) drawPing(ctx, mc, now);
        if (enabled("Clock")) drawClock(ctx, mc, now);
        if (enabled("Keystrokes")) drawKeys(ctx, mc, now);
        if (enabled("Arraylist")) drawArraylist(ctx, mc, now);
    }

    private static boolean enabled(String name) {
        return ModuleManager.all().stream().anyMatch(m -> m.getName().equals(name) && m.isEnabled());
    }

    private static void drawWatermark(DrawContext ctx, MinecraftClient mc, long now) {
        int x = 10, y = 10, w = 132, h = 28;
        ctx.fill(x, y, x + w, y + h, 0xEE0D0F18);
        ctx.fill(x, y, x + 2, y + h, ColorUtil.rainbow(now, 0));
        ctx.drawText(mc.textRenderer, Text.literal("∞"), x + 9, y + 7, ColorUtil.rainbow(now, 0), false);
        ctx.drawText(mc.textRenderer, Text.literal("InfinityVisuals"), x + 25, y + 7, 0xFFF0ECFF, false);
    }

    private static void drawFps(DrawContext ctx, MinecraftClient mc, long now) {
        ctx.drawText(mc.textRenderer, Text.literal("FPS " + mc.getCurrentFps()), 10, 44,
                ColorUtil.rainbow(now, .2f), false);
    }

    private static void drawCoordinates(DrawContext ctx, MinecraftClient mc, long now) {
        String s = String.format("XYZ %.1f %.1f %.1f", mc.player.getX(), mc.player.getY(), mc.player.getZ());
        ctx.drawText(mc.textRenderer, Text.literal(s), 10, 58, 0xFFE6E9EF, false);
    }

    private static void drawPing(DrawContext ctx, MinecraftClient mc, long now) {
        if (mc.getNetworkHandler() == null) return;
        var entry = mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid());
        if (entry == null) return;
        ctx.drawText(mc.textRenderer, Text.literal("Ping " + entry.getLatency() + "ms"), 10, 72,
                ColorUtil.rainbow(now, .35f), false);
    }

    private static void drawClock(DrawContext ctx, MinecraftClient mc, long now) {
        long t = System.currentTimeMillis();
        java.time.LocalTime time = java.time.LocalTime.now();
        String s = String.format("%02d:%02d:%02d", time.getHour(), time.getMinute(), time.getSecond());
        ctx.drawText(mc.textRenderer, Text.literal(s), 10, 86, 0xFFE6E9EF, false);
    }

    private static void drawKeys(DrawContext ctx, MinecraftClient mc, long now) {
        int x = 10, y = 104, s = 20, g = 3;
        String[] keys = {"W", "A", "S", "D"};
        for (int i = 0; i < 4; i++) {
            int px = x + (i == 0 ? s + g : i == 1 ? 0 : i == 2 ? s + g : 2 * (s + g));
            int py = y + (i == 0 ? 0 : s + g);
            boolean pressed = switch (keys[i]) {
                case "W" -> mc.options.forwardKey.isPressed();
                case "A" -> mc.options.leftKey.isPressed();
                case "S" -> mc.options.backKey.isPressed();
                default -> mc.options.rightKey.isPressed();
            };
            ctx.fill(px, py, px + s, py + s, pressed ? ColorUtil.rainbow(now, i * .1f) : 0xEE1A1D25);
            ctx.drawCenteredTextWithShadow(mc.textRenderer, Text.literal(keys[i]), px + s / 2, py + 6, 0xFFF1ECFF);
        }
    }

    private static void drawArraylist(DrawContext ctx, MinecraftClient mc, long now) {
        List<Module> active = ModuleManager.all().stream().filter(Module::isEnabled).toList();
        int y = 10;
        for (int i = 0; i < active.size(); i++) {
            Module m = active.get(i);
            String name = m.getName();
            int w = mc.textRenderer.getWidth(name) + 10;
            int x = mc.getWindow().getScaledWidth() - w - 6;
            ctx.fill(x, y - 2, x + w, y + 12, 0xDD0D1017);
            ctx.fill(x + w - 2, y - 2, x + w, y + 12, ColorUtil.rainbow(now, i * .06f));
            ctx.drawText(mc.textRenderer, Text.literal(name), x + 5, y, 0xFFF2F4F8, false);
            y += 14;
        }
    }
}
