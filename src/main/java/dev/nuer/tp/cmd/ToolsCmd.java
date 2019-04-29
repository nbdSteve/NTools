package dev.nuer.tp.cmd;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.cmd.sub.Give;
import dev.nuer.tp.cmd.sub.Help;
import dev.nuer.tp.cmd.sub.Reload;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class that handles the /nt command for the plugin
 */
public class ToolsCmd implements CommandExecutor {

    /**
     * Constructor to make the command class work
     *
     * @param toolsPlus ToolsPlus, main instance
     */
    public ToolsCmd(ToolsPlus toolsPlus) {
    }

    /**
     * Main command method, reference sub classes to follow code path
     *
     * @param sender  person sending the command
     * @param command String, command name
     * @param label   String, label
     * @param args    String[], command arguments
     * @return boolean
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("t+")) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    if (sender.hasPermission("toolsplus.gui")) {
                        ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("generic-buy").open((Player) sender);
                    } else {
                        new PlayerMessage("no-permission", (Player) sender);
                    }
                } else {
                    ToolsPlus.LOGGER.info("[Tools+] The purchase Gui can only be viewed by players.");
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("help")) {
                    Help.onCmd(sender);
                }
                if (args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("reload")) {
                    Reload.onCmd(sender);
                }
            } else if (args.length == 5 || args.length == 6) {
                if (args[0].equalsIgnoreCase("g") || args[0].equalsIgnoreCase("give")) {
                    Give.onCmd(sender, args);
                }
            } else {
                if (sender instanceof Player) {
                    if (sender.hasPermission("toolsplus.gui")) {
                        new PlayerMessage("invalid-command", (Player) sender, "{reason}",
                                "The arguments you entered where incorrect, please refer to the wiki");
                    } else {
                        new PlayerMessage("no-permission", (Player) sender);
                    }
                } else {
                    ToolsPlus.LOGGER.info("[Tools+] Invalid command, check the GitHub wiki for command help.");
                }
            }
        }
        return true;
    }
}