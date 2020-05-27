package gg.steve.mc.tp.upgrade;

import gg.steve.mc.tp.tool.AbstractTool;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractUpgrade {
    private CurrencyType currency;

    public AbstractUpgrade(CurrencyType currency) {
        this.currency = currency;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public abstract void doUpgrade(AbstractTool tool, Player player, ItemStack item);

    public abstract int getMaxLevel();
}