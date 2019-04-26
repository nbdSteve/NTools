package dev.nuer.tp.tools.sell;

import dev.nuer.tp.method.player.PlayerMessage;
import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Class that handles getting if an item can be sold, and its respective price
 */
public class GetSellableItemPrices {

    /**
     * Gets the given items sell price based on the configuration
     *
     * @param item             ItemStack, the item to query
     * @param player           Player, the player who is selling
     * @param priceModifier    double, the amount to multiply the price by
     * @param usingShopGuiPlus boolean, if the plugin should hook with ShopGuiPlus
     * @param itemPrices       HashMap<String, Double>, the internal map of item prices
     * @return boolean
     */
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

    /**
     * Returns true if the respective item can be sold
     *
     * @param item             ItemStack, the item to query
     * @param player           Player, the player who is selling
     * @param usingShopGuiPlus boolean, if the plugin should hook with ShopGuiPlus
     * @param itemPrices       HashMap<String, Double>, the internal map of item prices
     * @return boolean
     */
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
