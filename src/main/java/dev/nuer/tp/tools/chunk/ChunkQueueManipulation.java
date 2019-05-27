package dev.nuer.tp.tools.chunk;

import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class ChunkQueueManipulation {

    public static void removeChunksFromQueue(Chunk startingChunk, Player player, int radius) {
        int chunkX = radius-1, chunkZ = radius-1;
        while (chunkZ > -radius) {
            while (chunkX > -radius) {
                Chunk chunk = player.getWorld().getChunkAt(startingChunk.getX() + chunkX, startingChunk.getZ() + chunkZ);
                if (ChunkRemoval.pendingChunks.contains(chunk)) {
                    new PlayerMessage("chunk-removed-from-queue", player,
                            "{x}", String.valueOf(chunk.getX()),
                            "{z}", String.valueOf(chunk.getZ()));
                    ChunkRemoval.pendingChunks.remove(chunk);
                }
                chunkX--;
            }
            chunkX = radius-1;
            chunkZ--;
        }
//        } else {
//            new PlayerMessage("chunk-not-in-queue", player,
//                    "{x}", String.valueOf(chunk.getX()),
//                    "{z}", String.valueOf(chunk.getZ()));
//        }
    }
}
