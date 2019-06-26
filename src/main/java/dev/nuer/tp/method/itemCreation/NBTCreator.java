package dev.nuer.tp.method.itemCreation;

import dev.nuer.tp.support.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

/**
 * Class that handles adding custom nbt data to items when the are created
 */
public class NBTCreator {

    /**
     * Adds the flags required for the tool to be registered by the plugin
     *
     * @param item         ItemStack, the item to tag
     * @param typeOfTool   String, the type of tool being created
     * @param idFromConfig Integer, the raw ID from the configuration files
     * @return ItemStack
     */
    public static ItemStack addToolData(ItemStack item, String typeOfTool, int idFromConfig, int startingUses, boolean omniTool) {
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setBoolean("tools+." + typeOfTool, true);
        nbtItem.setInteger("tools+.raw.id", idFromConfig);
        nbtItem.setInteger("tools+.uses", startingUses);
        if (omniTool) {
            nbtItem.setBoolean("tools+.omni", true);
        }
        return nbtItem.getItem();
    }
//
//    /**
//     * Adds the flags required for the tool to be registered by the plugin
//     *
//     * @param item         ItemStack, the item to tag
//     * @param typeOfTool   String, the type of tool being created
//     * @param idFromConfig Integer, the raw ID from the configuration files
//     * @param omniTool     boolean, if the tool should have omni functionality
//     * @return ItemStack
//     */
//    public static ItemStack addToolData(ItemStack item, String typeOfTool, int idFromConfig, boolean omniTool) {
//        NBTItem nbtItem = new NBTItem(item);
//        nbtItem.setBoolean("tools+." + typeOfTool, true);
//        nbtItem.setInteger("tools+.raw.id", idFromConfig);
//        if (omniTool) {
//            nbtItem.setBoolean("tools+.omni", true);
//        }
//        return nbtItem.getItem();
//    }
}