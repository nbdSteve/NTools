package dev.nuer.nt.api;

import dev.nuer.nt.NTools;
import dev.nuer.nt.tools.lightning.LightningCooldownCheck;
import dev.nuer.nt.tools.sand.SandCooldownCheck;
import org.bukkit.entity.Player;

public class NToolsAPI {

    final static NTools instance = NTools.getPlugin(NTools.class);

    public static boolean isEnabled() {
        if (instance.isEnabled()) {
            return true;
        }
        return false;
    }

    public static NTools getInstance() {
//        Bukkit.getServicesManager().register(NTools.class, null, instance, ServicePriority.High);
        return instance;
    }

    public static boolean isOnSandWandCooldown(Player player) {
        if (SandCooldownCheck.sandWandCDT != null) {
            return SandCooldownCheck.sandWandCDT.containsKey(player.getUniqueId());
        }
        return false;
    }

    public static long getSandWandCooldown(Player player) {
        return SandCooldownCheck.getSandWandCooldown(player);
    }

    public static boolean isOnLightningWandCooldown(Player player) {
        if (LightningCooldownCheck.lightningWandCDT != null) {
            return LightningCooldownCheck.lightningWandCDT.containsKey(player.getUniqueId());
        }
        return false;
    }

    public static long getLightningWandCooldown(Player player) {
        return LightningCooldownCheck.getLightningWandCooldown(player);
    }
}
