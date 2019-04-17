package dev.nuer.nt.listener;

import dev.nuer.nt.events.LightningWandStrikeEvent;
import dev.nuer.nt.events.SandBlockBreakEvent;
import dev.nuer.nt.events.TrayBlockBreakEvent;
import dev.nuer.nt.events.TrenchBlockBreakEvent;
import dev.nuer.nt.method.player.AddBlocksToPlayerInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ToolsListener implements Listener {

    @EventHandler
    public void blockBreakByTrenchTool(TrenchBlockBreakEvent event) {
        if (!event.isCancelled()) {
            AddBlocksToPlayerInventory.addBlocks(event.getBlockToBeBroken(), event.getPlayer());
        }
    }

    @EventHandler
    public void blockBreakByTrayTool(TrayBlockBreakEvent event) {
        if (!event.isCancelled()) {
            AddBlocksToPlayerInventory.addBlocks(event.getBlockToBeBroken(), event.getPlayer());
        }
    }

    @EventHandler
    public void sandWandBlockBreak(SandBlockBreakEvent event) {
        if (!event.isCancelled()) {
            AddBlocksToPlayerInventory.addBlocks(event.getBlockToBeBroken(), event.getPlayer());
        }
    }

    @EventHandler
    public void lightningWandStrikeEvent(LightningWandStrikeEvent event) {
        if (!event.isCancelled()) {
            event.getBlockToStrike().getWorld().strikeLightning(event.getBlockToStrike().getLocation());
            event.getPlayer().setLastDamage(0);
        }
    }
}
