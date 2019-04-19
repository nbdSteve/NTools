package dev.nuer.nt.api;

import dev.nuer.nt.NTools;
import dev.nuer.nt.tools.lightning.LightningCooldownCheck;
import dev.nuer.nt.tools.sand.SandCooldownCheck;
import dev.nuer.nt.tools.sell.SellCooldownCheck;
import org.bukkit.entity.Player;

public class NToolsAPI {

    public static NTools getInstance() {
        return NTools.getPlugin(NTools.class);
    }

    public static boolean isEnabled() {
        if (getInstance().isEnabled()) {
            return true;
        }
        return false;
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

    public static boolean isOnSellWandCooldown(Player player) {
        if (SellCooldownCheck.sellWandCDT != null) {
            return SellCooldownCheck.sellWandCDT.containsKey(player.getUniqueId());
        }
        return false;
    }

    public static long getSellWandCooldown(Player player) {
        return SellCooldownCheck.getSellWandCooldown(player);
    }
}
