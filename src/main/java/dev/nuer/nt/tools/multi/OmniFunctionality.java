package dev.nuer.nt.tools.multi;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class OmniFunctionality {

    public void changeToolType(Block block, Player player) {
        if (block.getType().equals(Material.DIRT)) {
            player.getItemInHand().setType(Material.DIAMOND_SHOVEL);
        }
        if (block.getType().equals(Material.STONE)) {
            player.getItemInHand().setType(Material.DIAMOND_PICKAXE);
        }
        if (block.getType().equals(Material.OAK_WOOD)) {
            player.getItemInHand().setType(Material.DIAMOND_AXE);
        }
    }
}
