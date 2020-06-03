package gg.steve.mc.tp.player.listener;

import gg.steve.mc.tp.managers.Files;
import gg.steve.mc.tp.mode.ModeType;
import gg.steve.mc.tp.player.PlayerToolManager;
import gg.steve.mc.tp.upgrade.UpgradeType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerCommandListener implements Listener {
    private static Map<String, List<String>> commands;

    public static void initialiseCommands() {
        commands = new HashMap<>();
        for (String entry : Files.CONFIG.get().getConfigurationSection("commands").getKeys(false)) {
            commands.put(entry, new ArrayList<>());
            for (String command : Files.CONFIG.get().getStringList("commands." + entry)) {
                commands.get(entry).add(command);
            }
        }
    }

    public static void shutdown() {
        if (commands != null && !commands.isEmpty()) commands.clear();
    }

    @EventHandler
    public void command(PlayerCommandPreprocessEvent event) {
        if (!PlayerToolManager.isHoldingTool(event.getPlayer().getUniqueId())) return;
        String type = "";
        for (String entry : commands.keySet()) {
            if (commands.get(entry).contains(event.getMessage().toLowerCase())) {
                type = entry;
                break;
            }
        }
        if (type.equalsIgnoreCase("")) return;
        event.setCancelled(true);
        switch (type) {
            case "radius-upgrade":
                PlayerToolManager.getToolPlayer(event.getPlayer().getUniqueId()).getLoadedTool().openUpgradeGui(event.getPlayer(), UpgradeType.RADIUS);
                break;
            case "modifier-upgrade":
                PlayerToolManager.getToolPlayer(event.getPlayer().getUniqueId()).getLoadedTool().openUpgradeGui(event.getPlayer(), UpgradeType.MODIFIER);
                break;
            case "tool-mode":
                PlayerToolManager.getToolPlayer(event.getPlayer().getUniqueId()).getLoadedTool().openModeGui(event.getPlayer(), ModeType.TOOL);
                break;
            case "sell-mode":
                PlayerToolManager.getToolPlayer(event.getPlayer().getUniqueId()).getLoadedTool().openModeGui(event.getPlayer(), ModeType.SELL);
                break;
            case "uses":
                PlayerToolManager.getToolPlayer(event.getPlayer().getUniqueId()).getLoadedTool().openUsesGui(event.getPlayer());
                break;
        }
    }
}
