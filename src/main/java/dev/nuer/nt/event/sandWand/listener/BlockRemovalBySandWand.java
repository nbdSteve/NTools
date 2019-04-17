package dev.nuer.nt.event.sandWand.listener;

import dev.nuer.nt.event.custom.BlockBreakBySandWand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockRemovalBySandWand implements Listener {

    @EventHandler
    public void sandWandBlockBreak(BlockBreakBySandWand event) {
//        AddBlocksToPlayerInventory.addBlocks(event.getBlockToBeBroken(), event.getPlayer());
    }
}
