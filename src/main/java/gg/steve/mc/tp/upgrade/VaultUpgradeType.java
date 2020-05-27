package gg.steve.mc.tp.upgrade;

import gg.steve.mc.tp.tool.AbstractTool;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VaultUpgradeType extends AbstractUpgrade{

    public VaultUpgradeType() {
        super(CurrencyType.VAULT);
    }

    @Override
    public void doUpgrade(AbstractTool tool, Player player, ItemStack item) {

    }

    @Override
    public int getMaxLevel() {
        return 0;
    }
}