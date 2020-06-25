package gg.steve.mc.tp.modules.tnt.utils;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.attribute.types.CooldownToolAttribute;
import gg.steve.mc.tp.integration.providers.FactionsProvider;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.framework.utils.LogUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TntBankUtil {
    
    public static int doTntDeposit(Player player, List<Inventory> inventories, PlayerTool tool) {
        if (!FactionsProvider.isRunningFactions()) {
            LogUtil.warning("Tried to deposit TNT to a faction but there is no factions plugin registered, contact nbdSteve#0583 on discord.");
            return -3;
        }
        int deposit = 0;
        boolean hasDeposited = false;
        if (CooldownToolAttribute.isCooldownActive(player, tool)) return 0;
        for (Inventory inventory : inventories) {
            for (int slot = 0; slot < inventory.getSize(); slot++) {
                if (inventory.getItem(slot) == null || !inventory.getItem(slot).getType().equals(Material.TNT))
                    continue;
                ItemStack item = inventory.getItem(slot);
                if (item.hasItemMeta()) continue;
                if (!hasDeposited && tool.isOnCooldown(player)) return 0;
                hasDeposited = true;
                inventory.clear(slot);
                deposit += item.getAmount();
            }
        }
        if (deposit > 0) {
            if (!FactionsProvider.doTntDeposit(player, deposit)) {
                return -2;
            }
            TntMessage.DEPOSIT.message(player, ToolsPlus.formatNumber(deposit));
        } else {
            TntMessage.NO_TNT.message(player);
        }
        return deposit;
    }
}
