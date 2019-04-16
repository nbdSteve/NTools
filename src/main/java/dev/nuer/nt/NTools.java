package dev.nuer.nt;

import dev.nuer.nt.cmd.Nt;
import dev.nuer.nt.event.BlockDamageByPlayer;
import dev.nuer.nt.event.CrouchRightClickOpenGui;
import dev.nuer.nt.file.LoadFile;
import dev.nuer.nt.gui.AbstractGui;
import dev.nuer.nt.gui.listener.GuiClickListener;
import dev.nuer.nt.initialize.GuiInitializer;
import dev.nuer.nt.initialize.OtherMapInitializer;
import dev.nuer.nt.initialize.ToolMapInitializer;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.util.logging.Logger;

/**
 * Main class for the NTools plugin
 */
public final class NTools extends JavaPlugin {
    //Store the plugin files
    private static LoadFile files;
    //Store the gui instances
    private GuiInitializer pluginGui;
    //Create a logger for the plugin
    public static Logger LOGGER = Logger.getLogger(NTools.class.getName());
    //Store the servers economy
    public static Economy economy;
    //If the plugin should log debug timing messages
    public static boolean doDebugMessages;
    //Static way to format price placeholders
    public static DecimalFormat numberFormat = new DecimalFormat("#,###");

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
        LOGGER.info("[NTools] Thanks for using NTools, if you find any bugs contact nbdSteve#0583 on Discord.");
        //Create files instance
        files = new LoadFile();
        //Load the black / white and unique id list maps
        OtherMapInitializer.initializeMaps();
        //Load the tool maps
        ToolMapInitializer.initializeToolMaps();
        //Create the Gui instances
        pluginGui = new GuiInitializer();
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
        getServer().getPluginManager().registerEvents(new CrouchRightClickOpenGui(), this);
        getServer().getPluginManager().registerEvents(new GuiClickListener(), this);
    }

    /**
     * Method called on plugin shutdown
     */
    @Override
    public void onDisable() {
        OtherMapInitializer.clearMaps();
        ToolMapInitializer.clearToolMaps();
        LOGGER.info("[NTools] Thanks for using NTools, if you find any bugs contact nbdSteve#0583 on Discord.");
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