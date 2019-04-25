package dev.nuer.nt.listener;

import dev.nuer.nt.ToolsPlus;
import dev.nuer.nt.events.*;
import dev.nuer.nt.method.player.AddBlocksToPlayerInventory;
import dev.nuer.nt.tools.tnt.BankContentsOfChest;
import dev.nuer.nt.tools.tnt.CraftContentsOfChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Class that listens for custom plugin events
 */
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
            }
        }
    }

    @EventHandler
    public void harvesterBlockBreakEvent(HarvesterBlockBreakEvent event) {
        if (!event.isCancelled()) {
            if (event.isSelling()) {
                if (ToolsPlus.economy != null) {
                    AddBlocksToPlayerInventory.sellBlocks(event.getBlockToHarvest(), event.getPlayer());
                    ToolsPlus.economy.depositPlayer(event.getPlayer(), event.getBlockPrice());
                }
            } else {
                AddBlocksToPlayerInventory.addBlocks(event.getBlockToHarvest(), event.getPlayer());
            }
        }
    }

    @EventHandler
    public void sellWandChestSaleEvent(SellWandContainerSaleEvent event) {
        if (!event.isCancelled()) {
            ToolsPlus.economy.depositPlayer(event.getPlayer(), event.getItemPrice());
        }
    }

    @EventHandler
    public void tntCraftContentsEvent(TNTWandCraftEvent event) {
        if (!event.isCancelled()) {
            CraftContentsOfChest.craftChestContents(event.getPlayer(), event.getCraftingModifier(), event.getChestBeingAffected());
        }
    }

    @EventHandler
    public void tntBankContentsEvent(TNTWandBankEvent event) {
        if (!event.isCancelled()) {
            BankContentsOfChest.getTNTCountForChest(event.getPlayer(), event.getChestBeingAffected());
        }
    }
}
