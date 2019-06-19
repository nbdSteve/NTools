package dev.nuer.tp.cmd.sub;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.managers.ToolsAttributeManager;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

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
        if (sender.hasPermission("tools+.admin")) {
            try {
                Player target = Bukkit.getPlayer(args[1]);
                if (!Bukkit.getOnlinePlayers().contains(target)) {
                    if (sender instanceof Player) {
                        new PlayerMessage("invalid-command", (Player) sender, "{reason}",
                                "The player you are trying to give that tool to is not online");
                    } else {
                        ToolsPlus.LOGGER.info("[Tools+] Invalid command, check the GitHub wiki for command help.");
                    }
                    return;
                }
                //Create the starting modifier for the tool
                int toolStartingModifier = 1;
                if ((args.length == 6 || args.length == 7 || args.length == 8) &&
                        (args[2].equalsIgnoreCase("multi")
                                || args[2].equalsIgnoreCase("sell")
                                || args[2].equalsIgnoreCase("harvester")
                                || args[2].equalsIgnoreCase("tnt")
                                || args[2].equalsIgnoreCase("aqua")
                                || args[2].equalsIgnoreCase("chunk"))) {
                    toolStartingModifier = verifyStartingModifier(sender, args[5], args[2], Integer.parseInt(args[4]));
                }
                int startingMode = 1;
                if (args.length == 7 && (args[2].equalsIgnoreCase("harvester")
                        || args[2].equalsIgnoreCase("multi"))) {
                    startingMode = Integer.parseInt(args[6]);
                } else if (args.length == 8 && (args[2].equalsIgnoreCase("tnt")
                        || args[2].equalsIgnoreCase("aqua"))) {
                    startingMode = Integer.parseInt(args[7]);
                }
                //Get the starting uses from the configuration, or reset for command
                String startingUses = null;
                try {
                    startingUses = FileManager.get(args[2]).getString(args[2] + "-wands." + args[4] + ".uses.starting");
                    if (args.length == 6 && (args[2].equalsIgnoreCase("lightning")
                            || args[2].equalsIgnoreCase("sand")
                            || args[2].equalsIgnoreCase("smelt"))) {
                        startingUses = args[5];
                    } else if (args.length == 7 && (args[2].equalsIgnoreCase("sell")
                            || args[2].equalsIgnoreCase("tnt")
                            || args[2].equalsIgnoreCase("aqua"))) {
                        startingUses = args[6];
                    }
                } catch (NullPointerException e) {
                    //Not a wand just disregard
                }
                if (args[2].equalsIgnoreCase("multi")) {
                    new CraftItem(args[3],
                            FileManager.get("multi").getString("multi-tools." + args[4] + ".name"),
                            FileManager.get("multi").getStringList("multi-tools." + args[4] + ".lore"),
                            FileManager.get("multi").getStringList("multi-tools." + args[4] + ".enchantments"),
                            "multi", Integer.parseInt(args[4]), target, "{mode}", ToolsAttributeManager.multiToolModeUnique.get(Integer.parseInt(args[4])).get(startingMode),
                            "{radius}", ToolsAttributeManager.multiToolRadiusUnique.get(Integer.parseInt(args[4])).get(toolStartingModifier), "debug", "debug");
                }
                if (args[2].equalsIgnoreCase("trench")) {
                    new CraftItem(args[3],
                            FileManager.get("trench").getString("trench-tools." + args[4] + ".name"),
                            FileManager.get("trench").getStringList("trench-tools." + args[4] + ".lore"),
                            FileManager.get("trench").getStringList("trench-tools." + args[4] + ".enchantments"),
                            "trench", Integer.parseInt(args[4]), target);
                }
                if (args[2].equalsIgnoreCase("tray")) {
                    new CraftItem(args[3],
                            FileManager.get("tray").getString("tray-tools." + args[4] + ".name"),
                            FileManager.get("tray").getStringList("tray-tools." + args[4] + ".lore"),
                            FileManager.get("tray").getStringList("tray-tools." + args[4] + ".enchantments"),
                            "tray", Integer.parseInt(args[4]), target);
                }
                if (args[2].equalsIgnoreCase("sand")) {
                    new CraftItem(args[3],
                            FileManager.get("sand").getString("sand-wands." + args[4] + ".name"),
                            FileManager.get("sand").getStringList("sand-wands." + args[4] + ".lore"),
                            FileManager.get("sand").getStringList("sand-wands." + args[4] + ".enchantments"),
                            "sand", Integer.parseInt(args[4]), target, "debug", "debug",
                            "debug", "debug", "{uses}", startingUses);
                }
                if (args[2].equalsIgnoreCase("lightning")) {
                    new CraftItem(args[3], FileManager.get("lightning").getString("lightning-wands" + "." + args[4] + ".name"),
                            FileManager.get("lightning").getStringList("lightning-wands." + args[4] + ".lore"),
                            FileManager.get("lightning").getStringList("lightning-wands." + args[4] + ".enchantments"),
                            "lightning", Integer.parseInt(args[4]), target, "debug", "debug", "debug", "debug",
                            "{uses}", startingUses);
                }
                if (args[2].equalsIgnoreCase("harvester")) {
                    String[] modifierParts = ToolsAttributeManager.harvesterModifierUnique.get(Integer.parseInt(args[4])).get(toolStartingModifier).split("-");
                    new CraftItem(args[3], FileManager.get("harvester").getString("harvester-tools" + "." + args[4] + ".name"),
                            FileManager.get("harvester").getStringList("harvester-tools." + args[4] + ".lore"),
                            FileManager.get("harvester").getStringList("harvester-tools." + args[4] + ".enchantments"), "harvester",
                            Integer.parseInt(args[4]), target, "{mode}", ToolsAttributeManager.harvesterModeUnique.get(Integer.parseInt(args[4])).get(startingMode),
                            "{modifier}", modifierParts[0], "debug", "debug");
                }
                if (args[2].equalsIgnoreCase("sell")) {
                    String[] modifierParts = ToolsAttributeManager.sellWandModifierUnique.get(Integer.parseInt(args[4])).get(toolStartingModifier).split("-");
                    new CraftItem(args[3],
                            FileManager.get("sell").getString("sell-wands." + args[4] + ".name"),
                            FileManager.get("sell").getStringList("sell-wands." + args[4] + ".lore"),
                            FileManager.get("sell").getStringList("sell-wands." + args[4] + ".enchantments"),
                            "sell", Integer.parseInt(args[4]), target, "debug", "debug",
                            "{modifier}", modifierParts[0], "{uses}", startingUses);
                }
                if (args[2].equalsIgnoreCase("tnt")) {
                    String[] modifierParts =
                            ToolsAttributeManager.tntWandModifierUnique.get(Integer.parseInt(args[4])).get(toolStartingModifier).split("-");
                    new CraftItem(args[3],
                            FileManager.get("tnt").getString("tnt-wands." + args[4] + ".name"),
                            FileManager.get("tnt").getStringList("tnt-wands." + args[4] + ".lore"),
                            FileManager.get("tnt").getStringList("tnt-wands." + args[4] + ".enchantments"),
                            "tnt", Integer.parseInt(args[4]), target, "{mode}", ToolsAttributeManager.tntWandModeUnique.get(Integer.parseInt(args[4])).get(startingMode),
                            "{modifier}", modifierParts[0], "{uses}", startingUses);
                }
                if (args[2].equalsIgnoreCase("aqua")) {
                    new CraftItem(args[3],
                            FileManager.get("aqua").getString("aqua-wands." + args[4] + ".name"),
                            FileManager.get("aqua").getStringList("aqua-wands." + args[4] + ".lore"),
                            FileManager.get("aqua").getStringList("aqua-wands." + args[4] + ".enchantments"),
                            "aqua", Integer.parseInt(args[4]), target, "{mode}", ToolsAttributeManager.aquaWandModeUnique.get(Integer.parseInt(args[4])).get(startingMode),
                            "{radius}", ToolsAttributeManager.aquaWandRadiusUnique.get(Integer.parseInt(args[4])).get(toolStartingModifier), "{uses}", startingUses);
                }
                if (args[2].equalsIgnoreCase("smelt")) {
                    new CraftItem(args[3],
                            FileManager.get("smelt").getString("smelt-wands." + args[4] + ".name"),
                            FileManager.get("smelt").getStringList("smelt-wands." + args[4] + ".lore"),
                            FileManager.get("smelt").getStringList("smelt-wands." + args[4] + ".enchantments"),
                            "smelt", Integer.parseInt(args[4]), target, "debug", "debug",
                            "debug", "debug", "{uses}", startingUses);
                }
                if (args[2].equalsIgnoreCase("chunk")) {
                    new CraftItem(args[3],
                            FileManager.get("chunk").getString("chunk-tools." + args[4] + ".name"),
                            FileManager.get("chunk").getStringList("chunk-tools." + args[4] + ".lore"),
                            FileManager.get("chunk").getStringList("chunk-tools." + args[4] + ".enchantments"),
                            "chunk", Integer.parseInt(args[4]), target, "debug", "debug",
                            "{radius}", ToolsAttributeManager.chunkToolRadiusUnique.get(Integer.parseInt(args[4])).get(toolStartingModifier), "debug", "debug");
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

    /**
     * Checks that the modifier the player is trying to apply is valid
     *
     * @param sender           CommandSender, person sending the command
     * @param startingModifier String, the modifier to set
     * @param typeOfTool       String, the type of tool; sell, multi etc.
     * @param toolID           Integer, the id from config
     * @return
     */
    public static int verifyStartingModifier(CommandSender sender, String startingModifier, String typeOfTool, int toolID) {
        int modifier = 1;
        try {
            modifier = Integer.parseInt(startingModifier);
        } catch (NumberFormatException e) {
            return modifier;
        }
        if (modifier < 1 || modifier > getMap(typeOfTool).get(toolID).size() - 3) {
            if (sender instanceof Player) {
                new PlayerMessage("invalid-command", (Player) sender, "{reason}", "The modifier: " + startingModifier + ", is not defined for that tool");
            } else {
                ToolsPlus.LOGGER.severe("[Tools+] The modifier you entered: " + startingModifier + ", is not defined for that tool.");
            }
            return 1;
        }
        return modifier;
    }

    /**
     * Returns the modifier map for that tool
     *
     * @param typeOfTool String, the type of tool; sell, multi etc.
     * @return HashMap<Integer, ArrayList < String>>
     */
    public static HashMap<Integer, ArrayList<String>> getMap(String typeOfTool) {
        if (typeOfTool.equalsIgnoreCase("multi")) return ToolsAttributeManager.multiToolRadiusUnique;
        if (typeOfTool.equalsIgnoreCase("harvester")) return ToolsAttributeManager.harvesterModifierUnique;
        if (typeOfTool.equalsIgnoreCase("sell")) return ToolsAttributeManager.sellWandModifierUnique;
        if (typeOfTool.equalsIgnoreCase("tnt")) return ToolsAttributeManager.tntWandModifierUnique;
        if (typeOfTool.equalsIgnoreCase("aqua")) return ToolsAttributeManager.aquaWandRadiusUnique;
        return null;
    }
}