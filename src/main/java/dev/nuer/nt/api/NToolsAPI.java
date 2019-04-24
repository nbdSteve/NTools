package dev.nuer.nt.api;

import dev.nuer.nt.NTools;
import dev.nuer.nt.tools.PlayerToolCooldown;
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

    public static boolean isOnToolCooldown(Player player, String cooldownToolType) {
        return PlayerToolCooldown.getCooldownMap(cooldownToolType).get(player.getUniqueId()) != null;
    }

    public static int getToolCooldown(Player player, String cooldownToolType) {
        if (isOnToolCooldown(player, cooldownToolType)) {
            return PlayerToolCooldown.getCooldownMap(cooldownToolType).get(player.getUniqueId());
        }
        return 0;
    }
}
