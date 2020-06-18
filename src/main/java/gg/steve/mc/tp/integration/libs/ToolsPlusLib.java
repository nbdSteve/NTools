package gg.steve.mc.tp.integration.libs;

import org.bukkit.Location;

public abstract class ToolsPlusLib {
    private ToolsPlusLibType libType;

    public ToolsPlusLib(ToolsPlusLibType libType) {
        this.libType = libType;
    }

    public ToolsPlusLibType getLibType() {
        return libType;
    }

    public abstract boolean isBreakAllowed(Location location);
}
