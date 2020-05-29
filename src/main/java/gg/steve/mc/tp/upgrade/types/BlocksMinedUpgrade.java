package gg.steve.mc.tp.upgrade.types;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.message.GeneralMessage;
import gg.steve.mc.tp.tool.LoadedTool;
import gg.steve.mc.tp.upgrade.AbstractUpgrade;
import gg.steve.mc.tp.upgrade.CurrencyType;
import gg.steve.mc.tp.upgrade.utils.UpgradeHelper;
import org.bukkit.entity.Player;

public class BlocksMinedUpgrade extends AbstractUpgrade {

    public BlocksMinedUpgrade(PluginFile file, boolean enabled) {
        super(CurrencyType.BLOCKS_MINED, file, enabled);
    }

    @Override
    public boolean doUpgrade(Player player, LoadedTool tool) {
        UpgradeHelper helper = new UpgradeHelper(player, tool, this);
        if (!helper.isUpgradeable()) return false;
        if (tool.getBlocksMined() < helper.getCost()) {
            GeneralMessage.INSUFFICIENT_FUNDS.message(player,
                    ToolsPlus.formatNumber(tool.getBlocksMined()),
                    ToolsPlus.formatNumber(helper.getCost()),
                    getCurrency().getPrefix(),
                    getCurrency().getSuffix());
            return false;
        }
        return helper.isUpgradeSuccess();
    }
}
