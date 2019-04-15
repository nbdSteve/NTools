package dev.nuer.nt.event.miningTool;

import dev.nuer.nt.NTools;
import dev.nuer.nt.event.itemMetaMethod.GetToolType;
import dev.nuer.nt.initialize.OtherMapInitializer;
import dev.nuer.nt.method.player.AddBlocksToPlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;

/**
 * Class that handles breaking blocks for the trench, tray and multi tools
 */
public class BreakBlocksInRadius {

    /**
     * Void method to break blocks in a given radius
     *
     * @param toolType the type of tool
     * @param event    the event which is being called
     * @param player   the player who is breaking the blocks
     */
    public BreakBlocksInRadius(GetToolType toolType, BlockDamageEvent event, Player player) {
        //Create a new array of block coordinates in relation to the source block
        long start = System.currentTimeMillis();
        //Calculate the total area that needs to be removed, run this async for better performance on main thread
        Bukkit.getScheduler().runTaskAsynchronously(NTools.getPlugin(NTools.class), () -> {
            //Get the radius of the tool from the tools.yml
            int radiusFromFile = NTools.getFiles().get(toolType.getDirectory()).getInt(toolType.getToolType() + ".break-radius");
            //If the tool is a multi, get its current radius
            if (toolType.getIsMultiTool()) {
                radiusFromFile = toolType.getMultiToolRadius();
            }
            //Store the break radius for the tool
            int radiusX = -radiusFromFile;
            int radiusY = -radiusFromFile;
            int radiusZ = -radiusFromFile;
            int radius = radiusFromFile;
            while (radiusY < radius + 1) {
                while (radiusZ < radius + 1) {
                    while (radiusX < radius + 1) {
                        Block currentBlock = event.getBlock().getRelative(radiusX, radiusY, radiusZ);
                        if (currentBlock.getType().equals(Material.AIR)) {
                            //Air block so do nothing
                        } else {
                            if (toolType.getIsTrenchTool()) {
                                if (OtherMapInitializer.trenchBlockBlacklist.contains(currentBlock.getType().toString())) {
                                    event.setCancelled(true);
                                } else {
                                    Bukkit.getScheduler().runTask(NTools.getPlugin(NTools.class), () -> {
                                        BlockBreakEvent radialBreak = new BlockBreakEvent(currentBlock, player);
                                        if (!radialBreak.isCancelled()) {
                                            AddBlocksToPlayerInventory.addBlocks(radialBreak.getBlock(), player);
                                        }
                                    });
                                }
                            } else if (toolType.getIsTrayTool()) {
                                if (!OtherMapInitializer.trayBlockWhitelist.contains(currentBlock.getType().toString())) {
                                    event.setCancelled(true);
                                } else {
                                    Bukkit.getScheduler().runTask(NTools.getPlugin(NTools.class), () -> {
                                        BlockBreakEvent radialBreak = new BlockBreakEvent(currentBlock, player);
                                        if (!radialBreak.isCancelled()) {
                                            AddBlocksToPlayerInventory.addBlocks(radialBreak.getBlock(), player);
                                        }
                                    });
                                }
                            }
                        }
                        radiusX++;
                    }
                    radiusX = -radiusFromFile;
                    radiusZ++;
                }
                radiusZ = -radiusFromFile;
                radiusY++;
            }
        });
        if (AddBlocksToPlayerInventory.messagedPlayers.contains(player)) {
            AddBlocksToPlayerInventory.messagedPlayers.remove(player);
        }
        if (NTools.doDebugMessages) {
            long finish = System.currentTimeMillis();
            NTools.LOGGER.info("[NTools] Trench / Tray block removal operation completed in: " + (finish - start) + "ms");
        }
    }
}