package gg.steve.mc.tp.cmd.misc;

import gg.steve.mc.tp.cmd.SubCommand;
import gg.steve.mc.tp.message.GeneralMessage;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.permission.PermissionNode;
import org.bukkit.command.CommandSender;

public class ModuleSubCmd extends SubCommand {

    public ModuleSubCmd() {
        super("module", 1, 2, false, PermissionNode.MODULE_LIST);
        addAlias("m");
        addAlias("mods");
        addAlias("modules");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        GeneralMessage.MODULE_LIST.message(sender, ModuleManager.getModuleCount(), ModuleManager.getModulesAsList());
    }
}
