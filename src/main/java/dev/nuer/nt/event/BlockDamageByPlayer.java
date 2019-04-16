package dev.nuer.nt.event;

import dev.nuer.nt.event.itemMetaMethod.GetToolType;
import dev.nuer.nt.event.miningTool.BreakBlocksInRadius;
import dev.nuer.nt.event.sandWand.RemoveSandStack;
import dev.nuer.nt.external.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

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
        //Get the type of tool being used
        try {
            if (nbtItem.getBoolean("ntool.trench")) {
                new BreakBlocksInRadius(nbtItem, event, player, "trench",
                        "trench-tools." + nbtItem.getInteger(
                                "ntool.raw.id"), false, true);
            }
        } catch (NullPointerException e) {
            //NBT tag is not initialized because this is not a trench tool
        }
        try {
            if (nbtItem.getBoolean("ntool.tray")) {
                new BreakBlocksInRadius(nbtItem, event, player, "trench", "tray-tools." + nbtItem.getInteger(
                        "ntool.raw.id"), false, false);
            }
        } catch (NullPointerException e) {
            //NBT tag is not initialized because this is not a tray tool
        }
        try {
            if (nbtItem.getBoolean("ntool.multi")) {
                new BreakBlocksInRadius(nbtItem, event, player, "multi", "multi-tools." + nbtItem.getInteger(
                        "ntool.raw.id"), true, nbtItem.getBoolean("ntool.multi.trench"));
            }
        } catch (NullPointerException e) {
            //NBT tag is not initialized because this is not a multi tool
        }
        try {
            if (nbtItem.getBoolean("ntool.sand")) {
                new RemoveSandStack(event, player, "sand", "sand-tools." + nbtItem.getInteger("ntool" +
                        ".raw.id"));
            }
        } catch (NullPointerException e) {
            //NBT tag is not initialized because this is not a sand wand
        }
    }
}