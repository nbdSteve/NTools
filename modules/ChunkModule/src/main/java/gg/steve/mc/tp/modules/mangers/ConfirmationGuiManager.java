package gg.steve.mc.tp.modules.mangers;

import gg.steve.mc.tp.modules.gui.ConfirmationGui;
import gg.steve.mc.tp.tool.PlayerTool;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConfirmationGuiManager {
    private static Map<UUID, ConfirmationGui> playerGuis;

    public static void initialise() {
        playerGuis = new HashMap<>();
    }

    public static void shutdown() {
        if (playerGuis != null && !playerGuis.isEmpty()) playerGuis.clear();
    }

    public static void open(Player player, PlayerTool tool, Block clicked) {
        if (!playerGuis.containsKey(player.getUniqueId())) playerGuis.put(player.getUniqueId(), new ConfirmationGui());
        playerGuis.get(player.getUniqueId()).refresh(tool, clicked);
        playerGuis.get(player.getUniqueId()).open(player);
    }
}
