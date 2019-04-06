package dev.nuer.nt;

import dev.nuer.nt.cmd.Tools;
import dev.nuer.nt.event.TrenchBlockBreak;
import dev.nuer.nt.file.LoadFile;
import dev.nuer.nt.method.AddBlocksToBlacklist;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class NTools extends JavaPlugin {
    //Get plugin files
    private static LoadFile files;
    //Store the trench block blacklist
    private static ArrayList<String> trenchBlockBlacklist;
    //Store the tray block whitelist
    private static ArrayList<String> trayBlockWhitelist;

    @Override
    public void onEnable() {
        files = new LoadFile();
        trenchBlockBlacklist = AddBlocksToBlacklist.addTrenchBlacklist();
        trayBlockWhitelist = AddBlocksToBlacklist.addTrayWhitelist();
        getCommand("Tools").setExecutor(new Tools(this));
        getServer().getPluginManager().registerEvents(new TrenchBlockBreak(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static LoadFile getFiles() {
        return files;
    }

    public static ArrayList<String> getTrenchBlockBlacklist() {
        return trenchBlockBlacklist;
    }

    public static ArrayList<String> getTrayBlockWhitelist() {
        return trayBlockWhitelist;
    }
}
