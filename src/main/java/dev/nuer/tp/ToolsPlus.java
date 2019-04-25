package dev.nuer.tp;

import dev.nuer.tp.cmd.ToolsCmd;
import dev.nuer.tp.file.LoadFile;
import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.gui.listener.GuiClickListener;
import dev.nuer.tp.initialize.GuiInitializer;
import dev.nuer.tp.initialize.MapInitializer;
import dev.nuer.tp.listener.*;
import dev.nuer.tp.tools.multi.OmniFunctionality;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.util.logging.Logger;

/**
 * Main class for the ToolsPlus plugin
 */
public final class ToolsPlus extends JavaPlugin {
    //Store the plugin files
    private static LoadFile files;
    //Store the gui instances
    private GuiInitializer pluginGui;
    //Create a logger for the plugin
    public static Logger LOGGER = Bukkit.getLogger();
    //Store the servers economy
    public static Economy economy;
    //If the plugin should log debug timing messages
    public static boolean doDebugMessages;
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
        LOGGER.info("[ToolsPlus] Thanks for using ToolsPlus, if you find any bugs contact nbdSteve#0583 on Discord.");
        //Create files instance
        files = new LoadFile();
        //Load the black / white and unique id list maps
        MapInitializer.initializeMaps();
        //Create the Gui instances
        pluginGui = new GuiInitializer();
        //Load the omni tool block lists
        OmniFunctionality.loadOmniToolBlocks();
        //Get the server econ
        try {
            economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
        } catch (NullPointerException economyNotEnabled) {
            LOGGER.info("[ToolsPlus] Vault.jar not found, disabling economy features.");
            economy = null;
        }
        //Register the commands for the plugin
        getCommand("tools").setExecutor(new ToolsCmd(this));
        getCommand("nt").setExecutor(new ToolsCmd(this));
        //Register the events for the plugin
        getServer().getPluginManager().registerEvents(new BlockDamageByPlayer(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractWithMob(), this);
        getServer().getPluginManager().registerEvents(new CustomToolEventsListener(), this);
        getServer().getPluginManager().registerEvents(new CrouchRightClickOpenGui(), this);
        getServer().getPluginManager().registerEvents(new GuiClickListener(), this);
    }

    /**
     * Method called on plugin shutdown
     */
    @Override
    public void onDisable() {
        MapInitializer.clearMaps();
        LOGGER.info("[ToolsPlus] Thanks for using ToolsPlus, if you find any bugs contact nbdSteve#0583 on Discord.");
    }

    /**
     * Gets the specified gui based off of the name
     *
     * @param guiName String, gui name
     * @return
     */
    public AbstractGui getGuiByName(String guiName) {
        return pluginGui.getGuiByName(guiName);
    }
}