package dev.nuer.nt.tools.tnt;

import com.massivecraft.factions.FPlayers;
import dev.nuer.nt.NTools;
import dev.nuer.nt.external.actionbarapi.ActionBarAPI;
import dev.nuer.nt.method.player.PlayerMessage;
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

    public static void getTNTCountForChest(Player player, Inventory inventoryToQuery, Chest chestToAlter) {
        int slot = 0;
        HashMap<Material, Integer> materialAndAmount = new HashMap<>();
        for (ItemStack item : inventoryToQuery) {
            if (item != null && item.getType().equals(Material.TNT)) {
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
        if (NTools.getFiles().get("config").getBoolean("tnt-wand-action-bar.enabled")) {
            //Create the action bar message
            String message = NTools.getFiles().get("config").getString("tnt-wand-action-bar.bank-message").replace("{deposit}",
                    NTools.numberFormat.format(tntDeposited));
            //Send it to the player
            ActionBarAPI.sendActionBar(player, ChatColor.translateAlternateColorCodes('&', message));
        } else {
            new PlayerMessage("chest-tnt-bank-contents", player, "{deposit}", NTools.numberFormat.format(tntDeposited));
        }
    }
}
