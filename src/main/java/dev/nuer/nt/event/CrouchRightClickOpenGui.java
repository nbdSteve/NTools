package dev.nuer.nt.event;

import dev.nuer.nt.NTools;
import dev.nuer.nt.event.itemMetaMethod.GetToolType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

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
                ItemMeta itemMeta = player.getInventory().getItemInHand().getItemMeta();
                //Create a local variable for the item lore
                List<String> itemLore = itemMeta.getLore();
                //Create a local variable for type of trench tool
                GetToolType toolType = new GetToolType(itemLore, itemMeta, player.getInventory().getItemInHand());
                if (toolType.getIsMultiTool()) {
                    NTools.getPlugin(NTools.class).getGuiByName("multi-config").open(player);
                }
            }
        }
    }
}
