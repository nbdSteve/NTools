package dev.nuer.nt.api;

import dev.nuer.nt.ToolsPlus;
import dev.nuer.nt.tools.PlayerToolCooldown;
import org.bukkit.entity.Player;

public class NToolsAPI {

    public static ToolsPlus getInstance() {
        return ToolsPlus.getPlugin(ToolsPlus.class);
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
