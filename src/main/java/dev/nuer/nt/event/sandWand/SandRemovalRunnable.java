package dev.nuer.nt.event.sandWand;

import dev.nuer.nt.NTools;
import dev.nuer.nt.initialize.OtherMapInitializer;
import dev.nuer.nt.method.BlockInBorderCheck;
import dev.nuer.nt.method.player.AddBlocksToPlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Class that handles removing blocks from an array over a specified time period
 */
public class SandRemovalRunnable {

    /**
     * Remove blocks from the specified array with the specified delay between each removal
     *
     * @param player         Player, player who is breaking the blocks
     * @param blocksToRemove ArrayList<Block>, blocks to remove
     * @param breakDelay     Long, delay in ticks between each break
     */
    public static void genericSandRemoval(Player player, ArrayList<Block> blocksToRemove, long breakDelay) {
        new BukkitRunnable() {
            int arrayPosition = 0;

            @Override
            public void run() {
                if (arrayPosition < blocksToRemove.size()) {
                    String currentBlockType = blocksToRemove.get(arrayPosition).getType().toString();
                    if (OtherMapInitializer.sandWandBlockWhitelist.contains(currentBlockType)) {
                        BlockBreakEvent stackRemove = new BlockBreakEvent(blocksToRemove.get(arrayPosition), player);
                        Bukkit.getPluginManager().callEvent(stackRemove);
                        if (stackRemove.isCancelled()) {
                        } else if (BlockInBorderCheck.isInsideBorder(stackRemove.getBlock(), player)) {
                        } else if (OtherMapInitializer.sandWandBlockWhitelist.contains(currentBlockType)) {
                            AddBlocksToPlayerInventory.addBlocks(stackRemove.getBlock(), player);
                        }
                    }
                    arrayPosition++;
                } else {
                    if (AddBlocksToPlayerInventory.messagedPlayers.contains(player)) {
                        AddBlocksToPlayerInventory.messagedPlayers.remove(player);
                    }
                    this.cancel();
                }
            }
        }.runTaskTimer(NTools.getPlugin(NTools.class), 0, breakDelay);
    }
}
