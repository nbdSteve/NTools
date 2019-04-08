package dev.nuer.nt.event;

import dev.nuer.nt.NTools;
import org.bukkit.ChatColor;

import java.util.List;

/**
 * Gets the type of tool for the event, based on the tools.yml
 */
public class GetToolType {
    //Store if the tool is a trench tool
    private boolean isTrenchTool;
    //Store if the tool is a tray tool
    private boolean isTrayTool;
    //Store if the tool is a multi-tool
    private boolean isMultiTool;
    //Store the type of tool from the tools.yml
    private String toolType;
    //Store the radius if it is a multi-tool
    private int multiToolRadius;

    GetToolType(List<String> itemLore) {
        for (int i = 1; i <= 54; i++) {
            try {
                if (itemLore.contains(NTools.getTrenchTools().get(i))) {
                    toolType = "trench." + i;
                    isTrenchTool = true;
                    break;
                } else if (itemLore.contains(NTools.getTrayTools().get(i))) {
                    toolType = "tray." + i;
                    isTrayTool = true;
                } else if (itemLore.contains(NTools.getMultiTools().get(i))) {
                    toolType = "multi-tool." + i;
                    break;
                } else if (itemLore.contains(ChatColor.translateAlternateColorCodes('&',
                        NTools.getFiles().get("tools").getString("multi-tool." + i + ".unique-lore")))) {
                    toolType = "multi-tool." + i;
                    isTrayTool = true;
                    break;
                }
            } catch (NullPointerException toolDoesNotExist) {
                //Do nothing
            }
        }
    }

    boolean getIsTrenchTool() {
        return isTrenchTool;
    }

    boolean getIsTrayTool() {
        return isTrayTool;
    }

    public boolean getIsMultiTool() {
        return isMultiTool;
    }

    String getToolType() {
        return toolType;
    }

    public int getMultiToolRadius() {
        return multiToolRadius;
    }
}
