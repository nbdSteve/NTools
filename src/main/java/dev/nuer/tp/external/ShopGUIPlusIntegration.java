package dev.nuer.tp.external;

import dev.nuer.tp.ToolsPlus;
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
        if (!ToolsPlus.getFiles().get(directory + "_price_list").getBoolean("shop-gui-plus-hook")) return false;
        return Bukkit.getPluginManager().getPlugin("ShopGUIPlusIntegration") != null;
    }
}
