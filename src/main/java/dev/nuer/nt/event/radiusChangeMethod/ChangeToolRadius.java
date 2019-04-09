package dev.nuer.nt.event.radiusChangeMethod;

import dev.nuer.nt.event.itemMetaMethod.GetMultiToolVariables;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ChangeToolRadius {

    public static void incrementRadius(int toolTypeRawID, String toolType, List<String> itemLore,
                                       ItemMeta itemMeta, ItemStack item, Player player) {
        int radius = GetMultiToolVariables.queryToolRadius(toolTypeRawID, toolType, itemLore,
                itemMeta, item, true, false);
        if (radius == -1) {
            //Send player message or close gui
            player.sendMessage("max");
        }
    }

    public static void decrementRadius(int toolTypeRawID, String toolType, List<String> itemLore,
                                       ItemMeta itemMeta, ItemStack item, Player player) {
        int radius = GetMultiToolVariables.queryToolRadius(toolTypeRawID, toolType, itemLore,
                itemMeta, item, false, true);
        if (radius == -1) {
            //Send player message or close gui
            player.sendMessage("min");
        }
    }
}
