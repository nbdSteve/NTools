package dev.nuer.nt.tools.sand;

import dev.nuer.nt.NTools;
import dev.nuer.nt.method.player.PlayerMessage;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class SandCooldownCheck {

    public static HashMap<UUID, Long> sandWandCDT;
    private static int cooldownForAPI;

    public static boolean isOnSandWandCooldown(UUID playerUUID, int cooldownInSeconds, Player player) {
        if (sandWandCDT == null) {
            sandWandCDT = new HashMap<>();
        }
        cooldownForAPI = cooldownInSeconds;
        if (cooldownInSeconds >= 0) {
            if (sandWandCDT.containsKey(playerUUID)) {
                long timer = ((sandWandCDT.get(playerUUID) / 1000) + cooldownInSeconds) - (System.currentTimeMillis() / 1000);
                if (timer > 0) {
                    try {
                        if (NTools.getFiles().get("config").getBoolean("cooldown-action-bar.enabled")) {
                            String message = NTools.getFiles().get("config").getString("cooldown-action-bar.message").replace("{time}", String.valueOf(timer));
                            PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(ChatColor.translateAlternateColorCodes('&', message)), (byte) 2);
                            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                        } else {
                            new PlayerMessage("sand-wand-cooldown", Bukkit.getPlayer(playerUUID), "{time}", String.valueOf(timer));
                        }
                    } catch (Exception e) {
                        new PlayerMessage("sand-wand-cooldown", Bukkit.getPlayer(playerUUID), "{time}", String.valueOf(timer));
                    }
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

    public static long getSandWandCooldown(Player player) {
        try {
            return ((sandWandCDT.get(player.getUniqueId()) / 1000) + cooldownForAPI) - (System.currentTimeMillis() / 1000);
        } catch (NullPointerException cooldownNotRun) {
            return 0;
        }
    }
}
