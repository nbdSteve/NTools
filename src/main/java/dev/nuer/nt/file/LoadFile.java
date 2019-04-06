package dev.nuer.nt.file;

import dev.nuer.nt.NTools;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class LoadFile {
    //Register main instance
    private Plugin plugin = NTools.getPlugin(NTools.class);
    //HashMap to store the files
    private HashMap<Files, CreateFile> files;

    /**
     * Enum to store each file, this is public so we can call methods on these
     */
    public enum Files {
        CONFIG, MESSAGES, TOOLS
    }

    /**
     * Generate all of the files in the enum
     */
    public LoadFile() {
        files = new HashMap<>();
        files.put(Files.CONFIG, new CreateFile("config.yml"));
        files.put(Files.MESSAGES, new CreateFile("messages.yml"));
        files.put(Files.TOOLS, new CreateFile("tools.yml"));
        plugin.getLogger().info("Loading provided files...");
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
        plugin.getLogger().info("Reloading provided files...");
    }
}
