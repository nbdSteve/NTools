package dev.nuer.tp.tools;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.external.nbtapi.NBTItem;
import dev.nuer.tp.method.itemCreation.UpdateItem;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that handles methods related to price modifiers, this for Sell and Harvester tool variants
 */
public class PriceModifier {

    /**
     * Method to get the current price modifier of an item
     *
     * @param itemLore          List<String>, the lore to query
     * @param item              ItemStack, the item to check and make a NBTItem out of
     * @param getDouble         boolean, if true this will get the current modifier as a double
     * @param modifierUniqueIDs HashMap<Integer, ArrayList<String>>, the list of unique modifiers IDs to query
     * @return Double, the price modifier or position in tree.
     */
    public static double getCurrentModifier(List<String> itemLore, ItemStack item, boolean getDouble,
                                            HashMap<Integer, ArrayList<String>> modifierUniqueIDs) {
        NBTItem nbtItem = new NBTItem(item);
        int toolTypeRawID = nbtItem.getInteger("ntool.raw.id");
        int index = 0;
        String modifierUniqueLore = modifierUniqueIDs.get(toolTypeRawID).get(index);
        for (String loreLine : itemLore) {
            //Check if the lore contains the radius unique line
            if (loreLine.contains(modifierUniqueLore)) {
                int arrayIndex = 1;
                while (arrayIndex < modifierUniqueIDs.get(toolTypeRawID).size()) {
                    String[] modifierParts = modifierUniqueIDs.get(toolTypeRawID).get(arrayIndex).split("-");
                    if (loreLine.contains(modifierParts[0])) {
                        if (getDouble) {
                            return Double.parseDouble(modifierParts[1]);
                        } else {
                            return arrayIndex;
                        }
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
     *
     * @param itemLore
     * @param itemMeta
     * @param item
     * @param player
     * @param modifierUniqueIDs
     * @param directory
     * @param filePath
     */
    public static void increasePriceModifier(List<String> itemLore, ItemMeta itemMeta, ItemStack item, Player player,
                                                 HashMap<Integer, ArrayList<String>> modifierUniqueIDs, String directory, String filePath) {
        player.closeInventory();
        verifyItemLore(itemLore, itemMeta, item, player, modifierUniqueIDs, directory, filePath);
    }

    /**
     *
     * @param itemLore
     * @param itemMeta
     * @param item
     * @param player
     * @param modifierUniqueIDs
     * @param directory
     * @param filePath
     */
    public static void verifyItemLore(List<String> itemLore, ItemMeta itemMeta, ItemStack item, Player player,
                                      HashMap<Integer, ArrayList<String>> modifierUniqueIDs, String directory, String filePath) {
        NBTItem nbtItem = new NBTItem(item);
        int toolTypeRawID = nbtItem.getInteger("ntool.raw.id");
        int index = 0;
        String modifierUniqueLore = modifierUniqueIDs.get(toolTypeRawID).get(index);
        for (String loreLine : itemLore) {
            //Check if the lore contains the radius unique line
            if (loreLine.contains(modifierUniqueLore)) {
                int arrayIndex = 1;
                while (arrayIndex < modifierUniqueIDs.get(toolTypeRawID).size()) {
                    String[] modifierParts = modifierUniqueIDs.get(toolTypeRawID).get(arrayIndex).split("-");
                    if (loreLine.contains(modifierParts[0])) {
                        increaseModifierInLore(toolTypeRawID, index, modifierUniqueLore, itemLore, itemMeta, item, player, modifierUniqueIDs, directory, filePath);
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
     *
     * @param toolTypeRawID
     * @param index
     * @param modifierUniqueLore
     * @param itemLore
     * @param itemMeta
     * @param item
     * @param player
     * @param modifierUniqueIDs
     * @param directory
     * @param filePath
     */
    public static void increaseModifierInLore(int toolTypeRawID, int index, String modifierUniqueLore,
                                              List<String> itemLore, ItemMeta itemMeta, ItemStack item, Player player,
                                              HashMap<Integer, ArrayList<String>> modifierUniqueIDs, String directory, String filePath) {
        int modifier = (int) getCurrentModifier(itemLore, item, false, modifierUniqueIDs);
        double priceToUpgrade = ToolsPlus.getFiles().get(directory).getInt(filePath + toolTypeRawID + ".upgrade-cost." + modifier);
        int maxModifier = modifierUniqueIDs.get(toolTypeRawID).size() - 3;
        if (modifier + 1 <= maxModifier) {
            String[] modifierParts = modifierUniqueIDs.get(toolTypeRawID).get(modifier + 1).split("-");
            if (ToolsPlus.economy != null) {
                if (ToolsPlus.economy.getBalance(player) >= priceToUpgrade) {
                    ToolsPlus.economy.withdrawPlayer(player, priceToUpgrade);
                    itemLore.set(index, modifierUniqueLore + " " + modifierParts[0]);
                    UpdateItem.updateItem(itemLore, itemMeta, item);
                    new PlayerMessage("incremented-modifier", player, "{price}",
                            ToolsPlus.numberFormat.format(priceToUpgrade));
                } else {
                    new PlayerMessage("insufficient", player);
                }
            } else {
                itemLore.set(index, modifierUniqueLore + " " + modifierParts[0]);
                UpdateItem.updateItem(itemLore, itemMeta, item);
                new PlayerMessage("incremented-modifier-no-cost", player);
            }
        } else {
            new PlayerMessage("max-modifier", player);
        }
    }
}
