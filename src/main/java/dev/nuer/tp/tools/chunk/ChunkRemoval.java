package dev.nuer.tp.tools.chunk;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.events.BlockRemovalByChunkToolEvent;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.method.player.PlayerMessage;
import dev.nuer.tp.support.nbtapi.NBTItem;
import dev.nuer.tp.tools.DecrementUses;
import dev.nuer.tp.tools.PlayerToolCooldown;
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
    //ArrayList containing all of the chunks that are pending removal
    public static ArrayList<Chunk> pendingChunks = new ArrayList<>();

    /**
     * Removes all of the chunks in the specified radius based off of the players location
     *
     * @param startingChunk Chunk, the centre chunk
     * @param player        Player, the player using the tool
     * @param radius        int, the tools chunk radius
     */
    public static void removeChunksInRadius(Chunk startingChunk, Player player, int radius, NBTItem nbtItem) {
        int cooldownFromConfig = FileManager.get("chunk").getInt("chunk-wands." + nbtItem.getInteger("tools+.raw.id") + ".cooldown");
        if (PlayerToolCooldown.isOnCooldown(player, "chunk")) {
            return;
        } else {
            DecrementUses.decrementUses(player, "chunk", nbtItem, nbtItem.getInteger("tools+.uses"));
            PlayerToolCooldown.setPlayerOnCooldown(player, cooldownFromConfig, "chunk");
        }
        int chunksAddedToQueue = 0;
        int chunksAlreadyQueued = 0;
        int chunkX = radius - 1, chunkZ = radius - 1;
        while (chunkZ > -radius) {
            while (chunkX > -radius) {
                Chunk chunk = player.getWorld().getChunkAt(startingChunk.getX() + chunkX, startingChunk.getZ() + chunkZ);
                if (pendingChunks.contains(chunk)) {
                    chunksAlreadyQueued++;
                } else {
                    chunksAddedToQueue++;
                    pendingChunks.add(chunk);
                    removeChunk(chunk, player);
                }
                chunkX--;
            }
            chunkX = radius - 1;
            chunkZ--;
        }
        new PlayerMessage("update-chunk-queue", player,
                "{x}", String.valueOf(startingChunk.getX()),
                "{z}", String.valueOf(startingChunk.getZ()),
                "{chunksAdded}", String.valueOf(chunksAddedToQueue),
                "{chunksAlreadyQueued}", String.valueOf(chunksAlreadyQueued));
    }

    /**
     * Removes the specified chunk
     *
     * @param chunkToRemove Chunk, the chunk to remove
     * @param player        Player, the player using the tool
     */
    private static void removeChunk(Chunk chunkToRemove, Player player) {
        //if (pendingChunks == null) pendingChunks = new ArrayList<>();
        new BukkitRunnable() {
            int delay = FileManager.get("chunk_wand_config").getInt("chunk-removal-delay");

            @Override
            public void run() {
                if (delay > 0) {
                    delay--;
                } else if (pendingChunks.contains(chunkToRemove)) {
                    chunkRemovalTask(chunkToRemove, player);
                    cancel();
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(ToolsPlus.instance, 0L, 20L);
    }

    /**
     * Creates a BukkitTask to remove all of the blocks inside the specified chunk
     *
     * @param chunkToRemove Chunk, the chunk to remove
     * @param player        Player, the player using the tool
     */
    private static void chunkRemovalTask(Chunk chunkToRemove, Player player) {
        ArrayList<Block> blocksToRemove = new ArrayList<>();
        Bukkit.getScheduler().runTaskAsynchronously(ToolsPlus.instance, () -> {
            int x = 0, y = 255, z = 0;
            while (y > 0) {
                while (z < 16) {
                    while (x < 16) {
                        if (chunkToRemove.getBlock(x, y, z).getType().equals(Material.AIR)) {
                            //continue;
                        } else if (FileManager.get("chunk_wand_config").getStringList("buster-block-blacklist").contains(chunkToRemove.getBlock(x, y, z).getType().toString().toLowerCase())) {
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
                        for (int i = 0; i < FileManager.get("chunk_wand_config").getInt("removal-grouping-size"); i++) {
                            try {
                                BlockBreakEvent blockBreak = new BlockBreakEvent(blocksToRemove.get(indexOfArray), player);
                                Bukkit.getPluginManager().callEvent(blockBreak);
                                if (!blockBreak.isCancelled()) {
                                    blockBreak.setCancelled(true);
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
            }.runTaskTimer(ToolsPlus.instance, 0L, FileManager.get("chunk_wand_config").getInt("delay-between-grouping-removal"));
        });
    }
}
