package dev.nuer.nt.api;

import dev.nuer.nt.NTools;
import dev.nuer.nt.tools.sand.SandCooldownCheck;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;

public class NToolsAPI {

    final static NTools instance = NTools.getPlugin(NTools.class);

    public static boolean isEnabled() {
        if (instance.isEnabled()) {
            return true;
        }
        return false;
    }

    public static NTools getInstance() {
        Bukkit.getServicesManager().register(NTools.class, null, instance, ServicePriority.High);
        return instance;
    }

    public static boolean isOnSandWandCooldown(Player player) {
        return SandCooldownCheck.sandWandCDT.containsKey(player.getUniqueId());
    }

    public static long getSandWandCooldown(Player player) {
        return SandCooldownCheck.sandWandCDT.get(player.getUniqueId());
    }
}
