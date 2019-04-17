package dev.nuer.nt.tools.lightning;

import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class LightningCooldownCheck {

    public static HashMap<UUID, Long> lightningWandCDT;

    public static boolean isOnSandWandCooldown(UUID playerUUID, int cooldownInSeconds) {
        if (lightningWandCDT == null) {
            lightningWandCDT = new HashMap<>();
        }
        if (cooldownInSeconds >= 0) {
            if (lightningWandCDT.containsKey(playerUUID)) {
                long timer = ((lightningWandCDT.get(playerUUID) / 1000) + cooldownInSeconds) - (System.currentTimeMillis() / 1000);
                if (timer > 0) {
                    new PlayerMessage("lightning-wand-cooldown", Bukkit.getPlayer(playerUUID), "{time}", String.valueOf(timer));
                } else {
                    lightningWandCDT.remove(playerUUID);
                }
                return true;
            } else {
                lightningWandCDT.put(playerUUID, System.currentTimeMillis());
            }
        }
        return false;
    }
}
