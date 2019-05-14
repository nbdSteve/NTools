package dev.nuer.tp.tools;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.support.nbtapi.NBTItem;
import dev.nuer.tp.managers.FileManager;
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
public class AlterToolModifier {

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
        int toolTypeRawID = nbtItem.getInteger("tools+.raw.id");
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
     * Method to increase the price modifier of a tool
     *
     * @param itemLore          List<String>, the items lore to edit
     * @param itemMeta          ItemMeta, the items meta
     * @param item              ItemStack, the item being affected
     * @param player            Player, the player who's tools is being updated
     * @param modifierUniqueIDs String, the modifier id
     * @param directory         String, the directory to get the modifier from
     * @param filePath          String, the modifier file path from the configuration
     */
    public static void increasePriceModifier(List<String> itemLore, ItemMeta itemMeta, ItemStack item, Player player,
                                             HashMap<Integer, ArrayList<String>> modifierUniqueIDs, String directory, String filePath) {
        player.closeInventory();
        verifyItemLore(itemLore, itemMeta, item, player, modifierUniqueIDs, directory, filePath);
    }

    /**
     * Method to increase the modifier if it is in the lore
     *
     * @param itemLore          List<String>, the items lore to edit
     * @param itemMeta          ItemMeta, the items meta
     * @param item              ItemStack, the item being affected
     * @param player            Player, the player who's tools is being updated
     * @param modifierUniqueIDs String, the modifier id
     * @param directory         String, the directory to get the modifier from
     * @param filePath          String, the modifier file path from the configuration
     */
    public static void verifyItemLore(List<String> itemLore, ItemMeta itemMeta, ItemStack item, Player player,
                                      HashMap<Integer, ArrayList<String>> modifierUniqueIDs, String directory, String filePath) {
        NBTItem nbtItem = new NBTItem(item);
        int toolTypeRawID = nbtItem.getInteger("tools+.raw.id");
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
     * Method to increase the modifier in the tools lore
     *
     * @param toolTypeRawID      Integer, the raw tool id from the configuration
     * @param index              Integer, the index of the lore to change
     * @param modifierUniqueLore String, the modifier id from the configuration
     * @param itemLore           List<String>, the items lore to edit
     * @param itemMeta           ItemMeta, the items meta
     * @param item               ItemStack, the item being affected
     * @param player             Player, the player whos tools is being updated
     * @param modifierUniqueIDs  String, the modifier id
     * @param directory          String, the directory to get the modifier from
     * @param filePath           String, the modifier file path from the configuration
     */
    public static void increaseModifierInLore(int toolTypeRawID, int index, String modifierUniqueLore,
                                              List<String> itemLore, ItemMeta itemMeta, ItemStack item, Player player,
                                              HashMap<Integer, ArrayList<String>> modifierUniqueIDs, String directory, String filePath) {
        int modifier = (int) getCurrentModifier(itemLore, item, false, modifierUniqueIDs);
        double priceToUpgrade = FileManager.get(directory).getInt(filePath + toolTypeRawID + ".upgrade-cost." + modifier);
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
                new PlayerMessage("incremented-modifier", player, "{price}", "FREE");
            }
        } else {
            new PlayerMessage("max-modifier", player);
        }
    }
}
