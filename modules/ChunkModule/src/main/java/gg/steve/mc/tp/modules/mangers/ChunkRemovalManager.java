package gg.steve.mc.tp.modules.mangers;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.framework.utils.TPSUtil;
import gg.steve.mc.tp.modules.constants.ConfigConstants;
import gg.steve.mc.tp.modules.message.ChunkMessage;
import gg.steve.mc.tp.modules.utils.ChunkUtil;
import gg.steve.mc.tp.modules.utils.PlayerChunkRemoverUtil;
import gg.steve.mc.tp.tool.PlayerTool;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class ChunkRemovalManager {
    private static Map<UUID, List<PlayerChunkRemoverUtil>> playerChunkRemoval;

    public static void initialise() {
        playerChunkRemoval = new HashMap<>();
    }

    public static void shutdown() {
        if (playerChunkRemoval != null && !playerChunkRemoval.isEmpty()) {
            for (UUID playerId : playerChunkRemoval.keySet()) {
                for (PlayerChunkRemoverUtil chunkRemover : playerChunkRemoval.get(playerId)) {
                    chunkRemover.cancel();
                }
            }
            playerChunkRemoval.clear();
        }
    }

    public static void addPlayerTask(UUID playerId, UUID removerId, BukkitTask removalTask) {
        if (!playerChunkRemoval.containsKey(playerId)) playerChunkRemoval.put(playerId, new ArrayList<>());
        playerChunkRemoval.get(playerId).add(new PlayerChunkRemoverUtil(removerId, removalTask));
    }

    public static boolean canAddTask(UUID playerId) {
        if (!playerChunkRemoval.containsKey(playerId)) return true;
        return playerChunkRemoval.get(playerId).size() < ConfigConstants.maxPlayerTasks;
    }

    public static void doChunkRemoval(Player player, Block start, PlayerTool tool) {
        List<Chunk> chunks = ChunkUtil.getChunksInRadius(start.getChunk(), player, tool);
        if (chunks.isEmpty()) {
            ChunkMessage.NO_CHUNKS_TO_BUST.message(player);
            return;
        }
        ChunkMessage.CONFIRM.message(player);
        Map<Chunk, List<Block>> blocksForRemoval = ChunkUtil.getRemovableBlocksInChunks(chunks, player);
        UUID removalId = UUID.randomUUID();
        tool.isOnCooldown(player);
        final boolean[] started = {false};
        addPlayerTask(player.getUniqueId(), removalId, Bukkit.getScheduler().runTaskTimer(ToolsPlus.get(), () -> {
            if (!started[0]) {
                ChunkMessage.BUSTING_STARTED.message(player, String.valueOf(start.getChunk().getX()), String.valueOf(start.getChunk().getZ()));
                started[0] = true;
            }
            if (TPSUtil.getTPS() < ConfigConstants.minTPS) {
                ChunkMessage.BUSTING_PAUSED.message(player);
                return;
            }
            if (blocksForRemoval.isEmpty()) {
                List<PlayerChunkRemoverUtil> pendingRemoval = new ArrayList<>(blocksForRemoval.size());
                for (PlayerChunkRemoverUtil chunkRemover : playerChunkRemoval.get(player.getUniqueId())) {
                    if (chunkRemover.getRemoverId().equals(removalId)) {
                        chunkRemover.cancel();
                        pendingRemoval.add(chunkRemover);
                    }
                }
                for (PlayerChunkRemoverUtil chunkRemover : pendingRemoval) {
                    playerChunkRemoval.get(player.getUniqueId()).remove(chunkRemover);
                }
                return;
            }
            List<Chunk> completed = new ArrayList<>();
            for (Chunk chunk : blocksForRemoval.keySet()) {
                if (blocksForRemoval.get(chunk).isEmpty()) {
                    completed.add(chunk);
                    break;
                }
                for (int i = 0; i < ConfigConstants.blocksRemovedPerPeriod; i++) {
                    if (i >= blocksForRemoval.get(chunk).size()) break;
                    blocksForRemoval.get(chunk).get(0).setType(Material.AIR);
                    blocksForRemoval.get(chunk).remove(0);
                }
            }
            for (Chunk chunk : completed) {
                blocksForRemoval.remove(chunk);
            }
        }, ConfigConstants.delay, ConfigConstants.period));
        if (!tool.decrementUses(player)) return;
        if (!tool.incrementBlocksMined(player, chunks.size())) return;
    }
}
