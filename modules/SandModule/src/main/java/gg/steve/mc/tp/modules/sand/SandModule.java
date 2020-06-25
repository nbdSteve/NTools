package gg.steve.mc.tp.modules.sand;

import gg.steve.mc.tp.framework.yml.PluginFile;
import gg.steve.mc.tp.framework.yml.utils.FileManagerUtil;
import gg.steve.mc.tp.managers.ToolConfigDataManager;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.modules.sand.tool.SandWand;
import gg.steve.mc.tp.modules.sand.utils.SandStackUtil;
import gg.steve.mc.tp.tool.AbstractTool;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SandModule extends ToolsPlusModule {
    public static String moduleId = "SAND";
    public static String moduleConfigId = "SAND_CONFIG";

    public SandModule() {
        super(moduleId);
        setNiceName("Sand Wand");
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
        return new SandWand(nbtItem, pluginFile);
    }

    @Override
    public Map<String, String> getModuleFiles() {
        Map<String, String> files = new HashMap<>();
        files.put(moduleConfigId, "configs" + File.separator + "sand.yml");
        return files;
    }

    @Override
    public void onLoad() {
        ToolConfigDataManager.addMaterialList(moduleId, FileManagerUtil.get(moduleConfigId).getStringList("whitelist"));
        SandStackUtil.initialise();
    }

    @Override
    public void onShutdown() {
        SandStackUtil.shutdown();
    }
}