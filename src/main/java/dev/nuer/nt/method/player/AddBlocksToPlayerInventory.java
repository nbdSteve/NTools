package dev.nuer.nt.method.player;

import org.bukkit.Material;
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
    public static void addBlocks(BlockEvent event, Player player) {
        if (!messagedPlayers.contains(player)) {
            messagedPlayers.add(player);
            if (player.getInventory().firstEmpty() == -1) {
                new PlayerMessage("inventory-full", player);
            }
        }
        for (ItemStack item : event.getBlock().getDrops()) {
            player.getInventory().addItem(item);
        }
        event.getBlock().setType(Material.AIR);
        event.getBlock().getDrops().clear();
    }
}
