package dev.nuer.tp.listener;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.external.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Class that handles PlayerInteractEvent to open the multi tool options Gui
 */
public class CrouchRightClickOpenGui implements Listener {

    /**
     * Method called on PlayerInteract
     *
     * @param event PlayerInteractEvent
     */
    @EventHandler
    public void openToolConfigurationGui(PlayerInteractEvent event) {
        //Store the player
        Player player = event.getPlayer();
        //Check player is crouched
        if (!player.isSneaking()) return;
        //Check action
        if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
            return;
        //Check that the item has meta and lore
        if (event.getItem() == null || !event.getItem().hasItemMeta() || !event.getItem().getItemMeta().hasLore())
            return;
        //Create a local variable for the item meta
        NBTItem nbtItem = new NBTItem(event.getItem());
        //See which tool it is and open the respective gui
        try {
            if (nbtItem.getBoolean("tools+.multi")) {
                ToolsPlus.instance.getGuiByName("multi-config").open(player);
            }
        } catch (NullPointerException e) {
            //NBT tag is null because this is not a multi tool
        }
        try {
            if (nbtItem.getBoolean("tools+.harvester")) {
                ToolsPlus.instance.getGuiByName("harvester-config").open(player);
            }
        } catch (NullPointerException e) {
            //NBT tag is null because this is not a harvester hoe
        }
        try {
            if (nbtItem.getBoolean("tools+.sell")) {
                ToolsPlus.instance.getGuiByName("sell-config").open(player);
            }
        } catch (NullPointerException e) {
            //NBT tag is null because this is not a sell wand
        }
        try {
            if (nbtItem.getBoolean("tools+.tnt")) {
                ToolsPlus.instance.getGuiByName("tnt-config").open(player);
            }
        } catch (NullPointerException e) {
            //NBT tag is null because this is not a tnt wand
        }
        try {
            if (nbtItem.getBoolean("tools+.aqua")) {
                ToolsPlus.instance.getGuiByName("aqua-config").open(player);
            }
        } catch (NullPointerException e) {
            //NBT tag is null because this is not a aqua wand
        }
    }
}