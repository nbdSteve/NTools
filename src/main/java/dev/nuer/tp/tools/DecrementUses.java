package dev.nuer.tp.tools;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.external.actionbarapi.ActionBarAPI;
import dev.nuer.tp.external.nbtapi.NBTItem;
import dev.nuer.tp.method.itemCreation.UpdateItem;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Class that handles to decrement a tools uses
 */
public class DecrementUses {

    /**
     * Method to decrease the number of uses remaining for a tool based on its current uses
     *
     * @param player      Player, the player who's tool is being affected
     * @param typeOfTool  String, the type of tool the player is using
     * @param item        NBTItem, the item being effected
     * @param currentUses Integer, the number of uses the tool currently has
     */
    public static void decrementUses(Player player, String typeOfTool, NBTItem item, int currentUses) {
        //For special tools to not decrease the number of uses
        if (currentUses == -1) {
            return;
        }
        //Break the tool if it is out of uses
        if (currentUses-- <= 1) {
            player.setItemInHand(new ItemStack(Material.AIR));
            if (ToolsPlus.getFiles().get("config").getBoolean("wand-break-action-bar.enabled")) {
                String message = ToolsPlus.getFiles().get("config").getString("wand-break-action-bar." + typeOfTool + "-message");
                ActionBarAPI.sendActionBar(player, ChatColor.translateAlternateColorCodes('&', message));
            } else {
                new PlayerMessage("wand-uses-break", Bukkit.getPlayer(player.getUniqueId()), "{item}", typeOfTool);
            }
            return;
        }
        //Store the unique uses line
        String usesUniqueLine = ChatColor.translateAlternateColorCodes('&', ToolsPlus.getFiles().get(typeOfTool).getString(typeOfTool + "-wands." + item.getInteger("ntool.raw.id") + ".uses.unique"));
        //Store the uses placeholder to update
        String usesReplaceLine = ChatColor.translateAlternateColorCodes('&', ToolsPlus.getFiles().get(typeOfTool).getString(typeOfTool + "-wands." + item.getInteger("ntool.raw.id") + ".uses.update"));
        for (int i = 0; i < item.getItem().getItemMeta().getLore().size(); i++) {
            if (item.getItem().getItemMeta().getLore().get(i).contains(usesUniqueLine)) {
                //Remove the item from their hand
                item.setInteger("ntool.uses", currentUses--);
                List<String> itemLore = item.getItem().getItemMeta().getLore();
                //Update the lore with the new line
                itemLore.set(i, usesUniqueLine + " " + usesReplaceLine.replace("{uses}", String.valueOf(item.getInteger("ntool.uses"))));
                UpdateItem.updateItem(itemLore, item.getItem().getItemMeta(), item.getItem());
                player.setItemInHand(item.getItem());
            }
        }
    }
}