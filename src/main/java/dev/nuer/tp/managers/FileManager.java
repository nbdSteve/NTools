package dev.nuer.tp.managers;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.method.fileCreation.PluginFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

/**
 * Class that handles loading / generating files on start up and reload
 */
public class FileManager {
    //HashMap to store the files
    private static HashMap<Files, PluginFile> files;

    /**
     * Enum to store each file, this is public so we can call methods on these
     */
    public enum Files {
        CONFIG, MESSAGES, LIGHTNING, LIGHTNING_PURCHASE_GUI, MULTI, MULTI_CONFIG_GUI, MULTI_PURCHASE_GUI,
        SAND, SAND_PURCHASE_GUI, TRAY, TRAY_PURCHASE_GUI, TRENCH, TRENCH_PURCHASE_GUI, HARVESTER,
        HARVESTER_PURCHASE_GUI, HARVESTER_CONFIG_GUI, SELL, SELL_PURCHASE_GUI, SELL_PRICE_LIST, SELL_CONFIG_GUI,
        TNT, TNT_PURCHASE_GUI, TNT_CONFIG_GUI, OMNI_CONFIG, AQUA, AQUA_PURCHASE_GUI, AQUA_CONFIG_GUI
    }

    /**
     * Generate all of the files in the enum
     */
    public static void load() {
        files = new HashMap<>();
        //Load generic files
        files.put(Files.CONFIG, new PluginFile("tools+.yml"));
        files.put(Files.MESSAGES, new PluginFile("messages.yml"));
        files.put(Files.OMNI_CONFIG, new PluginFile("omni-block-list.yml"));
        //Load lightning wand files
        files.put(Files.LIGHTNING, new PluginFile("lightning" + File.separator + "wands.yml"));
        files.put(Files.LIGHTNING_PURCHASE_GUI, new PluginFile("lightning" + File.separator + "purchase-gui.yml"));
        //Load multi tool files
        files.put(Files.MULTI, new PluginFile("multi" + File.separator + "tools.yml"));
        files.put(Files.MULTI_CONFIG_GUI, new PluginFile("multi" + File.separator + "config-gui.yml"));
        files.put(Files.MULTI_PURCHASE_GUI, new PluginFile("multi" + File.separator + "purchase-gui.yml"));
        //Load sand wand files
        files.put(Files.SAND, new PluginFile("sand" + File.separator + "wands.yml"));
        files.put(Files.SAND_PURCHASE_GUI, new PluginFile("sand" + File.separator + "purchase-gui.yml"));
        //Load tray tool files
        files.put(Files.TRAY, new PluginFile("tray" + File.separator + "tools.yml"));
        files.put(Files.TRAY_PURCHASE_GUI, new PluginFile("tray" + File.separator + "purchase-gui.yml"));
        //Load trench tool files
        files.put(Files.TRENCH, new PluginFile("trench" + File.separator + "tools.yml"));
        files.put(Files.TRENCH_PURCHASE_GUI, new PluginFile("trench" + File.separator + "purchase-gui.yml"));
        //Load harvester tool files
        files.put(Files.HARVESTER, new PluginFile("harvester" + File.separator + "tools.yml"));
        files.put(Files.HARVESTER_PURCHASE_GUI, new PluginFile("harvester" + File.separator + "purchase-gui.yml"));
        files.put(Files.HARVESTER_CONFIG_GUI, new PluginFile("harvester" + File.separator + "config-gui.yml"));
        //Load sell wand files
        files.put(Files.SELL, new PluginFile("sell" + File.separator + "wands.yml"));
        files.put(Files.SELL_PURCHASE_GUI, new PluginFile("sell" + File.separator + "purchase-gui.yml"));
        files.put(Files.SELL_PRICE_LIST, new PluginFile("sell" + File.separator + "price-list.yml"));
        files.put(Files.SELL_CONFIG_GUI, new PluginFile("sell" + File.separator + "config-gui.yml"));
        //Load tnt wand files
        files.put(Files.TNT, new PluginFile("tnt" + File.separator + "wands.yml"));
        files.put(Files.TNT_PURCHASE_GUI, new PluginFile("tnt" + File.separator + "purchase-gui.yml"));
        files.put(Files.TNT_CONFIG_GUI, new PluginFile("tnt" + File.separator + "config-gui.yml"));
        //Load aqua wand files
        files.put(Files.AQUA, new PluginFile("aqua" + File.separator + "wands.yml"));
        files.put(Files.AQUA_PURCHASE_GUI, new PluginFile("aqua" + File.separator + "purchase-gui.yml"));
        files.put(Files.AQUA_CONFIG_GUI, new PluginFile("aqua" + File.separator + "config-gui.yml"));
        //Log that files are loaded
        ToolsPlus.LOGGER.info("[Tools+] Successfully loaded all configuration files...");
    }

    /**
     * Gets the specified YAML file
     *
     * @param fileName String, the name of the file from the Files enum
     * @return FileConfiguration, the yaml config for that file
     */
    public static YamlConfiguration get(String fileName) {
        if (files.containsKey(Files.valueOf(fileName.toUpperCase()))) {
            return files.get(Files.valueOf(fileName.toUpperCase())).get();
        }
        return null;
    }

    /**
     * Saves the specified file
     *
     * @param fileName String, file to save
     */
    public static void saveFile(String fileName) {
        if (files.containsKey(Files.valueOf(fileName.toUpperCase()))) {
            files.get(Files.valueOf(fileName.toUpperCase())).save();
        }
    }

    /**
     * Reloads all of the files in the Files enum
     */
    public static void reload() {
        for (PluginFile file : files.values()) {
            file.reload();
        }
        ToolsPlus.LOGGER.info("[Tools+] Reloading configuration files...");
    }
}
