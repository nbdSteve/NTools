package gg.steve.mc.tp.modules.harvester;

import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.modules.harvester.tool.HarvesterHoe;
import gg.steve.mc.tp.modules.harvester.tool.HarvesterHoeData;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HarvesterModule extends ToolsPlusModule {
    public static String moduleId = "HARVESTER";
    public static String moduleConfigId = "HARVESTER_CONFIG";

    public HarvesterModule() {
        super(moduleId);
        setNiceName("Harvester Hoe");
    }

    @Override
    public String getVersion() {
        return "2.0.0-PR1";
    }

    @Override
    public String getAuthor() {
        return "stevegoodhill";
    }

    @Override
    public List<Listener> getListeners() {
        return new ArrayList<>();
    }

    @Override
    public PlaceholderExpansion getPlaceholderExpansion() {
        return null;
    }

    @Override
    public AbstractTool loadTool(NBTItem nbtItem, PluginFile pluginFile) {
        return new HarvesterHoe(nbtItem, pluginFile);
    }

    @Override
    public Map<String, String> getModuleFiles() {
        Map<String, String> files = new HashMap<>();
        files.put(moduleConfigId, "configs" + File.separator + "harvester.yml");
        return files;
    }

    @Override
    public void onLoad() {
        HarvesterHoeData.initialise();
    }

    @Override
    public void onShutdown() {
        HarvesterHoeData.shutdown();
    }
}
