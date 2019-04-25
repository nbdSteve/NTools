package dev.nuer.tp.tools.sell;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.events.SellWandContainerSaleEvent;
import dev.nuer.tp.external.ShopGUIPlusIntegration;
import dev.nuer.tp.external.actionbarapi.ActionBarAPI;
import dev.nuer.tp.external.nbtapi.NBTItem;
import dev.nuer.tp.initialize.MapInitializer;
import dev.nuer.tp.method.player.PlayerMessage;
import dev.nuer.tp.tools.DecrementUses;
import dev.nuer.tp.tools.PlayerToolCooldown;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SellChestContents {

    public static void sellContents(Block clickedBlock, Player player, String directory, String filePath, double priceModifier,
                                    NBTItem nbtItem) {
        Bukkit.getScheduler().runTaskAsynchronously(ToolsPlus.getPlugin(ToolsPlus.class), () -> {
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
            if (PlayerToolCooldown.isOnCooldown(player, "sell")) {
                return;
            } else {
                DecrementUses.decrementUses(player, "sell", nbtItem, nbtItem.getInteger("ntool.uses"));
                PlayerToolCooldown.setPlayerOnCooldown(player, cooldownFromConfig, "sell");
            }
            int slot = 0;
            double totalDeposit = 0;
            for (ItemStack item : inventoryToSell) {
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
                ActionBarAPI.sendActionBar(player, ChatColor.translateAlternateColorCodes('&', message));
            } else {
                new PlayerMessage("chest-contents-sell", player, "{deposit}", ToolsPlus.numberFormat.format(totalDeposit));
            }
        });
    }

    public static boolean canSellContents(Inventory inventory, Player player, boolean usingShopGuiPlus) {
        for (ItemStack item : inventory) {
            if (GetSellableItemPrices.canBeSold(item, player, usingShopGuiPlus, MapInitializer.sellWandItemPrices))
                return true;
        }
        return false;
    }
}