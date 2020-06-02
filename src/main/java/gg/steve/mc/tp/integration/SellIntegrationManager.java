package gg.steve.mc.tp.integration;

import com.earth2me.essentials.Essentials;
import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.integration.sell.InternalPriceProvider;
import gg.steve.mc.tp.integration.sell.PriceProviderType;
import gg.steve.mc.tp.managers.Files;
import gg.steve.mc.tp.message.GeneralMessage;
import gg.steve.mc.tp.tool.LoadedTool;
import gg.steve.mc.tp.utils.LogUtil;
import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SellIntegrationManager {
    private static List<PriceProviderType> providerHierarchy;

    public static void initialiseProviderHierarchy() {
        providerHierarchy = new ArrayList<>();
        for (String provider : Files.CONFIG.get().getStringList("price-provider-hierarchy")) {
            providerHierarchy.add(PriceProviderType.getProvider(provider));
        }
        if (providerHierarchy.isEmpty()) providerHierarchy.add(PriceProviderType.INTERNAL);
    }

    public static void shutdown() {
        if (providerHierarchy != null && !providerHierarchy.isEmpty()) providerHierarchy.clear();
    }

    public static void doBlockSale(Player player, List<Block> blocks, LoadedTool tool, boolean silk) {
        double deposit = 0, amount = 0;
        for (Block block : blocks) {
            if (silk) {
                ItemStack item = new ItemStack(block.getType(), 1, block.getData());
                if (getItemPrice(player, item) == -1) {
                    if (tool.isOnCooldown(player)) return;
                    player.getInventory().addItem(item);
                    continue;
                }
                if (tool.isOnCooldown(player)) return;
                deposit += sellItem(player, item, tool);
                amount++;
            } else {
                for (ItemStack item : block.getDrops(player.getItemInHand())) {
                    if (getItemPrice(player, item) == -1) {
                        player.getInventory().addItem(item);
                        continue;
                    }
                    deposit += sellItem(player, item, tool);
                    amount += item.getAmount();
                }
            }
            block.getDrops().clear();
            block.setType(Material.AIR);
        }
        if (deposit > 0) {
            GeneralMessage.SALE.message(player,
                    tool.getAbstractTool().getModule().getNiceName(),
                    ToolsPlus.formatNumber(amount),
                    ToolsPlus.formatNumber(deposit));
        }
    }

    public static int doInventorySale(Player player, List<Inventory> inventories, LoadedTool tool) {
        int deposit = 0, amount = 0;
        for (Inventory inventory : inventories) {
            for (int slot = 0; slot < inventory.getSize(); slot++) {
                if (inventory.getItem(slot) == null || inventory.getItem(slot).getType().equals(Material.AIR))
                    continue;
                ItemStack item = inventory.getItem(slot);
                if (item.hasItemMeta()) continue;
                if (getItemPrice(player, item) == -1) continue;
                if (tool.isOnCooldown(player)) return 0;
                inventory.clear(slot);
                deposit += sellItem(player, item, tool);
                amount += item.getAmount();
            }
        }
        if (deposit > 0) {
            GeneralMessage.SALE.message(player,
                    tool.getAbstractTool().getModule().getNiceName(),
                    ToolsPlus.formatNumber(amount),
                    ToolsPlus.formatNumber(deposit));
        }
        return amount;
    }

    public static double sellItem(Player player, ItemStack item, LoadedTool tool) {
        if (ToolsPlus.eco() == null) {
            LogUtil.warning("Tried to auto sell an item for " + player.getName() + ", but there is no economy loaded.");
            return 0;
        }
        ToolsPlus.eco().depositPlayer(player, getItemPrice(player, item) * item.getAmount() * tool.getModifier());
        return getItemPrice(player, item) * item.getAmount() * tool.getModifier();
    }

    public static double getItemPrice(Player player, ItemStack item) {
        for (int i = 0; i <= providerHierarchy.size(); i++) {
            switch (providerHierarchy.get(i)) {
                case SHOP_GUI_PLUS:
                    if (Bukkit.getPluginManager().getPlugin("ShopGuiPlus") == null) continue;
                    try {
                        if (ShopGuiPlusApi.getItemStackPriceSell(player, item) <= 0) continue;
                        return ShopGuiPlusApi.getItemStackPriceSell(player, item);
                    } catch (Exception e) {
                        LogUtil.warning("Error getting item price for shopgui+ economy.");
                        continue;
                    }
                case ESSENTIALS:
                    if (Bukkit.getPluginManager().getPlugin("Essentials") == null) continue;
                    try {
                        Essentials ess = Essentials.getPlugin(Essentials.class);
                        if (ess.getWorth().getPrice(ess, item).doubleValue() <= 0) continue;
                        return ess.getWorth().getPrice(ess, item).doubleValue();
                    } catch (Exception e) {
                        LogUtil.warning("Error getting item price for essentials economy.");
                        continue;
                    }
                case INTERNAL:
                    return InternalPriceProvider.getPrice(item);
            }
        }
        return -1;
    }
}
