package gg.steve.mc.tp.modules.tool;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.attribute.types.CooldownToolAttribute;
import gg.steve.mc.tp.framework.utils.LogUtil;
import gg.steve.mc.tp.framework.utils.TPSUtil;
import gg.steve.mc.tp.integration.region.RegionProviderType;
import gg.steve.mc.tp.modules.mangers.ConfirmationGuiManager;
import gg.steve.mc.tp.modules.message.ChunkMessage;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.tool.ToolData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ChunkWandData implements ToolData {

    @Override
    public void onBlockBreak(BlockBreakEvent blockBreakEvent, PlayerTool playerTool) {

    }

    @Override
    public void onInteract(PlayerInteractEvent event, PlayerTool tool) {
        Block start = event.getClickedBlock();
        if (event.getClickedBlock() == null || event.getClickedBlock().getType() == Material.AIR) {
            start = event.getPlayer().getWorld().getHighestBlockAt(event.getPlayer().getLocation());
        }
        if (CooldownToolAttribute.isCooldownActive(event.getPlayer(), tool)) return;
        boolean isBustable = true;
        for (RegionProviderType regionProvider : RegionProviderType.values()) {
            try {
                if (!regionProvider.isBreakAllowed(event.getPlayer(), start))
                    isBustable = false;
            } catch (NoClassDefFoundError ignored) {
            }
        }
        if (!isBustable) {
            ChunkMessage.NO_CHUNKS_TO_BUST.message(event.getPlayer());
            return;
        }
        ConfirmationGuiManager.open(event.getPlayer(), tool, start);
    }
}
