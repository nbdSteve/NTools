package dev.nuer.nt;

import dev.nuer.nt.cmd.Tools;
import dev.nuer.nt.event.RadialBlockBreak;
import dev.nuer.nt.file.LoadFile;
import dev.nuer.nt.method.AddBlocksToBlacklist;
import dev.nuer.nt.method.AddToolsToMap;
import dev.nuer.nt.method.GetMultiToolUnique;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Main class for the NTools plugin
 */
public final class NTools extends JavaPlugin {
    //Store the plugin files
    private static LoadFile files;
    //Store the trench block blacklist
    private static ArrayList<String> trenchBlockBlacklist;
    //Store the tray block whitelist
    private static ArrayList<String> trayBlockWhitelist;
    //Store the map of trench tools, queried in the event class
    private static HashMap<Integer, String> trenchTools;
    //Store the map of tray tools, queried in the event class
    private static HashMap<Integer, String> trayTools;
    //Store the map of multi tools, queried in the event class
    private static HashMap<Integer, String> multiTools;
    private static HashMap<Integer, String> multiToolModeUnique;
    private static HashMap<Integer, String> multiToolRadiusUnique;

    /**
     * Void method to regenerate all of the HashMap associated with the plugin, will update with config
     * changes.
     */
    public static void loadToolMaps() {
        Instant start = Instant.now();
        trenchBlockBlacklist = AddBlocksToBlacklist.createBlocklist("trench-block-blacklist");
        trayBlockWhitelist = AddBlocksToBlacklist.createBlocklist("tray-block-whitelist");
        trenchTools = AddToolsToMap.createToolMap("trench.");
        trayTools = AddToolsToMap.createToolMap("tray.");
        multiTools = AddToolsToMap.createToolMap("multi-tool.");
        multiToolModeUnique = GetMultiToolUnique.createUniqueLore("multi-tool.", ".mode.unique");
        multiToolRadiusUnique = GetMultiToolUnique.createUniqueLore("multi-tool.", ".radius.unique");
        Instant finish = Instant.now();
        System.out.print("Queried tool from map, took " + Duration.between(start, finish).toMillis() + "ms");
    }

    /**
     * Void method to clear all of the HashMap associated with the plugin, since they are static this will
     * need to be called to stop overlap.
     */
    public static void clearMaps() {
        trenchBlockBlacklist.clear();
        trayBlockWhitelist.clear();
        trenchTools.clear();
        trayTools.clear();
        multiTools.clear();
    }

    /**
     * Get the plugin files
     *
     * @return LoadFiles instance
     */
    public static LoadFile getFiles() {
        return files;
    }

    /**
     * Gets the blacklisted blocks for the trench tools
     *
     * @return ArrayList of Strings
     */
    public static ArrayList<String> getTrenchBlockBlacklist() {
        return trenchBlockBlacklist;
    }

    /**
     * Gets the whitelisted blocks for the tray tools
     *
     * @return ArrayList of Strings
     */
    public static ArrayList<String> getTrayBlockWhitelist() {
        return trayBlockWhitelist;
    }

    /**
     * Gets the trench tools that are loaded from the tools.yml
     *
     * @return HashMap of tools and unique-lore
     */
    public static HashMap<Integer, String> getTrenchTools() {
        return trenchTools;
    }

    /**
     * Gets the tray tools that are loaded from the tools.yml
     *
     * @return HashMap of tools and unique-lore
     */
    public static HashMap<Integer, String> getTrayTools() {
        return trayTools;
    }

    /**
     * Gets the multi tools that are loaded from the tools.yml
     *
     * @return HashMap of tools and unique-lore
     */
    public static HashMap<Integer, String> getMultiTools() {
        return multiTools;
    }

    public static HashMap<Integer, String> getMultiToolModeUnique() {
        return multiToolModeUnique;
    }

    public static HashMap<Integer, String> getMultiToolRadiusUnique() {
        return multiToolRadiusUnique;
    }

    /**
     * Method called on plugin start up
     */
    @Override
    public void onEnable() {
        files = new LoadFile();
        loadToolMaps();
        getLogger().info("Loading tools from tools.yml...");
        getCommand("Tools").setExecutor(new Tools(this));
        getServer().getPluginManager().registerEvents(new RadialBlockBreak(), this);
    }

    /**
     * Method called on plugin shutdown
     */
    @Override
    public void onDisable() {
        clearMaps();
        getLogger().info("Cleaning maps...");
    }
}
