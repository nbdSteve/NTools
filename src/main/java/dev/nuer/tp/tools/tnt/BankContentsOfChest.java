package dev.nuer.tp.tools.tnt;

import com.massivecraft.factions.FPlayers;
import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.external.actionbarapi.ActionBarAPI;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BankContentsOfChest {

    public static boolean hasFaciton(Player player) {
        return FPlayers.getInstance().getByPlayer(player).hasFaction();
    }

    public static void addTNTToBank(Player player, int tntToAdd) {
        FPlayers.getInstance().getByPlayer(player).getFaction().addTnt(tntToAdd);
    }

    public static boolean chestContainsTNT(Inventory inventory) {
        for (ItemStack item : inventory) {
            if (item != null && item.getType().equals(Material.TNT)) {
                return true;
            }
        }
        return false;
    }

    public static void getTNTCountForChest(Player player, Chest chestToAlter) {
        int slot = 0;
        HashMap<Material, Integer> materialAndAmount = new HashMap<>();
        for (ItemStack item : chestToAlter.getInventory()) {
            if (!item.hasItemMeta() && item != null && item.getType().equals(Material.TNT)) {
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
            ActionBarAPI.sendActionBar(player, ChatColor.translateAlternateColorCodes('&', message));
        } else {
            new PlayerMessage("chest-tnt-bank-contents", player, "{deposit}", ToolsPlus.numberFormat.format(tntDeposited));
        }
    }
}
