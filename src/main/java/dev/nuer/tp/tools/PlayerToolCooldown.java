package dev.nuer.tp.tools;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.external.actionbarapi.ActionBarAPI;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class PlayerToolCooldown {
    private static HashMap<UUID, Integer> playersOnSellWandCooldown = new HashMap<>();
    private static HashMap<UUID, Integer> playersOnSandWandCooldown = new HashMap<>();
    private static HashMap<UUID, Integer> playersOnLightningWandCooldown = new HashMap<>();
    private static HashMap<UUID, Integer> playersOnTntWandCooldown = new HashMap<>();

    public static void setPlayerOnCooldown(Player player, int delayInSeconds, String cooldownToolType) {
        if (delayInSeconds < 0) {
            return;
        }
        //Add the player to the appropriate cooldown map
        getCooldownMap(cooldownToolType).put(player.getUniqueId(), delayInSeconds);
        new BukkitRunnable() {
            int timer = delayInSeconds;

            @Override
            public void run() {
                if (timer < delayInSeconds && timer > 0) {
                    getCooldownMap(cooldownToolType).remove(player.getUniqueId());
                    getCooldownMap(cooldownToolType).put(player.getUniqueId(), timer);
                    getCooldownRemaining(player, cooldownToolType, true);
                } else if (timer == 0) {
                    getCooldownMap(cooldownToolType).remove(player.getUniqueId());
                    this.cancel();
                }
                timer--;
            }
        }.runTaskTimer(ToolsPlus.getPlugin(ToolsPlus.class), 0L, 20L);
    }

    public static boolean isOnCooldown(Player player, String cooldownToolType) {
        if (getCooldownMap(cooldownToolType).get(player.getUniqueId()) != null) {
            return true;
        }
        return false;
    }

    public static int getCooldownRemaining(Player player, String cooldownToolType, boolean sendPlayerResponse) {
        if (sendPlayerResponse) {
            if (ToolsPlus.getFiles().get("config").getBoolean("cooldown-action-bar.enabled")) {
                String message = ToolsPlus.getFiles().get("config").getString("cooldown-action-bar." + cooldownToolType + "-message").replace("{time}", String.valueOf(getCooldownMap(cooldownToolType).get(player.getUniqueId())));
                ActionBarAPI.sendActionBar(player, ChatColor.translateAlternateColorCodes('&', message));
            } else {
                new PlayerMessage("wand-cooldown", Bukkit.getPlayer(player.getUniqueId()), "{time}", String.valueOf(getCooldownMap(cooldownToolType).get(player.getUniqueId())));
            }
        }
        return getCooldownMap(cooldownToolType).get(player.getUniqueId());
    }

    public static HashMap<UUID, Integer> getCooldownMap(String cooldownToolType) {
        if (cooldownToolType.equalsIgnoreCase("sell")) return playersOnSellWandCooldown;
        if (cooldownToolType.equalsIgnoreCase("sand")) return playersOnSandWandCooldown;
        if (cooldownToolType.equalsIgnoreCase("lightning")) return playersOnLightningWandCooldown;
        if (cooldownToolType.equalsIgnoreCase("tnt")) return playersOnTntWandCooldown;
        return null;
    }
}
