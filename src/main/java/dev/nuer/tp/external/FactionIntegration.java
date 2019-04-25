package dev.nuer.tp.external;

import dev.nuer.tp.ToolsPlus;
import org.bukkit.Bukkit;

/**
 * Class that handles checking if the plugin should hook with SavageFactions
 */
public class FactionIntegration {

    /**
     * Return true if the plugin should integrate with SavageFactions
     *
     * @param directory String, the tool directory
     * @return boolean
     */
    public static boolean usingFactions(String directory) {
        if (!ToolsPlus.getFiles().get(directory).getBoolean("tnt-wand.savage-factions-hook")) return false;
        return Bukkit.getPluginManager().getPlugin("Factions") != null;
    }
}
