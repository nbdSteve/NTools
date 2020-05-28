package gg.steve.mc.tp.tool;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.managers.Files;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.module.ModuleType;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.utils.LogUtil;
import gg.steve.mc.tp.utils.PluginFile;
import gg.steve.mc.tp.utils.ToolLoaderUtil;
import gg.steve.mc.tp.utils.YamlFileUtil;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ToolsManager {
    private static Map<String, AbstractTool> tools;
    private static Map<UUID, LoadedTool> serverTools;

    public static void initialiseTools() {
        tools = new HashMap<>();
        serverTools = new HashMap<>();
        for (String tool : Files.CONFIG.get().getStringList("loaded-tools")) {
            PluginFile file = new YamlFileUtil().load("tools" + File.separator + tool + ".yml", ToolsPlus.get());
            ModuleType module = ModuleType.valueOf(file.get().getString("type").toUpperCase());
            if (!ModuleManager.isInstalled(module)) {
                LogUtil.info("Error while loading tool: " + tool + ", the required module (" + module.getModuleName() + ") is not installed.");
                continue;
            }
            ToolLoaderUtil loader = new ToolLoaderUtil(file, tool);
            tools.put(tool, loader.getTool());
        }
        LogUtil.info("Successfully loaded " + tools.size() + " tool(s) into the plugins internal map.");
    }

    public static void shutdown() {
        if (tools != null && !tools.isEmpty()) tools.clear();
        if (serverTools != null && !serverTools.isEmpty()) serverTools.clear();
    }

    public static AbstractTool getTool(String name) {
        return tools.get(name);
    }

    public static boolean isLoadedToolRegistered(UUID toolId) {
        if (serverTools == null || serverTools.isEmpty()) return false;
        return serverTools.containsKey(toolId);
    }

    public static boolean addLoadedTool(UUID toolId, NBTItem item) {
        if (isLoadedToolRegistered(toolId)) return false;
        return serverTools.put(toolId, new LoadedTool(toolId, item)) != null;
    }

    public static boolean removeLoadedTool(UUID toolId) {
        if (!isLoadedToolRegistered(toolId)) return false;
        return serverTools.remove(toolId) != null;
    }

    public static LoadedTool getLoadedTool(UUID toolId) {
        if (!isLoadedToolRegistered(toolId)) return null;
        return serverTools.get(toolId);
    }

    public static String getAbstractToolCount() {
        return String.valueOf(tools.size());
    }

    public static String getPlayerToolCount() {
        return String.valueOf(serverTools.size());
    }

    public static String getAbstractToolsAsList() {
        StringBuilder message = new StringBuilder();
        if (tools.size() > 0) {
            int i = 0;
            for (String name : tools.keySet()) {
                message.append(name);
                if (i != tools.size() - 1) {
                    message.append(", ");
                }
                i++;
            }
        }
        return message.toString();
    }
}