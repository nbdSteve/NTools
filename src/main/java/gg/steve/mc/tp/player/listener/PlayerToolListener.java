package gg.steve.mc.tp.player.listener;

import gg.steve.mc.tp.attribute.ToolAttributeType;
import gg.steve.mc.tp.attribute.types.OmniToolAttribute;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.module.ModuleType;
import gg.steve.mc.tp.player.PlayerToolManager;
import gg.steve.mc.tp.player.ToolPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerToolListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void blockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        if (!PlayerToolManager.isHoldingTool(event.getPlayer().getUniqueId())) return;
        ToolPlayer player = PlayerToolManager.getToolPlayer(event.getPlayer().getUniqueId());
        if (player == null) return;
        ModuleType module = ModuleType.valueOf(player.getToolType().name());
        if (!ModuleManager.isInstalled(module)) return;
        player.getLoadedTool().getCurrentModeData().onBlockBreak(event, player.getLoadedTool());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void omniProc(BlockDamageEvent event) {
        if (!PlayerToolManager.isHoldingTool(event.getPlayer().getUniqueId())) return;
        ToolPlayer player = PlayerToolManager.getToolPlayer(event.getPlayer().getUniqueId());
        if (player == null) return;
        ModuleType module = ModuleType.valueOf(player.getToolType().name());
        if (!ModuleManager.isInstalled(module)) return;
        if (!player.getLoadedTool().getAbstractTool().getAttributeManager().isAttributeEnabled(ToolAttributeType.OMNI)) return;
        OmniToolAttribute.changeToolType(event.getBlock(), event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void interact(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        if (!PlayerToolManager.isHoldingTool(event.getPlayer().getUniqueId())) return;
        ToolPlayer player = PlayerToolManager.getToolPlayer(event.getPlayer().getUniqueId());
        if (player == null) return;
        ModuleType module = ModuleType.valueOf(player.getToolType().name());
        if (!ModuleManager.isInstalled(module)) return;
        player.getLoadedTool().getCurrentModeData().onInteract(event, player.getLoadedTool());
    }
}
