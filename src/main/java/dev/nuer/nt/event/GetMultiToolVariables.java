package dev.nuer.nt.event;

import dev.nuer.nt.NTools;
import org.bukkit.ChatColor;

import java.util.List;

public class GetMultiToolVariables {

    public static int getRadius(String toolType, List<String> itemLore) {
        String radius =
                ChatColor.translateAlternateColorCodes('&',
                        NTools.getFiles().get("tools").getString(toolType + ".radius.unique"));
        for (String loreLine : itemLore) {
            if (loreLine.contains(radius)) {
                for (int i = 3; i <= 54; i += 2) {
                    String currentRadius = ChatColor.translateAlternateColorCodes('&',
                            NTools.getFiles().get("tools").getString(toolType + ".radius." + i + "x" + i));
                    if (loreLine.contains(currentRadius)) {
                        return i/2;
                    }
                }
            }
        }
        return 0;
    }

    public static String multiToolType(String toolType, List<String> itemLore) {
        String mode = ChatColor.translateAlternateColorCodes('&',
                NTools.getFiles().get("tools").getString(toolType + ".mode.unique"));
        String trench = ChatColor.translateAlternateColorCodes('&',
                NTools.getFiles().get("tools").getString(toolType + ".mode.trench"));
        String tray = ChatColor.translateAlternateColorCodes('&',
                NTools.getFiles().get("tools").getString(toolType + ".mode.tray"));
        for (String loreLine : itemLore) {
            if (loreLine.contains(mode)) {
                if (loreLine.contains(trench)) {
                    return "trench";
                } else if (loreLine.contains(tray)) {
                    return "tray";
                }
            }
        }
        return null;
    }
}
