package gg.steve.mc.tp.modules.listener;

import gg.steve.mc.tp.managers.Files;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("Trench module has been registered.");
        event.getPlayer().sendMessage(Files.TRENCH_CONFIG.get().getString("example-string"));
    }
}
