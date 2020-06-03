package gg.steve.mc.tp.cmd.give;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.cmd.SubCommand;
import gg.steve.mc.tp.message.DebugMessage;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.permission.PermissionNode;
import gg.steve.mc.tp.player.PlayerToolManager;
import gg.steve.mc.tp.tool.AbstractTool;
import gg.steve.mc.tp.tool.ToolsManager;
import gg.steve.mc.tp.tool.utils.GetToolHoldingUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveSubCmd extends SubCommand {

    public GiveSubCmd() {
        super("give", 3, 4, false, PermissionNode.GIVE);
        addAlias("g");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        // /t+ give player tool amount
        int amount = 1;
        if (args.length == 4) {
            try {
                amount = Integer.parseInt(args[3]);
                if (amount < 1) throw new Exception();
            } catch (Exception e) {
                DebugMessage.INVALID_AMOUNT.message(sender);
                return;
            }
        }
        AbstractTool tool;
        if ((tool = ToolsManager.getTool(args[2])) == null) {
            DebugMessage.INVALID_TOOL.message(sender);
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            DebugMessage.PLAYER_NOT_ONLINE.message(sender);
            return;
        }
        for (int i = 0; i < amount; i++) {
            target.getInventory().addItem(tool.getItemStack());
        }
        if (!target.getItemInHand().getType().equals(Material.AIR)) {
            NBTItem hand = new NBTItem(target.getItemInHand());
            if (GetToolHoldingUtil.isHoldingTool(hand)) {
                PlayerToolManager.updateToolType(target.getUniqueId(), GetToolHoldingUtil.getHoldingTool(hand));
            }
        }
        Player player = null;
        if (sender instanceof Player && !target.getUniqueId().equals(((Player) sender).getUniqueId())) {
            player = (Player) sender;
        }
        DebugMessage.GIVE_RECEIVER.message(target, ToolsPlus.formatNumber(amount), tool.getModule().getNiceName());
        if (!(sender instanceof Player) || player != null) {
            DebugMessage.GIVE_GIVER.message(sender, target.getName(), ToolsPlus.formatNumber(amount), tool.getModule().getNiceName());
        }
    }
}