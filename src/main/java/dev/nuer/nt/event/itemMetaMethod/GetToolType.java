package dev.nuer.nt.event.itemMetaMethod;

import dev.nuer.nt.NTools;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
    private String directory;
    //Store the radius if it is a multi-tool
    private int multiToolRadius;
    private int toolTypeRawID;

    /**
     * Gets the type of tool from the tools.yml for a given item
     *
     * @param itemLore List<String>, the lore of the item being queried
     * @param itemMeta ItemMeta, the meta of the item being queried
     * @param item     ItemStack, the item being queried
     */
    public GetToolType(List<String> itemLore, ItemMeta itemMeta, ItemStack item) {
        for (int i = 1; i <= 54; i++) {
            try {
                //Check to see which type of tool the item is
                if (itemLore.contains(NTools.getToolMap("trench").get(i))) {
                    toolTypeRawID = i;
                    directory = "trench";
                    toolType = "trench-tools." + toolTypeRawID;
                    isTrenchTool = true;
                    break;
                } else if (itemLore.contains(NTools.getToolMap("tray").get(i))) {
                    toolTypeRawID = i;
                    directory = "tray";
                    toolType = "tray-tools." + toolTypeRawID;
                    isTrayTool = true;
                } else if (itemLore.contains(NTools.getToolMap("sand").get(i))){
                    toolTypeRawID = i;
                    directory = "sand";
                    toolType = "sand-wands." + toolTypeRawID;
                } else if (itemLore.contains(NTools.getToolMap("multi").get(i))) {
                    toolTypeRawID = i;
                    directory = "multi";
                    toolType = "multi-tools." + toolTypeRawID;
                    isMultiTool = true;
                    //Store mode unique ids
                    if (GetMultiToolVariables.queryToolMode(toolTypeRawID, itemLore,
                            itemMeta, item,
                            false).equalsIgnoreCase("trench")) {
                        isTrenchTool = true;
                    } else if (GetMultiToolVariables.queryToolMode(toolTypeRawID, itemLore,
                            itemMeta, item, false).equalsIgnoreCase("tray")) {
                        isTrayTool = true;
                    }
                    //Store radius unique ids
                    multiToolRadius = GetMultiToolVariables.queryToolRadius(toolTypeRawID,
                            itemLore, itemMeta, item, false, false, null);
                    break;
                }
            } catch (NullPointerException toolDoesNotExist) {
                //Do nothing
            }
        }
    }

    /**
     * Getter for trench tool boolean
     *
     * @return
     */
    public boolean getIsTrenchTool() {
        return isTrenchTool;
    }

    /**
     * Getter for tray tool boolean
     *
     * @return
     */
    public boolean getIsTrayTool() {
        return isTrayTool;
    }

    /**
     * Getter for multi tool boolean
     *
     * @return
     */
    public boolean getIsMultiTool() {
        return isMultiTool;
    }

    /**
     * Getter for tool type string
     *
     * @return
     */
    public String getToolType() {
        return toolType;
    }

    /**
     * Getter for multi tool radius
     *
     * @return
     */
    public int getMultiToolRadius() {
        return multiToolRadius;
    }

    public int getToolTypeRawID() {
        return toolTypeRawID;
    }

    public String getDirectory() {
        return directory;
    }
}
