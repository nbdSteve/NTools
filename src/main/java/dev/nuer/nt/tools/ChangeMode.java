package dev.nuer.nt.tools;

import dev.nuer.nt.external.nbtapi.NBTItem;
import dev.nuer.nt.initialize.MapInitializer;
import dev.nuer.nt.method.itemCreation.UpdateItem;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChangeMode {

    public static void switchMode(List<String> itemLore, ItemMeta itemMeta, ItemStack item, Player player, HashMap<Integer, ArrayList<String>> modeUniqueIDs) {
        player.closeInventory();
        changeToolMode(itemLore, itemMeta, item, modeUniqueIDs, true);
        new PlayerMessage("mode-switch", player);
    }

    public static boolean changeToolMode(List<String> itemLore, ItemMeta itemMeta, ItemStack item,
                                             HashMap<Integer, ArrayList<String>> modeUniqueIDs, boolean changeMode) {
        NBTItem nbtItem = new NBTItem(item);
        String mode = ChatColor.translateAlternateColorCodes('&',
                modeUniqueIDs.get(nbtItem.getInteger("ntool.raw.id")).get(0));
        String mode1 = ChatColor.translateAlternateColorCodes('&',
                modeUniqueIDs.get(nbtItem.getInteger("ntool.raw.id")).get(1));
        String mode2 = ChatColor.translateAlternateColorCodes('&',
                modeUniqueIDs.get(nbtItem.getInteger("ntool.raw.id")).get(2));
        int index = 0;
        for (String loreLine : itemLore) {
            //Check if the lore contains the mode unique line
            if (loreLine.contains(mode)) {
                //Check if the lore contains the trench unique line
                if (loreLine.contains(mode1)) {
                    //If true change the mode to tray, else return that it is a trench tool
                    if (changeMode) {
                        itemLore.set(index, mode + " " + mode2);
                        UpdateItem.updateItem(itemLore, itemMeta, item);
                    }
                    return true;
                } else if (loreLine.contains(mode2)) {
                    if (changeMode) {
                        itemLore.set(index, mode + " " + mode1);
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
