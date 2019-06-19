package dev.nuer.tp.tools;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.player.PlayerMessage;
import dev.nuer.tp.support.actionbarapi.ActionBarAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

/**
 * Class that handles all player tool cooldowns for the plugin
 */
public class PlayerToolCooldown {
    //Store the players who are on the sell wand cooldown
    private static HashMap<UUID, Integer> playersOnSellWandCooldown = new HashMap<>();
    //Store the players who are on the sand wand cooldown
    private static HashMap<UUID, Integer> playersOnSandWandCooldown = new HashMap<>();
    //Store the players who are on the lightning wand cooldown
    private static HashMap<UUID, Integer> playersOnLightningWandCooldown = new HashMap<>();
    //Store the players who are on the tnt wand cooldown
    private static HashMap<UUID, Integer> playersOnTntWandCooldown = new HashMap<>();
    //Store the players who are on the aqua wand cooldown
    private static HashMap<UUID, Integer> playersOnAquaWandCooldown = new HashMap<>();
    //Store the players who are on the smelt wand cooldown
    private static HashMap<UUID, Integer> playersOnSmeltWandCooldown = new HashMap<>();
    //Store the players who are on the chunk wand cooldown
    private static HashMap<UUID, Integer> playersOnChunkWandCooldown = new HashMap<>();

    /**
     * Sets the respective player on the respective cooldown
     *
     * @param player           Player, the player to set on cooldown
     * @param delayInSeconds   Integer, the length of cooldown
     * @param cooldownToolType String, the tool type
     */
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
        }.runTaskTimer(ToolsPlus.instance, 0L, 20L);
    }

    /**
     * Check if the player is on a tool cooldown
     *
     * @param player           Player, the player to check
     * @param cooldownToolType String, the type of tool for the cooldown
     * @return boolean
     */
    public static boolean isOnCooldown(Player player, String cooldownToolType) {
        if (getCooldownMap(cooldownToolType).get(player.getUniqueId()) != null) {
            return true;
        }
        return false;
    }

    /**
     * Gets the players remaining cooldown in seconds
     *
     * @param player             Player, the player  to query
     * @param cooldownToolType   String, the type of cooldown
     * @param sendPlayerResponse boolean, if a message will be sent to the player
     * @return
     */
    public static int getCooldownRemaining(Player player, String cooldownToolType, boolean sendPlayerResponse) {
        if (sendPlayerResponse) {
            if (FileManager.get("config").getBoolean("cooldown-action-bar.enabled")) {
                String message = FileManager.get("config").getString("cooldown-action-bar." + cooldownToolType + "-message").replace("{time}", String.valueOf(getCooldownMap(cooldownToolType).get(player.getUniqueId())));
                ActionBarAPI.sendActionBar(player, Chat.applyColor(message));
            } else {
                new PlayerMessage("wand-cooldown", Bukkit.getPlayer(player.getUniqueId()), "{time}", String.valueOf(getCooldownMap(cooldownToolType).get(player.getUniqueId())));
            }
        }
        return getCooldownMap(cooldownToolType).get(player.getUniqueId());
    }

    /**
     * Gets the cooldown map for a tool
     *
     * @param cooldownToolType String, the type of tool for the cooldown
     * @return HashMap<UUID, Integer>
     */
    public static HashMap<UUID, Integer> getCooldownMap(String cooldownToolType) {
        if (cooldownToolType.equalsIgnoreCase("sell")) return playersOnSellWandCooldown;
        if (cooldownToolType.equalsIgnoreCase("sand")) return playersOnSandWandCooldown;
        if (cooldownToolType.equalsIgnoreCase("lightning")) return playersOnLightningWandCooldown;
        if (cooldownToolType.equalsIgnoreCase("tnt")) return playersOnTntWandCooldown;
        if (cooldownToolType.equalsIgnoreCase("aqua")) return playersOnAquaWandCooldown;
        if (cooldownToolType.equalsIgnoreCase("smelt")) return playersOnSmeltWandCooldown;
        if (cooldownToolType.equalsIgnoreCase("chunk")) return playersOnChunkWandCooldown;
        return null;
    }
}
