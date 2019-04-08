package dev.nuer.nt.event;

import dev.nuer.nt.NTools;
import org.bukkit.ChatColor;

import java.util.List;

public class GetMultiToolVariables {

    public static int getRadius(String toolType, List<String> itemLore) {
        String radius =
                NTools.getFiles().get("tools").getString(ChatColor.translateAlternateColorCodes('&',
                        toolType + ".radius.unique"));
        try {
            String rad1 =
                    NTools.getFiles().get("tools").getString(ChatColor.translateAlternateColorCodes('&',
                            toolType + ".radius.3x3"));
            String rad2 =
                    NTools.getFiles().get("tools").getString(ChatColor.translateAlternateColorCodes('&',
                            toolType + ".radius.5x5"));
            String rad3 =
                    NTools.getFiles().get("tools").getString(ChatColor.translateAlternateColorCodes('&',
                            toolType + ".radius.7x7"));
            for (int i = 0; i <= itemLore.size(); i++) {
                String loreLine = itemLore.get(i);
                if (loreLine.contains(radius)) {
                    if (loreLine.contains(rad1)) {
                        return 1;
                    } else if (loreLine.contains(rad2)) {
                        return 2;
                    } else if (loreLine.contains(rad3)) {
                        return 3;
                    }
                }
            }
        } catch (NullPointerException radiusNotInitialized) {
            //Do nothing, just that radius is not initialized
        }
        return 0;
    }

    public static String multiToolType(String toolType, List<String> itemLore) {
        String mode =
                NTools.getFiles().get("tools").getString(ChatColor.translateAlternateColorCodes('&',
                        toolType + ".mode.unique"));
        String trench =
                NTools.getFiles().get("tools").getString(ChatColor.translateAlternateColorCodes('&',
                        toolType + ".mode.trench"));
        String tray =
                NTools.getFiles().get("tools").getString(ChatColor.translateAlternateColorCodes('&',
                        toolType + ".mode.tray"));
        for (int i = 0; i <= itemLore.size(); i++) {
            String loreLine = itemLore.get(i);
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
