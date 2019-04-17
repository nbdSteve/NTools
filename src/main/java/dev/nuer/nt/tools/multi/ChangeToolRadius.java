package dev.nuer.nt.tools.multi;

import dev.nuer.nt.NTools;
import dev.nuer.nt.external.nbtapi.NBTItem;
import dev.nuer.nt.initialize.MapInitializer;
import dev.nuer.nt.method.itemCreation.UpdateItem;
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
     * @param itemLore StringList, lore of the item to update
     * @param itemMeta ItemMeta, item meta of the item to update
     * @param item     the item being update
     * @param player   the player being queried
     */
    public static void incrementRadius(List<String> itemLore, ItemMeta itemMeta, ItemStack item, Player player) {
        player.closeInventory();
        changeToolRadius(itemLore, itemMeta, item, true, false, player);
    }

    /**
     * Method that will decrease the current radius of a tool if it is above the min radius
     *
     * @param itemLore StringList, lore of the item to update
     * @param itemMeta ItemMeta, item meta of the item to update
     * @param item     the item being update
     * @param player   the player being queried
     */
    public static void decrementRadius(List<String> itemLore, ItemMeta itemMeta, ItemStack item, Player player) {
        player.closeInventory();
        changeToolRadius(itemLore, itemMeta, item, false, true, player);
    }

    /**
     * @param itemLore
     * @param itemMeta
     * @param item
     * @param incrementRadius
     * @param decrementRadius
     * @param player
     */
    public static void changeToolRadius(List<String> itemLore, ItemMeta itemMeta, ItemStack item,
                                        boolean incrementRadius, boolean decrementRadius, Player player) {
        NBTItem nbtItem = new NBTItem(item);
        int toolTypeRawID = nbtItem.getInteger("ntool.raw.id");
        int index = 0;
        String radiusLore = MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get(index);
        for (String loreLine : itemLore) {
            //Check if the lore contains the radius unique line
            if (loreLine.contains(radiusLore)) {
                int arrayIndex = 1;
                while (arrayIndex < MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).size()) {
                    if (loreLine.contains(MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get(arrayIndex))) {
                        changeRadius(toolTypeRawID, index, radiusLore, itemLore,
                                itemMeta, item, incrementRadius, decrementRadius, player);
                        return;
                    }
                    arrayIndex++;
                }
            }
            //Increment the index if the line is not found
            index++;
        }
    }

    /**
     * Method that will increment / decrement the tools radius based on its current radius
     *
     * @param increment  boolean, if the radius should be incremented
     * @param decrement  boolean, if the radius should be decremented
     * @param index      int, index for the line of lore
     * @param radiusLore String, radius unique line or lore from tools.yml
     * @param itemLore   List<String>, the lore of the item being queried
     * @param itemMeta   ItemMeta, the meta of the item being queried
     * @param item       ItemStack, the item being queried
     * @return
     */
    public static void changeRadius(int toolTypeRawID, int index, String radiusLore, List<String> itemLore,
                                    ItemMeta itemMeta, ItemStack item, boolean increment, boolean decrement, Player player) {
        int radius = GetMultiToolVariables.getToolRadius(itemLore, item);
        double priceToUpgrade = NTools.getFiles().get("multi").getInt("multi-tools." + toolTypeRawID + ".upgrade-cost." + radius);
        if (increment) {
            int maxRadius =
                    Integer.parseInt(MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get
                            (MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).size() - 1));
            if (radius + 1 <= maxRadius) {
                if (NTools.economy != null) {
                    if (NTools.economy.getBalance(player) >= priceToUpgrade) {
                        NTools.economy.withdrawPlayer(player, priceToUpgrade);
                        itemLore.set(index,
                                radiusLore + " " + MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get(radius + 1));
                        UpdateItem.updateItem(itemLore, itemMeta, item);
                        new PlayerMessage("incremented-radius", player, "{price}",
                                NTools.numberFormat.format(priceToUpgrade));
                    } else {
                        new PlayerMessage("insufficient", player);
                    }
                } else {
                    itemLore.set(index,
                            radiusLore + " " + MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get(radius + 1));
                    UpdateItem.updateItem(itemLore, itemMeta, item);
                    new PlayerMessage("incremented-radius-no-cost", player);
                }
            } else {
                new PlayerMessage("max-radius", player);
            }
            return;
        }
        if (decrement) {
            int minRadius =
                    Integer.parseInt(MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get
                            (MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).size() - 2));
            if (radius - 1 >= minRadius) {
                itemLore.set(index,
                        radiusLore + " " + MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get(radius - 1));
                UpdateItem.updateItem(itemLore, itemMeta, item);
                new PlayerMessage("decremented-radius", player);
            } else {
                new PlayerMessage("min-radius", player);
            }
        }
    }
}
