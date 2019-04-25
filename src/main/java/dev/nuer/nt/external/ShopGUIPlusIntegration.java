package dev.nuer.nt.external;

import dev.nuer.nt.ToolsPlus;
import org.bukkit.Bukkit;

public class ShopGUIPlusIntegration {

    public static boolean usingShopGUIPlus(String directory) {
        if (!ToolsPlus.getFiles().get(directory + "_price_list").getBoolean("shop-gui-plus-hook")) return false;
        return Bukkit.getPluginManager().getPlugin("ShopGUIPlusIntegration") != null;
    }
}
