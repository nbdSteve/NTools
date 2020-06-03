package gg.steve.mc.tp.player;

import gg.steve.mc.tp.gui.AbstractGui;
import gg.steve.mc.tp.tool.LoadedTool;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ToolPlayer {
    private UUID playerId;
    private LoadedTool tool;
    private AbstractGui upgradeGui;

    public ToolPlayer(UUID playerId, LoadedTool tool) {
        this.playerId = playerId;
        this.tool = tool;
    }

    public AbstractGui getUpgradeGui() {
        return upgradeGui;
    }

    public void setUpgradeGui(AbstractGui upgradeGui) {
        this.upgradeGui = upgradeGui;
    }

    public String getToolType() {
        return tool.getAbstractTool().getModuleId();
    }

    public void setTool(LoadedTool tool) {
        this.tool = tool;
    }

    public String getToolName() {
        return tool.getName();
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.playerId);
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.playerId);
    }

    public LoadedTool getLoadedTool() {
        return tool;
    }
}
