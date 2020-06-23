package gg.steve.mc.tp.modules.aqua.utils;

import gg.steve.mc.tp.integration.region.RegionProviderType;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GetLiquidBlocksUtil {

    public static List<Block> getCube(Player player, Block start, int radius) {
        List<Block> blockList = new ArrayList<>();
        // check if the block is whitelisted / blacklisted based on the module
        if (start.isLiquid()) blockList.add(start);
        if (radius <= 0) return blockList;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block block = start.getRelative(x, y, z);
                    if (!block.isLiquid()) continue;
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
                    blockList.add(block);
                }
            }
        }
        return blockList;
    }
}
