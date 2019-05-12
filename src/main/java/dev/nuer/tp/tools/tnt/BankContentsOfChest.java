package dev.nuer.tp.tools.tnt;

import com.massivecraft.factions.FPlayers;
import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.external.actionbarapi.ActionBarAPI;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Class that handles sending tnt to a players faction bank
 */
public class BankContentsOfChest {

    /**
     * Return true if the player has a faction
     *
     * @param player Player, the player to check
     * @return boolean
     */
    public static boolean hasFaciton(Player player) {
        return FPlayers.getInstance().getByPlayer(player).hasFaction();
    }

    /**
     * Adds the specified amount of tnt to a players faction bank
     *
     * @param player   Player, the player who is getting tnt
     * @param tntToAdd Integer, the amount of tnt to add
     */
    public static void addTNTToBank(Player player, int tntToAdd) {
        FPlayers.getInstance().getByPlayer(player).getFaction().addTnt(tntToAdd);
    }

    /**
     * Returns true if the clicked chest contains tnt
     *
     * @param inventory Inventory, the inventory to check
     * @return boolean
     */
    public static boolean chestContainsTNT(Inventory inventory) {
        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType().equals(Material.TNT)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the amount of tnt inside a chest
     *
     * @param player       Player, the player who clicked
     * @param chestToAlter Chest, the inventory to check
     */
    public static void getTNTCountForChest(Player player, Chest chestToAlter) {
        int slot = 0;
        HashMap<Material, Integer> materialAndAmount = new HashMap<>();
        for (ItemStack item : chestToAlter.getInventory().getContents()) {
            if (item != null && !item.hasItemMeta() && item.getType().equals(Material.TNT)) {
                try {
                    int currentAmount = materialAndAmount.get(item.getType());
                    materialAndAmount.put(item.getType(), item.getAmount() + currentAmount);
                } catch (Exception e) {
                    materialAndAmount.put(item.getType(), item.getAmount());
                }
                chestToAlter.getInventory().setItem(slot, new ItemStack(Material.AIR));
            }
            slot++;
        }
        int tntDeposited = materialAndAmount.get(Material.TNT);
        addTNTToBank(player, tntDeposited);
        if (ToolsPlus.getFiles().get("config").getBoolean("tnt-wand-action-bar.enabled")) {
            //Create the action bar message
            String message = ToolsPlus.getFiles().get("config").getString("tnt-wand-action-bar.bank-message").replace("{deposit}",
                    ToolsPlus.numberFormat.format(tntDeposited));
            //Send it to the player
            ActionBarAPI.sendActionBar(player, Chat.applyColor(message));
        } else {
            new PlayerMessage("chest-tnt-bank-contents", player, "{deposit}", ToolsPlus.numberFormat.format(tntDeposited));
        }
    }
}
