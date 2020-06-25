package gg.steve.mc.tp.modules.sand.utils;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.integration.region.RegionProviderType;
import gg.steve.mc.tp.managers.ToolConfigDataManager;
import gg.steve.mc.tp.modules.sand.SandModule;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.framework.utils.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

public class SandStackUtil {
    private static Map<UUID, UUID> players;
    private static Map<UUID, Integer> progress;
    private static Map<UUID, List<Block>> stacks;
    private static int taskId;

    public static void initialise() {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(ToolsPlus.get(), () -> {
            if (stacks == null || stacks.isEmpty()) return;
            List<UUID> finished = new ArrayList<>();
            for (UUID removalId : stacks.keySet()) {
                if (progress.get(removalId) >= stacks.get(removalId).size()) {
                    finished.add(removalId);
                } else {
                    Block block = stacks.get(removalId).get(progress.get(removalId));
                    InventoryUtil.addBlockToInventory(block, players.get(removalId));
                    block.setType(Material.AIR);
                    block.getDrops().clear();
                    progress.put(removalId, progress.get(removalId) + 1);
                }
            }
            for (UUID removalId : finished) {
                stacks.remove(removalId);
                progress.remove(removalId);
                players.remove(removalId);
            }
        }, 0L, 0L);
    }

    public static void shutdown() {
        if (players != null && !players.isEmpty()) players.clear();
        if (progress != null && !progress.isEmpty()) stacks.clear();
        if (stacks != null && !stacks.isEmpty()) stacks.clear();
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public static void addSandStack(List<Block> stack, UUID playerId) {
        if (stacks == null) stacks = new HashMap<>();
        if (progress == null) progress = new HashMap<>();
        if (players == null) players = new HashMap<>();
        UUID removalId = UUID.randomUUID();
        stacks.put(removalId, stack);
        progress.put(removalId, 0);
        players.put(removalId, playerId);
    }

    public static int doCalculation(Block start, Player player, PlayerTool tool) {
        int radius = tool.getRadius();
        boolean passed = false;
        int mined = 0;
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                List<Block> stack = new ArrayList<>();
                Block block = start.getWorld().getHighestBlockAt(start.getRelative(i, 0, j).getLocation());
                for (int y = block.getY(); y > 0; y--) {
                    Block remove = block.getWorld().getBlockAt(block.getX(), y, block.getZ());
                    if (remove.getType().equals(Material.AIR)) continue;
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
                    if (!ToolConfigDataManager.queryMaterialList(SandModule.moduleId, remove.getType(), true))
                        continue;
                    if (!passed && tool.isOnCooldown(player)) return 0;
                    passed = true;
                    stack.add(remove);
                }
                if (stack.isEmpty()) continue;
                SandStackUtil.addSandStack(stack, player.getUniqueId());
                mined += stack.size();
            }
        }
        return mined;
    }
}
