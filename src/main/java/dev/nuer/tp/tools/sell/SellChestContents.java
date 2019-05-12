package dev.nuer.tp.tools.sell;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.events.SellWandContainerSaleEvent;
import dev.nuer.tp.external.ShopGUIPlusIntegration;
import dev.nuer.tp.external.actionbarapi.ActionBarAPI;
import dev.nuer.tp.external.nbtapi.NBTItem;
import dev.nuer.tp.initialize.MapInitializer;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.player.PlayerMessage;
import dev.nuer.tp.tools.DecrementUses;
import dev.nuer.tp.tools.PlayerToolCooldown;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
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
        int cooldownFromConfig = ToolsPlus.getFiles().get(directory).getInt(filePath + ".cooldown");
        //Store the chest
        Chest chestToSell = (Chest) clickedBlock.getState();
        //Store the chests inventory
        Inventory inventoryToSell = chestToSell.getInventory();
        if (!canSellContents(inventoryToSell, player, usingShopGuiPlus)) {
            new PlayerMessage("can-not-sell-contents", player);
            return;
        }
            DecrementUses.decrementUses(player, "sell", nbtItem, nbtItem.getInteger("tools+.uses"));
            PlayerToolCooldown.setPlayerOnCooldown(player, cooldownFromConfig, "sell");
        int slot = 0;
        double totalDeposit = 0;
        for (ItemStack item : inventoryToSell.getContents()) {
            if (item == null || item.getType().equals(Material.AIR)) {
                continue;
            } else if (GetSellableItemPrices.canBeSold(item, player, usingShopGuiPlus, MapInitializer.sellWandItemPrices)) {
                double finalPrice = GetSellableItemPrices.getItemPrice(item, player, priceModifier, usingShopGuiPlus, MapInitializer.sellWandItemPrices);
                SellWandContainerSaleEvent sellChestEvent = new SellWandContainerSaleEvent(chestToSell, player, item, finalPrice);
                Bukkit.getPluginManager().callEvent(sellChestEvent);
                if (!sellChestEvent.isCancelled()) {
                    totalDeposit += finalPrice;
                    chestToSell.getInventory().setItem(slot, new ItemStack(Material.AIR));
                }
            }
            slot++;
        }
        if (ToolsPlus.getFiles().get("config").getBoolean("sell-wand-action-bar.enabled")) {
            //Create the action bar message
            String message = ToolsPlus.getFiles().get("config").getString("sell-wand-action-bar.message").replace("{deposit}",
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
            if (GetSellableItemPrices.canBeSold(item, player, usingShopGuiPlus, MapInitializer.sellWandItemPrices))
                return true;
        }
        return false;
    }
}