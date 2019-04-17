package dev.nuer.nt.external;

import dev.nuer.nt.external.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

public class NBTCreator {

    public static ItemStack addToolData(ItemStack item, String typeOfTool, int idFromConfig,
                                        boolean isMultiTool, int multiToolMinRad) {
        NBTItem nbtItem = new NBTItem(item);
        if (typeOfTool.equalsIgnoreCase("trench")) nbtItem.setBoolean("ntool.trench", true);
        if (typeOfTool.equalsIgnoreCase("tray")) nbtItem.setBoolean("ntool.tray", true);
        if (typeOfTool.equalsIgnoreCase("multi")) nbtItem.setBoolean("ntool.multi", true);
        if (typeOfTool.equalsIgnoreCase("sand")) nbtItem.setBoolean("ntool.sand", true);
        nbtItem.setInteger("ntool.raw.id", idFromConfig);
//        if (isMultiTool) {
//            nbtItem.setBoolean("ntool.multi.trench", true);
//            nbtItem.setInteger("ntool.multi.radius", multiToolMinRad);
//            nbtItem.setInteger("ntool.multi.highest.radius", 4);
//        }
        return nbtItem.getItem();
    }
}