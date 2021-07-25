package gg.steve.mc.tp.modules.crowbar.tool;

import de.dustplanet.util.SilkUtil;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.tool.ToolData;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CrowbarData implements ToolData {

    @Override
    public void onBlockBreak(BlockBreakEvent event, PlayerTool tool) {
        if (!event.getBlock().getType().toString().contains("SPAWNER")) {
            event.setCancelled(true);
            return;
        }
        CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();
        short id = spawner.getSpawnedType().getTypeId();
        SilkUtil su = SilkUtil.hookIntoSilkSpanwers();
        su.newSpawnerItem(String.valueOf(id), "", 1, false);
        if (!tool.decrementUses(event.getPlayer())) return;
        if (!tool.incrementBlocksMined(event.getPlayer(), 1)) return;
    }

    @Override
    public void onInteract(PlayerInteractEvent event, PlayerTool tool) {
    }
}
