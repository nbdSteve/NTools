package gg.steve.mc.tp.modules.craft;

import gg.steve.mc.tp.framework.yml.PluginFile;
import gg.steve.mc.tp.framework.yml.utils.FileManagerUtil;
import gg.steve.mc.tp.managers.ToolConfigDataManager;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.modules.craft.recipes.RecipeManager;
import gg.steve.mc.tp.modules.craft.tool.CraftWand;
import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CraftModule extends ToolsPlusModule {
    public static String moduleId = "CRAFT";
    public static String moduleConfigId = "CRAFT_CONFIG";

    public CraftModule() {
        super(moduleId);
        setNiceName("Craft Wand");
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
        return new CraftWand(moduleId, nbtItem, pluginFile);
    }

    @Override
    public Map<String, String> getModuleFiles() {
        Map<String, String> files = new HashMap<>();
        files.put(moduleConfigId, "configs" + File.separator + "craft.yml");
        return files;
    }

    @Override
    public void onLoad() {
        ToolConfigDataManager.addMaterialList(moduleId, FileManagerUtil.get(moduleConfigId).getStringList("containers"));
        RecipeManager.loadRecipes();
    }

    @Override
    public void onShutdown() {
        RecipeManager.shutdown();
    }
}
