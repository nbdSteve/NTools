package dev.nuer.tp.tools;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.events.TrayBlockBreakEvent;
import dev.nuer.tp.events.TrenchBlockBreakEvent;
import dev.nuer.tp.external.nbtapi.NBTItem;
import dev.nuer.tp.initialize.MapInitializer;
import dev.nuer.tp.method.player.AddBlocksToPlayerInventory;
import dev.nuer.tp.tools.multi.ChangeToolRadius;
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
     * @param event    the event which is being called
     * @param player   the player who is breaking the blocks
     */
    public BreakBlocksInRadius(NBTItem item, BlockDamageEvent event, Player player, String directory,
                               String filePath, boolean multiTool, boolean trenchTool) {
        //Create a new array of block coordinates in relation to the source block
        long start = System.currentTimeMillis();
        //Calculate the total area that needs to be removed, run this async for better performance on main
        // thread
        Bukkit.getScheduler().runTaskAsynchronously(ToolsPlus.getPlugin(ToolsPlus.class), () -> {
            //Get the radius of the tool from the tools.yml
            int radiusFromFile = ToolsPlus.getFiles().get(directory).getInt(filePath + ".break-radius");
            //If the tool is a multi, get its current radius
            if (multiTool) {
                radiusFromFile = ChangeToolRadius.getToolRadius(item.getItem().getItemMeta().getLore(), item.getItem());
            }
            if (item.getBoolean("ntool.omni")) {
                OmniFunctionality.changeToolType(event.getBlock(), player);
            }
            //Store the break radius for the tool
            int radiusX = -radiusFromFile;
            int radiusY = -radiusFromFile;
            int radiusZ = -radiusFromFile;
            int radius = radiusFromFile;
            //Calculate the break area
            while (radiusY < radius + 1) {
                while (radiusZ < radius + 1) {
                    while (radiusX < radius + 1) {
                        Block currentBlock = event.getBlock().getRelative(radiusX, radiusY, radiusZ);
                        if (currentBlock.getType().equals(Material.AIR)) {
                            //Air block, do nothing
                        } else {
                            if (trenchTool) {
                                //Check that the block should be broken
                                if (MapInitializer.trenchBlockBlacklist.contains(currentBlock.getType().toString())) {
                                    event.setCancelled(true);
                                } else {
                                    //Run the task to break the blocks sync
                                    Bukkit.getScheduler().runTask(ToolsPlus.getPlugin(ToolsPlus.class), () -> {
                                        BlockBreakEvent radialBreak = new BlockBreakEvent(currentBlock, player);
                                        Bukkit.getServer().getPluginManager().callEvent(radialBreak);
                                        if (!radialBreak.isCancelled()) {
                                            Bukkit.getPluginManager().callEvent(new TrenchBlockBreakEvent(currentBlock, player));
                                        }
                                    });
                                }
                            } else {
                                if (!MapInitializer.trayBlockWhitelist.contains(currentBlock.getType().toString())) {
                                    event.setCancelled(true);
                                } else {
                                    Bukkit.getScheduler().runTask(ToolsPlus.getPlugin(ToolsPlus.class), () -> {
                                        BlockBreakEvent radialBreak = new BlockBreakEvent(currentBlock, player);
                                        Bukkit.getServer().getPluginManager().callEvent(radialBreak);
                                        if (!radialBreak.isCancelled()) {
                                            Bukkit.getPluginManager().callEvent(new TrayBlockBreakEvent(currentBlock, player));
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
        //Send the player a message about full inv
        AddBlocksToPlayerInventory.messagedPlayers.remove(player);
        //Log stats if enabled
        if (ToolsPlus.doDebugMessages) {
            long finish = System.currentTimeMillis();
            ToolsPlus.LOGGER.info("[ToolsPlus] Mining tool block removal operation completed in: " + (finish - start) + "ms");
        }
    }
}