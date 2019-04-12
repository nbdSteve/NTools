package dev.nuer.nt.method.player;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
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
     * @param event  the event to get the drops of
     * @param player the player who brock the block
     */
    public static void addBlocks(Block blockToBreak, Player player) {
        if (!messagedPlayers.contains(player)) {
            messagedPlayers.add(player);
            if (player.getInventory().firstEmpty() == -1) {
                new PlayerMessage("inventory-full", player);
            }
        }
        for (ItemStack item : blockToBreak.getDrops()) {
            player.getInventory().addItem(item);
        }
        blockToBreak.setType(Material.AIR);
        blockToBreak.getDrops().clear();
    }
}
