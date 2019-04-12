package dev.nuer.nt;

import dev.nuer.nt.cmd.Nt;
import dev.nuer.nt.event.CrouchClickOpenToolOptionGui;
import dev.nuer.nt.event.BlockDamageByPlayer;
import dev.nuer.nt.file.LoadFile;
import dev.nuer.nt.gui.*;
import dev.nuer.nt.gui.listener.GuiClickListener;
import dev.nuer.nt.gui.purchase.BuyMultiToolsGui;
import dev.nuer.nt.gui.purchase.BuySandWandsGui;
import dev.nuer.nt.gui.purchase.BuyTrayToolsGui;
import dev.nuer.nt.gui.purchase.BuyTrenchToolsGui;
import dev.nuer.nt.method.AddBlocksToBlacklist;
import dev.nuer.nt.method.AddToolsToMap;
import dev.nuer.nt.method.GetMultiToolLoreID;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Main class for the NTools plugin
 */
public final class NTools extends JavaPlugin {
    //Store the plugin files
    private static LoadFile files;
    //Store the trench block blacklist
    public static ArrayList<String> trenchBlockBlacklist;
    //Store the tray block whitelist
    public static ArrayList<String> trayBlockWhitelist;
    //Store the map of trench tools, queried in the event class
    private static HashMap<Integer, String> trenchTools;
    //Store the map of tray tools, queried in the event class
    private static HashMap<Integer, String> trayTools;
    //Store the map of multi tools, queried in the event class
    private static HashMap<Integer, String> multiTools;
    //Store the map of multi tool unique lore and raw tool id
    public static HashMap<Integer, ArrayList<String>> multiToolModeUnique;
    //Store the map of multi tool unique radius id and raw tool id
    public static HashMap<Integer, ArrayList<String>> multiToolRadiusUnique;
    //Store the map of sand wands
    private static HashMap<Integer, String> sandWands;
    //Store the blocks that can be broken by the sand wands
    public static ArrayList<String> sandWandBlockWhitelist;
    //Instance of multi tool options gui
    private MultiToolOptionsGui multiToolOptionsGui;
    //Instance of generic buy gui
    private BuyToolsGenericGui buyToolsGenericGui;
    //Instance of multi tools gui
    private BuyMultiToolsGui buyMultiToolsGui;
    //Instance of trench tools gui
    private BuyTrenchToolsGui buyTrenchToolsGui;
    //Instance of tray tools gui
    private BuyTrayToolsGui buyTrayToolsGui;
    //Instance of tray tools gui
    private BuySandWandsGui buySandWandsGui;
    //Create a logger for the plugin
    public static Logger LOGGER = Logger.getLogger(NTools.class.getName());
    //Store the servers economy
    public static Economy economy;
    //If the plugin should log debug timing messages
    public static boolean doDebugMessages;
    //Static way to format price placeholders
    public static DecimalFormat numberFormat = new DecimalFormat("#,###");

    /**
     * Void method to regenerate all of the HashMap associated with the plugin, will update with config
     * changes.
     */
    public static void loadToolMaps() {
        trenchBlockBlacklist = AddBlocksToBlacklist.createBlockList("config", "trench-block-blacklist");
        trayBlockWhitelist = AddBlocksToBlacklist.createBlockList("config", "tray-block-whitelist");
        sandWandBlockWhitelist = AddBlocksToBlacklist.createBlockList("config", "sand-block-whitelist");
        trenchTools = AddToolsToMap.createToolMap("trench", "trench-tools.");
        trayTools = AddToolsToMap.createToolMap("tray", "tray-tools.");
        multiTools = AddToolsToMap.createToolMap("multi", "multi-tools.");
        sandWands = AddToolsToMap.createToolMap("sand", "sand-wands.");
        multiToolModeUnique = GetMultiToolLoreID.createUniqueModeIDs("multi-tools.");
        multiToolRadiusUnique = GetMultiToolLoreID.createUniqueRadiusIDs("multi-tools.");
        LOGGER.info("[NTools] Successfully loaded all tools from configuration into internal maps.");
        //Get if the plugin should do debug messages
        doDebugMessages = getFiles().get("config").getBoolean("enable-debug-messages");
    }

