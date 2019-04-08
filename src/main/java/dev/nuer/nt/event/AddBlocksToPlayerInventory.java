package dev.nuer.nt.event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.ItemStack;

public class AddBlocksToPlayerInventory {

    public static void addBlocks(BlockEvent event, Player player) {
        for (ItemStack item : event.getBlock().getDrops()) {
            player.getInventory().addItem(item);
        }
        event.getBlock().setType(Material.AIR);
        event.getBlock().getDrops().clear();
    }
}
