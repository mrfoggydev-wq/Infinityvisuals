package padej.soup.config;

public final class VisualProfile {
    public enum Theme { PURPLE, CYAN, RAINBOW, MONO }
    public enum ESPStyle { BOX, CORNER, OUTLINE, GLOW }
    public enum TracerStyle { LINE, BEAM, ARROW }
    public enum ParticleStyle { DOT, STAR, RING, SPARK }
    public enum CrosshairStyle { PLUS, DOT, CIRCLE, GAP }

    public final ConfigSection gui = new ConfigSection("GUI");
    public final ConfigSection visuals = new ConfigSection("Visuals");
    public final ConfigSection hud = new ConfigSection("HUD");
    public final ConfigSection world = new ConfigSection("World");
    public final ConfigSection player = new ConfigSection("Player");

    public final BooleanValue guiAnimations = gui.add(new BooleanValue("Animations", true));
    public final BooleanValue guiSound = gui.add(new BooleanValue("Click sounds", false));
    public final DoubleValue guiScale = gui.add(new DoubleValue("Scale", .75, 1.25, 1.0));
    public final ColorValue accent = gui.add(new ColorValue("Accent", 0xFF8A6BFF));

    public final EnumValue<ESPStyle> espStyle = visuals.add(new EnumValue<>("ESP style", ESPStyle.BOX));
    public final EnumValue<TracerStyle> tracerStyle = visuals.add(new EnumValue<>("Tracer style", TracerStyle.LINE));
    public final EnumValue<ParticleStyle> particleStyle = visuals.add(new EnumValue<>("Particle style", ParticleStyle.DOT));
    public final DoubleValue espRange = visuals.add(new DoubleValue("ESP range", 8, 128, 64));
    public final DoubleValue tracerWidth = visuals.add(new DoubleValue("Tracer width", .5, 4, 1.2));
    public final DoubleValue particleAmount = visuals.add(new DoubleValue("Particle amount", 0, 100, 50));
    public final BooleanValue throughWalls = visuals.add(new BooleanValue("Through walls", false));
    public final BooleanValue teamColors = visuals.add(new BooleanValue("Team colors", true));

    public final BooleanValue fps = hud.add(new BooleanValue("FPS", true));
    public final BooleanValue ping = hud.add(new BooleanValue("Ping", true));
    public final BooleanValue coordinates = hud.add(new BooleanValue("Coordinates", true));
    public final BooleanValue armor = hud.add(new BooleanValue("Armor", true));
    public final BooleanValue potions = hud.add(new BooleanValue("Potions", true));
    public final BooleanValue keystrokes = hud.add(new BooleanValue("Keystrokes", true));
    public final BooleanValue arrayList = hud.add(new BooleanValue("ArrayList", true));
    public final BooleanValue targetHud = hud.add(new BooleanValue("TargetHUD", true));

    public final DoubleValue time = world.add(new DoubleValue("Client time", 0, 24000, 6000));
    public final BooleanValue customWeather = world.add(new BooleanValue("Custom weather", false));
    public final BooleanValue blockHighlight = world.add(new BooleanValue("Block highlight", true));
    public final BooleanValue lightOverlay = world.add(new BooleanValue("Light overlay", false));

    public final DoubleValue zoom = player.add(new DoubleValue("Zoom", 1, 20, 4));
    public final DoubleValue viewModel = player.add(new DoubleValue("ViewModel", .5, 2, 1));
    public final BooleanValue inventoryMove = player.add(new BooleanValue("Inventory move", true));
    public final BooleanValue autoSprint = player.add(new BooleanValue("Auto sprint", false));
}