package gg.steve.mc.tp.modules;

import gg.steve.mc.tp.ToolsPlus;
import org.bukkit.event.Listener;

import java.util.List;

public abstract class ToolsPlusModule {

    public String getName() {
        return getModuleType().getModuleName();
    }

    public abstract ModuleType getModuleType();

    public abstract List<Listener> getListeners();

    public ToolsPlus getToolsPlus() {
        return ToolsPlus.get();
    }
}