package dev.nuer.tp.tools.sell;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.events.SellWandContainerSaleEvent;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.managers.ToolsAttributeManager;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.player.PlayerMessage;
import dev.nuer.tp.support.ShopGUIPlusIntegration;
import dev.nuer.tp.support.actionbarapi.ActionBarAPI;
import dev.nuer.tp.support.nbt.NBTItem;
import dev.nuer.tp.tools.DecrementUses;
import dev.nuer.tp.tools.PlayerToolCooldown;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Class that handles selling the contents on a chest
 */
public class SellChestContents {

    /**
     * Sells the contents of a given chest
     *
     * @param clickedBlock  Block, the chest that was clicked
     * @param player        Player, the player who is trying to sell items
     * @param directory     String, the file to read values from
     * @param filePath      String, the internal file path in the configuration
     * @param priceModifier double, the amount to multiply the base sell price by
     * @param nbtItem       NBTItem, the item being used
     */
    public static void sellContents(Block clickedBlock, Player player, String directory, String filePath, double priceModifier,
                                    NBTItem nbtItem) {
        //Verify that the player is not on cooldown
        if (PlayerToolCooldown.isOnCooldown(player, "sell")) return;
        //Get if the plugin is using shop gui plus
        boolean usingShopGuiPlus = ShopGUIPlusIntegration.usingShopGUIPlus("sell");
        //Store the tool cooldown
        int cooldownFromConfig = FileManager.get(directory).getInt(filePath + ".cooldown");
        //Store the chest
        Chest chestToSell = (Chest) clickedBlock.getState();
        //Check if the inventory is a double chest or not
        DoubleChestInventory doubleChestInventory = null;
        try {
            doubleChestInventory = (DoubleChestInventory) chestToSell.getInventory();
        } catch (ClassCastException e) {
            //Do nothing, the chest is single inventory
        }
        //Store the inventory
        Inventory inventoryToSell = chestToSell.getInventory();
        if (doubleChestInventory != null) {
            inventoryToSell = doubleChestInventory;
        }
        //Check if the items can be sold
        if (!canSellContents(inventoryToSell, player, usingShopGuiPlus)) {
            new PlayerMessage("can-not-sell-contents", player);
            return;
        }
        //Decrement tool uses
        DecrementUses.decrementUses(player, "sell-wand", nbtItem, nbtItem.getInteger("tools+.uses"));
        //Set the player on cooldown
        PlayerToolCooldown.setPlayerOnCooldown(player, cooldownFromConfig, "sell");
        int slot = 0;
        //Increment the total player deposit
        double totalDeposit = 0;
        for (ItemStack item : inventoryToSell.getContents()) {
            if (GetSellableItemPrices.canBeSold(item, player, usingShopGuiPlus, ToolsAttributeManager.sellWandItemPrices)) {
                double finalPrice = GetSellableItemPrices.getItemPrice(item, player, priceModifier, usingShopGuiPlus, ToolsAttributeManager.sellWandItemPrices);
                SellWandContainerSaleEvent sellChestEvent = new SellWandContainerSaleEvent(inventoryToSell, player, item, finalPrice);
                Bukkit.getPluginManager().callEvent(sellChestEvent);
                if (!sellChestEvent.isCancelled()) {
                    totalDeposit += finalPrice;
                    if (doubleChestInventory != null) doubleChestInventory.clear(slot); else chestToSell.getInventory().clear(slot);
                }
            }
            slot++;
        }
        if (FileManager.get("config").getBoolean("sell-wand-action-bar.enabled")) {
            //Create the action bar message
            String message = FileManager.get("config").getString("sell-wand-action-bar.message").replace("{deposit}",
                    ToolsPlus.numberFormat.format(totalDeposit));
            //Send it to the player
            ActionBarAPI.sendActionBar(player, Chat.applyColor(message));
        } else {
            new PlayerMessage("chest-contents-sell", player, "{deposit}", ToolsPlus.numberFormat.format(totalDeposit));
        }
    }

    /**
     * Returns true if the clicked chest contents can be sold to the server
     *
     * @param inventory        Inventory, the inventory to check
     * @param player           Player, player who is selling
     * @param usingShopGuiPlus boolean, if the plugin should hook with ShopGUIPlus
     * @return boolean
     */
    public static boolean canSellContents(Inventory inventory, Player player, boolean usingShopGuiPlus) {
        for (ItemStack item : inventory.getContents()) {
            if (GetSellableItemPrices.canBeSold(item, player, usingShopGuiPlus, ToolsAttributeManager.sellWandItemPrices))
                return true;
        }
        return false;
    }
}