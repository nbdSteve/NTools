package gg.steve.mc.tp.cmd.misc;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.message.DebugMessage;
import gg.steve.mc.tp.message.GeneralMessage;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.permission.PermissionNode;
import gg.steve.mc.tp.tool.ToolsManager;
import org.bukkit.command.CommandSender;

public class HelpCmd {

    public static void help(CommandSender sender) {
        if (!PermissionNode.HELP.hasPermission(sender)) {
            DebugMessage.INSUFFICIENT_PERMISSION.message(sender, PermissionNode.HELP.get());
            return;
        }
        GeneralMessage.HELP.message(sender,
                ToolsPlus.getVersion(),
                ModuleManager.getModuleCount(),
                ModuleManager.getModulesAsList(),
                ToolsManager.getAbstractToolCount(),
                ToolsManager.getAbstractToolsAsList(),
                ToolsManager.getPlayerToolCount());
    }
}
