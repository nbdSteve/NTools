package dev.nuer.tp.method;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Class that handles checking for version updates
 */
public class VersionChecker implements Listener {
    //Store the resource key from spigot
    private static String resourceKey = "66897";
    //Store the current version internally
    private static String pluginVersion = "1.5.0.1";

    /**
     * Checks the latest version against the current version
     *
     * @param player Player, player to send the update message to
     */
    public static void checkVersion(Player player) {
        try {
            URLConnection urlConn = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceKey).openConnection();
            String version = new BufferedReader(new InputStreamReader(urlConn.getInputStream())).readLine();
            if (!version.equalsIgnoreCase(pluginVersion)) {
                ToolsPlus.LOGGER.severe("[Tools+] There is a new version of Tools+ available for download, please update to the latest version.");
                if (player != null) {
                    new PlayerMessage("outdated-version", player, "{currentVersion}",
                            pluginVersion, "{latestVersion}", version);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        if (event.getPlayer().isOp()) {
            Bukkit.getScheduler().runTaskLater(ToolsPlus.instance, () -> {
                checkVersion(event.getPlayer());
            }, 8L);
        }
    }
}