package gg.steve.mc.tp.currency;

import gg.steve.mc.tp.tool.LoadedTool;
import org.bukkit.entity.Player;

public abstract class AbstractCurrency {
    private CurrencyType currency;

    public AbstractCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public CurrencyType getCurrencyType() {
        return currency;
    }

    public String getPrefix() {
        return currency.getPrefix();
    }

    public String getSuffix() {
        return currency.getSuffix();
    }

    public String getNiceName() {
        return currency.getNiceName();
    }

    public abstract boolean isSufficientFunds(Player player, LoadedTool tool, double cost);
}
