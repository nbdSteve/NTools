package gg.steve.mc.tp.cmd.misc;

import gg.steve.mc.tp.message.CommandDebug;
import gg.steve.mc.tp.message.MessageType;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.permission.PermissionNode;
import gg.steve.mc.tp.tool.ToolsManager;
import org.bukkit.command.CommandSender;

public class ListCmd {

    public static void list(CommandSender sender, String[] args) {
        if (args.length != 2) {
            // incorrect args
            return;
        }
        if (!PermissionNode.LIST.hasPermission(sender)) {
            CommandDebug.INSUFFICIENT_PERMISSION.message(sender, PermissionNode.LIST.get());
            return;
        }
        switch (args[1]) {
            case "modules": case "m":
                MessageType.MODULE_LIST.message(sender, ModuleManager.getModuleCount(), ModuleManager.getModulesAsList());
                return;
            case "tools": case "tool": case "t":
                MessageType.TOOL_LIST.message(sender, ToolsManager.getAbstractToolCount(), ToolsManager.getAbstractToolsAsList(), ToolsManager.getPlayerToolCount());
                return;
        }
        // invalid command
    }
}