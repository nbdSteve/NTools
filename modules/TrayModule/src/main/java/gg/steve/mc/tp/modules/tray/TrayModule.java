package gg.steve.mc.tp.modules.tray;

import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.module.ModuleType;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.modules.tray.tool.TrayTool;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import gg.steve.mc.tp.tool.ToolType;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class TrayModule extends ToolsPlusModule {
    
    @Override
    public ModuleType getModuleType() {
        return ModuleType.TRAY;
    }

    @Override
    public List<Listener> getListeners() {
        return new ArrayList<>();
    }

    @Override
    public PlaceholderExpansion getPlaceholderExpansion() {
        return null;
    }
    
    public AbstractTool loadTool(ToolType toolType, NBTItem nbtItem, PluginFile pluginFile) {
        return new TrayTool(nbtItem, pluginFile);
    }
}
