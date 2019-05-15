package dev.nuer.tp.listener;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.events.*;
import dev.nuer.tp.method.player.AddBlocksToPlayerInventory;
import dev.nuer.tp.tools.smelt.SmeltContentsOfChest;
import dev.nuer.tp.tools.tnt.BankContentsOfChest;
import dev.nuer.tp.tools.tnt.CraftContentsOfChest;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Class that listens for custom plugin events
 */
public class CustomToolEventsListener implements Listener {

    @EventHandler
    public void blockBreakByTrenchTool(TrenchBlockBreakEvent event) {
        //Check if the event is cancelled
        if (event.isCancelled()) return;
        //Run the code for trench tools
        AddBlocksToPlayerInventory.addBlocks(event.getBlockToBeBroken(), event.getPlayer());
    }

    @EventHandler
    public void blockBreakByTrayTool(TrayBlockBreakEvent event) {
        //Check if the event is cancelled
        if (event.isCancelled()) return;
        //Run the code for tray tools
        AddBlocksToPlayerInventory.addBlocks(event.getBlockToBeBroken(), event.getPlayer());
    }

    @EventHandler
    public void sandWandBlockBreak(SandWandBlockBreakEvent event) {
        //Check if the event is cancelled
        if (event.isCancelled()) return;
        //Run the code for sand wands
        AddBlocksToPlayerInventory.addBlocks(event.getBlockToBeBroken(), event.getPlayer());
    }

    @EventHandler
    public void lightningWandStrikeEvent(LightningWandStrikeEvent event) {
        //Check if the event is cancelled
        if (event.isCancelled()) return;
        //Run the code for lightning wands
        if (event.getCreeperToPower() != null) {
            event.getBlockToStrike().getWorld().strikeLightningEffect(event.getBlockToStrike().getLocation());
            event.getCreeperToPower().setPowered(true);
        } else {
            event.getBlockToStrike().getWorld().strikeLightning(event.getBlockToStrike().getLocation());
        }
    }

    @EventHandler
    public void harvesterBlockBreakEvent(HarvesterBlockBreakEvent event) {
        //Check if the event is cancelled
        if (event.isCancelled()) return;
        //Run the code for harvester tools
        if (event.isSelling()) {
            if (ToolsPlus.economy != null) {
                AddBlocksToPlayerInventory.sellBlocks(event.getBlockToHarvest(), event.getPlayer());
                ToolsPlus.economy.depositPlayer(event.getPlayer(), event.getBlockPrice());
            }
        } else {
            AddBlocksToPlayerInventory.addBlocks(event.getBlockToHarvest(), event.getPlayer());
        }
    }

    @EventHandler
    public void sellWandChestSaleEvent(SellWandContainerSaleEvent event) {
        //Check if the event is cancelled
        if (event.isCancelled()) return;
        //Run the code for sell wands
        ToolsPlus.economy.depositPlayer(event.getPlayer(), event.getItemPrice());
    }

    @EventHandler
    public void tntCraftContentsEvent(TNTWandCraftEvent event) {
        //Check if the event is cancelled
        if (event.isCancelled()) return;
        //Run the code for tnt craft wands
        CraftContentsOfChest.craftChestContents(event.getPlayer(), event.getCraftingModifier(), event.getChestBeingAffected());
    }

    @EventHandler
    public void tntBankContentsEvent(TNTWandBankEvent event) {
        //Check if the event is cancelled
        if (event.isCancelled()) return;
        //Run the code for tnt bank method
        BankContentsOfChest.getTNTCountForChest(event.getPlayer(), event.getChestBeingAffected());
    }

    @EventHandler
    public void aquaWandMeltIceEvent(AquaWandMeltIceEvent event) {
        //Check if the event is cancelled
        if (event.isCancelled()) return;
        //Run code for the aqua melt method
        event.getBlockToMelt().setType(Material.WATER);
    }

    @EventHandler
    public void aquaWandDrainLiquidEvent(AquaWandDrainLiquidEvent event) {
        //Check if the event is cancelled
        if (event.isCancelled()) return;
        //Run code for the aqua drain liquid method
        event.getBlockToDrain().setType(Material.AIR);
    }

    @EventHandler
    public void chestSmeltEvent(SmeltWandConversionEvent event) {
        //Check if the event is cancelled
        if (event.isCancelled()) return;
        //Run code to smelt the chests contents
        SmeltContentsOfChest.smeltChestContents(event.getPlayer(), event.getChestBeingAffected().getInventory());
    }
}