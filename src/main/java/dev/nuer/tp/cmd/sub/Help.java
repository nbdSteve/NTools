package dev.nuer.tp.cmd.sub;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class that handles the help argument of the main command
 */
public class Help {

    /**
     * Sends the help message from the configuration
     *
     * @param sender CommandSender, person sending the command
     */
    public static void onCmd(CommandSender sender) {
        if (sender instanceof Player) {
            if (sender.hasPermission("toolsplus.help")) {
                new PlayerMessage("help", (Player) sender);
            } else {
                new PlayerMessage("no-permission", (Player) sender);
            }
        } else {
            ToolsPlus.LOGGER.info("[ToolsPlus] The help message can only be viewed by players.");
        }
    }
}
