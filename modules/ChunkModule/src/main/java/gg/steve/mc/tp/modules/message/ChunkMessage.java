package gg.steve.mc.tp.modules.message;

import gg.steve.mc.tp.framework.utils.ColorUtil;
import gg.steve.mc.tp.framework.utils.actionbarapi.ActionBarAPI;
import gg.steve.mc.tp.framework.yml.utils.FileManagerUtil;
import gg.steve.mc.tp.modules.ChunkModule;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public enum ChunkMessage {
    TPS_TOO_LOW("tps-too-low"),
    NO_CHUNKS_TO_BUST("no-chunks-to-bust"),
    CONFIRM("confirm"),
    DECLINE("decline"),
    BUSTING_PAUSED("busting-paused"),
    BUSTING_STARTED("busting-started", "{chunk-x}", "{chunk-z}"),
    NOT_HOLDING_SAME_TOOL("not-holding-same-tool"),
    MAX_TASKS_ACTIVE("max-tasks-active");

    private String path;
    private boolean actionBar;
    private List<String> placeholders;

    ChunkMessage(String path, String... placeholders) {
        this.path = path;
        this.placeholders = Arrays.asList(placeholders);
        this.actionBar = FileManagerUtil.get(ChunkModule.moduleConfigId).getBoolean("messages." + this.path + ".action-bar");
    }

    public void message(Player receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.actionBar) {
            for (String line : FileManagerUtil.get(ChunkModule.moduleConfigId).getStringList("messages." + this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                ActionBarAPI.sendActionBar(receiver, ColorUtil.colorize(line));
            }
        } else {
            for (String line : FileManagerUtil.get(ChunkModule.moduleConfigId).getStringList("messages." + this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                receiver.sendMessage(ColorUtil.colorize(line));
            }
        }
    }
}
