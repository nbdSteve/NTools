package dev.nuer.tp.tools.harvest;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.events.HarvesterBlockBreakEvent;
import dev.nuer.tp.external.actionbarapi.ActionBarAPI;
import dev.nuer.tp.method.player.AddBlocksToPlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;

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
        ArrayList<Block> blocksToHarvest = new ArrayList<>();
        if (event.getBlock().getType().toString().equalsIgnoreCase("SUGAR_CANE_BLOCK")) {
            connectedBlockRemoval(event.getBlock().getY(), event, player, blocksToHarvest, true);
        } else if (event.getBlock().getType().toString().equalsIgnoreCase("CACTUS")) {
            connectedBlockRemoval(event.getBlock().getY(), event, player, blocksToHarvest, false);
        } else {
            blocksToHarvest.add(event.getBlock());
        }
        double totalDeposit = 0;
        //need to iterate from the top down
        for (int i = blocksToHarvest.size() - 1; i >= 0; i--) {
            BlockBreakEvent blockRemoval = new BlockBreakEvent(blocksToHarvest.get(i), player);
            Bukkit.getPluginManager().callEvent(blockRemoval);
            if (!blockRemoval.isCancelled()) {
                double priceToDeposit = blockPrice * priceModifier;
                Bukkit.getPluginManager().callEvent(new HarvesterBlockBreakEvent(
                        blockRemoval.getBlock(), player, priceToDeposit, sellMode));
                totalDeposit += priceToDeposit;
            }
        }
        if (sellMode) {
            if (ToolsPlus.getFiles().get("config").getBoolean("harvester-action-bar.enabled")) {
                //Create the action bar message
                String message = ToolsPlus.getFiles().get("config").getString("harvester-action-bar.message").replace("{deposit}",
                        ToolsPlus.numberFormat.format(totalDeposit));
                //Send it to the player
                ActionBarAPI.sendActionBar(player, ChatColor.translateAlternateColorCodes('&', message));
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
}
