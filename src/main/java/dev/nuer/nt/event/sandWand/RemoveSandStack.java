package dev.nuer.nt.event.sandWand;

import dev.nuer.nt.NTools;
import dev.nuer.nt.event.itemMetaMethod.GetToolType;
import dev.nuer.nt.initialize.OtherMapInitializer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;

import java.util.ArrayList;

/**
 * Class that handles creating the array list of blocks to be removed for the sand wands
 */
public class RemoveSandStack {

    /**
     * Method to check that a block is valid and then add it to an array list to be removed
     *
     * @param toolType GetToolType, the type of sand wand
     * @param event    BlockDamageEvent, the event to call this code
     * @param player   Player, player using the sand wand
     */
    public static void removeStack(GetToolType toolType, BlockDamageEvent event, Player player) {
        int cooldownFromConf = NTools.getFiles().get(toolType.getDirectory()).getInt(toolType.getToolType() + ".cooldown");
        if (!SandCooldownCheck.isOnSandWandCooldown(player.getUniqueId(), cooldownFromConf)) {
            int positionX = event.getBlock().getX();
            int positionY = 255;
            int positionZ = event.getBlock().getZ();
            ArrayList<Block> blocksToRemove = new ArrayList<>();
            while (positionY >= 1) {
                String currentBlockType = player.getWorld().getBlockAt(positionX, positionY, positionZ).getType().toString();
                if (OtherMapInitializer.sandWandBlockWhitelist.contains(currentBlockType)) {
                    blocksToRemove.add(player.getWorld().getBlockAt(positionX, positionY, positionZ));
                }
                positionY--;
            }
            SandRemovalRunnable.genericSandRemoval(player, blocksToRemove,
                    NTools.getFiles().get("sand").getLong(toolType.getToolType() + ".break-delay"));
        }
    }
}