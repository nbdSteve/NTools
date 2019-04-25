package dev.nuer.tp.tools.multi;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.external.nbtapi.NBTItem;
import dev.nuer.tp.initialize.MapInitializer;
import dev.nuer.tp.method.itemCreation.UpdateItem;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Class that handles increasing / decreasing and updating the tool with the new radius
 */
public class ChangeToolRadius {

    /**
     * Gets a multi tools current break radius
     *
     * @param itemLore List<String>, the lore to check
     * @param item     ItemStack, the item to check - nbt object will be made of it
     * @return Integer, tools current radius
     */
    public static int getToolRadius(List<String> itemLore, ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        int toolTypeRawID = nbtItem.getInteger("ntool.raw.id");
        int index = 0;
        String radiusUniqueLore = MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get(index);
        for (String loreLine : itemLore) {
            //Check if the lore contains the radius unique line
            if (loreLine.contains(radiusUniqueLore)) {
                int arrayIndex = 1;
                while (arrayIndex < MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).size()) {
                    if (loreLine.contains(MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get(arrayIndex))) {
                        return arrayIndex;
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
     * Method that will increase the current radius of a tool if it is below the max radius
     *
     * @param itemLore List<String>, lore of the item to update
     * @param itemMeta ItemMeta, item meta of the item to update
     * @param item     ItemStack, the item being update
     * @param player   Player, the player being queried
     */
    public static void incrementRadius(List<String> itemLore, ItemMeta itemMeta, ItemStack item, Player player) {
        player.closeInventory();
        changeToolRadius(itemLore, itemMeta, item, true, false, player);
    }

    /**
     * Method that will decrease the current radius of a tool if it is above the min radius
     *
     * @param itemLore List<String>, lore of the item to update
     * @param itemMeta ItemMeta, item meta of the item to update
     * @param item     ItemStack, the item being updated
     * @param player   the player being queried
     */
    public static void decrementRadius(List<String> itemLore, ItemMeta itemMeta, ItemStack item, Player player) {
        player.closeInventory();
        changeToolRadius(itemLore, itemMeta, item, false, true, player);
    }

    /**
     * Method called before the changeRadius method, the is a precursor so that variables can be assigned,
     * this was made into a separate method to make the code easier to read.
     *
     * @param itemLore        List<String>, lore of the item to update
     * @param itemMeta        ItemMeta, item meta of the item to update
     * @param item            ItemStack, the item being updated
     * @param incrementRadius boolean, if the radius to be increased
     * @param decrementRadius boolean, if the radius should be decreased
     * @param player          Player, the player who's tool is being affected
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
     * Method that will increment / decrement the tools radius based on parameters
     *
     * @param toolTypeRawID Integer, the raw tool ID from the configuration files
     * @param index         Integer, the index that the unique radius lore is located on
     * @param radiusLore    String, the unique radius lore as a string
     * @param itemLore      List<String>, lore of the item to update
     * @param itemMeta      ItemMeta, item meta of the item to update
     * @param item          ItemStack, the item being updated
     * @param increment     boolean, if the radius should be incremented
     * @param decrement     boolean, if the radius should be decremented
     * @param player        Player, the player who's tool is being affected
     */
    public static void changeRadius(int toolTypeRawID, int index, String radiusLore, List<String> itemLore,
                                    ItemMeta itemMeta, ItemStack item, boolean increment, boolean decrement, Player player) {
        int radius = ChangeToolRadius.getToolRadius(itemLore, item);
        double priceToUpgrade = ToolsPlus.getFiles().get("multi").getInt("multi-tools." + toolTypeRawID + ".upgrade-cost." + radius);
        if (increment) {
            int maxRadius =
                    Integer.parseInt(MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get
                            (MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).size() - 2));
            if (radius + 1 <= maxRadius) {
                if (ToolsPlus.economy != null) {
                    if (ToolsPlus.economy.getBalance(player) >= priceToUpgrade) {
                        ToolsPlus.economy.withdrawPlayer(player, priceToUpgrade);
                        itemLore.set(index,
                                radiusLore + " " + MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get(radius + 1));
                        UpdateItem.updateItem(itemLore, itemMeta, item);
                        new PlayerMessage("incremented-radius", player, "{price}",
                                ToolsPlus.numberFormat.format(priceToUpgrade));
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
                            (MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).size() - 1));
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
