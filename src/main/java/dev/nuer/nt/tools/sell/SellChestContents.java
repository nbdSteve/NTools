package dev.nuer.nt.tools.sell;

import dev.nuer.nt.NTools;
import dev.nuer.nt.external.actionbarapi.ActionBarAPI;
import dev.nuer.nt.initialize.MapInitializer;
import dev.nuer.nt.method.player.PlayerMessage;
import dev.nuer.nt.tools.harvest.HandleSellingMessages;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;

public class SellChestContents {

    public static void sellContents(Block clickedBlock, Player player, String directory, String filePath, double priceModifier) {
        //Store the tool cooldown
        int cooldownFromConfig = NTools.getFiles().get(directory).getInt(filePath + ".cooldown");
        //Store the chest
        Chest chestToSell = (Chest) clickedBlock.getState();
        //Store the chests inventory
        Inventory inventoryToSell = chestToSell.getInventory();
        if (canSellContents(inventoryToSell)) {
            if (!SellCooldownCheck.isOnSellWandCooldown(player.getUniqueId(), cooldownFromConfig, player)) {
                int slot = 0;
                double totalDeposit = 0;
                for (ItemStack item : inventoryToSell) {
                    if (item == null || item.getType().equals(Material.AIR)) {
                        continue;
                    } else if (canBeSold(item)) {
                        double finalPrice = item.getAmount() * MapInitializer.sellWandItemPrices.get(item.getType().toString()) * priceModifier;
                        totalDeposit += finalPrice;
                        NTools.economy.depositPlayer(player, finalPrice);
                        chestToSell.getInventory().setItem(slot, new ItemStack(Material.AIR));
                    }
                    slot++;
                }
                if (NTools.getFiles().get("config").getBoolean("sell-wand-action-bar.enabled")) {
                    //Create the action bar message
                    String message = NTools.getFiles().get("config").getString("sell-wand-action-bar.message").replace("{deposit}",
                            new DecimalFormat("##.00").format(totalDeposit));
                    //Send it to the player
                    ActionBarAPI.sendActionBar(player, ChatColor.translateAlternateColorCodes('&', message));
                } else {
                    new PlayerMessage("chest-contents-sell", player, "{deposit}", new DecimalFormat("##.00").format(totalDeposit));
                }
            }
        } else {
            NTools.LOGGER.info("test");
            new PlayerMessage("can-not-sell-contents", player);
        }
    }

    public static boolean canBeSold(ItemStack item) {
        return MapInitializer.sellWandItemPrices.get(item.getType().toString()) != null;
    }

    public static boolean canSellContents(Inventory inventory) {
        for (ItemStack item : inventory) {
            if (canBeSold(item)) {
                return true;
            }
        }
        return false;
    }
}
