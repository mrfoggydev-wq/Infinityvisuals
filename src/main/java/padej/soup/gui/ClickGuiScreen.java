package padej.soup.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import padej.soup.module.Module;
import padej.soup.module.ModuleCategory;
import padej.soup.module.ModuleManager;
import padej.soup.util.ColorUtil;

import java.util.List;

/**
 * Crisp, high-contrast ClickGUI for Minecraft 1.21.4.
 * No blur, no text shadows and no translucent layers: every element is drawn
 * as an opaque rectangle/text pass so the UI remains readable at any scale.
 */
public final class ClickGuiScreen extends Screen {
    private static final int W = 780;
    private static final int H = 470;

    private static final int BG = 0xFF080A0F;
    private static final int PANEL = 0xFF10131A;
    private static final int PANEL_2 = 0xFF141820;
    private static final int PANEL_HOVER = 0xFF1B202A;
    private static final int PANEL_ACTIVE = 0xFF202633;
    private static final int BORDER = 0xFF292F3B;
    private static final int TEXT = 0xFFF4F6FA;
    private static final int TEXT_DIM = 0xFFA7AEBB;
    private static final int TEXT_MUTED = 0xFF707887;

    private ModuleCategory selected = ModuleCategory.VISUALS;
    private int scrollOffset = 0;

