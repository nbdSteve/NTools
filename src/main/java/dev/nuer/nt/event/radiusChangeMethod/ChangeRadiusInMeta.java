package dev.nuer.nt.event.radiusChangeMethod;

import dev.nuer.nt.NTools;
import dev.nuer.nt.event.itemMetaMethod.UpdateItem;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Class that handles incrementing / decrementing the radius for a multi tool
 */
public class ChangeRadiusInMeta {

    /**
     * Method that will increment / decrement the tools radius based on its current radius
     *
     * @param increment  boolean, if the radius should be incremented
     * @param decrement  boolean, if the radius should be decremented
     * @param index      int, index for the line of lore
     * @param radius     int, current radius for the tool
     * @param radiusLore String, radius unique line or lore from tools.yml
     * @param toolType   String, type of tool from the tools.yml
     * @param itemLore   List<String>, the lore of the item being queried
     * @param itemMeta   ItemMeta, the meta of the item being queried
     * @param item       ItemStack, the item being queried
     * @return int, the tool radius
     */
    public static int changeRadius(boolean increment, boolean decrement, int index, int radius,
                                   String radiusLore, String toolType, List<String> itemLore,
                                   ItemMeta itemMeta, ItemStack item) {
        if (increment) {
            try {
                if ((radius + 2) / 2 <= NTools.getFiles().get("tools").getInt(toolType + ".max-break-radius"
                )) {
                    itemLore.set(index, radiusLore + " " + ChatColor.translateAlternateColorCodes('&',
                            NTools.getFiles().get("tools").getString(toolType + ".radius." + (radius + 2) + "x" + (radius + 2))));
                    UpdateItem.updateItem(itemLore, itemMeta, item);
                } else {
                    return -1;
                }
            } catch (NullPointerException radiusUndefinedException) {
                return -1;
            }
        }
        if (decrement) {
            try {
                if ((radius - 2) / 2 >= NTools.getFiles().get("tools").getInt(toolType + ".min-break-radius"
                )) {
                    itemLore.set(index, radiusLore + " " + ChatColor.translateAlternateColorCodes('&',
                            NTools.getFiles().get("tools").getString(toolType + ".radius." + (radius - 2) + "x" + (radius - 2))));
                    UpdateItem.updateItem(itemLore, itemMeta, item);
                } else {
                    return -1;
                }
            } catch (NullPointerException radiusUndefinedException) {
                return -1;
            }
        }
        return radius / 2;
    }
}
