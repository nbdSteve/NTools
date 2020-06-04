package gg.steve.mc.tp.integration;

import com.earth2me.essentials.Essentials;
import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.attribute.types.CooldownToolAttribute;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<Material, Map<Byte, Integer>> saleCache = new HashMap<>();
        for (Block block : blocks) {
            if (silk) {
                if (saleCache.containsKey(block.getType())) {
                    if (saleCache.get(block.getType()).containsKey(block.getData())) {
                        saleCache.get(block.getType()).put(block.getData(), saleCache.get(block.getType()).get(block.getData()) + 1);
                    } else {
                        saleCache.get(block.getType()).put(block.getData(), 1);
                    }
                } else {
                    saleCache.put(block.getType(), new HashMap<>());
                    saleCache.get(block.getType()).put(block.getData(), 1);
                }
            } else {
                for (ItemStack item : block.getDrops(player.getItemInHand())) {
                    if (saleCache.containsKey(item.getType())) {
                        if (saleCache.get(item.getType()).containsKey((byte) item.getDurability())) {
                            saleCache.get(item.getType()).put((byte) item.getDurability(), saleCache.get(item.getType()).get((byte) item.getDurability()) + 1);
                        } else {
                            saleCache.get(item.getType()).put((byte) item.getDurability(), 1);
                        }
                    } else {
                        saleCache.put(item.getType(), new HashMap<>());
                        saleCache.get(item.getType()).put((byte) item.getDurability(), 1);
                    }
                }
            }
            block.getDrops().clear();
            block.setType(Material.AIR);
        }
        for (Material material : saleCache.keySet()) {
            for (Byte data : saleCache.get(material).keySet()) {
                int size = saleCache.get(material).get(data);
                double sale;
                if ((sale = sellItem(player, material, data, size, tool)) > 0) {
                    deposit += sale;
                    amount += size;
                } else {
                    player.getInventory().addItem(new ItemStack(material, size, data));
                }
            }
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
        boolean hasSold = false;
        if (CooldownToolAttribute.isCooldownActive(player, tool)) return 0;
        for (Inventory inventory : inventories) {
            for (int slot = 0; slot < inventory.getSize(); slot++) {
                if (inventory.getItem(slot) == null || inventory.getItem(slot).getType().equals(Material.AIR))
                    continue;
                ItemStack item = inventory.getItem(slot);
                if (item.hasItemMeta()) continue;
                if (!hasSold && (getItemPrice(player, item) <= 0)) continue;
                if (!hasSold && tool.isOnCooldown(player)) return 0;
                double sale;
                if ((sale = sellItem(player, item, tool)) <= 0) continue;
                hasSold = true;
                inventory.clear(slot);
                deposit += sale;
                amount += item.getAmount();
            }
        }
        if (deposit > 0) {
            GeneralMessage.SALE.message(player,
                    tool.getAbstractTool().getModule().getNiceName(),
                    ToolsPlus.formatNumber(amount),
                    ToolsPlus.formatNumber(deposit));
        } else {
            if (!CooldownToolAttribute.isCooldownActive(player, tool)) GeneralMessage.NO_SALE.message(player, tool.getAbstractTool().getModule().getNiceName());
        }
        return amount;
    }

    public static double sellItem(Player player, ItemStack item, LoadedTool tool) {
        if (ToolsPlus.eco() == null) {
            LogUtil.warning("Tried to auto sell an item for " + player.getName() + ", but there is no economy loaded.");
            return 0;
        }
        double price = getItemPrice(player, item) * item.getAmount() * tool.getModifier();
        ToolsPlus.eco().depositPlayer(player, price);
        return price;
    }

    public static double getItemPrice(Player player, ItemStack item) {
        for (int i = 0; i <= providerHierarchy.size(); i++) {
            switch (providerHierarchy.get(i)) {
                case SHOP_GUI_PLUS:
                    if (Bukkit.getPluginManager().getPlugin("ShopGUIPlus") == null) continue;
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
        return 0;
    }

    public static double sellItem(Player player, Material material, Byte data, int amount, LoadedTool tool) {
        if (ToolsPlus.eco() == null) {
            LogUtil.warning("Tried to auto sell an item for " + player.getName() + ", but there is no economy loaded.");
            return 0;
        }
        double price = getItemPrice(player, material, data) * amount * tool.getModifier();
        ToolsPlus.eco().depositPlayer(player, price);
        return price;
    }

    public static double getItemPrice(Player player, Material material, Byte data) {
        ItemStack item = new ItemStack(material, 1, data);
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
        return 0;
    }
}
