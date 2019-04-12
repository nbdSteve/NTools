package dev.nuer.nt.event.sandWand;

import dev.nuer.nt.NTools;
import dev.nuer.nt.method.BlockInBorderCheck;
import dev.nuer.nt.method.player.AddBlocksToPlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class SandRemovalRunnable {

    public static void genericSandRemoval(Player player, ArrayList<Block> blocksToRemove, long breakDelay) {
        new BukkitRunnable() {
            int arrayPosition = 0;

            @Override
            public void run() {
                if (arrayPosition < blocksToRemove.size()) {
                    String currentBlockType = blocksToRemove.get(arrayPosition).getType().toString();
                    if (NTools.sandWandBlockWhitelist.contains(currentBlockType)) {
                        BlockBreakEvent stackRemove = new BlockBreakEvent(blocksToRemove.get(arrayPosition), player);
                        Bukkit.getPluginManager().callEvent(stackRemove);
                        if (stackRemove.isCancelled()) {
                        } else if (BlockInBorderCheck.isInsideBorder(stackRemove.getBlock(), player)) {
                        } else if (NTools.sandWandBlockWhitelist.contains(currentBlockType)) {
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
