package dev.nuer.tp.cmd.sub;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.initialize.MapInitializer;
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
            if (sender.hasPermission("toolsplus.admin")) {
                //Reload and instantiate all configuration sections
                MapInitializer.clearMaps();
                OmniFunctionality.clearOmniLists();
                ToolsPlus.getFiles().reload();
                ToolsPlus.instantiateGuis();
                MapInitializer.initializeMaps();
                OmniFunctionality.loadOmniToolBlocks();
                new PlayerMessage("reload", (Player) sender);
            } else {
                new PlayerMessage("no-permission", (Player) sender);
            }
        } else {
            //Reload and instantiate all configuration sections
            MapInitializer.clearMaps();
            OmniFunctionality.clearOmniLists();
            ToolsPlus.getFiles().reload();
            ToolsPlus.instantiateGuis();
            MapInitializer.initializeMaps();
            OmniFunctionality.loadOmniToolBlocks();
            ToolsPlus.LOGGER.info("[ToolsPlus] Reloaded all tool maps and configuration files.");
        }
    }
}