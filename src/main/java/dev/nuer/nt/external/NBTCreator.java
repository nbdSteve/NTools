package dev.nuer.nt.external;

import dev.nuer.nt.external.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

public class NBTCreator {

    public static ItemStack addToolData(ItemStack item, String typeOfTool, int idFromConfig) {
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setBoolean("ntool." + typeOfTool, true);
        nbtItem.setInteger("ntool.raw.id", idFromConfig);
        return nbtItem.getItem();
    }
}