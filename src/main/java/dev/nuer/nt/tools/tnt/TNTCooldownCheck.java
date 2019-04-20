package dev.nuer.nt.tools.tnt;

import dev.nuer.nt.NTools;
import dev.nuer.nt.external.actionbarapi.ActionBarAPI;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class TNTCooldownCheck {
    public static HashMap<UUID, Long> tntWandCDT;
    private static int cooldownForAPI;

    public static boolean isOnTNTWandCooldown(UUID playerUUID, int cooldownInSeconds, Player player) {
        if (tntWandCDT == null) {
            tntWandCDT = new HashMap<>();
        }
        cooldownForAPI = cooldownInSeconds;
        if (cooldownInSeconds >= 0) {
            if (tntWandCDT.containsKey(playerUUID)) {
                long timer = ((tntWandCDT.get(playerUUID) / 1000) + cooldownInSeconds) - (System.currentTimeMillis() / 1000);
                if (timer > 0) {
                    if (NTools.getFiles().get("config").getBoolean("cooldown-action-bar.enabled")) {
                        String message = NTools.getFiles().get("config").getString("cooldown-action-bar.message").replace("{time}", String.valueOf(timer));
                        ActionBarAPI.sendActionBar(player, ChatColor.translateAlternateColorCodes('&', message));
                    } else {
                        new PlayerMessage("tnt-wand-cooldown", Bukkit.getPlayer(playerUUID), "{time}", String.valueOf(timer));
                    }
                } else {
                    tntWandCDT.remove(playerUUID);
                }
                return true;
            } else {
                tntWandCDT.put(playerUUID, System.currentTimeMillis());
            }
        }
        return false;
    }

    public static long getTNTWandCooldown(Player player) {
        try {
            return ((tntWandCDT.get(player.getUniqueId()) / 1000) + cooldownForAPI) - (System.currentTimeMillis() / 1000);
        } catch (NullPointerException cooldownNotRun) {
            return 0;
        }
    }
}
