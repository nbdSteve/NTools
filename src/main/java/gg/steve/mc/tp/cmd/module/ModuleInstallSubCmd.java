package gg.steve.mc.tp.cmd.module;

import gg.steve.mc.tp.cmd.SubCommand;
import gg.steve.mc.tp.message.DebugMessage;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.module.ToolsPlusModule;
import gg.steve.mc.tp.permission.PermissionNode;
import org.bukkit.command.CommandSender;

public class ModuleInstallSubCmd extends SubCommand {

    public ModuleInstallSubCmd() {
        super("install", 2, 3, false, PermissionNode.MODULE_INSTALL);
        addAlias("i");
        addAlias("add");
        addAlias("ins");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        // t+ module install tray
        if (args.length == 2) {
            DebugMessage.INVALID_MODULE.message(sender);
            return;
        }
        ToolsPlusModule module;
        try {
            module = ModuleManager.getInstalledModule(args[2].toUpperCase());
        } catch (Exception e) {
            DebugMessage.INVALID_MODULE.message(sender);
            return;
        }
        ModuleManager.installToolModule(module);
        DebugMessage.MODULE_INSTALLED.message(sender, module.getNiceName());
    }
}
