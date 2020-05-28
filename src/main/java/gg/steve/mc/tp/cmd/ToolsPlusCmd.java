package gg.steve.mc.tp.cmd;

import gg.steve.mc.tp.cmd.misc.HelpCmd;
import gg.steve.mc.tp.cmd.misc.ListCmd;
import gg.steve.mc.tp.cmd.misc.ReloadCmd;
import gg.steve.mc.tp.player.PlayerToolManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToolsPlusCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            HelpCmd.help(sender);
            return true;
        }
        switch (args[0]) {
            case "help":
            case "h":
                HelpCmd.help(sender);
                break;
            case "reload":
            case "r":
                ReloadCmd.reload(sender);
                break;
            case "list":
            case "l":
                ListCmd.list(sender, args);
                break;
            case "upgrade":
                PlayerToolManager.getToolPlayer(((Player) sender).getUniqueId()).getLoadedTool().getAbstractTool().getUpgrade().doUpgrade((Player) sender, PlayerToolManager.getToolPlayer(((Player) sender).getUniqueId()).getLoadedTool());
        }
        return true;
    }
}
