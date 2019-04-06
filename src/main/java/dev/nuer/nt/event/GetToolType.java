package dev.nuer.nt.event;

import dev.nuer.nt.NTools;
import org.bukkit.ChatColor;

import java.util.List;

public class GetToolType {

    private boolean isTrenchTool;
    private boolean isTrayTool;
    private String toolType;

    public GetToolType(List<String> itemLore) {
        for (int i = 1; i <= 54; i++) {
            if (itemLore.contains(ChatColor.translateAlternateColorCodes('&',
                    NTools.getFiles().get("tools").getString("trench." + i + ".unique-lore")))) {
                toolType = "trench." + i;
                isTrenchTool = true;
                break;
            } else if (itemLore.contains(ChatColor.translateAlternateColorCodes('&',
                    NTools.getFiles().get("tools").getString("tray." + i + ".unique-lore")))) {
                toolType = "tray." + i;
                isTrayTool = true;
                break;
            }
        }
    }

    public boolean getIsTrenchTool() {
        return isTrenchTool;
    }

    public boolean getIsTrayTool() {
        return isTrayTool;
    }

    public String getToolType() {
        return toolType;
    }
}
