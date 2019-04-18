package dev.nuer.nt.tools.harvest;

import dev.nuer.nt.external.nbtapi.NBTItem;
import dev.nuer.nt.initialize.MapInitializer;
import dev.nuer.nt.method.itemCreation.UpdateItem;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ChangeMode {

    public static void switchMode(List<String> itemLore, ItemMeta itemMeta, ItemStack item, Player player) {
        player.closeInventory();
        harvesterIsSelling(itemLore, itemMeta, item, true);
        new PlayerMessage("mode-switch", player);
    }

    public static boolean harvesterIsSelling(List<String> itemLore, ItemMeta itemMeta, ItemStack item, boolean changeMode) {
        NBTItem nbtItem = new NBTItem(item);
        String mode = ChatColor.translateAlternateColorCodes('&',
                MapInitializer.harvesterModeUnique.get(nbtItem.getInteger("ntool.raw.id")).get(0));
        String selling = ChatColor.translateAlternateColorCodes('&',
                MapInitializer.harvesterModeUnique.get(nbtItem.getInteger("ntool.raw.id")).get(1));
        String harvest = ChatColor.translateAlternateColorCodes('&',
                MapInitializer.harvesterModeUnique.get(nbtItem.getInteger("ntool.raw.id")).get(2));
        int index = 0;
        for (String loreLine : itemLore) {
            //Check if the lore contains the mode unique line
            if (loreLine.contains(mode)) {
                //Check if the lore contains the trench unique line
                if (loreLine.contains(selling)) {
                    //If true change the mode to tray, else return that it is a trench tool
                    if (changeMode) {
                        itemLore.set(index, mode + " " + harvest);
                        UpdateItem.updateItem(itemLore, itemMeta, item);
                    }
                    return true;
                } else if (loreLine.contains(harvest)) {
                    if (changeMode) {
                        itemLore.set(index, mode + " " + selling);
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
