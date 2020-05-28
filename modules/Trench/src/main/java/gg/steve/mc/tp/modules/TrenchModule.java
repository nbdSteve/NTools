package gg.steve.mc.tp.modules;

import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.module.ModuleType;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.modules.tool.TrenchTool;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import gg.steve.mc.tp.tool.ToolType;
import gg.steve.mc.tp.upgrade.AbstractUpgrade;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class TrenchModule extends ToolsPlusModule {

    @Override
    public ModuleType getModuleType() {
        return ModuleType.TRENCH;
    }

    @Override
    public List<Listener> getListeners() {
        return new ArrayList<>();
//        List<Listener> listeners = new ArrayList<>();
//        return listeners;
    }

    @Override
    public AbstractTool loadTool(ToolType toolType, AbstractUpgrade abstractUpgrade, NBTItem nbtItem, PluginFile pluginFile) {
        return new TrenchTool(abstractUpgrade, nbtItem, pluginFile);
    }
}
