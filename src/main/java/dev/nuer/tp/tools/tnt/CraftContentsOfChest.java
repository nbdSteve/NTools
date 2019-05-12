package dev.nuer.tp.tools.tnt;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.external.actionbarapi.ActionBarAPI;
import dev.nuer.tp.initialize.MapInitializer;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Class handles crafting the contents of a chest for tnt wands
 */
public class CraftContentsOfChest {

    /**
     * Crafts the respective items into their recipe in a chest
     *
     * @param player        Player, the player who is crafting
     * @param craftingPrice double, the cost of crafting (in items)
     * @param chestToAlter  Chest, the chest to craft from
     */
    public static void craftChestContents(Player player, double craftingPrice, Chest chestToAlter) {
        int slot = 0;
        HashMap<Material, Integer> materialAndAmount = new HashMap<>();
        for (ItemStack item : chestToAlter.getInventory().getContents()) {
            try {
                if (!item.hasItemMeta() && MapInitializer.tntWandCraftingRecipe.containsKey(item.getType().toString())) {
                    try {
                        int newAmount = item.getAmount() + materialAndAmount.get(item.getType());
                        materialAndAmount.put(item.getType(), newAmount);
                    } catch (Exception e) {
                        materialAndAmount.put(item.getType(), item.getAmount());
                    }
                    chestToAlter.getInventory().setItem(slot, new ItemStack(Material.AIR));
                }
            } catch (NullPointerException e) {
                //Recipe does not contain this item
            }
            slot++;
        }
        int numberCrafted = craftItems(materialAndAmount, craftingPrice, chestToAlter);
        if (ToolsPlus.getFiles().get("config").getBoolean("tnt-wand-action-bar.enabled")) {
            //Create the action bar message
            String message = ToolsPlus.getFiles().get("config").getString("tnt-wand-action-bar.craft-message").replace("{deposit}",
                    ToolsPlus.numberFormat.format(numberCrafted));
            //Send it to the player
            ActionBarAPI.sendActionBar(player, Chat.applyColor(message));
        } else {
            new PlayerMessage("chest-tnt-craft-contents", player, "{deposit}", ToolsPlus.numberFormat.format(numberCrafted));
        }
    }

    /**
     * Returns the amount of an item that was crafted
     *
     * @param materialAndAmount HashMap<Material, Integer>, the amount of a given material in the chest
     * @param craftingPrice     double, the price of crafting (in items)
     * @param chestToAlter      Chest, the chest to craft from
     * @return
     */
    public static int craftItems(HashMap<Material, Integer> materialAndAmount, double craftingPrice, Chest chestToAlter) {
        int maxCraftable = getMaxCratable(materialAndAmount, craftingPrice);
        for (Material item : materialAndAmount.keySet()) {
            double remainder = materialAndAmount.get(item) - (maxCraftable * (MapInitializer.tntWandCraftingRecipe.get(item.toString()) * craftingPrice));
            if (remainder > 0) {
                chestToAlter.getInventory().addItem(new ItemStack(item, (int) remainder));
            }
        }
        chestToAlter.getInventory().addItem(new ItemStack(Material.TNT, maxCraftable));
        return maxCraftable;
    }

    /**
     * Returns true if the chest contains items that can be crafted
     *
     * @param inventory        Inventory, the inventory to check
     * @param craftingModifier double, the cost of crafting (in items)
     * @return boolean
     */
    public static boolean canCraftContents(Inventory inventory, double craftingModifier) {
        HashMap<String, Integer> itemAmounts = new HashMap<>();
        for (ItemStack item : inventory.getContents()) {
            if (item != null && MapInitializer.tntWandCraftingRecipe.containsKey(item.getType().toString())) {
                if (itemAmounts.get(item.getType().toString()) == null) {
                    itemAmounts.put(item.getType().toString(), item.getAmount());
                } else {
                    int newAmount = itemAmounts.get(item.getType().toString()) + item.getAmount();
                    itemAmounts.put(item.getType().toString(), newAmount);
                }
            }
        }
        //Check that each item has the required amount
        for (String item : MapInitializer.tntWandCraftingRecipe.keySet()) {
            try {
                if (itemAmounts.get(item) < MapInitializer.tntWandCraftingRecipe.get(item) * craftingModifier) {
                    return false;
                }
            } catch (NullPointerException e) {
                //This is thrown if the required item is not in the chest
                return false;
            }
        }
        return true;
    }

    /**
     * Calculates the maximum number of tnt blocks that can be crafted for that inventory
     *
     * @param materialAndAmount HashMap<Material, Integer>, the map of material and total item amounts
     * @param craftingPrice     Double, the price per item for a single block
     * @return Integer
     */
    public static int getMaxCratable(HashMap<Material, Integer> materialAndAmount, double craftingPrice) {
        HashMap<Material, Double> maxCraftAmounts = new HashMap<>();
        for (Material item : materialAndAmount.keySet()) {
            double amountRequired = MapInitializer.tntWandCraftingRecipe.get(item.toString()) * craftingPrice;
            double numberCrafted = materialAndAmount.get(item) / amountRequired;
            maxCraftAmounts.put(item, numberCrafted);
        }
        double maxAmount = 0;
        for (Material item : maxCraftAmounts.keySet()) {
            if (ToolsPlus.debugMode) {
                ToolsPlus.LOGGER.info("[Tools+] (TnT Wand Debug) Current max craftable: " + maxCraftAmounts.get(item));
            }
            if (maxCraftAmounts.keySet().iterator().hasNext()) {
                if (ToolsPlus.debugMode) {
                    ToolsPlus.LOGGER.info("[Tools+] (TnT Wand Debug) Next max craftable: " + maxCraftAmounts.get(maxCraftAmounts.keySet().iterator().next()));
                }
                if (maxCraftAmounts.get(maxCraftAmounts.keySet().iterator().next()) < maxCraftAmounts.get(item)) {
                    maxAmount = maxCraftAmounts.get(maxCraftAmounts.keySet().iterator().next());
                } else {
                    maxAmount = maxCraftAmounts.get(item);
                }
            }
        }
        if (ToolsPlus.debugMode) {
            ToolsPlus.LOGGER.info("[Tools+] (TnT Wand Debug) Final max craftable: " + maxAmount);
        }
        return (int) maxAmount;
    }
}