package dev.nuer.tp.file;

import dev.nuer.tp.ToolsPlus;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.HashMap;

/**
 * Class that handles loading / generating files on start up and reload
 */
public class LoadFile {
    //HashMap to store the files
    private HashMap<Files, CreateFile> files;

    /**
     * Enum to store each file, this is public so we can call methods on these
     */
    public enum Files {
        CONFIG, MESSAGES, LIGHTNING, LIGHTNING_PURCHASE_GUI, MULTI, MULTI_CONFIG_GUI, MULTI_PURCHASE_GUI,
        SAND, SAND_PURCHASE_GUI, TRAY, TRAY_PURCHASE_GUI, TRENCH, TRENCH_PURCHASE_GUI, HARVESTER,
        HARVESTER_PURCHASE_GUI, HARVESTER_CONFIG_GUI, SELL, SELL_PURCHASE_GUI, SELL_PRICE_LIST, SELL_CONFIG_GUI,
        TNT, TNT_PURCHASE_GUI, TNT_CONFIG_GUI
    }

    /**
     * Generate all of the files in the enum
     */
    public LoadFile() {
        files = new HashMap<>();
        //Load generic files
        files.put(Files.CONFIG, new CreateFile("toolsplus.yml"));
        files.put(Files.MESSAGES, new CreateFile("messages.yml"));
        //Load lightning wand files
        files.put(Files.LIGHTNING, new CreateFile("lightning" + File.separator + "wands.yml"));
        files.put(Files.LIGHTNING_PURCHASE_GUI, new CreateFile("lightning" + File.separator + "purchase-gui.yml"));
        //Load multi tool files
        files.put(Files.MULTI, new CreateFile("multi" + File.separator + "tools.yml"));
        files.put(Files.MULTI_CONFIG_GUI, new CreateFile("multi" + File.separator + "config-gui.yml"));
        files.put(Files.MULTI_PURCHASE_GUI, new CreateFile("multi" + File.separator + "purchase-gui.yml"));
        //Load sand wand files
        files.put(Files.SAND, new CreateFile("sand" + File.separator + "wands.yml"));
        files.put(Files.SAND_PURCHASE_GUI, new CreateFile("sand" + File.separator + "purchase-gui.yml"));
        //Load tray tool files
        files.put(Files.TRAY, new CreateFile("tray" + File.separator + "tools.yml"));
        files.put(Files.TRAY_PURCHASE_GUI, new CreateFile("tray" + File.separator + "purchase-gui.yml"));
        //Load trench tool files
        files.put(Files.TRENCH, new CreateFile("trench" + File.separator + "tools.yml"));
        files.put(Files.TRENCH_PURCHASE_GUI, new CreateFile("trench" + File.separator + "purchase-gui.yml"));
        //Load harvester tool files
        files.put(Files.HARVESTER, new CreateFile("harvester" + File.separator + "tools.yml"));
        files.put(Files.HARVESTER_PURCHASE_GUI, new CreateFile("harvester" + File.separator + "purchase-gui.yml"));
        files.put(Files.HARVESTER_CONFIG_GUI, new CreateFile("harvester" + File.separator + "config-gui.yml"));
        //Load sell wand files
        files.put(Files.SELL, new CreateFile("sell" + File.separator + "wands.yml"));
        files.put(Files.SELL_PURCHASE_GUI, new CreateFile("sell" + File.separator + "purchase-gui.yml"));
        files.put(Files.SELL_PRICE_LIST, new CreateFile("sell" + File.separator + "price-list.yml"));
        files.put(Files.SELL_CONFIG_GUI, new CreateFile("sell" + File.separator + "config-gui.yml"));
        //Load tnt wand files
        files.put(Files.TNT, new CreateFile("tnt" + File.separator + "wands.yml"));
        files.put(Files.TNT_PURCHASE_GUI, new CreateFile("tnt" + File.separator + "purchase-gui.yml"));
        files.put(Files.TNT_CONFIG_GUI, new CreateFile("tnt" + File.separator + "config-gui.yml"));
        //Log that files are loaded
        ToolsPlus.LOGGER.info("[ToolsPlus] Successfully loaded all configuration files...");
    }

    /**
     * Gets the specified YAML file
     *
     * @param fileName String, the name of the file from the Files enum
     * @return FileConfiguration, the yaml config for that file
     */
    public FileConfiguration get(String fileName) {
        if (files.containsKey(Files.valueOf(fileName.toUpperCase()))) {
            return files.get(Files.valueOf(fileName.toUpperCase())).get();
        }
        return null;
    }

    /**
     * Reloads all of the files in the Files enum
     */
    public void reload() {
        for (Files file : Files.values()) {
            files.get(file).reload();
        }
        ToolsPlus.LOGGER.info("[ToolsPlus] Reloading configuration files...");
    }
}
