package dev.nuer.nt.tools.multi;

import dev.nuer.nt.external.nbtapi.NBTItem;
import dev.nuer.nt.initialize.MapInitializer;
import dev.nuer.nt.method.itemCreation.UpdateItem;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Class that handles getting and setting the radius / mode of a multi tool
 */
public class GetMultiToolVariables {

    public static int getToolRadius(List<String> itemLore, ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        int toolTypeRawID = nbtItem.getInteger("ntool.raw.id");
        int index = 0;
        String radiusLore = MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get(index);
        for (String loreLine : itemLore) {
            //Check if the lore contains the radius unique line
            if (loreLine.contains(radiusLore)) {
                int arrayIndex = 1;
                while (arrayIndex < MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).size()) {
                    if (loreLine.contains(MapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get(arrayIndex))) {
                        return arrayIndex;
                    }
                    arrayIndex++;
                }
            }
            //Increment the index if the line is not found
            index++;
        }
        return 0;
    }

    /**
     * Query the mode of a multi tool, change it if required
     *
     * @param itemLore      List<String>, the lore of the item being queried
     * @param itemMeta      ItemMeta, the meta of the item being queried
     * @param item          ItemStack, the item being queried
     * @param changeMode    boolean, if the mode should be changed
     * @return
     */
    public static String queryToolMode(List<String> itemLore, ItemMeta itemMeta,
                                       ItemStack item, boolean changeMode) {
        NBTItem nbtItem = new NBTItem(item);
        String mode = ChatColor.translateAlternateColorCodes('&',
                MapInitializer.multiToolModeUnique.get(nbtItem.getInteger("ntool.raw.id")).get(0));
        String trench = ChatColor.translateAlternateColorCodes('&',
                MapInitializer.multiToolModeUnique.get(nbtItem.getInteger("ntool.raw.id")).get(1));
        String tray = ChatColor.translateAlternateColorCodes('&',
                MapInitializer.multiToolModeUnique.get(nbtItem.getInteger("ntool.raw.id")).get(2));
        int index = 0;
        for (String loreLine : itemLore) {
            //Check if the lore contains the mode unique line
            if (loreLine.contains(mode)) {
                //Check if the lore contains the trench unique line
                if (loreLine.contains(trench)) {
                    //If true change the mode to tray, else return that it is a trench tool
                    if (changeMode) {
                        itemLore.set(index, mode + " " + tray);
                        UpdateItem.updateItem(itemLore, itemMeta, item);
                    }
                    return "trench";
                } else if (loreLine.contains(tray)) {
                    if (changeMode) {
                        itemLore.set(index, mode + " " + trench);
                        UpdateItem.updateItem(itemLore, itemMeta, item);
                    }
                    return "tray";
                }
            }
            index++;
        }
        return null;
    }

    public static boolean multiToolIsTrenchTool(int toolTypeRawID, List<String> itemLore, ItemMeta itemMeta,
                                                ItemStack item, boolean changeMode) {
        String mode = ChatColor.translateAlternateColorCodes('&',
                MapInitializer.multiToolModeUnique.get(toolTypeRawID).get(0));
        String trench = ChatColor.translateAlternateColorCodes('&',
                MapInitializer.multiToolModeUnique.get(toolTypeRawID).get(1));
        String tray = ChatColor.translateAlternateColorCodes('&',
                MapInitializer.multiToolModeUnique.get(toolTypeRawID).get(2));
        int index = 0;
        for (String loreLine : itemLore) {
            //Check if the lore contains the mode unique line
            if (loreLine.contains(mode)) {
                //Check if the lore contains the trench unique line
                if (loreLine.contains(trench)) {
                    //If true change the mode to tray, else return that it is a trench tool
                    if (changeMode) {
                        itemLore.set(index, mode + " " + tray);
                        UpdateItem.updateItem(itemLore, itemMeta, item);
                    }
                    return true;
                } else if (loreLine.contains(tray)) {
                    if (changeMode) {
                        itemLore.set(index, mode + " " + trench);
                        UpdateItem.updateItem(itemLore, itemMeta, item);
                    }
                    return false;
                }
            }
            index++;
        }
        return false;
    }
}
