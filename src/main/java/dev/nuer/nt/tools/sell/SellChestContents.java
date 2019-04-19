package dev.nuer.nt.tools.sell;

import dev.nuer.nt.NTools;
import dev.nuer.nt.external.ShopGUIPlus;
import dev.nuer.nt.external.actionbarapi.ActionBarAPI;
import dev.nuer.nt.initialize.MapInitializer;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SellChestContents {

    public static void sellContents(Block clickedBlock, Player player, String directory, String filePath, double priceModifier) {
        Bukkit.getScheduler().runTaskAsynchronously(NTools.getPlugin(NTools.class), () -> {
            //Get if the plugin is using shop gui plus
            boolean usingShopGuiPlus = ShopGUIPlus.usingShopGUIPlus("sell");
            //Store the tool cooldown
            int cooldownFromConfig = NTools.getFiles().get(directory).getInt(filePath + ".cooldown");
            //Store the chest
            Chest chestToSell = (Chest) clickedBlock.getState();
            //Store the chests inventory
            Inventory inventoryToSell = chestToSell.getInventory();
            if (!canSellContents(inventoryToSell, player, usingShopGuiPlus)) {
                new PlayerMessage("can-not-sell-contents", player);
                return;
            }
            if (SellCooldownCheck.isOnSellWandCooldown(player.getUniqueId(), cooldownFromConfig, player)) {
                return;
            }
            int slot = 0;
            double totalDeposit = 0;
            for (ItemStack item : inventoryToSell) {
                if (item == null || item.getType().equals(Material.AIR)) {
                    continue;
                } else if (ShopGUIPlus.canBeSold(item, player, usingShopGuiPlus, MapInitializer.sellWandItemPrices)) {
                    //TODO add custom event
                    double finalPrice = ShopGUIPlus.getItemPrice(item, player, priceModifier, usingShopGuiPlus, MapInitializer.sellWandItemPrices);
                    totalDeposit += finalPrice;
                    NTools.economy.depositPlayer(player, finalPrice);
                    chestToSell.getInventory().setItem(slot, new ItemStack(Material.AIR));
                }
                slot++;
            }
            if (NTools.getFiles().get("config").getBoolean("sell-wand-action-bar.enabled")) {
                //Create the action bar message
                String message = NTools.getFiles().get("config").getString("sell-wand-action-bar.message").replace("{deposit}",
                        NTools.numberFormat.format(totalDeposit));
                //Send it to the player
                ActionBarAPI.sendActionBar(player, ChatColor.translateAlternateColorCodes('&', message));
            } else {
                new PlayerMessage("chest-contents-sell", player, "{deposit}", NTools.numberFormat.format(totalDeposit));
            }
        });
    }

    public static boolean canSellContents(Inventory inventory, Player player, boolean usingShopGuiPlus) {
        for (ItemStack item : inventory) {
            if (ShopGUIPlus.canBeSold(item, player, usingShopGuiPlus, MapInitializer.sellWandItemPrices))
                return true;
        }
        return false;
    }
}