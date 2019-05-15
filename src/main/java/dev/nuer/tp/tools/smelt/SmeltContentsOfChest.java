package dev.nuer.tp.tools.smelt;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.managers.ToolsAttributeManager;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.player.PlayerMessage;
import dev.nuer.tp.support.actionbarapi.ActionBarAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Class that handles smelting the contents of a chest
 */
public class SmeltContentsOfChest {

    /**
     * Smelts the contents of the respective inventory
     *
     * @param player    Player, the player using the smelt wand
     * @param inventory Inventory, the inventory to convert
     */
    public static void smeltChestContents(Player player, Inventory inventory) {
        int slot = 0;
        int totalSmelted = 0;
        for (ItemStack item : inventory.getContents()) {
            try {
                if (!item.hasItemMeta() && ToolsAttributeManager.smeltBlockConversions.containsKey(item.getType())) {
                    totalSmelted += item.getAmount();
                    inventory.setItem(slot, new ItemStack(ToolsAttributeManager.smeltBlockConversions.get(item.getType()), item.getAmount()));
                }
            } catch (NullPointerException e) {
                //Item is cannot be smelted
            }
            slot++;
        }
        if (FileManager.get("config").getBoolean("smelt-wand-action-bar.enabled")) {
            //Create the action bar message
            String message = FileManager.get("config").getString("smelt-wand-action-bar.message").replace("{deposit}",
                    ToolsPlus.numberFormat.format(totalSmelted));
            //Send it to the player
            ActionBarAPI.sendActionBar(player, Chat.applyColor(message));
        } else {
            new PlayerMessage("chest-smelt-convert-contents", player, "{deposit}", ToolsPlus.numberFormat.format(totalSmelted));
        }
    }

    /**
     * Returns true if the chest contains items that can be smelted
     *
     * @param inventory Inventory, the inventory to check
     * @return boolean
     */
    public static boolean canSmeltContents(Inventory inventory) {
        for (Material material : ToolsAttributeManager.smeltBlockConversions.keySet()) {
            if (inventory.contains(material)) return true;
        }
        return false;
    }
}
