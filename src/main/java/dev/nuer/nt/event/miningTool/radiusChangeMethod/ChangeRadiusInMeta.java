package dev.nuer.nt.event.miningTool.radiusChangeMethod;

import dev.nuer.nt.NTools;
import dev.nuer.nt.event.itemMetaMethod.UpdateItem;
import dev.nuer.nt.initialize.OtherMapInitializer;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.entity.Player;
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
     * @param itemLore   List<String>, the lore of the item being queried
     * @param itemMeta   ItemMeta, the meta of the item being queried
     * @param item       ItemStack, the item being queried
     * @return int, the tool radius
     */
    public static int changeRadius(boolean increment, boolean decrement, int index, int radius, double priceToUpgrade,
                                   String radiusLore, int toolTypeRawID, List<String> itemLore,
                                   ItemMeta itemMeta, ItemStack item, Player player) {
        if (increment) {
            try {
                int maxRadius = Integer.parseInt(OtherMapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get
                        (OtherMapInitializer.multiToolRadiusUnique.get(toolTypeRawID).size() - 1));
                if (radius + 1 <= maxRadius) {
                    if (player != null) {
                        if (NTools.economy != null) {
                            if (NTools.economy.getBalance(player) >= priceToUpgrade) {
                                NTools.economy.withdrawPlayer(player, priceToUpgrade);
                                itemLore.set(index, radiusLore + " " + OtherMapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get(radius + 1));
                                UpdateItem.updateItem(itemLore, itemMeta, item);
                                new PlayerMessage("incremented-radius", player, "{price}", NTools.numberFormat.format(priceToUpgrade));
                            } else {
                                new PlayerMessage("insufficient", player);
                            }
                        } else {
                            itemLore.set(index, radiusLore + " " + OtherMapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get(radius + 1));
                            UpdateItem.updateItem(itemLore, itemMeta, item);
                        }
                    } else {
                        itemLore.set(index, radiusLore + " " + OtherMapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get(radius + 1));
                        UpdateItem.updateItem(itemLore, itemMeta, item);
                    }
                } else {
                    return -1;
                }
            } catch (NullPointerException radiusUndefinedException) {
                return -1;
            }
        }
        if (decrement) {
            try {
                int minRadius = Integer.parseInt(OtherMapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get
                        (OtherMapInitializer.multiToolRadiusUnique.get(toolTypeRawID).size() - 2));
                if (radius - 1 >= minRadius) {
                    itemLore.set(index,
                            radiusLore + " " + OtherMapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get(radius - 1));
                    UpdateItem.updateItem(itemLore, itemMeta, item);
                } else {
                    return -1;
                }
            } catch (NullPointerException radiusUndefinedException) {
                return -1;
            }
        }
        return radius;
    }
}