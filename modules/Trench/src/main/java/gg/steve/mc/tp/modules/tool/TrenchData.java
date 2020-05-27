package gg.steve.mc.tp.modules.tool;

import gg.steve.mc.tp.tool.LoadedTool;
import gg.steve.mc.tp.tool.ToolData;
import org.bukkit.event.block.BlockBreakEvent;

public class TrenchData implements ToolData {

    public TrenchData() {
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, LoadedTool tool) {
        if (!tool.decrementUses(event.getPlayer())) return;
    }
}
