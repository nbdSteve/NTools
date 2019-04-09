package dev.nuer.nt.event.itemMetaMethod;

import dev.nuer.nt.NTools;
import dev.nuer.nt.event.radiusChangeMethod.ChangeRadiusInMeta;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Class that handles getting and setting the radius / mode of a multi tool
 */
public class GetMultiToolVariables {

    /**
     * Query the current radius of a given multi tool
     *
     * @param toolType        String, the tool type for the tools.yml
     * @param itemLore        List<String>, the lore of the item being queried
     * @param itemMeta        ItemMeta, the meta of the item being queried
     * @param item            ItemStack, the item being queried
     * @param incrementRadius boolean, if the radius should be incremented
     * @param decrementRadius boolean, if the radius should be decremented
     * @return int, tool radius
     */
    public static int queryToolRadius(int toolTypeRawID, String toolType, List<String> itemLore, ItemMeta itemMeta,
                                      ItemStack item, boolean incrementRadius, boolean decrementRadius) {
        //Store the unique line of the lore for the radius
        String radiusLore = NTools.getMultiToolRadiusUnique().get(toolTypeRawID).get(0);
        //Store the index of the lore, could for a for loop but figured this might be more efficient
        int index = 0;
        for (String loreLine : itemLore) {
            //Check if the lore contains the radius unique line
            if (loreLine.contains(radiusLore)) {
                int arrayIndex = 1;
                while (arrayIndex < NTools.getMultiToolRadiusUnique().get(toolTypeRawID).size()) {
                    if (loreLine.contains(NTools.getMultiToolRadiusUnique().get(toolTypeRawID).get(arrayIndex))) {
                        return ChangeRadiusInMeta.changeRadius(incrementRadius, decrementRadius, index, arrayIndex, radiusLore,
                                toolTypeRawID, itemLore, itemMeta, item);
                    }
                    arrayIndex++;
                }
            }
            //Increment the index if the line is not found
            index++;
        }
        return 0;
    }

    /**
     * Query the mode of a multi tool, change it if required
     *
     * @param toolTypeRawID int, the type of tool from the tools.yml
     * @param itemLore      List<String>, the lore of the item being queried
     * @param itemMeta      ItemMeta, the meta of the item being queried
     * @param item          ItemStack, the item being queried
     * @param changeMode    boolean, if the mode should be changed
     * @return
     */
    public static String queryToolMode(int toolTypeRawID, List<String> itemLore, ItemMeta itemMeta,
                                       ItemStack item, boolean changeMode) {
        String mode = ChatColor.translateAlternateColorCodes('&',
                NTools.getMultiToolModeUnique().get(toolTypeRawID).get(0));
        String trench = ChatColor.translateAlternateColorCodes('&',
                NTools.getMultiToolModeUnique().get(toolTypeRawID).get(1));
        String tray = ChatColor.translateAlternateColorCodes('&',
                NTools.getMultiToolModeUnique().get(toolTypeRawID).get(2));
        int index = 0;
        for (String loreLine : itemLore) {
            //Check if the lore contains the mode unique line
            if (loreLine.contains(mode)) {
                //Check if the lore contains the trench unique line
                if (loreLine.contains(trench)) {
                    //If true change the mode to tray, else return that it is a trench tool
                    if (changeMode) {
                        itemLore.set(index, mode + " " + tray);
                        UpdateItem.updateItem(itemLore, itemMeta, item);
                    }
                    return "trench";
                } else if (loreLine.contains(tray)) {
                    if (changeMode) {
                        itemLore.set(index, mode + " " + trench);
                        UpdateItem.updateItem(itemLore, itemMeta, item);
                    }
                    return "tray";
                }
            }
            index++;
        }
        return null;
    }
}
