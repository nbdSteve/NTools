package gg.steve.mc.tp.tool;

public enum ToolType {
    TRENCH,
    TRAY,
    SELL,
    TNT,
    SAND,
    LIGHTNING,
    HARVESTER,
    SMELT,
    AQUA,
    CHUNK,
    NONE;

    public String getNiceName() {
        StringBuilder builder = new StringBuilder();
        builder.append(name(), 0, 1);
        builder.append(name().substring(1).toLowerCase());
        return builder.toString();
    }
}
