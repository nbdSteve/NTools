package gg.steve.mc.tp.modules.aqua.tool;

import gg.steve.mc.tp.mode.ModeType;
import gg.steve.mc.tp.modules.aqua.AquaModule;
import gg.steve.mc.tp.modules.aqua.utils.GetLiquidBlocksUtil;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.tool.ToolData;
import gg.steve.mc.tp.utils.CubeUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class AquaWandData implements ToolData {

    @Override
    public void onBlockBreak(BlockBreakEvent blockBreakEvent, PlayerTool playerTool) {

    }

    @Override
    public void onInteract(PlayerInteractEvent event, PlayerTool tool) {
        event.setCancelled(true);
        event.getPlayer().updateInventory();
        if (event.getClickedBlock() == null || event.getClickedBlock().getType() == Material.AIR) return;
        boolean drain = tool.getModeChange(ModeType.TOOL).getCurrentModeString(tool.getCurrentMode(ModeType.TOOL)).equalsIgnoreCase("drain");
        List<Block> blocks;
        if (drain) {
            blocks = GetLiquidBlocksUtil.getCube(event.getPlayer(), event.getClickedBlock(), tool.getRadius());
        } else {
            blocks = CubeUtil.getCube(event.getPlayer(), event.getClickedBlock(), tool.getRadius(), AquaModule.moduleId, true);
        }
        if (blocks.isEmpty()) return;
        if (tool.isOnCooldown(event.getPlayer())) return;
        if (!tool.decrementUses(event.getPlayer())) return;
        if (drain) {
            for (Block block : blocks) {
                if (!block.isLiquid()) continue;
                block.setType(Material.AIR);
            }
        } else {
            for (Block block : blocks) {
                if (block.isLiquid()) continue;
                block.setType(Material.WATER);
            }
        }
        if (!tool.incrementBlocksMined(event.getPlayer(), blocks.size())) return;
    }
}
