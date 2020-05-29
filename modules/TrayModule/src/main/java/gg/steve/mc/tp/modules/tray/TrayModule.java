package gg.steve.mc.tp.modules.tray;

import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.module.ModuleType;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.modules.tray.tool.TrayTool;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import gg.steve.mc.tp.tool.ToolType;
import gg.steve.mc.tp.upgrade.AbstractUpgrade;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class TrayModule extends ToolsPlusModule {
    public ModuleType getModuleType() {
        return ModuleType.TRAY;
    }

    public List<Listener> getListeners() {
        return new ArrayList<>();
    }

    public AbstractTool loadTool(ToolType toolType, AbstractUpgrade abstractUpgrade, NBTItem nbtItem, PluginFile pluginFile) {
        return new TrayTool(abstractUpgrade, nbtItem, pluginFile);
    }
}
