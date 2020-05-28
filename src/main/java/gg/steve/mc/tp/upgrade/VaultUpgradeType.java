package gg.steve.mc.tp.upgrade;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.LoadedTool;
import gg.steve.mc.tp.utils.GetToolHoldingUtil;
import gg.steve.mc.tp.utils.ItemBuilderUtil;
import gg.steve.mc.tp.utils.LogUtil;
import gg.steve.mc.tp.managers.PluginFile;
import org.bukkit.entity.Player;

import java.util.List;

public class VaultUpgradeType extends AbstractUpgrade {

    public VaultUpgradeType(PluginFile file, boolean enabled) {
        super(CurrencyType.VAULT, file, enabled);
    }

    @Override
    public boolean doUpgrade(Player player, LoadedTool tool) {
        NBTItem item = new NBTItem(player.getItemInHand());
        if (!item.getItem().hasItemMeta() || item.getItem().getItemMeta().getLore().isEmpty()) {
            LogUtil.warning("Tried to upgrade a tool that doesn't have any lore! Aborting.");
            return false;
        }
        int level = tool.getUpgradeLevel();
        if (level == getMaxLevel()) {
            player.sendMessage("already max level");
            return false;
        }
        int next = level + 1;
        double cost = getUpgradePriceForLevel(next);
        if (ToolsPlus.eco().getBalance(player) < cost) {
            player.sendMessage("insufficient balance");
            return false;
        }
        ToolsPlus.eco().withdrawPlayer(player, cost);
        tool.setUpgradeLevel(next);
        String currentLore = getUpdateString().replace("{upgrade}", getLoreStringForLevel(level));
        String replacementLore = getUpdateString().replace("{upgrade}", getLoreStringForLevel(next));
        item.setInteger("tools+.upgrade-level", next);
        ItemBuilderUtil builder = new ItemBuilderUtil(item.getItem());
        List<String> lore = builder.getLore();
        for (int i = 0; i < lore.size(); i++) {
            if (lore.get(i).contains(currentLore)) {
                String line = lore.get(i).replace(currentLore, replacementLore);
                lore.set(i, line);
                break;
            }
        }
        builder.setLore(lore);
        item.getItem().setItemMeta(builder.getItemMeta());
        if (GetToolHoldingUtil.isStillHoldingTool(tool.getToolId(), player.getItemInHand())) {
            player.setItemInHand(item.getItem());
            player.updateInventory();
            return true;
        } else {
            LogUtil.warning("Upgrade dupe attempted by player: " + player.getName() + ", Tools+ has stopped the tool action from happening.");
            ToolsPlus.eco().depositPlayer(player, cost);
            return false;
        }
    }
}