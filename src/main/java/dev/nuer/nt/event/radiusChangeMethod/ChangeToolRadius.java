package dev.nuer.nt.event.radiusChangeMethod;

import dev.nuer.nt.event.itemMetaMethod.GetMultiToolVariables;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Class that handles increasing / decreasing and updating the tool with the new radius
 */
public class ChangeToolRadius {

    /**
     * Method that will increase the current radius of a tool if it is below the max radius
     *
     * @param toolTypeRawID Integer, raw tool ID from the tools.yml
     * @param toolType      String, formatted id for the tool type
     * @param itemLore      StringList, lore of the item to update
     * @param itemMeta      ItemMeta, item meta of the item to update
     * @param item          the item being update
     * @param player        the player being queried
     */
    public static void incrementRadius(int toolTypeRawID, String toolType, List<String> itemLore,
                                       ItemMeta itemMeta, ItemStack item, Player player) {
        player.closeInventory();
        int radius = GetMultiToolVariables.queryToolRadius(toolTypeRawID, toolType, itemLore,
                itemMeta, item, true, false);
        if (radius == -1) {
            new PlayerMessage("max-radius", player);
        } else {
            new PlayerMessage("incremented-radius", player);
        }
    }

    /**
     * Method that will decrease the current radius of a tool if it is above the min radius
     *
     * @param toolTypeRawID Integer, raw tool ID from the tools.yml
     * @param toolType      String, formatted id for the tool type
     * @param itemLore      StringList, lore of the item to update
     * @param itemMeta      ItemMeta, item meta of the item to update
     * @param item          the item being update
     * @param player        the player being queried
     */
    public static void decrementRadius(int toolTypeRawID, String toolType, List<String> itemLore,
                                       ItemMeta itemMeta, ItemStack item, Player player) {
        player.closeInventory();
        int radius = GetMultiToolVariables.queryToolRadius(toolTypeRawID, toolType, itemLore,
                itemMeta, item, false, true);
        if (radius == -1) {
            new PlayerMessage("min-radius", player);
        } else {
            new PlayerMessage("decremented-radius", player);
        }
    }
}
