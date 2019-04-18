package dev.nuer.nt.tools.harvest;

import dev.nuer.nt.NTools;
import dev.nuer.nt.external.nbtapi.NBTItem;
import dev.nuer.nt.initialize.MapInitializer;
import dev.nuer.nt.method.itemCreation.UpdateItem;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class IncreasePriceModifier {

    public static double getCurrentModifier(List<String> itemLore, ItemStack item, boolean getDouble) {
        NBTItem nbtItem = new NBTItem(item);
        int toolTypeRawID = nbtItem.getInteger("ntool.raw.id");
        int index = 0;
        String modifierUniqueLore = MapInitializer.harvesterModifierUnique.get(toolTypeRawID).get(index);
        for (String loreLine : itemLore) {
            //Check if the lore contains the radius unique line
            if (loreLine.contains(modifierUniqueLore)) {
                int arrayIndex = 1;
                while (arrayIndex < MapInitializer.harvesterModifierUnique.get(toolTypeRawID).size()) {
                    String[] modifierParts = MapInitializer.harvesterModifierUnique.get(toolTypeRawID).get(arrayIndex).split("-");
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
     * Method that will increase the current radius of a tool if it is below the max radius
     *
     * @param itemLore StringList, lore of the item to update
     * @param itemMeta ItemMeta, item meta of the item to update
     * @param item     the item being update
     * @param player   the player being queried
     */
    public static void increaseHarvesterModifier(List<String> itemLore, ItemMeta itemMeta, ItemStack item, Player player) {
        player.closeInventory();
        verifyItemLore(itemLore, itemMeta, item, player);
    }

    public static void verifyItemLore(List<String> itemLore, ItemMeta itemMeta, ItemStack item, Player player) {
        NBTItem nbtItem = new NBTItem(item);
        int toolTypeRawID = nbtItem.getInteger("ntool.raw.id");
        int index = 0;
        String modifierUniqueLore = MapInitializer.harvesterModifierUnique.get(toolTypeRawID).get(index);
        for (String loreLine : itemLore) {
            //Check if the lore contains the radius unique line
            if (loreLine.contains(modifierUniqueLore)) {
                int arrayIndex = 1;
                while (arrayIndex < MapInitializer.harvesterModifierUnique.get(toolTypeRawID).size()) {
                    String[] modifierParts = MapInitializer.harvesterModifierUnique.get(toolTypeRawID).get(arrayIndex).split("-");
                    if (loreLine.contains(modifierParts[0])) {
                        increaseModifierInLore(toolTypeRawID, index, modifierUniqueLore, itemLore, itemMeta, item, player);
                        return;
                    }
                    arrayIndex++;
                }
            }
            //Increment the index if the line is not found
            index++;
        }
    }

    public static void increaseModifierInLore(int toolTypeRawID, int index, String modifierUniqueLore, List<String> itemLore,
                                              ItemMeta itemMeta, ItemStack item, Player player) {
        int modifier = (int) getCurrentModifier(itemLore, item, false);
        double priceToUpgrade = NTools.getFiles().get("harvester").getInt("harvester-tools." + toolTypeRawID + ".upgrade-cost." + modifier);
        int maxModifier = MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).size() - 3;
        if (modifier + 1 <= maxModifier) {
            String[] modifierParts = MapInitializer.harvesterModifierUnique.get(toolTypeRawID).get(modifier + 1).split("-");
            if (NTools.economy != null) {
                if (NTools.economy.getBalance(player) >= priceToUpgrade) {
                    NTools.economy.withdrawPlayer(player, priceToUpgrade);
                    itemLore.set(index, modifierUniqueLore + " " + modifierParts[0]);
                    UpdateItem.updateItem(itemLore, itemMeta, item);
                    new PlayerMessage("incremented-modifier", player, "{price}",
                            NTools.numberFormat.format(priceToUpgrade));
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
