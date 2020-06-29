package gg.steve.mc.tp.modules.utils;

import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class PlayerChunkRemoverUtil {
    private UUID removerId;
    private BukkitTask removalTask;

    public PlayerChunkRemoverUtil(UUID removerId, BukkitTask removalTask) {
        this.removerId = removerId;
        this.removalTask = removalTask;
    }

    public void cancel() {
        this.removalTask.cancel();
    }

    public UUID getRemoverId() {
        return removerId;
    }
}
