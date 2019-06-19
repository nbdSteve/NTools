package dev.nuer.tp;

import dev.nuer.tp.cmd.ToolsCmd;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.gui.listener.GuiClickListener;
import dev.nuer.tp.managers.GuiManager;
import dev.nuer.tp.managers.ToolsAttributeManager;
import dev.nuer.tp.listener.*;
import dev.nuer.tp.method.VersionChecker;
import dev.nuer.tp.tools.OmniFunctionality;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.util.logging.Logger;

/**
 * Main class for the Tools+ plugin
 */
public final class ToolsPlus extends JavaPlugin {
    //Store the plugins main instance
    public static ToolsPlus instance;
    //Store the servers economy
    public static Economy economy;
    //If the plugin should log debug timing messages
    public static boolean debugMode;
    //Static way to format price placeholders
    public static DecimalFormat numberFormat = new DecimalFormat("#,###.##");
    //Create a logger for the plugin
    public static Logger LOGGER = Bukkit.getLogger();
    //Store the plugin version
    public static String version = "1.5.1.2";

    /**
     * Method called on plugin start up
     */
    @Override
    public void onEnable() {
        LOGGER.info("[Tools+] Thank you for choosing to use Tools+!");
        LOGGER.info("[Tools+] If you find any bugs please contact nbdSteve#0583 on Discord.");
        //Get the instance
        instance = this;
        //Load internal plugin files
        FileManager.load();
        //Load the black / white and unique id list maps
        ToolsAttributeManager.load();
        //Create the Gui instances
        GuiManager.load();
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
        getServer().getPluginManager().registerEvents(new PlayerToolInteractionListener(), this);
        getServer().getPluginManager().registerEvents(new GuiClickListener(), this);
        //Check that the plugin is in the latest version
        if (!FileManager.get("config").getBoolean("disable-update-version-message")) {
            VersionChecker.checkVersion(null);
            getServer().getPluginManager().registerEvents(new VersionChecker(), this);
        }
    }

    /**
     * Method called on plugin shutdown
     */
    @Override
    public void onDisable() {
        ToolsAttributeManager.clear();
        OmniFunctionality.clearOmniLists();
        LOGGER.info("[Tools+] Thank you for choosing to use Tools+!");
        LOGGER.info("[Tools+] If you find any bugs please contact nbdSteve#0583 on Discord.");
    }

    /**
     * Updates whether the plugin is in debug more or not
     */
    public static void updateDebugMode() {
        debugMode = FileManager.get("config").getBoolean("enable-debug");
    }
}