package gg.steve.mc.tp.modules.lightning;

import gg.steve.mc.tp.framework.yml.PluginFile;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.modules.lightning.tool.LightningWand;
import gg.steve.mc.tp.tool.AbstractTool;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LightningModule extends ToolsPlusModule {
    public static String moduleId = "LIGHTNING";
    public static String moduleConfigId = "LIGHTNING_CONFIG";
    
    public LightningModule() {
        super(moduleId);
        setNiceName("Lightning Wand");
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
        return new LightningWand(moduleId, nbtItem, pluginFile);
    }

    @Override
    public Map<String, String> getModuleFiles() {
        Map<String, String> files = new HashMap<>();
        files.put(moduleConfigId, "configs" + File.separator + "lightning.yml");
        return files;
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onShutdown() {

    }
}
