package gg.steve.mc.tp.cmd.misc;

import gg.steve.mc.tp.cmd.SubCommand;
import gg.steve.mc.tp.message.GeneralMessage;
import gg.steve.mc.tp.permission.PermissionNode;
import gg.steve.mc.tp.tool.ToolsManager;
import org.bukkit.command.CommandSender;

public class ToolSubCmd extends SubCommand {

    public ToolSubCmd() {
        super("tool", 1, 2, false, PermissionNode.TOOL_LIST);
        addAlias("t");
        addAlias("tools");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        GeneralMessage.TOOL_LIST.message(sender, ToolsManager.getAbstractToolCount(), ToolsManager.getAbstractToolsAsList(), ToolsManager.getPlayerToolCount());
    }
}
