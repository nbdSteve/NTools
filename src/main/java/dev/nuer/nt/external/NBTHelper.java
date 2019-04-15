package dev.nuer.nt.external;

import dev.nuer.nt.external.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

public class NBTHelper {

    public static ItemStack addToolData(ItemStack item, String typeOfTool, int idFromConfig,
                                        boolean isMultiTool, String multiToolCurrentMode) {
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setBoolean("ntool", true);
        nbtItem.setString("ntool.tool.type", typeOfTool);
        nbtItem.setInteger("ntool.raw.id", idFromConfig);
        if (isMultiTool) {
            nbtItem.setString("ntool.multi.mode", multiToolCurrentMode);
        }
        return nbtItem.getItem();
    }
}