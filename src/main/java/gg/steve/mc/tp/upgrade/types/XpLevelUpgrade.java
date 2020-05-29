package gg.steve.mc.tp.upgrade.types;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.message.GeneralMessage;
import gg.steve.mc.tp.tool.LoadedTool;
import gg.steve.mc.tp.upgrade.AbstractUpgrade;
import gg.steve.mc.tp.upgrade.CurrencyType;
import gg.steve.mc.tp.upgrade.utils.UpgradeHelper;
import org.bukkit.entity.Player;

public class XpLevelUpgrade extends AbstractUpgrade {

    public XpLevelUpgrade(PluginFile file, boolean enabled) {
        super(CurrencyType.XP_LEVEL, file, enabled);
    }

    @Override
    public boolean doUpgrade(Player player, LoadedTool tool) {
        UpgradeHelper helper = new UpgradeHelper(player, tool, this);
        if (!helper.isUpgradeable()) return false;
        if (player.getLevel() < helper.getCost()) {
            GeneralMessage.INSUFFICIENT_FUNDS.message(player,
                    ToolsPlus.formatNumber(player.getLevel()),
                    ToolsPlus.formatNumber(helper.getCost()),
                    getCurrency().getPrefix(),
                    getCurrency().getSuffix());
            return false;
        }
        player.setLevel((int) (player.getLevel() - helper.getCost()));
        return helper.isUpgradeSuccess();
    }
}
