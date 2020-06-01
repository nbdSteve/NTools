package gg.steve.mc.tp.modules.sell;

import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.module.ModuleType;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.modules.sell.tool.SellWand;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class SellModule extends ToolsPlusModule {

    public SellModule() {
        super(ModuleType.SELL);
    }

    public String getVersion() {
        return "2.0.0-PR1";
    }

    public String getAuthor() {
        return "nbdSteve";
    }

    public List<Listener> getListeners() {
        return new ArrayList<>();
    }

    public PlaceholderExpansion getPlaceholderExpansion() {
        return null;
    }

    public AbstractTool loadTool(NBTItem nbtItem, PluginFile pluginFile) {
        return new SellWand(nbtItem, pluginFile);
    }
}
