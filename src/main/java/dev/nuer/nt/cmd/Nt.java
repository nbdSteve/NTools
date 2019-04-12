package dev.nuer.nt.cmd;

import dev.nuer.nt.NTools;
import dev.nuer.nt.method.itemCreation.CraftItem;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class that handles the /nt and /tools command for the plugin
 */
public class Nt implements CommandExecutor {
    /**
     * Constructor to make the command class work
     *
     * @param nTools
     */
    public Nt(NTools nTools) {
    }

    /**
     * Main command method, reference sub classes to follow code path
     *
     * @param sender  person sending the command
     * @param command String, command name
     * @param label   String, label
     * @param args    String[], command arguments
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("nt") || command.getName().equalsIgnoreCase("tools")) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    if (sender.hasPermission("ntools.gui")) {
                        NTools.getPlugin(NTools.class).getBuyToolsGenericGui().open((Player) sender);
                    } else {
                        new PlayerMessage("no-permission", (Player) sender);
                    }
                } else {
                    NTools.LOGGER.info("[NTools] The purchase Gui can only be viewed by players.");
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("help")) {
                    if (sender instanceof Player) {
                        if (sender.hasPermission("ntools.help")) {
                            new PlayerMessage("help", (Player) sender);
                        } else {
                            new PlayerMessage("no-permission", (Player) sender);
                        }
                    } else {
                        NTools.LOGGER.info("[NTools] The help message can only be viewed by players.");
                    }
                }
                if (args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("reload")) {
                    if (sender instanceof Player) {
                        if (sender.hasPermission("ntools.admin")) {
                            NTools.getFiles().reload();
                            NTools.clearMaps();
                            NTools.loadToolMaps();
                            new PlayerMessage("reload", (Player) sender);
                        } else {
                            new PlayerMessage("no-permission", (Player) sender);
                        }
                    } else {
                        NTools.getFiles().reload();
                        NTools.clearMaps();
                        NTools.loadToolMaps();
                    }
                }
                if (args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("config")) {
                    if (sender instanceof Player) {
                        if (sender.hasPermission("ntools.gui.multi")) {
                            NTools.getPlugin(NTools.class).getMultiToolOptionsGui().open((Player) sender);
                        } else {
                            new PlayerMessage("no-permission", (Player) sender);
                        }
                    } else {
                        NTools.LOGGER.info("[NTools] The upgrade Gui can only be viewed by players.");
                    }
                }
            } else if (args.length == 5) {
                if (args[0].equalsIgnoreCase("g") || args[0].equalsIgnoreCase("give")) {
                    if (sender.hasPermission("ntools.admin")) {
                        try {
                            Player target = Bukkit.getPlayer(args[1]);
                            if (!target.isOnline()) {
                                if (sender instanceof Player) {
                                    new PlayerMessage("invalid-command", (Player) sender);
                                } else {
                                    NTools.LOGGER.info("[NTools] Invalid command, check the GitHub wiki for command help.");
                                }
                            }
                            if (args[2].equalsIgnoreCase("multi")) {
                                new CraftItem(args[3], (NTools.getFiles().get("multi").getString("multi-tools." + args[4] + ".name")),
                                        (NTools.getFiles().get("multi").getStringList("multi-tools." + args[4] + ".lore")),
                                        (NTools.multiToolRadiusUnique.get(Integer.parseInt(args[4])).get(1)),
                                        (NTools.multiToolModeUnique.get(Integer.parseInt(args[4])).get(1)),
                                        (NTools.getFiles().get("multi").getStringList("multi-tools." + args[4] + ".enchantments")), target);
                            }
                            if (args[2].equalsIgnoreCase("trench")) {
                                new CraftItem(args[3], (NTools.getFiles().get("trench").getString("trench-tools." + args[4] + ".name")),
                                        (NTools.getFiles().get("trench").getStringList("trench-tools." + args[4] + ".lore")), null, null,
                                        (NTools.getFiles().get("trench").getStringList("trench-tools." + args[4] + ".enchantments")), target);
                            }
                            if (args[2].equalsIgnoreCase("tray")) {
                                new CraftItem(args[3], (NTools.getFiles().get("tray").getString("tray-tools." + args[4] + ".name")),
                                        (NTools.getFiles().get("tray").getStringList("tray-tools." + args[4] + ".lore")), null, null,
                                        (NTools.getFiles().get("tray").getStringList("tray-tools." + args[4] + ".enchantments")), target);
                            }
                            if (args[2].equalsIgnoreCase("sand")) {
                                new CraftItem(args[3], (NTools.getFiles().get("sand").getString("sand-wands." + args[4] + ".name")),
                                        (NTools.getFiles().get("sand").getStringList("sand-wands." + args[4] + ".lore")), null, null,
                                        (NTools.getFiles().get("sand").getStringList("sand-wands." + args[4] + ".enchantments")), target);
                            }
                        } catch (Exception invalidCommandParameters) {
                            if (sender instanceof Player) {
                                new PlayerMessage("invalid-command", (Player) sender);
                            } else {
                                NTools.LOGGER.info("[NTools] Invalid command, check the GitHub wiki for command help.");
                            }
                        }
                    } else {
                        if (sender instanceof Player) {
                            new PlayerMessage("no-permission", (Player) sender);
                        }
                    }
                }
            } else {
                if (sender instanceof Player) {
                    if (sender.hasPermission("ntools.gui")) {
                        new PlayerMessage("invalid-command", (Player) sender);
                    } else {
                        new PlayerMessage("no-permission", (Player) sender);
                    }
                } else {
                    NTools.LOGGER.info("[NTools] Invalid command, check the GitHub wiki for command help.");
                }
            }
        }
        return true;
    }
}