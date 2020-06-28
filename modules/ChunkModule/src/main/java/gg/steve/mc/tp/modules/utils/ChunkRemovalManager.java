package gg.steve.mc.tp.modules.utils;

import gg.steve.mc.tp.framework.yml.utils.FileManagerUtil;
import gg.steve.mc.tp.modules.ChunkModule;
import gg.steve.mc.tp.modules.gui.ConfirmationGui;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class ChunkRemovalManager {
    private static Map<UUID, List<BukkitTask>> playerChunkRemoval;
    private static ConfirmationGui confirmationGui;

    public static void initialise() {
        playerChunkRemoval = new HashMap<>();
        confirmationGui = new ConfirmationGui();
    }

    public static void shutdown() {
        if (playerChunkRemoval != null && !playerChunkRemoval.isEmpty()) {
            for (UUID playerId : playerChunkRemoval.keySet()) {
                for (BukkitTask task : playerChunkRemoval.get(playerId)) {
                    task.cancel();
                }
            }
            playerChunkRemoval.clear();
        }
    }

    public static void addPlayerTask(UUID playerId, BukkitTask task) {
        if (!playerChunkRemoval.containsKey(playerId)) playerChunkRemoval.put(playerId, new ArrayList<>());
        playerChunkRemoval.get(playerId).add(task);
    }

    public static boolean canAddTask(UUID playerId) {
        if (!playerChunkRemoval.containsKey(playerId)) return true;
        return playerChunkRemoval.get(playerId).size() < FileManagerUtil.get(ChunkModule.moduleConfigId).getInt("max-tasks-per-player");
    }

    public static void openConfirmationGui(Player player) {
        confirmationGui.open(player);
    }
}
