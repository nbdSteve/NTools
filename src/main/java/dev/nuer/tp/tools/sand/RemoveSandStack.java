package dev.nuer.tp.tools.sand;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.events.SandWandBlockBreakEvent;
import dev.nuer.tp.listener.BlockBreakListener;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.managers.ToolsAttributeManager;
import dev.nuer.tp.method.player.AddBlocksToPlayerInventory;
import dev.nuer.tp.support.nbt.NBTItem;
import dev.nuer.tp.tools.DecrementUses;
import dev.nuer.tp.tools.PlayerToolCooldown;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Class that handles creating the array list of blocks to be removed for the sand wands
 */
public class RemoveSandStack {

    /**
     * Will remove the vertical pillar of sand when a player has clicked it
     *
     * @param event     BlockDamageEvent, the event triggering the sand removal
     * @param player    Player, the player who clicked
     * @param directory String, the file to read values from
     * @param filePath  String, the internal file path to get values from
     * @param nbtItem   NBTItem, the item used
     */
    public RemoveSandStack(Block block, Player player, String directory,
                           String filePath, NBTItem nbtItem) {
        int cooldownFromConfig = FileManager.get(directory).getInt(filePath + ".cooldown");
        Bukkit.getScheduler().runTaskAsynchronously(ToolsPlus.instance, () -> {
            if (PlayerToolCooldown.isOnCooldown(player, "sand")) {
                return;
            } else {
                DecrementUses.decrementUses(player, "sand-wand", nbtItem, nbtItem.getInteger("tools+.uses"));
                PlayerToolCooldown.setPlayerOnCooldown(player, cooldownFromConfig, "sand");
            }
            int positionX = block.getX();
            int positionY = 255;
            int positionZ = block.getZ();
            ArrayList<Block> blocksToRemove = new ArrayList<>();
            while (positionY >= 1) {
                String currentBlockType =
                        player.getWorld().getBlockAt(positionX, positionY, positionZ).getType().toString();
                if (ToolsAttributeManager.sandWandBlockWhitelist.contains(currentBlockType)) {
                    blocksToRemove.add(player.getWorld().getBlockAt(positionX, positionY, positionZ));
                }
                positionY--;
            }
            genericSandRemoval(player, blocksToRemove, FileManager.get(directory).getLong(filePath + ".break-delay"));
        });
    }

    /**
     * Remove blocks from the specified array with the specified delay between each removal
     *
     * @param player         Player, player who is breaking the blocks
     * @param blocksToRemove ArrayList<Block>, blocks to remove
     * @param breakDelay     Long, delay in ticks between each break
     */
    private static void genericSandRemoval(Player player, ArrayList<Block> blocksToRemove, long breakDelay) {
        new BukkitRunnable() {
            int arrayPosition = 0;

            @Override
            public void run() {
                if (arrayPosition < blocksToRemove.size()) {
                    String currentBlockType = blocksToRemove.get(arrayPosition).getType().toString();
                    if (ToolsAttributeManager.sandWandBlockWhitelist.contains(currentBlockType)) {
                        //Generate a new block break event for the coming block
                        BlockBreakEvent stackRemove = new BlockBreakEvent(blocksToRemove.get(arrayPosition), player);
                        //Check if the tool should affect other plugins for all of the blocks broken, if not then store the block
                        if (!FileManager.get("config").getBoolean("register-all-block-break.sand-wands"))
                            BlockBreakListener.pendingBlocks.add(blocksToRemove.get(arrayPosition));
                        //Call the block break event
                        Bukkit.getPluginManager().callEvent(stackRemove);
                        //If the event is cancelled and it is affecting other plugins return, if not affecting the block wont be added to list
                        if (stackRemove.isCancelled() && FileManager.get("config").getBoolean("register-all-block-break.sand-wands"))
                            return;
                        if (!BlockBreakListener.pendingBlocks.contains(blocksToRemove.get(arrayPosition))) {
                            Bukkit.getPluginManager().callEvent(new SandWandBlockBreakEvent(stackRemove.getBlock(), player));
                        }
                    }
                    arrayPosition++;
                } else {
                    if (AddBlocksToPlayerInventory.messagedPlayers.contains(player)) {
                        AddBlocksToPlayerInventory.messagedPlayers.remove(player);
                    }
                    this.cancel();
                }
            }
        }.runTaskTimer(ToolsPlus.instance, 0, breakDelay);
    }
}