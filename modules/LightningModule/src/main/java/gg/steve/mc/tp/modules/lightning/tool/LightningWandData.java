package gg.steve.mc.tp.modules.lightning.tool;

import gg.steve.mc.tp.modules.lightning.utils.StrikeUtil;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.tool.ToolData;
import gg.steve.mc.tp.framework.utils.LogUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class LightningWandData implements ToolData {

    @Override
    public void onBlockBreak(BlockBreakEvent blockBreakEvent, PlayerTool playerTool) {

    }

    @Override
    public void onInteract(PlayerInteractEvent event, PlayerTool tool) {
        Block target;
        if (event.getClickedBlock() == null || event.getClickedBlock().getType().equals(Material.AIR)) {
            target = StrikeUtil.getTargetBlock(event.getPlayer());
            LogUtil.info(target.getLocation().toString());
        } else {
            target = event.getClickedBlock();
        }
        event.setCancelled(true);
        if (tool.isOnCooldown(event.getPlayer())) return;
        StrikeUtil.doStrike(event.getPlayer(), target);
        if (!tool.decrementUses(event.getPlayer())) return;
        if (!tool.incrementBlocksMined(event.getPlayer(), 1)) return;
    }
}
