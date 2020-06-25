package gg.steve.mc.tp.modules.sand.tool;

import gg.steve.mc.tp.framework.message.GeneralMessage;
import gg.steve.mc.tp.modules.sand.utils.SandStackUtil;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.tool.ToolData;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SandWandData implements ToolData {

    @Override
    public void onBlockBreak(BlockBreakEvent blockBreakEvent, PlayerTool playerTool) {

    }

    @Override
    public void onInteract(PlayerInteractEvent event, PlayerTool tool) {
        if (event.getClickedBlock() == null || event.getClickedBlock().getType().equals(Material.AIR)) return;
        int mined;
        if ((mined = SandStackUtil.doCalculation(event.getClickedBlock(), event.getPlayer(), tool)) == 0)
            return;
        event.setCancelled(true);
        if (event.getPlayer().getInventory().firstEmpty() == -1) {
            GeneralMessage.INVENTORY_FULL.message(event.getPlayer());
        }
        if (!tool.decrementUses(event.getPlayer())) return;
        if (!tool.incrementBlocksMined(event.getPlayer(), mined)) return;
    }
}
