package gg.steve.mc.tp.upgrade.types;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.message.GeneralMessage;
import gg.steve.mc.tp.tool.LoadedTool;
import gg.steve.mc.tp.upgrade.AbstractUpgrade;
import gg.steve.mc.tp.upgrade.CurrencyType;
import gg.steve.mc.tp.upgrade.utils.UpgradeHelper;
import gg.steve.mc.tp.utils.XpUtil;
import org.bukkit.entity.Player;

public class XpRawUpgrade extends AbstractUpgrade {

    public XpRawUpgrade(PluginFile file, boolean enabled) {
        super(CurrencyType.XP_RAW, file, enabled);
    }

    @Override
    public boolean doUpgrade(Player player, LoadedTool tool) {
        UpgradeHelper helper = new UpgradeHelper(player, tool, this);
        if (!helper.isUpgradeable()) return false;
        if (XpUtil.getTotalExperience(player) < helper.getCost()) {
            GeneralMessage.INSUFFICIENT_FUNDS.message(player,
                    ToolsPlus.formatNumber(XpUtil.getTotalExperience(player)),
                    ToolsPlus.formatNumber(helper.getCost()),
                    getCurrency().getPrefix(),
                    getCurrency().getSuffix());
            return false;
        }
        XpUtil.setTotalExperience(player, (int) (XpUtil.getTotalExperience(player) - helper.getCost()));
        return helper.isUpgradeSuccess();
    }
}
