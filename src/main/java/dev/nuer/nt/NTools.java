package dev.nuer.nt;

import dev.nuer.nt.file.LoadFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class NTools extends JavaPlugin {
    //Get plugin files
    private static LoadFile files;

    @Override
    public void onEnable() {
        files = new LoadFile();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static LoadFile getFiles() {
        return files;
    }
}
