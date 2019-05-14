package dev.nuer.tp.support;

import dev.nuer.tp.managers.FileManager;
import org.bukkit.Bukkit;

/**
 * Class that handles checking if the plugin should hook with ShopGUIPlus
 */
public class ShopGUIPlusIntegration {

    /**
     * Return true if the plugin should integrate with ShopGUIPlus
     *
     * @param directory String, the tool directory
     * @return boolean
     */
    public static boolean usingShopGUIPlus(String directory) {
        if (!FileManager.get(directory + "_price_list").getBoolean("shop-gui-plus-hook")) return false;
        return Bukkit.getPluginManager().getPlugin("ShopGUIPlusIntegration") != null;
    }
}
