package dev.nuer.nt.tools.sand;

import dev.nuer.nt.NTools;
import dev.nuer.nt.events.SandBlockBreakEvent;
import dev.nuer.nt.initialize.MapInitializer;
import dev.nuer.nt.method.player.AddBlocksToPlayerInventory;
import dev.nuer.nt.tools.PlayerToolCooldown;
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

    public RemoveSandStack(BlockDamageEvent event, Player player, String directory,
                           String filePath) {
        int cooldownFromConfig = NTools.getFiles().get(directory).getInt(filePath + ".cooldown");
        Bukkit.getScheduler().runTaskAsynchronously(NTools.getPlugin(NTools.class), () -> {
            if (PlayerToolCooldown.isOnCooldown(player, "sand")) {
                return;
            } else {
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
            genericSandRemoval(player, blocksToRemove, NTools.getFiles().get(directory).getLong(filePath + ".break-delay"));
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
        }.runTaskTimer(NTools.getPlugin(NTools.class), 0, breakDelay);
    }
}