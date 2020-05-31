package gg.steve.mc.tp.module;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.event.Listener;

import java.util.List;

public abstract class ToolsPlusModule {
    private ModuleType moduleType;

    public ToolsPlusModule(ModuleType moduleType) {
        this.moduleType = moduleType;
    }

    public String getName() {
        return getModuleType().getModuleName();
    }

    public ModuleType getModuleType() {
        return moduleType;
    }

    public abstract List<Listener> getListeners();

    public abstract PlaceholderExpansion getPlaceholderExpansion();

    public abstract AbstractTool loadTool(NBTItem item, PluginFile file);

    public ToolsPlus getToolsPlus() {
        return ToolsPlus.get();
    }
}