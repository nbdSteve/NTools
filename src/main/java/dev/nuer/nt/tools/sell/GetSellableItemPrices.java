package dev.nuer.nt.tools.sell;

import dev.nuer.nt.method.player.PlayerMessage;
import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class GetSellableItemPrices {

    public static double getItemPrice(ItemStack item, Player player, double priceModifier,
                                      boolean usingShopGuiPlus, HashMap<String, Double> itemPrices) {
        if (usingShopGuiPlus) {
            try {
                return ShopGuiPlusApi.getItemStackPriceSell(player, item) * item.getAmount() * priceModifier;
            } catch (Exception e) {
                new PlayerMessage("invalid-config", player, "{reason}", "ShopGuiPlus player data not loaded");
            }
        }
        return itemPrices.get(item.getType().toString()) * item.getAmount() * priceModifier;
    }

    public static boolean canBeSold(ItemStack item, Player player, boolean usingShopGuiPlus, HashMap<String, Double> itemPrices) {
        if (item == null) {
            return false;
        }
        if (item.hasItemMeta()) {
            return false;
        }
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
