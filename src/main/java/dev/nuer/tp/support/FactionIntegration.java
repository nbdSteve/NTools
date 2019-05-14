package dev.nuer.tp.support;

import dev.nuer.tp.managers.FileManager;
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
        if (!FileManager.get(directory).getBoolean("tnt-wand.savage-factions-hook")) return false;
        return Bukkit.getPluginManager().getPlugin("Factions") != null;
    }
}
