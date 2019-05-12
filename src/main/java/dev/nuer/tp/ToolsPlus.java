package dev.nuer.tp;

import dev.nuer.tp.cmd.ToolsCmd;
import dev.nuer.tp.file.LoadFile;
import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.gui.listener.GuiClickListener;
import dev.nuer.tp.initialize.GuiInitializer;
import dev.nuer.tp.initialize.MapInitializer;
import dev.nuer.tp.listener.*;
import dev.nuer.tp.method.VersionChecker;
import dev.nuer.tp.tools.OmniFunctionality;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.util.logging.Logger;

/**
 * Main class for the Tools+ plugin
 */
public final class ToolsPlus extends JavaPlugin {
    //Store the plugins main instance
    public static ToolsPlus instance;
    //Store the plugin files
    private static LoadFile files;
    //Store the gui instances
    private static GuiInitializer pluginGui;
    //Create a logger for the plugin
    public static Logger LOGGER = Bukkit.getLogger();
    //Store the servers economy
    public static Economy economy;
    //If the plugin should log debug timing messages
    public static boolean debugMode;
    //Static way to format price placeholders
    public static DecimalFormat numberFormat = new DecimalFormat("#,###.##");

    /**
     * Get the plugin files
     *
     * @return LoadFiles instance
     */
    public static LoadFile getFiles() {
        return files;
    }

    /**
     * Method called on plugin start up
     */
    @Override
    public void onEnable() {
        LOGGER.info("[Tools+] Thank you for choosing to use Tools+!");
        LOGGER.info("[Tools+] If you find any bugs please contact nbdSteve#0583 on Discord.");
        //Get the instance
        instance = this;
        //Create files instance
        files = new LoadFile();
        //Load the black / white and unique id list maps
        MapInitializer.initializeMaps();
        //Create the Gui instances
        instantiateGuis();
        //Load the omni tool block lists
        OmniFunctionality.loadOmniToolBlocks();
        //Get the server economy
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
        } else {
            LOGGER.info("[Tools+] Unable to find economy instance, disabling economy features.");
            economy = null;
        }
        //Get if the plugin is in debug mode
        updateDebugMode();
        //Register the commands for the plugin
        getCommand("t+").setExecutor(new ToolsCmd(this));
        //Register the events for the plugin
        getServer().getPluginManager().registerEvents(new BlockDamageByPlayer(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractWithMob(), this);
        getServer().getPluginManager().registerEvents(new CustomToolEventsListener(), this);
        getServer().getPluginManager().registerEvents(new CrouchRightClickOpenGui(), this);
        getServer().getPluginManager().registerEvents(new GuiClickListener(), this);
        //Check that the plugin is in the latest version
//        VersionChecker.checkVersion(null);
//        getServer().getPluginManager().registerEvents(new VersionChecker(), this);
    }

    /**
     * Method called on plugin shutdown
     */
    @Override
    public void onDisable() {
        MapInitializer.clearMaps();
        OmniFunctionality.clearOmniLists();
        LOGGER.info("[Tools+] Thank you for choosing to use Tools+!");
        LOGGER.info("[Tools+] If you find any bugs please contact nbdSteve#0583 on Discord.");
    }

    /**
     * Creates a new instance of the plugins Guis, called on start up and reload
     */
    public static void instantiateGuis() {
        pluginGui = new GuiInitializer();
    }

    /**
     * Gets the specified gui based off of the name
     *
     * @param guiName String, gui name
     * @return AbstractGui
     */
    public AbstractGui getGuiByName(String guiName) {
        return pluginGui.getGuiByName(guiName);
    }

    public static void updateDebugMode() {
        debugMode = getFiles().get("config").getBoolean("enable-debug");
    }
}