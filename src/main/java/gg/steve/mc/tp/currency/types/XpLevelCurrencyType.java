package gg.steve.mc.tp.currency.types;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.currency.AbstractCurrency;
import gg.steve.mc.tp.currency.CurrencyType;
import gg.steve.mc.tp.message.GeneralMessage;
import gg.steve.mc.tp.tool.LoadedTool;
import org.bukkit.entity.Player;

public class XpLevelCurrencyType extends AbstractCurrency {

    public XpLevelCurrencyType() {
        super(CurrencyType.XP_LEVEL);
    }

    @Override
    public boolean isSufficientFunds(Player player, LoadedTool tool, double cost) {
        if (player.getLevel() < cost) {
            GeneralMessage.INSUFFICIENT_FUNDS.message(player,
                    ToolsPlus.formatNumber(player.getLevel()),
                    ToolsPlus.formatNumber(cost),
                    getCurrencyType().getPrefix(),
                    getCurrencyType().getSuffix());
            return false;
        }
        player.setLevel((int) (player.getLevel() - cost));
        return true;
    }
}
