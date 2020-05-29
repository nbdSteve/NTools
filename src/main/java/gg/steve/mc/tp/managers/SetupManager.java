package gg.steve.mc.tp.managers;

import com.sun.java.accessibility.util.GUIInitializedMulticaster;
import gg.steve.mc.tp.cmd.ToolsPlusCmd;
import gg.steve.mc.tp.gui.GuiClickListener;
import gg.steve.mc.tp.gui.GuiManager;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.player.HoldToolListener;
import gg.steve.mc.tp.player.PlayerToolListener;
import gg.steve.mc.tp.player.PlayerToolManager;
import gg.steve.mc.tp.tool.ToolsManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Class that handles setting up the plugin on start
 */
public class SetupManager {
    private static FileManager fileManager;

    private SetupManager() throws IllegalAccessException {
        throw new IllegalAccessException("Manager class cannot be instantiated.");
    }

    /**
     * Loads the files into the file manager
     *
     * @param fileManager FileManager, the plugins file manager
     */
    public static void setupFiles(FileManager fm) {
        fileManager = fm;
        Files.CONFIG.load(fm);
        Files.PERMISSIONS.load(fm);
        Files.DEBUG.load(fm);
        Files.MESSAGES.load(fm);
    }

    public static void registerCommands(JavaPlugin instance) {
        instance.getCommand("tp").setExecutor(new ToolsPlusCmd());
    }

    /**
     * Register all of the events for the plugin
     *
     * @param instance Plugin, the main plugin instance
     */
    public static void registerEvents(JavaPlugin instance) {
        PluginManager pm = instance.getServer().getPluginManager();
        pm.registerEvents(new GuiClickListener(), instance);
        pm.registerEvents(new PlayerToolManager(), instance);
        pm.registerEvents(new HoldToolListener(), instance);
        pm.registerEvents(new PlayerToolListener(), instance);
    }

    public static void registerEvent(JavaPlugin instance, Listener listener) {
        instance.getServer().getPluginManager().registerEvents(listener, instance);
    }

    public static void loadPluginCache() {
        ModuleManager.loadInstalledModules();
        GuiManager.initialise();
        ToolsManager.initialiseTools();
        PlayerToolManager.initialise();
        ToolConfigDataManager.initialise();
    }

    public static void shutdownPluginCache() {
        ToolConfigDataManager.shutdown();
        PlayerToolManager.shutdown();
        ToolsManager.shutdown();
        GuiManager.shutdown();
        ModuleManager.uninstalledAllModules();
    }

    public static FileManager getFileManager() {
        return fileManager;
    }
}
