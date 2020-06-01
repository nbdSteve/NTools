package gg.steve.mc.tp.cmd;

import gg.steve.mc.tp.player.PlayerToolManager;
import gg.steve.mc.tp.tool.ToolsManager;
import gg.steve.mc.tp.upgrade.UpgradeType;
import gg.steve.mc.tp.utils.LogUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToolsPlusCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!SubCommandType.HELP_CMD.getSub().isValidCommand(sender, args)) return true;
            SubCommandType.HELP_CMD.getSub().onCommand(sender, args);
            return true;
        }
        for (SubCommandType subCommand : SubCommandType.values()) {
            if (!subCommand.getSub().isExecutor(args[0])) continue;
            if (!subCommand.getSub().isValidCommand(sender, args)) break;
            subCommand.getSub().onCommand(sender, args);
        }
        switch (args[0]) {
//            case "help":
//            case "h":
//                HelpSubCmd.help(sender);
//                break;
//            case "reload":
//            case "r":
//                ReloadCmd.reload(sender);
//                break;
//            case "list":
//            case "l":
//                ListCmd.list(sender, args);
//                break;
            case "upgrade":
                PlayerToolManager.getToolPlayer(((Player) sender).getUniqueId()).getLoadedTool().openUpgradeGui((Player) sender, UpgradeType.RADIUS);
                break;
            case "give":
                ((Player) sender).getInventory().addItem(ToolsManager.getTool("ex-trench").getItemStack());
                ((Player) sender).getInventory().addItem(ToolsManager.getTool("ex-trench-1").getItemStack());
                ((Player) sender).getInventory().addItem(ToolsManager.getTool("ex-tray").getItemStack());
                ((Player) sender).getInventory().addItem(ToolsManager.getTool("ex-sell").getItemStack());
//                PlayerToolManager.getToolPlayer(((Player) sender).getUniqueId()).getLoadedTool().getAbstractTool().getUpgrade().doUpgrade((Player) sender, PlayerToolManager.getToolPlayer(((Player) sender).getUniqueId()).getLoadedTool());
        }
        return true;
    }
}
