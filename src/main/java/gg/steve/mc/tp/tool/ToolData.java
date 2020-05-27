package gg.steve.mc.tp.tool;

import org.bukkit.event.block.BlockBreakEvent;

public interface ToolData {

    void onBlockBreak(BlockBreakEvent event, LoadedTool tool);
}
