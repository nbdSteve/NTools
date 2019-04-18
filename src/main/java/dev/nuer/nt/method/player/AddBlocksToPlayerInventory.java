package dev.nuer.nt.method.player;

import dev.nuer.nt.NTools;
import dev.nuer.nt.external.actionbarapi.ActionBarAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Class that handles adding block drops to a players inventory
 */
public class AddBlocksToPlayerInventory {
    public static ArrayList<Player> messagedPlayers = new ArrayList<>();

    /**
     * Adds the drops from the specified event to the players inventory
     *
     * @param blockToBreak the event to get the drops of
     * @param player       the player who brock the block
     */
    public static void addBlocks(Block blockToBreak, Player player) {
        if (!messagedPlayers.contains(player)) {
            messagedPlayers.add(player);
            if (player.getInventory().firstEmpty() == -1) {
                if (NTools.getFiles().get("config").getBoolean("inventory-full-action-bar.enabled")) {
                    String message = NTools.getFiles().get("config").getString("inventory-full-action-bar.message");
                    ActionBarAPI.sendActionBar(player, ChatColor.translateAlternateColorCodes('&', message));
                } else {
                    new PlayerMessage("inventory-full", player);
                }
            }
        }
        for (ItemStack item : blockToBreak.getDrops()) {
            player.getInventory().addItem(item);
        }
        blockToBreak.setType(Material.AIR);
        blockToBreak.getDrops().clear();
    }

    public static void sellBlocks(Block blockToBreak, Player player) {
        if (!messagedPlayers.contains(player)) {
            messagedPlayers.add(player);
            if (player.getInventory().firstEmpty() == -1) {
                if (NTools.getFiles().get("config").getBoolean("inventory-full-action-bar.enabled")) {
                    String message = NTools.getFiles().get("config").getString("inventory-full-action-bar.message");
                    ActionBarAPI.sendActionBar(player, ChatColor.translateAlternateColorCodes('&', message));
                } else {
                    new PlayerMessage("inventory-full", player);
                }
            }
        }
        blockToBreak.setType(Material.AIR);
        blockToBreak.getDrops().clear();
    }
}
