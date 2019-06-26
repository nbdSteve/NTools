package dev.nuer.tp.tools;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.events.AquaWandDrainLiquidEvent;
import dev.nuer.tp.events.AquaWandMeltIceEvent;
import dev.nuer.tp.events.TrayBlockBreakEvent;
import dev.nuer.tp.events.TrenchBlockBreakEvent;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.managers.ToolsAttributeManager;
import dev.nuer.tp.method.player.AddBlocksToPlayerInventory;
import dev.nuer.tp.support.nbtapi.NBTItem;
import dev.nuer.tp.tools.multi.ChangeToolRadius;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;

import java.util.ArrayList;

/**
 * Class that handles breaking blocks for the trench, tray and multi tools
 */
public class BreakBlocksInRadius {
    //Store that the code for aqua tool has been run
    private boolean aquaCodeIsRun = false;
    //Store if trench code has been run
    private boolean trenchCodeRun = false;
    //Store if tray code has been run
    private boolean trayCodeRun = false;
    //Store the array list of blocks to be broken
    private ArrayList<Block> breakableBlocks = new ArrayList<>();

    /**
     * Void method to break blocks in a given radius
     *
     * @param event  the event which is being called
     * @param player the player who is breaking the blocks
     */
    public BreakBlocksInRadius(NBTItem item, BlockDamageEvent event, Player player, String directory,
                               String filePath, boolean multiTool, boolean trenchTool, boolean aquaWand) {
        //Verify that the player is not on cooldown
        if (PlayerToolCooldown.isOnCooldown(player, "aqua")) return;
        //Create a new array of block coordinates in relation to the source block
        long start = System.currentTimeMillis();
        //Calculate the total area that needs to be removed, run this async for better performance on main
        // thread
        Bukkit.getScheduler().runTaskAsynchronously(ToolsPlus.instance, () -> {
            //Get the radius of the tool from the tools.yml
            int radiusFromFile = FileManager.get(directory).getInt(filePath + ".break-radius");
            //If the tool is a multi, get its current radius
            if (multiTool) {
                radiusFromFile = ChangeToolRadius.getToolRadius(item.getItem().getItemMeta().getLore(), item.getItem(), ToolsAttributeManager.multiToolRadiusUnique);
            }
            if (aquaWand) {
                radiusFromFile = ChangeToolRadius.getToolRadius(item.getItem().getItemMeta().getLore(), item.getItem(), ToolsAttributeManager.aquaWandRadiusUnique);
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
                            if (aquaWand) {
                                if (currentBlock.getType().equals(Material.ICE) || currentBlock.isLiquid()) {
                                    this.breakableBlocks.add(currentBlock);
                                }
                            } else if (trenchTool) {
                                if (!ToolsAttributeManager.trenchBlockBlacklist.contains(currentBlock.getType().toString())) {
                                    this.breakableBlocks.add(currentBlock);
                                }
                            } else {
                                if (ToolsAttributeManager.trayBlockWhitelist.contains(currentBlock.getType().toString())) {
                                    this.breakableBlocks.add(currentBlock);
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
            Bukkit.getScheduler().runTask(ToolsPlus.instance, () -> {
                for (Block block : breakableBlocks) {
                    breakBlock(block, player, item, aquaWand, trenchTool);
                }
                if (aquaCodeIsRun) {
                    DecrementUses.decrementUses(player, "aqua-wand", item, item.getInteger("tools+.uses"));
                    PlayerToolCooldown.setPlayerOnCooldown(player, FileManager.get(directory).getInt(filePath + ".cooldown"), "aqua");
                } else if (trenchCodeRun) {
                    if (multiTool) {
                        DecrementUses.decrementUses(player, "multi-tool", item, item.getInteger("tools+.uses"));
                    } else {
                        DecrementUses.decrementUses(player, "trench-tool", item, item.getInteger("tools+.uses"));
                    }
                } else if (trayCodeRun) {
                    if (multiTool) {
                        DecrementUses.decrementUses(player, "multi-tool", item, item.getInteger("tools+.uses"));
                    } else {
                        DecrementUses.decrementUses(player, "tray-tool", item, item.getInteger("tools+.uses"));
                    }
                }
            });
        });
        //Send the player a message about full inv
        AddBlocksToPlayerInventory.messagedPlayers.remove(player);
        //Log stats if enabled
        if (ToolsPlus.debugMode) {
            long finish = System.currentTimeMillis();
            ToolsPlus.LOGGER.info("[Tools+] (Trench / Tray / Aqua Debug) Block removal operation completed in: " + (finish - start) + "ms");
        }
    }

    /**
     * Breaks to given block based on the tool being used
     *
     * @param block      Block, the block to the broken
     * @param player     Player, the player breaking the block
     * @param item       NBTItem, the item being used
     * @param aquaWand   boolean, if the tool is an aqua wand
     * @param trenchTool boolean, if the tool is a trench tool
     */
    private void breakBlock(Block block, Player player, NBTItem item, boolean aquaWand, boolean trenchTool) {
        BlockBreakEvent radialBreak = new BlockBreakEvent(block, player);
        Bukkit.getPluginManager().callEvent(radialBreak);
        if (radialBreak.isCancelled()) return;
        if (aquaWand) {
            aquaWandMethod(block, player, item);
        } else if (trenchTool) {
            Bukkit.getPluginManager().callEvent(new TrenchBlockBreakEvent(block, player));
            trenchCodeRun = true;
        } else {
            Bukkit.getPluginManager().callEvent(new TrayBlockBreakEvent(block, player));
            trayCodeRun = true;
        }
    }

    /**
     * Method that handles aqua wand block melting and draining
     *
     * @param currentBlock Block, the block to check
     * @param player       Player, the player breaking
     * @param item         NBTItem, the item being used
     */
    private void aquaWandMethod(Block currentBlock, Player player, NBTItem item) {
        //Check which mode the players tool is in
        if (ChangeMode.changeToolMode(item.getItem().getItemMeta().getLore(), item.getItem().getItemMeta(),
                item.getItem(), ToolsAttributeManager.aquaWandModeUnique, false)) {
            if (currentBlock.getType().equals(Material.ICE)) {
                this.aquaCodeIsRun = true;
                Bukkit.getPluginManager().callEvent(new AquaWandMeltIceEvent(currentBlock, player));
            }
        } else {
            if (currentBlock.isLiquid()) {
                this.aquaCodeIsRun = true;
                Bukkit.getPluginManager().callEvent(new AquaWandDrainLiquidEvent(currentBlock, player));
            }
        }
    }
}