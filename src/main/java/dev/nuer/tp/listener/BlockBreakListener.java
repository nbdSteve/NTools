package dev.nuer.tp.listener;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Small class that handles sorting if the tool block break will affect other plugins
 */
public class BlockBreakListener implements Listener {
    //Store the blocks that are pending break by the tools
    public static List<Block> pendingBlocks = new ArrayList<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        if (pendingBlocks.contains(event.getBlock())) {
            event.setCancelled(true);
            pendingBlocks.remove(event.getBlock());
        }
    }
}