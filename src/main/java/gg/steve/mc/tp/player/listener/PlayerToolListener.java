package gg.steve.mc.tp.player.listener;

import gg.steve.mc.tp.attribute.ToolAttributeType;
import gg.steve.mc.tp.attribute.types.OmniToolAttribute;
import gg.steve.mc.tp.mode.ModeType;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.player.PlayerToolManager;
import gg.steve.mc.tp.player.ToolPlayer;
import gg.steve.mc.tp.tool.LoadedTool;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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
        if (!ModuleManager.isInstalled(player.getToolType())) return;
        player.getLoadedTool().getCurrentModeData().onBlockBreak(event, player.getLoadedTool());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void omniProc(BlockDamageEvent event) {
        if (!PlayerToolManager.isHoldingTool(event.getPlayer().getUniqueId())) return;
        ToolPlayer player = PlayerToolManager.getToolPlayer(event.getPlayer().getUniqueId());
        if (player == null) return;
        if (!ModuleManager.isInstalled(player.getToolType())) return;
        if (!player.getLoadedTool().getAbstractTool().getAttributeManager().isAttributeEnabled(ToolAttributeType.OMNI))
            return;
        OmniToolAttribute.changeToolType(event.getBlock(), event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void interact(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        if (!PlayerToolManager.isHoldingTool(event.getPlayer().getUniqueId())) return;
        ToolPlayer player = PlayerToolManager.getToolPlayer(event.getPlayer().getUniqueId());
        if (player == null) return;
        if (!ModuleManager.isInstalled(player.getToolType())) return;
        player.getLoadedTool().getCurrentModeData().onInteract(event, player.getLoadedTool());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void sneakSwitch(PlayerInteractEvent event) {
        if (!event.getPlayer().isSneaking() || event.getAction() != Action.RIGHT_CLICK_AIR || event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        Player player = event.getPlayer();
        if (!PlayerToolManager.isHoldingTool(player.getUniqueId())) return;
        LoadedTool tool = PlayerToolManager.getToolPlayer(player.getUniqueId()).getLoadedTool();
        if (tool.getModeChange(ModeType.TOOL).isSneakSwitch()) {
            tool.openModeGui(event.getPlayer(), ModeType.TOOL);
            return;
        }
        if (tool.getModeChange(ModeType.SELL).isSneakSwitch()) {
            tool.openModeGui(event.getPlayer(), ModeType.SELL);
        }
    }
}
