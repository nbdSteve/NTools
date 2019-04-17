package dev.nuer.nt.listener;

import dev.nuer.nt.tools.multi.GetMultiToolVariables;
import dev.nuer.nt.tools.BreakBlocksInRadius;
import dev.nuer.nt.external.nbtapi.NBTItem;
import dev.nuer.nt.tools.sand.RemoveSandStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

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
        if (!player.getItemInHand().hasItemMeta() || !player.getItemInHand().getItemMeta().hasLore()) {
            return;
        }
        //Create a new nbt object
        NBTItem nbtItem = new NBTItem(player.getItemInHand());
        //Get the type of tool being used
        try {
            if (nbtItem.getBoolean("ntool.trench")) {
                event.setCancelled(true);
                new BreakBlocksInRadius(nbtItem, event, player, "trench",
                        "trench-tools." + nbtItem.getInteger("ntool.raw.id"), false, true);
            }
        } catch (NullPointerException e) {
            //NBT tag is null because this is not a trench tool
        }
        try {
            if (nbtItem.getBoolean("ntool.tray")) {
                event.setCancelled(true);
                new BreakBlocksInRadius(nbtItem, event, player, "tray", "tray-tools." + nbtItem.getInteger(
                        "ntool.raw.id"), false, false);
            }
        } catch (NullPointerException e) {
            //NBT tag is null because this is not a tray tool
        }
        try {
            if (nbtItem.getBoolean("ntool.multi")) {
                event.setCancelled(true);
                new BreakBlocksInRadius(nbtItem, event, player, "multi", "multi-tools." + nbtItem.getInteger(
                        "ntool.raw.id"), true, GetMultiToolVariables.multiToolIsTrenchTool(nbtItem.getInteger(
                        "ntool.raw.id"), nbtItem.getItem().getItemMeta().getLore(), nbtItem.getItem().getItemMeta(), nbtItem.getItem(), false));
            }
        } catch (NullPointerException e) {
            //NBT tag is null because this is not a multi tool
        }
        try {
            if (nbtItem.getBoolean("ntool.sand")) {
                event.setCancelled(true);
                new RemoveSandStack(event, player, "sand", "sand-wands." + nbtItem.getInteger("ntool" +
                        ".raw.id"));
            }
        } catch (NullPointerException e) {
            //NBT tag is null because this is not a sand wand
        }
    }
}