package dev.nuer.nt.listener;

import dev.nuer.nt.ToolsPlus;
import dev.nuer.nt.external.nbtapi.NBTItem;
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
    public void openMultiToolOptionsGui(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        //Check action
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            //Check player is crouched
            if (player.isSneaking()) {
                if (!player.getInventory().getItemInHand().hasItemMeta() ||
                        !player.getInventory().getItemInHand().getItemMeta().hasLore()) {
                    return;
                }
                //Create a local variable for the item meta
                NBTItem nbtItem = new NBTItem(player.getItemInHand());
                try {
                    if (nbtItem.getBoolean("ntool.multi")) {
                        ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("multi-config").open(player);
                    }
                } catch (NullPointerException e) {
                    //NBT tag is null because this is not a multi tool
                }
                try {
                    if (nbtItem.getBoolean("ntool.harvester")) {
                        ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("harvester-config").open(player);
                    }
                } catch (NullPointerException e) {
                    //NBT tag is null because this is not a harvester hoe
                }
                try {
                    if (nbtItem.getBoolean("ntool.sell")) {
                        ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("sell-config").open(player);
                    }
                } catch (NullPointerException e) {
                    //NBT tag is null because this is not a sell wand
                }
                try {
                    if (nbtItem.getBoolean("ntool.tnt")) {
                        ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("tnt-config").open(player);
                    }
                } catch (NullPointerException e) {
                    //NBT tag is null because this is not a tnt wand
                }
            }
        }
    }
}