    public ClickGuiScreen() {
        super(Text.literal("InfinityVisuals"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        final int x = (width - W) / 2;
        final int y = (height - H) / 2;

        // Solid background. Deliberately no blur/alpha overlay.
        context.fill(0, 0, width, height, 0xFF05070A);

        // Main window + one-pixel border.
        context.fill(x - 1, y - 1, x + W + 1, y + H + 1, BORDER);
        context.fill(x, y, x + W, y + H, BG);

        // Thin animated accent line. It is the only animated glow-like element.
        for (int i = 0; i < W; i += 2) {
            context.fill(x + i, y, x + Math.min(i + 2, W), y + 2,
                    ColorUtil.rainbow(System.currentTimeMillis(), i / (float) W));
        }

        drawHeader(context, x, y);
        drawSidebar(context, x, y, mouseX, mouseY);
        drawModules(context, x, y, mouseX, mouseY);
        drawFooter(context, x, y);
    }

    private void drawHeader(DrawContext context, int x, int y) {
        context.drawText(textRenderer, Text.literal("INFINITYVISUALS"), x + 20, y + 16, TEXT, false);
        context.drawText(textRenderer, Text.literal("VISUAL CLIENT  /  1.21.4"), x + 20, y + 31, TEXT_MUTED, false);

        context.fill(x + 198, y + 48, x + W - 18, y + 49, BORDER);
    }

    private void drawSidebar(DrawContext context, int x, int y, int mouseX, int mouseY) {
        final int sx = x + 14;
        final int sy = y + 57;
        final int sw = 170;
        final int sh = H - 82;

        context.fill(sx, sy, sx + sw, sy + sh, PANEL);
        context.fill(sx + sw - 1, sy, sx + sw, sy + sh, BORDER);

        context.drawText(textRenderer, Text.literal("CATEGORIES"), sx + 14, sy + 12, TEXT_MUTED, false);

        ModuleCategory[] categories = {
                ModuleCategory.VISUALS,
                ModuleCategory.HUD,
                ModuleCategory.WORLD,
                ModuleCategory.PLAYER
        };

        for (int i = 0; i < categories.length; i++) {
            ModuleCategory category = categories[i];
            int cy = sy + 34 + i * 49;
            boolean active = selected == category;
            boolean hover = mouseX >= sx + 7 && mouseX <= sx + sw - 7
                    && mouseY >= cy && mouseY <= cy + 40;

            int fill = active ? PANEL_ACTIVE : (hover ? PANEL_HOVER : PANEL);
            context.fill(sx + 7, cy, sx + sw - 7, cy + 40, fill);

            if (active) {
                int accent = ColorUtil.rainbow(System.currentTimeMillis(), i * 0.13f);
                context.fill(sx + 7, cy, sx + 10, cy + 40, accent);
            }

            context.drawText(textRenderer, Text.literal(label(category)), sx + 20, cy + 14,
                    active ? TEXT : TEXT_DIM, false);
        }
    }

    private void drawModules(DrawContext context, int x, int y, int mouseX, int mouseY) {
        final int startX = x + 198;
        final int startY = y + 57;
        final int cardW = 275;
        final int cardH = 72;
        final int gap = 10;

        List<Module> modules = ModuleManager.byCategory(selected);
        long now = System.currentTimeMillis();

        for (int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);
            int visibleIndex = i - scrollOffset * 2;
            if (visibleIndex < 0) continue;
            int cx = startX + (visibleIndex % 2) * (cardW + gap);
            int cy = startY + (visibleIndex / 2) * (cardH + gap);

            // Keep the existing simple two-column layout. Extra modules below the
            // viewport are intentionally not drawn instead of being clipped/blurry.
            if (cy + cardH > y + H - 29) {
                break;
            }

            boolean hover = mouseX >= cx && mouseX <= cx + cardW
                    && mouseY >= cy && mouseY <= cy + cardH;
            boolean enabled = module.isEnabled();

            context.fill(cx, cy, cx + cardW, cy + cardH,
                    hover ? PANEL_HOVER : PANEL_2);
            context.fill(cx, cy, cx + cardW, cy + 1, BORDER);
            context.fill(cx, cy + cardH - 1, cx + cardW, cy + cardH, BORDER);
            context.fill(cx + cardW - 1, cy, cx + cardW, cy + cardH, BORDER);

            int accent = enabled ? ColorUtil.rainbow(now, i * 0.065f) : 0xFF3A414D;
            context.fill(cx, cy, cx + 3, cy + cardH, accent);

            context.drawText(textRenderer, Text.literal(module.getName()),
                    cx + 13, cy + 10, enabled ? TEXT : 0xFFD4D8DF, false);

            String description = module.getDescription();
            if (description != null && !description.isBlank()) {
                context.drawText(textRenderer, Text.literal(description),
                        cx + 13, cy + 29, TEXT_MUTED, false);
            }

            // Sharp ON/OFF pill; no shadow or glow.
            int pillX = cx + cardW - 54;
            int pillY = cy + 48;
            int pillColor = enabled ? 0xFF263329 : 0xFF20242C;
            context.fill(pillX, pillY, pillX + 42, pillY + 17, pillColor);
            context.fill(pillX, pillY, pillX + 2, pillY + 17, accent);
            String state = enabled ? "ON" : "OFF";
            int stateWidth = textRenderer.getWidth(state);
            context.drawText(textRenderer, Text.literal(state),
                    pillX + (42 - stateWidth) / 2, pillY + 4, enabled ? TEXT : TEXT_MUTED, false);
        }
    }

    private void drawFooter(DrawContext context, int x, int y) {
        context.drawText(textRenderer, Text.literal("RIGHT SHIFT"), x + W - 148, y + H - 21, TEXT_DIM, false);
        context.drawText(textRenderer, Text.literal("CLOSE"), x + W - 82, y + H - 21, TEXT_MUTED, false);
    }

    private String label(ModuleCategory category) {
        return switch (category) {
            case VISUALS -> "Visuals";
            case HUD -> "HUD";
            case WORLD -> "World";
            case PLAYER -> "Player";
        };
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) {
            return super.mouseClicked(mouseX, mouseY, button);
        }

        int x = (width - W) / 2;
        int y = (height - H) / 2;
        int sx = x + 14;
        int sy = y + 57;

        ModuleCategory[] categories = {
                ModuleCategory.VISUALS,
                ModuleCategory.HUD,
                ModuleCategory.WORLD,
                ModuleCategory.PLAYER
        };

        for (int i = 0; i < categories.length; i++) {
            int cy = sy + 34 + i * 49;
            if (mouseX >= sx + 7 && mouseX <= sx + 163
                    && mouseY >= cy && mouseY <= cy + 40) {
                selected = categories[i];
                scrollOffset = 0;
                return true;
            }
        }

        int startX = x + 198;
        int startY = y + 57;
        int cardW = 275;
        int cardH = 72;
        int gap = 10;
        List<Module> modules = ModuleManager.byCategory(selected);

        for (int i = 0; i < modules.size(); i++) {
            int visibleIndex = i - scrollOffset * 2;
            if (visibleIndex < 0) continue;
            int cx = startX + (visibleIndex % 2) * (cardW + gap);
            int cy = startY + (visibleIndex / 2) * (cardH + gap);
            if (cy + cardH > y + H - 29) {
                break;
            }
            if (mouseX >= cx && mouseX <= cx + cardW
                    && mouseY >= cy && mouseY <= cy + cardH) {
                modules.get(i).toggle();
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        int maxRows = 5;
        int rows = (ModuleManager.byCategory(selected).size() + 1) / 2;
        int maxOffset = Math.max(0, rows - maxRows);
        if (verticalAmount < 0) scrollOffset = Math.min(maxOffset, scrollOffset + 1);
        if (verticalAmount > 0) scrollOffset = Math.max(0, scrollOffset - 1);
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 265) { scrollOffset = Math.max(0, scrollOffset - 1); return true; }
        if (keyCode == 264) {
            int rows = (ModuleManager.byCategory(selected).size() + 1) / 2;
            scrollOffset = Math.min(Math.max(0, rows - 5), scrollOffset + 1);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
