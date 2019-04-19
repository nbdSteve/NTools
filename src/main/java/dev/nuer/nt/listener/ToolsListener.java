package dev.nuer.nt.listener;

import dev.nuer.nt.NTools;
import dev.nuer.nt.events.*;
import dev.nuer.nt.external.actionbarapi.ActionBarAPI;
import dev.nuer.nt.method.player.AddBlocksToPlayerInventory;
import dev.nuer.nt.tools.harvest.HandleSellingMessages;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.text.DecimalFormat;

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
            if (event.getCreeperToPower() != null) {
                event.getBlockToStrike().getWorld().strikeLightningEffect(event.getBlockToStrike().getLocation());
                event.getCreeperToPower().setPowered(true);
            } else {
                event.getBlockToStrike().getWorld().strikeLightning(event.getBlockToStrike().getLocation());
                event.getPlayer().setLastDamage(0.0);
            }
        }
    }

    @EventHandler
    public void harvesterBlockBreakEvent(HarvesterBlockBreakEvent event) {
        if (!event.isCancelled()) {
            if (event.isSelling()) {
                if (NTools.economy != null) {
                    AddBlocksToPlayerInventory.sellBlocks(event.getBlockToHarvest(), event.getPlayer());
                    NTools.economy.depositPlayer(event.getPlayer(), event.getBlockPrice());
                    //Use action bar messages if they are enabled
                    if (NTools.getFiles().get("config").getBoolean("harvester-action-bar.enabled")) {
                        //Create the action bar message
                        String message = NTools.getFiles().get("config").getString("harvester-action-bar.message").replace("{deposit}",
                                new DecimalFormat("##.00").format(event.getBlockPrice() * event.getNumberOfBlocksToHarvest()));
                        //Send it to the player
                        ActionBarAPI.sendActionBar(event.getPlayer(), ChatColor.translateAlternateColorCodes('&', message));
                    } else {
                        HandleSellingMessages.handleSellingMessages(event.getPlayer(), event.getBlockPrice());
                    }
                }
            } else {
                AddBlocksToPlayerInventory.addBlocks(event.getBlockToHarvest(), event.getPlayer());
            }
        }
    }
}
