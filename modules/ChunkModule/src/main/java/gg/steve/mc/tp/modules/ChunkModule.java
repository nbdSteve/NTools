package gg.steve.mc.tp.modules;

import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.framework.yml.PluginFile;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.modules.tool.ChunkWand;
import gg.steve.mc.tp.modules.utils.ChunkRemovalManager;
import gg.steve.mc.tp.tool.AbstractTool;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Chunk;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChunkModule extends ToolsPlusModule {
    public static String moduleId = "CHUNK";
    public static String moduleConfigId = "CHUNK_CONFIG";

    public ChunkModule() {
        super(moduleId);
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
        return Collections.emptyList();
    }

    @Override
    public PlaceholderExpansion getPlaceholderExpansion() {
        return null;
    }

    @Override
    public AbstractTool loadTool(NBTItem nbtItem, PluginFile pluginFile) {
        return new ChunkWand(moduleId, nbtItem, pluginFile);
    }

    @Override
    public Map<String, String> getModuleFiles() {
        Map<String, String> files = new HashMap<>();
        files.put(moduleConfigId, "configs" + File.separator + "chunk.yml");
        return files;
    }

    @Override
    public void onLoad() {
        ChunkRemovalManager.initialise();
    }

    @Override
    public void onShutdown() {
        ChunkRemovalManager.shutdown();
    }
}
