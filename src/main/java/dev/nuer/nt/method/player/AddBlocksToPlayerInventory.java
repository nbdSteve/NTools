package dev.nuer.nt.method.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Class that handles adding block drops to a players inventory
 */
public class AddBlocksToPlayerInventory {

    /**
     * Adds the drops from the specified event to the players inventory
     *
     * @param event  the event to get the drops of
     * @param player the player who brock the block
     */
    public static void addBlocks(BlockEvent event, Player player) {
        for (ItemStack item : event.getBlock().getDrops()) {
            player.getInventory().addItem(item);
        }
        event.getBlock().setType(Material.AIR);
        event.getBlock().getDrops().clear();
    }
}
