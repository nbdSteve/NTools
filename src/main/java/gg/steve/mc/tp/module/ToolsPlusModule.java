package gg.steve.mc.tp.module;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import gg.steve.mc.tp.tool.ToolType;
import gg.steve.mc.tp.managers.PluginFile;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.event.Listener;

import java.util.List;

public abstract class ToolsPlusModule {

    public String getName() {
        return getModuleType().getModuleName();
    }

    public abstract ModuleType getModuleType();

    public abstract List<Listener> getListeners();

    public abstract PlaceholderExpansion getPlaceholderExpansion();

    public abstract AbstractTool loadTool(ToolType type, NBTItem item, PluginFile file);

    public ToolsPlus getToolsPlus() {
        return ToolsPlus.get();
    }
}