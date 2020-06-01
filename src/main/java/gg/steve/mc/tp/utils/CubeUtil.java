package gg.steve.mc.tp.utils;

import gg.steve.mc.tp.integration.region.RegionProviderType;
import gg.steve.mc.tp.managers.ToolConfigDataManager;
import gg.steve.mc.tp.tool.ToolType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CubeUtil {

    public static List<Block> getCube(Player player, Block start, int radius, ToolType type) {
        List<Block> blockList = new ArrayList<>();
        switch (type) {
            case TRENCH:
                if (!ToolConfigDataManager.getTrenchBlacklist().contains(start.getType())) {
                    blockList.add(start);
                }
                break;
            case TRAY:
                if (ToolConfigDataManager.getTrayWhitelist().contains(start.getType())) {
                    blockList.add(start);
                }
                break;
            case SELL:
                if (ToolConfigDataManager.getSellableContainers().contains(start.getType())) {
                    blockList.add(start);
                }
                break;
            default:
                break;
        }
        if (radius <= 0) return blockList;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block block = start.getRelative(x, y, z);
                    if (block.getType().equals(Material.AIR)) continue;
                    boolean breakAllowed = true;
                    for (RegionProviderType regionProvider : RegionProviderType.values()) {
                        if (!regionProvider.isBreakAllowed(player, block)) breakAllowed = false;
                    }
                    if (!breakAllowed) continue;
                    switch (type) {
                        case TRENCH:
                            if (ToolConfigDataManager.getTrenchBlacklist().contains(block.getType()))
                                continue;
                            break;
                        case TRAY:
                            if (!ToolConfigDataManager.getTrayWhitelist().contains(block.getType())) continue;
                            break;
                        case SELL:
                            if (!ToolConfigDataManager.getSellableContainers().contains(block.getType())) continue;
                        default:
                            break;
                    }
                    blockList.add(block);
                }
            }
        }
        return blockList;
    }
}
