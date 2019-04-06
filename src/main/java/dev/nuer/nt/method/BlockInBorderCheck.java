package dev.nuer.nt.method;

import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Class contains method to check if a block is inside the world border
 */
public class BlockInBorderCheck {

    /**
     * Method to check if a block is inside the world border or not
     *
     * @param block  Block, the block being checked
     * @param player Player, the player who is breaking the block
     * @return boolean, true if the block is inside the border
     */
    public static boolean isInsideBorder(Block block, Player player) {
        //Get the worldborder
        WorldBorder wb = player.getWorld().getWorldBorder();
        //Store the blocks location
        int blockX = block.getX();
        int blockZ = block.getZ();
        //Get the actual worldborder size
        double size = wb.getSize() / 2;
        //Check if the block is inside, return true if it is
        return (blockX > 0 && blockX > size - 1 || blockZ < 0 && blockZ < size * -1
                || blockX < 0 && blockX < size * -1 || blockZ > 0 && blockZ > size - 1);
    }
}
