package dev.nuer.nt.event.modeSwitchMethod;

import dev.nuer.nt.event.itemMetaMethod.GetMultiToolVariables;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ModeSwitch {

    public static void switchMode(int toolTypeRawID, String toolType, List<String> itemLore,
                                  ItemMeta itemMeta, ItemStack item) {
        GetMultiToolVariables.queryToolMode(toolTypeRawID, itemLore, itemMeta, item,
                true);
    }
}
