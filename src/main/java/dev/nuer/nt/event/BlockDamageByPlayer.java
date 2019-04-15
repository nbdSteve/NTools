package dev.nuer.nt.event;

import dev.nuer.nt.NTools;
import dev.nuer.nt.event.itemMetaMethod.GetToolType;
import dev.nuer.nt.event.miningTool.BreakBlocksInRadius;
import dev.nuer.nt.event.sandWand.RemoveSandStack;
import dev.nuer.nt.external.nbtapi.NBTItem;
import org.bukkit.Bukkit;
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
    GetToolType toolType;

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
        if (!player.getItemInHand().hasItemMeta() || !player.getItemInHand().getItemMeta().hasLore()) {
            return;
        }
        //Create a new nbt object
        NBTItem nbtItem = new NBTItem(player.getItemInHand());
        //Make sure the tool is an ntool
        if (!nbtItem.getBoolean("ntool")) {
            return;
        }
        //Run task async :)
        Bukkit.getScheduler().runTaskAsynchronously(NTools.getPlugin(NTools.class), () -> {
            this.toolType = new GetToolType(nbtItem.getString("ntool.tool.type"), nbtItem.getInteger("ntool.raw.id"));
            if (toolType.getIsMultiTool()) {
                toolType.getMultiToolMode(nbtItem.getString("ntool.multi.mode"));
            }
        });
        if (toolType == null) {
            return;
        }
        //Run the respective code for the tool type
        if (toolType.getDirectory().equalsIgnoreCase("sand")) {
            RemoveSandStack.removeStack(toolType, event, player);
        } else {
            new BreakBlocksInRadius(toolType, event, player);
        }
    }
}