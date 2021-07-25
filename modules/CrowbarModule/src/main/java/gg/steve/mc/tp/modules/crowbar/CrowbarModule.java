package gg.steve.mc.tp.modules.crowbar;

import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.framework.yml.PluginFile;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.modules.crowbar.tool.Crowbar;
import gg.steve.mc.tp.tool.AbstractTool;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrowbarModule extends ToolsPlusModule {
    public static String moduleId = "CROWBAR";
    public static String moduleConfigId = "CROWBAR_CONFIG";

    public CrowbarModule() {
        super(moduleId);
        setNiceName("Crowbar");
    }

    @Override
    public String getVersion() {
        return "2.1.4";
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
        return new Crowbar(moduleId, nbtItem, pluginFile);
    }

    @Override
    public Map<String, String> getModuleFiles() {
        Map<String, String> files = new HashMap<>();
//        files.put(moduleConfigId, "configs" + File.separator + "crowbar.yml");
        return files;
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onShutdown() {

    }
}
