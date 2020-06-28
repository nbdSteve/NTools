package gg.steve.mc.tp.modules.utils;

import gg.steve.mc.tp.integration.region.RegionProviderType;
import gg.steve.mc.tp.tool.PlayerTool;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChunkUtil {

    public static List<Chunk> getChunksInRadius(Chunk start, Player player, PlayerTool tool) {
        int radius = tool.getRadius();
        if (radius <= 0) return Collections.emptyList();
        List<Chunk> removeableChunks = new ArrayList<>();
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                Chunk chunk = start.getWorld().getChunkAt(start.getX() + x, start.getZ() + z);
                boolean isRemovableChunk = true;
                for (int blockX = 0; blockX <= 16; blockX++) {
                    for (int blockZ = 0; blockZ <= 16; blockZ++) {
                        Block block = chunk.getBlock(blockX, 100, blockZ);
                        if (block.getType().equals(Material.AIR)) continue;
                        // check if the player can break this specific block
                        for (RegionProviderType regionProvider : RegionProviderType.values()) {
                            try {
                                if (!regionProvider.isBreakAllowed(player, block)) isRemovableChunk = false;
                            } catch (NoClassDefFoundError e) {
                                continue;
                            }
                        }
                    }
                }
                if (isRemovableChunk) removeableChunks.add(chunk);
            }
        }
        return removeableChunks;
    }

    
}