package dev.nuer.tp.tools.chunk;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.managers.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;

public class ChunkRemoval {

    private static HashMap<Chunk, BukkitTask> pendingChunks;

    public static void removeChunk(Chunk chunkToRemove) {
        if (pendingChunks == null) pendingChunks = new HashMap<>();
        ArrayList<Block> blocksToRemove = new ArrayList<>();
        if (pendingChunks.containsKey(chunkToRemove)) return;
        Bukkit.getScheduler().runTaskAsynchronously(ToolsPlus.instance, () -> {
            int x = 0, y = 255, z = 0;
            while (y > 0) {
                while (z < 15) {
                    while (x < 15) {
                        if (!chunkToRemove.getBlock(x,y,z).getType().equals(Material.AIR)) {
                            continue;
                        } else if (FileManager.get("config").getStringList("chunk-buster-block-blacklist").contains(chunkToRemove.getBlock(x,y,z).getType().toString())) {
                            continue;
                        } else {
                            blocksToRemove.add(chunkToRemove.getBlock(x,y,z));
                        }
                        x++;
                    }
                    z++;
                    x=0;
                }
                z=0;
                y++;
            }
        });
        for (Block b : blocksToRemove) {
            b.setType(Material.AIR);
        }
    }
}
