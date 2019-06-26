package dev.nuer.tp.listener;

import dev.nuer.tp.support.nbtapi.NBTItem;
import dev.nuer.tp.managers.ToolsAttributeManager;
import dev.nuer.tp.tools.BreakBlocksInRadius;
import dev.nuer.tp.tools.ChangeMode;
import dev.nuer.tp.tools.AlterToolModifier;
import dev.nuer.tp.tools.DecrementUses;
import dev.nuer.tp.tools.harvest.HarvestBlock;
import dev.nuer.tp.tools.sand.RemoveSandStack;
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
        if (event.isCancelled()) return;
        //Store the player
        Player player = event.getPlayer();
        //If the players item doesn't have meta / lore, return
        if (!event.getItemInHand().hasItemMeta() || !event.getItemInHand().getItemMeta().hasLore()) return;
        //Create a new nbt object
        NBTItem nbtItem = new NBTItem(event.getItemInHand());
        //Get the type of tool being used
        try {
            if (nbtItem.getBoolean("tools+.trench")) {
                event.setCancelled(true);
                new BreakBlocksInRadius(nbtItem, event, player, "trench",
                        "trench-tools." + nbtItem.getInteger("tools+.raw.id"), false, true, false);
            }
        } catch (NullPointerException e) {
            //NBT tag is null because this is not a trench tool
        }
        try {
            if (nbtItem.getBoolean("tools+.tray")) {
                event.setCancelled(true);
                new BreakBlocksInRadius(nbtItem, event, player, "tray", "tray-tools." + nbtItem.getInteger(
                        "tools+.raw.id"), false, false, false);
            }
        } catch (NullPointerException e) {
            //NBT tag is null because this is not a tray tool
        }
        try {
            if (nbtItem.getBoolean("tools+.multi")) {
                event.setCancelled(true);
                new BreakBlocksInRadius(nbtItem, event, player, "multi", "multi-tools." + nbtItem.getInteger(
                        "tools+.raw.id"), true, ChangeMode.changeToolMode(
                        nbtItem.getItem().getItemMeta().getLore(), nbtItem.getItem().getItemMeta(),
                        nbtItem.getItem(), ToolsAttributeManager.multiToolModeUnique, false), false);
            }
        } catch (NullPointerException e) {
            //NBT tag is null because this is not a multi tool
        }
        try {
            if (nbtItem.getBoolean("tools+.aqua")) {
                event.setCancelled(true);
                new BreakBlocksInRadius(nbtItem, event, player, "aqua", "aqua-wands." + nbtItem.getInteger(
                        "tools+.raw.id"), false, false, true);
            }
        } catch (NullPointerException e) {
            //NBT tag is null because this is not a aqua wand
        }
        try {
            if (nbtItem.getBoolean("tools+.sand")) {
                event.setCancelled(true);
                new RemoveSandStack(event, player, "sand", "sand-wands." +
                        nbtItem.getInteger("tools+.raw.id"), nbtItem);
            }
        } catch (NullPointerException e) {
            //NBT tag is null because this is not a sand wand
        }
        try {
            if (nbtItem.getBoolean("tools+.harvester")) {
                event.setCancelled(true);
                if (ToolsAttributeManager.harvesterBlockPrices.get(event.getBlock().getType().toString()) != null) {
                    //Decrease the tools uses
                    DecrementUses.decrementUses(player, "harvester-tool", nbtItem, nbtItem.getInteger("tools+.uses"));
                    //Harvest the blocks
                    HarvestBlock.harvestBlocks(event, player, ChangeMode.changeToolMode(nbtItem.getItem().getItemMeta().getLore(),
                            nbtItem.getItem().getItemMeta(), nbtItem.getItem(), ToolsAttributeManager.harvesterModeUnique, false),
                            ToolsAttributeManager.harvesterBlockPrices.get(event.getBlock().getType().toString()),
                            AlterToolModifier.getCurrentModifier(nbtItem.getItem().getItemMeta().getLore(), nbtItem.getItem(), true, ToolsAttributeManager.harvesterModifierUnique));
                }
            }
        } catch (NullPointerException e) {
            //NBT tag is null because this is not a sand wand
        }
    }
}