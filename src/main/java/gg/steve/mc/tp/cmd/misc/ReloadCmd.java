package gg.steve.mc.tp.cmd.misc;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.managers.Files;
import gg.steve.mc.tp.message.DebugMessage;
import gg.steve.mc.tp.message.GeneralMessage;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.permission.PermissionNode;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ReloadCmd {

    public static void reload(CommandSender sender) {
        if (!PermissionNode.RELOAD.hasPermission(sender)) {
            DebugMessage.INSUFFICIENT_PERMISSION.message(sender, PermissionNode.RELOAD.get());
            return;
        }
        Files.reload();
        Bukkit.getPluginManager().disablePlugin(ToolsPlus.get());
        ToolsPlus.get().onLoad();
        Bukkit.getPluginManager().enablePlugin(ToolsPlus.get());
        GeneralMessage.RELOAD.message(sender, ModuleManager.getModuleCount());
    }
}
