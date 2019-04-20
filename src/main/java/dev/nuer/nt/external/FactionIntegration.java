package dev.nuer.nt.external;

import dev.nuer.nt.NTools;
import org.bukkit.Bukkit;

public class FactionIntegration {

    public static boolean usingFactions(String directory) {
        if (!NTools.getFiles().get(directory).getBoolean("tnt-wand.savage-factions-hook")) return false;
        return Bukkit.getPluginManager().getPlugin("Factions") != null;
    }
}
