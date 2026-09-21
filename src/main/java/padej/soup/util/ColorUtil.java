package padej.soup.util;

public final class ColorUtil {
    private ColorUtil() {}

    public static int rainbow(long timeMs, float offset) {
        float hue = ((timeMs % 8000L) / 8000.0f + offset) % 1.0f;
        if (hue < 0) hue += 1.0f;
        return hsv(hue, 0.62f, 1.0f);
    }

    public static int hsv(float h, float s, float v) {
        float r = 0, g = 0, b = 0;
        float i = (float)Math.floor(h * 6.0f);
        float f = h * 6.0f - i;
        float p = v * (1.0f - s);
        float q = v * (1.0f - f * s);
        float t = v * (1.0f - (1.0f - f) * s);
        switch ((int)i % 6) {
            case 0 -> { r = v; g = t; b = p; }
            case 1 -> { r = q; g = v; b = p; }
            case 2 -> { r = p; g = v; b = t; }
            case 3 -> { r = p; g = q; b = v; }
            case 4 -> { r = t; g = p; b = v; }
            case 5 -> { r = v; g = p; b = q; }
        }
        return 0xFF000000 | ((int)(r * 255) << 16) | ((int)(g * 255) << 8) | (int)(b * 255);
    }

    public static int withAlpha(int rgb, int alpha) {
        return (alpha << 24) | (rgb & 0x00FFFFFF);
    }
}