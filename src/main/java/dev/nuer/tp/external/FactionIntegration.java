package dev.nuer.tp.external;

import dev.nuer.tp.ToolsPlus;
import org.bukkit.Bukkit;

public class FactionIntegration {

    public static boolean usingFactions(String directory) {
        if (!ToolsPlus.getFiles().get(directory).getBoolean("tnt-wand.savage-factions-hook")) return false;
        return Bukkit.getPluginManager().getPlugin("Factions") != null;
    }
}
