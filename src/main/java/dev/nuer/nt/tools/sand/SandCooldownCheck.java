package dev.nuer.nt.tools.sand;

import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class SandCooldownCheck {

    public static HashMap<UUID, Long> sandWandCDT;

    public static boolean isOnSandWandCooldown(UUID playerUUID, int cooldownInSeconds) {
        if (sandWandCDT == null) {
            sandWandCDT = new HashMap<>();
        }
        if (cooldownInSeconds >= 0) {
            if (sandWandCDT.containsKey(playerUUID)) {
                long timer = ((sandWandCDT.get(playerUUID) / 1000) + cooldownInSeconds) - (System.currentTimeMillis() / 1000);
                if (timer > 0) {
                    new PlayerMessage("sand-wand-cooldown", Bukkit.getPlayer(playerUUID), "{time}", String.valueOf(timer));
                } else {
                    sandWandCDT.remove(playerUUID);
                }
                return true;
            } else {
                sandWandCDT.put(playerUUID, System.currentTimeMillis());
            }
        }
        return false;
    }
}
