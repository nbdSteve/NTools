package dev.nuer.tp.cmd.sub;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.initialize.MapInitializer;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class that handles the give argument of the main command
 */
public class Give {

    /**
     * Gives the player the specified tool
     *
     * @param sender CommandSender, person sending the command
     * @param args   String[], list of command arguments
     */
    public static void onCmd(CommandSender sender, String[] args) {
        if (sender.hasPermission("toolsplus.admin")) {
            try {
                Player target = Bukkit.getPlayer(args[1]);
                if (!Bukkit.getOnlinePlayers().contains(target)) {
                    if (sender instanceof Player) {
                        new PlayerMessage("invalid-command", (Player) sender, "{reason}",
                                "The player you are trying to give that tool to is not online.");
                    } else {
                        ToolsPlus.LOGGER.info("[Tools+] Invalid command, check the GitHub wiki for command help.");
                    }
                    return;
                }
                int toolStartingModifier = 1;
                if (args.length == 6) {
                    toolStartingModifier = Integer.parseInt(args[5]);
                }
                String startingUses = null;
                try {
                    startingUses = ToolsPlus.getFiles().get(args[2]).getString(args[2] +  "-wands." + args[4] + ".uses.starting");
                    if (args.length == 6) {
                        startingUses = args[5];
                    }
                } catch (NullPointerException e) {
                    //Not a wand just disregard
                }
                if (args[2].equalsIgnoreCase("multi")) {
                    new CraftItem(args[3],
                            ToolsPlus.getFiles().get("multi").getString("multi-tools." + args[4] + ".name"),
                            ToolsPlus.getFiles().get("multi").getStringList("multi-tools." + args[4] + ".lore"),
                            ToolsPlus.getFiles().get("multi").getStringList("multi-tools." + args[4] + ".enchantments"),
                            "multi", Integer.parseInt(args[4]), target, "{mode}", MapInitializer.multiToolModeUnique.get(Integer.parseInt(args[4])).get(1),
                            "{radius}", MapInitializer.multiToolRadiusUnique.get(Integer.parseInt(args[4])).get(toolStartingModifier), "debug", "debug");
                }
                if (args[2].equalsIgnoreCase("trench")) {
                    new CraftItem(args[3],
                            ToolsPlus.getFiles().get("trench").getString("trench-tools." + args[4] + ".name"),
                            ToolsPlus.getFiles().get("trench").getStringList("trench-tools." + args[4] + ".lore"),
                            ToolsPlus.getFiles().get("trench").getStringList("trench-tools." + args[4] + ".enchantments"),
                            "trench", Integer.parseInt(args[4]), target);
                }
                if (args[2].equalsIgnoreCase("tray")) {
                    new CraftItem(args[3],
                            ToolsPlus.getFiles().get("tray").getString("tray-tools." + args[4] + ".name"),
                            ToolsPlus.getFiles().get("tray").getStringList("tray-tools." + args[4] + ".lore"),
                            ToolsPlus.getFiles().get("tray").getStringList("tray-tools." + args[4] + ".enchantments"),
                            "tray", Integer.parseInt(args[4]), target);
                }
                if (args[2].equalsIgnoreCase("sand")) {
                    new CraftItem(args[3],
                            ToolsPlus.getFiles().get("sand").getString("sand-wands." + args[4] + ".name"),
                            ToolsPlus.getFiles().get("sand").getStringList("sand-wands." + args[4] + ".lore"),
                            ToolsPlus.getFiles().get("sand").getStringList("sand-wands." + args[4] + ".enchantments"),
                            "sand", Integer.parseInt(args[4]), target, "debug", "debug",
                            "debug", "debug", "{uses}", startingUses);
                }
                if (args[2].equalsIgnoreCase("lightning")) {
                    new CraftItem(args[3], ToolsPlus.getFiles().get("lightning").getString("lightning-wands" + "." + args[4] + ".name"),
                            ToolsPlus.getFiles().get("lightning").getStringList("lightning-wands." + args[4] + ".lore"),
                            ToolsPlus.getFiles().get("lightning").getStringList("lightning-wands." + args[4] + ".enchantments"),
                            "lightning", Integer.parseInt(args[4]), target, "debug", "debug", "debug", "debug",
                            "{uses}", startingUses);
                }
                if (args[2].equalsIgnoreCase("harvester")) {
                    String[] modifierParts = MapInitializer.harvesterModifierUnique.get(Integer.parseInt(args[4])).get(ToolsPlus.getFiles().get("harvester").getInt("harvester-tools." + args[4] + ".modifier.starting")).split("-");
                    new CraftItem(args[3], ToolsPlus.getFiles().get("harvester").getString("harvester-tools" + "." + args[4] + ".name"),
                            ToolsPlus.getFiles().get("harvester").getStringList("harvester-tools." + args[4] + ".lore"),
                            ToolsPlus.getFiles().get("harvester").getStringList("harvester-tools." + args[4] + ".enchantments"), "harvester",
                            Integer.parseInt(args[4]), target, "{mode}", MapInitializer.harvesterModeUnique.get(Integer.parseInt(args[4])).get(1), "{modifier}", modifierParts[(toolStartingModifier - 1)], "debug", "debug");
                }
                if (args[2].equalsIgnoreCase("sell")) {
                    String[] modifierParts = MapInitializer.sellWandModifierUnique.get(Integer.parseInt(args[4])).get(ToolsPlus.getFiles().get("sell").getInt("sell-wands." + args[4] + ".modifier.starting")).split("-");
                    new CraftItem(args[3],
                            ToolsPlus.getFiles().get("sell").getString("sell-wands." + args[4] + ".name"),
                            ToolsPlus.getFiles().get("sell").getStringList("sell-wands." + args[4] + ".lore"),
                            ToolsPlus.getFiles().get("sell").getStringList("sell-wands." + args[4] + ".enchantments"),
                            "sell", Integer.parseInt(args[4]), target, "debug", "debug",
                            "{modifier}", modifierParts[(toolStartingModifier - 1)], "{uses}", startingUses);
                }
                if (args[2].equalsIgnoreCase("tnt")) {
                    String[] modifierParts =
                            MapInitializer.tntWandModifierUnique.get(Integer.parseInt(args[4])).get(ToolsPlus.getFiles().get("tnt").getInt("tnt-wands." + args[4] + ".modifier.starting")).split("-");
                    new CraftItem(args[3],
                            ToolsPlus.getFiles().get("tnt").getString("tnt-wands." + args[4] + ".name"),
                            ToolsPlus.getFiles().get("tnt").getStringList("tnt-wands." + args[4] + ".lore"),
                            ToolsPlus.getFiles().get("tnt").getStringList("tnt-wands." + args[4] + ".enchantments"),
                            "tnt", Integer.parseInt(args[4]), target, "{mode}", MapInitializer.tntWandModeUnique.get(Integer.parseInt(args[4])).get(1),
                            "{modifier}", modifierParts[(toolStartingModifier - 1)], "{uses}", startingUses);
                }
            } catch (Exception invalidCommandParameters) {
                if (sender instanceof Player) {
                    invalidCommandParameters.printStackTrace();
                    new PlayerMessage("invalid-command", (Player) sender, "{reason}",
                            "An error occurred. Please check your command syntax, then your configuration (stack trace console)");
                } else {
                    ToolsPlus.LOGGER.info("[Tools+] Invalid command, check the GitHub wiki for command help.");
                }
            }
        } else {
            if (sender instanceof Player) {
                new PlayerMessage("no-permission", (Player) sender);
            }
        }
    }
}