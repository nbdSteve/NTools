package gg.steve.mc.tp.modules.utils;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.integration.region.RegionProviderType;
import gg.steve.mc.tp.managers.ToolConfigDataManager;
import gg.steve.mc.tp.modules.ChunkModule;
import gg.steve.mc.tp.tool.PlayerTool;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

public class ChunkUtil {

    public static List<Chunk> getChunksInRadius(Chunk start, Player player, PlayerTool tool) {
        int radius = tool.getRadius();
        if (radius < 0) return Collections.emptyList();
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

    public static Map<Chunk, List<Block>> getRemovableBlocksInChunks(List<Chunk> chunks, Player player) {
        Map<Chunk, List<Block>> blocksInChunks = new HashMap<>();
        Comparator<Block> comparator = new YLevelComparator();
        Bukkit.getScheduler().runTaskAsynchronously(ToolsPlus.get(), () -> {
            for (Chunk chunk : chunks) {
                blocksInChunks.put(chunk, new ArrayList<>());
                for (int blockX = 0; blockX <= 16; blockX++) {
                    for (int blockZ = 0; blockZ <= 16; blockZ++) {
                        for (int blockY = chunk.getWorld().getHighestBlockYAt(chunk.getBlock(blockX, 100, blockZ).getLocation()); blockY > 0; blockY--) {
                            Block block = chunk.getBlock(blockX, blockY, blockZ);
                            if (block.getType().equals(Material.AIR)) continue;
                            boolean breakAllowed = true;
                            // check if the player can break this specific block
                            for (RegionProviderType regionProvider : RegionProviderType.values()) {
                                try {
                                    if (!regionProvider.isBreakAllowed(player, block)) breakAllowed = false;
                                } catch (NoClassDefFoundError e) {
                                    continue;
                                }
                            }
                            if (!breakAllowed) continue;
                            // check if the block is whitelisted / blacklisted based on the module
                            if (!ToolConfigDataManager.queryMaterialList(ChunkModule.moduleId, block.getType(), false)) continue;
                            blocksInChunks.get(chunk).add(block);
                        }
                    }
                }
                blocksInChunks.get(chunk).sort(comparator);
            }
        });
        return blocksInChunks;
    }
}