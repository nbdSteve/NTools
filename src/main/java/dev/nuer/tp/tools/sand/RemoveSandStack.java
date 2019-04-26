package dev.nuer.tp.tools.sand;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.events.SandBlockBreakEvent;
import dev.nuer.tp.external.nbtapi.NBTItem;
import dev.nuer.tp.initialize.MapInitializer;
import dev.nuer.tp.method.player.AddBlocksToPlayerInventory;
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
    public RemoveSandStack(BlockDamageEvent event, Player player, String directory,
                           String filePath, NBTItem nbtItem) {
        int cooldownFromConfig = ToolsPlus.getFiles().get(directory).getInt(filePath + ".cooldown");
        Bukkit.getScheduler().runTaskAsynchronously(ToolsPlus.getPlugin(ToolsPlus.class), () -> {
            if (PlayerToolCooldown.isOnCooldown(player, "sand")) {
                return;
            } else {
                DecrementUses.decrementUses(player, "sand", nbtItem, nbtItem.getInteger("ntool.uses"));
                PlayerToolCooldown.setPlayerOnCooldown(player, cooldownFromConfig, "sand");
            }
            int positionX = event.getBlock().getX();
            int positionY = 255;
            int positionZ = event.getBlock().getZ();
            ArrayList<Block> blocksToRemove = new ArrayList<>();
            while (positionY >= 1) {
                String currentBlockType =
                        player.getWorld().getBlockAt(positionX, positionY, positionZ).getType().toString();
                if (MapInitializer.sandWandBlockWhitelist.contains(currentBlockType)) {
                    blocksToRemove.add(player.getWorld().getBlockAt(positionX, positionY, positionZ));
                }
                positionY--;
            }
            genericSandRemoval(player, blocksToRemove, ToolsPlus.getFiles().get(directory).getLong(filePath + ".break-delay"));
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
                    if (MapInitializer.sandWandBlockWhitelist.contains(currentBlockType)) {
                        BlockBreakEvent stackRemove = new BlockBreakEvent(blocksToRemove.get(arrayPosition), player);
                        Bukkit.getPluginManager().callEvent(stackRemove);
                        if (!stackRemove.isCancelled()) {
                            if (MapInitializer.sandWandBlockWhitelist.contains(currentBlockType)) {
                                Bukkit.getPluginManager().callEvent(new SandBlockBreakEvent(stackRemove.getBlock(), player));
                            }
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
        }.runTaskTimer(ToolsPlus.getPlugin(ToolsPlus.class), 0, breakDelay);
    }
}