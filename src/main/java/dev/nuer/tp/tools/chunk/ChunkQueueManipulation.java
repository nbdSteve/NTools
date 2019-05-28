package dev.nuer.tp.tools.chunk;

import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

/**
 * Class that handles removing chunks from the removal queue
 */
public class ChunkQueueManipulation {

    /**
     * Removes the chunks in a given radius from the removal queue
     *
     * @param startingChunk Chunk, the centre chunk
     * @param player        Player, player using the tool
     * @param radius        int, the tools radius
     */
    public static void removeChunksFromQueue(Chunk startingChunk, Player player, int radius) {
        int chunksRemovedFromQueue = 0;
        int chunkX = radius - 1, chunkZ = radius - 1;
        while (chunkZ > -radius) {
            while (chunkX > -radius) {
                Chunk chunk = player.getWorld().getChunkAt(startingChunk.getX() + chunkX, startingChunk.getZ() + chunkZ);
                if (ChunkRemoval.pendingChunks.contains(chunk)) {
                    chunksRemovedFromQueue++;
                    ChunkRemoval.pendingChunks.remove(chunk);
                }
                chunkX--;
            }
            chunkX = radius - 1;
            chunkZ--;
        }
        new PlayerMessage("chunks-removed-from-queue", player,
                "{x}", String.valueOf(startingChunk.getX()),
                "{z}", String.valueOf(startingChunk.getZ()),
                "{chunksRemoved}", String.valueOf(chunksRemovedFromQueue));
    }
}
