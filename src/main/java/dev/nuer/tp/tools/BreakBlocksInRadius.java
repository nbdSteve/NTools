package dev.nuer.tp.tools;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.events.AquaWandDrainLiquidEvent;
import dev.nuer.tp.events.AquaWandMeltIceEvent;
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
    //Store that the code for aqua tool has been run
    private boolean aquaCodeIsRun = false;

    /**
     * Void method to break blocks in a given radius
     *
     * @param event  the event which is being called
     * @param player the player who is breaking the blocks
     */
    public BreakBlocksInRadius(NBTItem item, BlockDamageEvent event, Player player, String directory,
                               String filePath, boolean multiTool, boolean trenchTool, boolean aquaWand) {
        //Create a new array of block coordinates in relation to the source block
        long start = System.currentTimeMillis();
        //Calculate the total area that needs to be removed, run this async for better performance on main
        // thread
        Bukkit.getScheduler().runTaskAsynchronously(ToolsPlus.instance, () -> {
            //Get the radius of the tool from the tools.yml
            int radiusFromFile = ToolsPlus.getFiles().get(directory).getInt(filePath + ".break-radius");
            //If the tool is a multi, get its current radius
            if (multiTool) {
                radiusFromFile = ChangeToolRadius.getToolRadius(item.getItem().getItemMeta().getLore(), item.getItem(), MapInitializer.multiToolRadiusUnique);
            }
            if (aquaWand) {
                if (PlayerToolCooldown.isOnCooldown(player, "aqua")) return;
                radiusFromFile = ChangeToolRadius.getToolRadius(item.getItem().getItemMeta().getLore(), item.getItem(), MapInitializer.aquaWandRadiusUnique);
            }
            if (item.getBoolean("tools+.omni")) {
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
                            BlockBreakEvent radialBreak = new BlockBreakEvent(currentBlock, player);
                            Bukkit.getServer().getPluginManager().callEvent(radialBreak);
                            if (aquaWand) {
                                //Run aqua wand melt / drain methods
                                aquaWandMethod(radialBreak, player, item, currentBlock);
                            } else if (trenchTool) {
                                //Run trench tool break methods
                                trenchToolMethod(radialBreak, player, currentBlock);
                            } else {
                                //Run tray tool break methods
                                trayToolMethod(radialBreak, player, currentBlock);
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
        //Run this task just a fraction later otherwise the boolean is not updated
        if (aquaWand) {
            Bukkit.getScheduler().runTaskLater(ToolsPlus.instance, () -> {
                if (aquaCodeIsRun) {
                    DecrementUses.decrementUses(player, "aqua", item, item.getInteger("tools+.uses"));
                    PlayerToolCooldown.setPlayerOnCooldown(player, ToolsPlus.getFiles().get(directory).getInt(filePath + ".cooldown"), "aqua");
                }
            }, 2L);
        }
        //Send the player a message about full inv
        AddBlocksToPlayerInventory.messagedPlayers.remove(player);
        //Log stats if enabled
        if (ToolsPlus.debugMode) {
            long finish = System.currentTimeMillis();
            ToolsPlus.LOGGER.info("[Tools+] (Trench / Tray / Aqua Debug) Block removal operation completed in: " + (finish - start) + "ms");
        }
    }

    /**
     * Method that handles aqua wand block melting and draining
     *
     * @param event        BlockBreakEvent, the new event being called
     * @param item         NBTItem, the item being used
     * @param currentBlock Block, the block to check
     */
    private void aquaWandMethod(BlockBreakEvent event, Player player, NBTItem item, Block currentBlock) {
        //Check that the event is not cancelled
        if (event.isCancelled()) return;
        //Check which mode the players tool is in
        if (ChangeMode.changeToolMode(item.getItem().getItemMeta().getLore(), item.getItem().getItemMeta(),
                item.getItem(), MapInitializer.aquaWandModeUnique, false)) {
            if (currentBlock.getType().equals(Material.ICE)) {
                this.aquaCodeIsRun = true;
                Bukkit.getScheduler().runTask(ToolsPlus.instance, () -> {
                    Bukkit.getPluginManager().callEvent(new AquaWandMeltIceEvent(currentBlock, player));
                });
            }
        } else {
            if (currentBlock.isLiquid()) {
                this.aquaCodeIsRun = true;
                Bukkit.getScheduler().runTask(ToolsPlus.instance, () -> {
                    Bukkit.getPluginManager().callEvent(new AquaWandDrainLiquidEvent(currentBlock, player));
                });
            }
        }
    }

    /**
     * Method that handles trench block breaking
     *
     * @param event        BlockBreakEvent, the new event being called
     * @param player       Player, player using the tool
     * @param currentBlock Block, the block to check
     */
    private void trenchToolMethod(BlockBreakEvent event, Player player, Block currentBlock) {
        //Check that the block should be broken
        if (MapInitializer.trenchBlockBlacklist.contains(currentBlock.getType().toString())) {
            event.setCancelled(true);
        } else {
            //Check that the event is not cancelled
            if (event.isCancelled()) return;
            //Run the task to break the blocks sync
            Bukkit.getScheduler().runTask(ToolsPlus.instance, () -> {
                Bukkit.getPluginManager().callEvent(new TrenchBlockBreakEvent(currentBlock, player));
            });
        }
    }

    /**
     * Method that handles tray block breaking
     *
     * @param event        BlockBreakEvent, the new event being called
     * @param player       Player, player using the tool
     * @param currentBlock Block, the block to check
     */
    private void trayToolMethod(BlockBreakEvent event, Player player, Block currentBlock) {
        //Check that the block should be broken
        if (!MapInitializer.trayBlockWhitelist.contains(currentBlock.getType().toString())) {
            event.setCancelled(true);
        } else {
            //Check that the event is not cancelled
            if (event.isCancelled()) return;
            //Run the task to break the blocks sync
            Bukkit.getScheduler().runTask(ToolsPlus.instance, () -> {
                Bukkit.getPluginManager().callEvent(new TrayBlockBreakEvent(currentBlock, player));
            });
        }
    }
}