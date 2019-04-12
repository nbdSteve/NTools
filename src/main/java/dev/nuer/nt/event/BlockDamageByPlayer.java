package dev.nuer.nt.event;

import dev.nuer.nt.event.itemMetaMethod.GetToolType;
import dev.nuer.nt.event.miningTool.BreakBlocksInRadius;
import dev.nuer.nt.event.sandWand.RemoveSandStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Main event class for the plugin, handles trench, sand, tray and multi tool events
 */
public class BlockDamageByPlayer implements Listener {

    /**
     * Check that the player is holding a valid item
     *
     * @param event BlockDamageEvent, event being called to execute code
     */
    @EventHandler
    public void playerBlockDamage(BlockDamageEvent event) {
        //Check if the event is in a protected region
        if (event.isCancelled()) {
            return;
        }
        //Store the player
        Player player = event.getPlayer();
        //If the players item doesn't have meta / lore, return
        if (!player.getInventory().getItemInHand().hasItemMeta() || !player.getInventory().getItemInHand().getItemMeta().hasLore()) {
            return;
        }
        //Create a local variable for the item meta
        ItemMeta itemMeta = player.getInventory().getItemInHand().getItemMeta();
        //Create a local variable for the item lore
        List<String> itemLore = itemMeta.getLore();
        //Create a local variable for type of trench tool
        GetToolType toolType = new GetToolType(itemLore, itemMeta, player.getInventory().getItemInHand());
        //Make sure the tool is valid
        if (toolType.getToolType() == null) {
            return;
        }
        //Run the respective code for the tool type
        if (toolType.getDirectory().equalsIgnoreCase("sand")) {
            RemoveSandStack.removeStack(toolType, event, player);
        } else {
            BreakBlocksInRadius.breakBlocks(toolType, event, player);
        }
    }
}