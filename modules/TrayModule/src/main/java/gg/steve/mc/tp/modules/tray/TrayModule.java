package gg.steve.mc.tp.modules.tray;

import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.module.ModuleType;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.modules.tray.tool.TrayTool;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class TrayModule extends ToolsPlusModule {

    public TrayModule() {
        super(ModuleType.TRAY);
    }

    @Override
    public String getVersion() {
        return "2.0.0-PR1";
    }

    @Override
    public String getAuthor() {
        return "nbdSteve";
    }

    @Override
    public List<Listener> getListeners() {
        return new ArrayList<>();
    }

    @Override
    public PlaceholderExpansion getPlaceholderExpansion() {
        return null;
    }

    public AbstractTool loadTool(NBTItem nbtItem, PluginFile pluginFile) {
        return new TrayTool(nbtItem, pluginFile);
    }
}
