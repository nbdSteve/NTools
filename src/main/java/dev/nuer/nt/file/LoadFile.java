package dev.nuer.nt.file;

import dev.nuer.nt.NTools;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.HashMap;

public class LoadFile {
    //HashMap to store the files
    private HashMap<Files, CreateFile> files;

    /**
     * Enum to store each file, this is public so we can call methods on these
     */
    public enum Files {
        CONFIG, MESSAGES, LIGHTNING, LIGHTNING_PURCHASE_GUI, MULTI, MULTI_CONFIG_GUI, MULTI_PURCHASE_GUI,
        SAND, SAND_PURCHASE_GUI, TRAY, TRAY_PURCHASE_GUI, TRENCH, TRENCH_PURCHASE_GUI, HARVESTER,
        HARVESTER_PURCHASE_GUI
    }

    /**
     * Generate all of the files in the enum
     */
    public LoadFile() {
        files = new HashMap<>();
        //Load generic files
        files.put(Files.CONFIG, new CreateFile("ntools.yml"));
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
        //Log that files are loaded
        NTools.LOGGER.info("[NTools] Successfully loaded all configuration files...");
    }

    /**
     * Gets the specified YAML file
     *
     * @param fileName String, the name of the file from the Files enum
     * @return
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
        NTools.LOGGER.info("[NTools] Reloading configuration files...");
    }
}
