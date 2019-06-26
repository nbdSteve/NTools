package dev.nuer.tp.tools.harvest;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.events.HarvesterBlockBreakEvent;
import dev.nuer.tp.listener.BlockBreakListener;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.player.AddBlocksToPlayerInventory;
import dev.nuer.tp.method.player.PlayerMessage;
import dev.nuer.tp.support.actionbarapi.ActionBarAPI;
import dev.nuer.tp.support.nbtapi.NBTItem;
import dev.nuer.tp.tools.DecrementUses;
import org.bukkit.Bukkit;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.material.Crops;

import java.util.ArrayList;

/**
 * Method to handle harvesting blocks for harvester hoes
 */
public class HarvestBlock {

    /**
     * Method to harvest the blocks for harvester hoes
     *
     * @param event         BlockDamageEvent, the event to trigger the harvest
     * @param player        Player, the player who is using the tool
     * @param sellMode      boolean, if the tool is in sell mode
     * @param blockPrice    double, the Price of the block being sold
     * @param priceModifier double, the modifier to multiply the price by
     */
    public static void harvestBlocks(BlockDamageEvent event, Player player, boolean sellMode, Double blockPrice, Double priceModifier) {
        //Check an arraylist of the blocks being harvested
        ArrayList<Block> blocksToHarvest = new ArrayList<>();
        //Check if the crop is one of these edge cases
        if (event.getBlock().getType().toString().equalsIgnoreCase("SUGAR_CANE_BLOCK")) {
            connectedBlockRemoval(event.getBlock().getY(), event, player, blocksToHarvest, true);
        } else if (event.getBlock().getType().toString().equalsIgnoreCase("CACTUS")) {
            connectedBlockRemoval(event.getBlock().getY(), event, player, blocksToHarvest, false);
        } else if (event.getBlock().getState().getData() instanceof Crops) {
            if (((Crops) event.getBlock().getState().getData()).getState() == CropState.RIPE) {
                blocksToHarvest.add(event.getBlock());
            } else {
                new PlayerMessage("crop-not-ripe-to-harvest", player);
                return;
            }
        } else if (event.getBlock().getType().equals(Material.CARROT) || event.getBlock().getType().equals(Material.POTATO)) {
            blocksToHarvest.add(event.getBlock());
        } else {
            blocksToHarvest.add(event.getBlock());
        }
        double totalDeposit = 0;
        //need to iterate from the top down
        for (int i = blocksToHarvest.size() - 1; i >= 0; i--) {
            //Generate a new block break event for the coming block
            BlockBreakEvent blockRemoval = new BlockBreakEvent(blocksToHarvest.get(i), player);
            //Check if the tool should affect other plugins for all of the blocks broken, if not then store the block
            if (!FileManager.get("config").getBoolean("register-all-block-break.harvester-tools"))
                BlockBreakListener.pendingBlocks.add(blocksToHarvest.get(i));
            //Call the block break event
            Bukkit.getPluginManager().callEvent(blockRemoval);
            //If the event is cancelled and it is affecting other plugins return, if not affecting the block wont be added to list
            if (blockRemoval.isCancelled() && FileManager.get("config").getBoolean("register-all-block-break.harvester-tools")) return;
            if (!BlockBreakListener.pendingBlocks.contains(blocksToHarvest.get(i))) {
                double priceToDeposit = blockPrice * priceModifier;
                Bukkit.getPluginManager().callEvent(new HarvesterBlockBreakEvent(
                        blockRemoval.getBlock(), player, priceToDeposit, sellMode));
                totalDeposit += priceToDeposit;
            }
        }
        if (sellMode) {
            if (FileManager.get("config").getBoolean("harvester-action-bar.enabled")) {
                //Create the action bar message
                String message = FileManager.get("config").getString("harvester-action-bar.message").replace("{deposit}",
                        ToolsPlus.numberFormat.format(totalDeposit));
                //Send it to the player
                ActionBarAPI.sendActionBar(player, Chat.applyColor(message));
            } else {
                HandleSellingMessages.handleSellingMessages(player, totalDeposit);
            }
        }
        AddBlocksToPlayerInventory.messagedPlayers.remove(player);
    }

    /**
     * Method that handles blocks that are destroyed from the bottom up
     *
     * @param yCoordinate     Integer, starting y coordinate
     * @param event           BlockDamageEvent, the event calling the harvest
     * @param player          Player, the player breaking the blocks
     * @param blocksToHarvest ArrayList<Block>, the blocks to be broken
     * @param sugarCane       boolean, if the block is sugar cane
     */
    public static void connectedBlockRemoval(int yCoordinate, BlockDamageEvent event, Player player, ArrayList<Block> blocksToHarvest, boolean sugarCane) {
        if (sugarCane) {
            while (player.getWorld().getBlockAt(event.getBlock().getX(), yCoordinate, event.getBlock().getZ()).
                    getType().toString().equalsIgnoreCase("SUGAR_CANE_BLOCK") && yCoordinate < 256) {
                blocksToHarvest.add(player.getWorld().getBlockAt(event.getBlock().getX(), yCoordinate, event.getBlock().getZ()));
                yCoordinate++;
            }
        } else {
            while (player.getWorld().getBlockAt(event.getBlock().getX(), yCoordinate, event.getBlock().getZ()).
                    getType().toString().equalsIgnoreCase("CACTUS") && yCoordinate < 256) {
                blocksToHarvest.add(player.getWorld().getBlockAt(event.getBlock().getX(), yCoordinate, event.getBlock().getZ()));
                yCoordinate++;
            }
        }
    }

//    public static void doCropReplant(Block block, NBTItem item) {
//        ToolsPlus.LOGGER.info("Called");
//        ToolsPlus.LOGGER.info("Called 1");
//        if (block.getType().equals(Material.POTATO)
//                || block.getType().equals(Material.CARROT)) {
//            ToolsPlus.LOGGER.info("Called 3");
//            block.setType(block.getType());
//        } else if (block.getState().getData() instanceof Crops) {
//            ToolsPlus.LOGGER.info("Called 2");
//            block.setType(Material.getMaterial(FileManager.get("harvester_tool_config").getString("seeds-replant-name").toUpperCase()));
//        }
//    }
//
//    public static boolean isCrop(Block block) {
//        if (block.getType().equals(Material.POTATO)
//                || block.getType().equals(Material.CARROT)
//                || block.getState().getData() instanceof Crops) {
//            return true;
//        }
//        return false;
//    }
}
