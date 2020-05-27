package gg.steve.mc.tp.player;

import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.module.ModuleType;
import gg.steve.mc.tp.tool.ToolsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerToolListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void blockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        if (!PlayerToolManager.isHoldingTool(event.getPlayer().getUniqueId())) return;
        ToolPlayer player = PlayerToolManager.getToolPlayer(event.getPlayer().getUniqueId());
        if (player == null) return;
        ModuleType module = ModuleType.valueOf(player.getToolType().name());
        if (!ModuleManager.isInstalled(module)) return;
        ToolsManager.getTool(player.getToolName()).getData().onBlockBreak(event, player.getLoadedTool());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().addItem(ToolsManager.getTool("trench-3x3").getItemStack());
    }
}
