package gg.steve.mc.tp.utils;

import gg.steve.mc.tp.managers.ToolConfigDataManager;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class CubeUtil {

    public static List<Block> getCube(Block start, int radius, String type) {
        if (radius <= 0) return new ArrayList<>();
        List<Block> blockList = new ArrayList<>();
        switch (type) {
            case "trench":
                if (!ToolConfigDataManager.getTrenchBlacklist().contains(start.getType())) {
                    blockList.add(start);
                }
                break;
            case "tray":
                if (ToolConfigDataManager.getTrayWhitelist().contains(start.getType())) {
                    blockList.add(start);
                }
                break;
            default:
                break;
        }
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block block = start.getRelative(x, y, z);
                    if (block.getType().equals(Material.AIR)) continue;
                    switch (type) {
                        case "trench":
                            if (ToolConfigDataManager.getTrenchBlacklist().contains(block.getType()))
                                continue;
                            break;
                        case "tray":
                            if (!ToolConfigDataManager.getTrayWhitelist().contains(block.getType())) continue;
                            break;
                        default:
                            break;
                    }
                    // region control
                    blockList.add(block);
                }
            }
        }
        return blockList;
    }
}
