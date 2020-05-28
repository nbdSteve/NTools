package gg.steve.mc.tp.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class CubeUtil {

    public static List<Block> getCube(Block start, int radius) {
        if (radius <= 0) return new ArrayList<>();
        List<Block> blockList = new ArrayList<>();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block block = start.getRelative(x, y, z);
                    if (block.getType().equals(Material.AIR)) continue;
                    // region control
                    blockList.add(block);
                }
            }
        }
        return blockList;
    }
}
