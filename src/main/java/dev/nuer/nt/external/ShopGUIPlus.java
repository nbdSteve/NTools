package dev.nuer.nt.external;

import dev.nuer.nt.NTools;
import dev.nuer.nt.initialize.MapInitializer;
import dev.nuer.nt.method.player.PlayerMessage;
import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ShopGUIPlus {

    public static boolean usingShopGUIPlus(String directory) {
        if (!NTools.getFiles().get(directory + "_price_list").getBoolean("shop-gui-plus-hook")) return false;
        return Bukkit.getPluginManager().getPlugin("ShopGUIPlus") != null;
    }

    public static double getItemPrice(ItemStack item, Player player, double priceModifier,
                                      boolean usingShopGuiPlus, HashMap<String, Double> itemPrices) {
        if (usingShopGuiPlus) {
            try {
                return ShopGuiPlusApi.getItemStackPriceSell(player, item) * item.getAmount() * priceModifier;
            } catch (Exception e) {
                e.printStackTrace();
                new PlayerMessage("invalid-config", player, "{reason}", "ShopGuiPlus player data not loaded");
            }
        }
        return itemPrices.get(item.getType().toString()) * item.getAmount() * priceModifier;
    }

    public static boolean canBeSold(ItemStack item, Player player, boolean usingShopGuiPlus, HashMap<String, Double> itemPrices) {
        if (usingShopGuiPlus) {
            try {
                return ShopGuiPlusApi.getItemStackPriceBuy(player, item) > 0;
            } catch (Exception e) {
                new PlayerMessage("invalid-config", player, "{reason}", "ShopGuiPlus player data not loaded");
            }
        }
        return itemPrices.get(item.getType().toString()) != null;
    }
}
