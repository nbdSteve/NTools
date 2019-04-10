package dev.nuer.nt;

import dev.nuer.nt.cmd.Tools;
import dev.nuer.nt.event.CrouchClickOpenToolOptionGui;
import dev.nuer.nt.event.RadialBlockBreak;
import dev.nuer.nt.file.LoadFile;
import dev.nuer.nt.gui.MultiToolOptionsGui;
import dev.nuer.nt.gui.listener.GuiClickListener;
import dev.nuer.nt.method.AddBlocksToBlacklist;
import dev.nuer.nt.method.AddToolsToMap;
import dev.nuer.nt.method.GetMultiToolLoreID;
import org.bukkit.plugin.java.JavaPlugin;

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
    //Store the map of multi tool unique lore and raw tool id
    private static HashMap<Integer, ArrayList<String>> multiToolModeUnique;
    //Store the map of multi tool unique radius id and raw tool id
    private static HashMap<Integer, ArrayList<String>> multiToolRadiusUnique;
    //Instance of multi tool options gui
    private MultiToolOptionsGui multiToolOptionsGui;

    /**
     * Void method to regenerate all of the HashMap associated with the plugin, will update with config
     * changes.
     */
    public static void loadToolMaps() {
        trenchBlockBlacklist = AddBlocksToBlacklist.createBlocklist("trench-block-blacklist");
        trayBlockWhitelist = AddBlocksToBlacklist.createBlocklist("tray-block-whitelist");
        trenchTools = AddToolsToMap.createToolMap("trench.");
        trayTools = AddToolsToMap.createToolMap("tray.");
        multiTools = AddToolsToMap.createToolMap("multi-tool.");
        multiToolModeUnique = GetMultiToolLoreID.createUniqueModeIDs("multi-tool.");
        multiToolRadiusUnique = GetMultiToolLoreID.createUniqueRadiusIDs("multi-tool.");
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
        multiToolModeUnique.clear();
        multiToolRadiusUnique.clear();
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

    /**
     * Gets the multi tool unique mode lore lines from the tools.yml
     *
     * @return HashMap of mode and raw tool id
     */
    public static HashMap<Integer, ArrayList<String>> getMultiToolModeUnique() {
        return multiToolModeUnique;
    }

    /**
     * Gets the multi tool unique radius lore lines from the tools.yml
     *
     * @return HashMap of radius and raw tool id
     */
    public static HashMap<Integer, ArrayList<String>> getMultiToolRadiusUnique() {
        return multiToolRadiusUnique;
    }

    /**
     * Method called on plugin start up
     */
    @Override
    public void onEnable() {
        getLogger().info("Thanks for using NTools, if you find any bugs contact: nbdSteve#0583 on Discord.");
        //Create files instance
        files = new LoadFile();
        //Load the tool maps
        loadToolMaps();
        getLogger().info("Successfully loaded all tools from tools.yml...");
        //Create the Gui instances
        multiToolOptionsGui = new MultiToolOptionsGui();
        //Register the commands for the plugin
        getCommand("Tools").setExecutor(new Tools(this));
        //Register the events for the plugin
        getServer().getPluginManager().registerEvents(new RadialBlockBreak(), this);
        getServer().getPluginManager().registerEvents(new CrouchClickOpenToolOptionGui(), this);
        getServer().getPluginManager().registerEvents(new GuiClickListener(), this);
    }

    /**
     * Method called on plugin shutdown
     */
    @Override
    public void onDisable() {
        clearMaps();
        getLogger().info("Successfully unloaded all of the tools from the plugin...");
        getLogger().info("Thanks for using NTools, if you find any bugs contact nbdSteve#0583 on Discord.");
    }

    /**
     * Gets the gui instance
     *
     * @return
     */
    public MultiToolOptionsGui getMultiToolOptionsGui() {
        return multiToolOptionsGui;
    }
}
