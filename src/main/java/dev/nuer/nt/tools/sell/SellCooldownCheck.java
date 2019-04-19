package dev.nuer.nt.tools.sell;

import dev.nuer.nt.NTools;
import dev.nuer.nt.external.actionbarapi.ActionBarAPI;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class SellCooldownCheck {

    public static HashMap<UUID, Long> sellWandCDT;
    private static int cooldownForAPI;

    public static boolean isOnSellWandCooldown(UUID playerUUID, int cooldownInSeconds, Player player) {
        if (sellWandCDT == null) {
            sellWandCDT = new HashMap<>();
        }
        cooldownForAPI = cooldownInSeconds;
        if (cooldownInSeconds >= 0) {
            if (sellWandCDT.containsKey(playerUUID)) {
                long timer = ((sellWandCDT.get(playerUUID) / 1000) + cooldownInSeconds) - (System.currentTimeMillis() / 1000);
                if (timer > 0) {
                    if (NTools.getFiles().get("config").getBoolean("cooldown-action-bar.enabled")) {
                        String message = NTools.getFiles().get("config").getString("cooldown-action-bar.message").replace("{time}", String.valueOf(timer));
                        ActionBarAPI.sendActionBar(player, ChatColor.translateAlternateColorCodes('&', message));
                    } else {
                        new PlayerMessage("sell-wand-cooldown", Bukkit.getPlayer(playerUUID), "{time}", String.valueOf(timer));
                    }
                } else {
                    sellWandCDT.remove(playerUUID);
                }
                return true;
            } else {
                sellWandCDT.put(playerUUID, System.currentTimeMillis());
            }
        }
        return false;
    }

    public static long getSellWandCooldown(Player player) {
        try {
            return ((sellWandCDT.get(player.getUniqueId()) / 1000) + cooldownForAPI) - (System.currentTimeMillis() / 1000);
        } catch (NullPointerException cooldownNotRun) {
            return 0;
        }
    }
}