    /**
     * Void method to clear all of the HashMap associated with the plugin, since they are static this will
     * need to be called to stop overlap.
     */
    public static void clearMaps() {
        trenchBlockBlacklist.clear();
        trayBlockWhitelist.clear();
        sandWandBlockWhitelist.clear();
        trenchTools.clear();
        trayTools.clear();
        multiTools.clear();
        sandWands.clear();
        multiToolModeUnique.clear();
        multiToolRadiusUnique.clear();
        LOGGER.info("[NTools] Successfully cleared all tools from internal maps.");
    }

    /**
     * Get the plugin files
     *
     * @return LoadFiles instance
     */
    public static LoadFile getFiles() {
        return files;
    }

    public static HashMap<Integer, String> getToolMap(String mapName) {
        if (mapName.equalsIgnoreCase("multi")) return multiTools;
        if (mapName.equalsIgnoreCase("trench")) return trenchTools;
        if (mapName.equalsIgnoreCase("tray")) return trayTools;
        if (mapName.equalsIgnoreCase("sand")) return sandWands;
//        if (mapName.equalsIgnoreCase("lighting")) return lightningWands;
        return null;
    }

    /**
     * Method called on plugin start up
     */
    @Override
    public void onEnable() {
        LOGGER.info("[NTools] Thanks for using NTools, if you find any bugs contact: nbdSteve#0583 on Discord.");
        //Create files instance
        files = new LoadFile();
        //Load the tool maps
        loadToolMaps();
        //Create the Gui instances
        multiToolOptionsGui = new MultiToolOptionsGui();
        buyToolsGenericGui = new BuyToolsGenericGui();
        buyMultiToolsGui = new BuyMultiToolsGui();
        buyTrenchToolsGui = new BuyTrenchToolsGui();
        buyTrayToolsGui = new BuyTrayToolsGui();
        buySandWandsGui = new BuySandWandsGui();
        //Get the server econ
        try {
            economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
        } catch (NullPointerException economyNotEnabled) {
            LOGGER.info("[NTools] Vault.jar not found, disabling economy features.");
            economy = null;
        }
        //Register the commands for the plugin
        getCommand("tools").setExecutor(new Nt(this));
        getCommand("nt").setExecutor(new Nt(this));
        //Register the events for the plugin
        getServer().getPluginManager().registerEvents(new BlockDamageByPlayer(), this);
        getServer().getPluginManager().registerEvents(new CrouchClickOpenToolOptionGui(), this);
        getServer().getPluginManager().registerEvents(new GuiClickListener(), this);
    }

    /**
     * Method called on plugin shutdown
     */
    @Override
    public void onDisable() {
        clearMaps();
        LOGGER.info("[NTools] Thanks for using NTools, if you find any bugs contact nbdSteve#0583 on Discord.");
    }

    /**
     * Gets the generic gui instance
     *
     * @return
     */
    public BuyToolsGenericGui getBuyToolsGenericGui() {
        return buyToolsGenericGui;
    }

    /**
     * Gets the multi tool gui instance
     *
     * @return
     */
    public BuyMultiToolsGui getBuyMultiToolsGui() {
        return buyMultiToolsGui;
    }

    /**
     * Gets the trench tools gui instance
     *
     * @return
     */
    public BuyTrenchToolsGui getBuyTrenchToolsGui() {
        return buyTrenchToolsGui;
    }

    /**
     * Gets the tray tools gui instance
     *
     * @return
     */
    public BuyTrayToolsGui getBuyTrayToolsGui() {
        return buyTrayToolsGui;
    }

    /**
     * Gets the tray tools gui instance
     *
     * @return
     */
    public BuySandWandsGui getBuySandWandsGui() {
        return buySandWandsGui;
    }

    /**
     * Gets the options gui instance
     *
     * @return
     */
    public MultiToolOptionsGui getMultiToolOptionsGui() {
        return multiToolOptionsGui;
    }
}