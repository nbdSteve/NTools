package dev.nuer.tp.cmd.sub;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.managers.GuiManager;
import dev.nuer.tp.managers.ToolsAttributeManager;
import dev.nuer.tp.method.player.PlayerMessage;
import dev.nuer.tp.tools.OmniFunctionality;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class that handles the reload argument of the main command
 */
public class Reload {

    /**
     * Reloads all internal maps and file configurations for the plugin
     *
     * @param sender CommandSender, person sending the command
     */
    public static void onCmd(CommandSender sender) {
        if (sender instanceof Player) {
            if (sender.hasPermission("tools+.admin")) {
                //Reload and instantiate all configuration sections
                ToolsAttributeManager.clear();
                OmniFunctionality.clearOmniLists();
                FileManager.reload();
                ToolsPlus.updateDebugMode();
                GuiManager.load();
                ToolsAttributeManager.load();
                OmniFunctionality.loadOmniToolBlocks();
                new PlayerMessage("reload", (Player) sender);
            } else {
                new PlayerMessage("no-permission", (Player) sender);
            }
        } else {
            //Reload and instantiate all configuration sections
            ToolsAttributeManager.clear();
            OmniFunctionality.clearOmniLists();
            FileManager.reload();
            ToolsPlus.updateDebugMode();
            GuiManager.load();
            ToolsAttributeManager.load();
            OmniFunctionality.loadOmniToolBlocks();
            ToolsPlus.LOGGER.info("[Tools+] Reloaded all tool maps and configuration files.");
        }
    }
}