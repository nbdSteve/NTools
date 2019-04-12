package dev.nuer.nt.event.miningTool;

import dev.nuer.nt.NTools;
import dev.nuer.nt.event.itemMetaMethod.GetToolType;
import dev.nuer.nt.method.BlockInBorderCheck;
import dev.nuer.nt.method.player.AddBlocksToPlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;

public class BreakBlocksInRadius {

    public static void breakBlocks(GetToolType toolType, BlockDamageEvent event, Player player) {
        //Get the radius of the tool from the tools.yml
        int radiusFromConf = NTools.getFiles().get(toolType.getDirectory()).getInt(toolType.getToolType() + ".break-radius");
        //If the tool is a multi, get its current radius
        if (toolType.getIsMultiTool()) {
            radiusFromConf = toolType.getMultiToolRadius();
        }
        //Store the break radius for the tool
        int radiusX = -radiusFromConf;
        int radiusY = -radiusFromConf;
        int radiusZ = -radiusFromConf;
        int radius = radiusFromConf;
        //Store system current time (for optimization checking)
        long start2 = System.currentTimeMillis();
        //Do block removal method
        while (radiusY < radius + 1) {
            while (radiusZ < radius + 1) {
                while (radiusX < radius + 1) {
                    //Creating a new event to check if the radial blocks can be broken
                    //This should support any plugin
                    BlockBreakEvent radialBreak =
                            new BlockBreakEvent(event.getBlock().getRelative(radiusX, radiusY, radiusZ), player);
                    String currentBlockType = radialBreak.getBlock().getType().toString();
                    Bukkit.getPluginManager().callEvent(radialBreak);
                    //Check if the event is cancelled, if true then don't break that block
                    if (radialBreak.isCancelled()) {
                    } else if (BlockInBorderCheck.isInsideBorder(radialBreak.getBlock(),
                            player)) {
                    } else {
                        if (toolType.getIsTrenchTool()) {
                            if (NTools.trenchBlockBlacklist.contains(currentBlockType)) {
                                event.setCancelled(true);
                            } else {
                                AddBlocksToPlayerInventory.addBlocks(radialBreak.getBlock(), player);
                            }
                        } else if (toolType.getIsTrayTool()) {
                            if (!NTools.trayBlockWhitelist.contains(currentBlockType)) {
                                event.setCancelled(true);
                            } else {
                                AddBlocksToPlayerInventory.addBlocks(radialBreak.getBlock(), player);
                            }
                        }
                    }
                    radiusX++;
                }
                radiusX = -radiusFromConf;
                radiusZ++;
            }
            radiusZ = -radiusFromConf;
            radiusY++;
        }
        //This is to stop message spam if their inventory is full
        if (AddBlocksToPlayerInventory.messagedPlayers.contains(player)) {
            AddBlocksToPlayerInventory.messagedPlayers.remove(player);
        }
        //Calculate and print the execution time for the method
        if (NTools.doDebugMessages) {
            long finish2 = System.currentTimeMillis();
            NTools.LOGGER.info("[NTools] Trench / Tray block removal operation completed in: " + (finish2 - start2) + "ms");
        }
    }
}
