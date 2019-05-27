package dev.nuer.tp.tools.chunk;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.events.BlockRemovalByChunkToolEvent;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Class that handles removing all of the blocks in a given chunk
 */
public class ChunkRemoval {

    public static ArrayList<Chunk> pendingChunks = new ArrayList<>();

    public static void removeChunksInRadius(Chunk startingChunk, Player player, int radius) {
        int chunkX = radius-1, chunkZ = radius-1;
        while (chunkZ > -radius) {
            while (chunkX > -radius) {
                Chunk chunk = player.getWorld().getChunkAt(startingChunk.getX() + chunkX, startingChunk.getZ() + chunkZ);
                removeChunk(chunk, player);
                chunkX--;
            }
            chunkX = radius-1;
            chunkZ--;
        }
    }

    private static void removeChunk(Chunk chunkToRemove, Player player) {
        //if (pendingChunks == null) pendingChunks = new ArrayList<>();
        if (pendingChunks.contains(chunkToRemove)) {
            new PlayerMessage("chunk-already-queued", player,
                    "{x}", String.valueOf(chunkToRemove.getX()),
                    "{z}", String.valueOf(chunkToRemove.getZ()));
            return;
        } else {
            new PlayerMessage("chunk-added-to-queue", player,
                    "{x}", String.valueOf(chunkToRemove.getX()),
                    "{z}", String.valueOf(chunkToRemove.getZ()));
            pendingChunks.add(chunkToRemove);
        }
        new BukkitRunnable() {
            int delay = FileManager.get("chunk_tool_config").getInt("chunk-removal-delay");

            @Override
            public void run() {
                if (delay > 0) {
                    delay--;
                } else if (pendingChunks.contains(chunkToRemove)) {
                    new PlayerMessage("starting-chunk-removal", player,
                            "{x}", String.valueOf(chunkToRemove.getX()),
                            "{z}", String.valueOf(chunkToRemove.getZ()));
                    chunkRemovalTask(chunkToRemove, player);
                    cancel();
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(ToolsPlus.instance, 0L, 20L);
    }

    private static void chunkRemovalTask(Chunk chunkToRemove, Player player) {
        ArrayList<Block> blocksToRemove = new ArrayList<>();
        Bukkit.getScheduler().runTaskAsynchronously(ToolsPlus.instance, () -> {
            int x = 0, y = 255, z = 0;
            while (y > 0) {
                while (z < 16) {
                    while (x < 16) {
                        if (chunkToRemove.getBlock(x, y, z).getType().equals(Material.AIR)) {
                            //continue;
                        } else if (FileManager.get("chunk_tool_config").getStringList("buster-block-blacklist").contains(chunkToRemove.getBlock(x, y, z).getType().toString().toLowerCase())) {
                            //continue;
                        } else {
                            blocksToRemove.add(chunkToRemove.getBlock(x, y, z));
                        }
                        x++;
                    }
                    z++;
                    x = 0;
                }
                z = 0;
                y--;
            }
            new BukkitRunnable() {
                int indexOfArray = 0;

                @Override
                public void run() {
                    if (indexOfArray < blocksToRemove.size()) {
                        for (int i = 0; i < FileManager.get("chunk_tool_config").getInt("removal-grouping-size"); i++) {
                            try {
                                BlockBreakEvent blockBreak = new BlockBreakEvent(blocksToRemove.get(indexOfArray), player);
                                Bukkit.getPluginManager().callEvent(blockBreak);
                                if (!blockBreak.isCancelled()) {
                                    //Call custom event
                                    Bukkit.getPluginManager().callEvent(new BlockRemovalByChunkToolEvent(blocksToRemove.get(indexOfArray), player));
                                }
                                indexOfArray++;
                            } catch (IndexOutOfBoundsException e) {
                                cancel();
                                pendingChunks.remove(chunkToRemove);
                            }
                        }
                    } else {
                        cancel();
                        pendingChunks.remove(chunkToRemove);
                    }
                }
            }.runTaskTimer(ToolsPlus.instance, 0L, FileManager.get("chunk_tool_config").getInt("delay-between-grouping-removal"));
        });
    }
}
