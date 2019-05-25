package dev.nuer.tp.listener;

import dev.nuer.tp.method.VerifyTool;
import dev.nuer.tp.support.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Class that handles misuse of tools+ tools, they are not being used in their intended interaction method.
 * This is to stop buckets being filled, etc.
 */
public class PlayerToolInteractionListener implements Listener {

    /**
     * Interaction blocker
     *
     * @param event PlayerInteractEvent, the event to check
     */
    @EventHandler
    public void interactWithTool(PlayerInteractEvent event) {
        if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                || event.getAction().equals(Action.RIGHT_CLICK_AIR))) return;
        if (event.getItem() != null
                || !event.getItem().getType().equals(Material.AIR)
                || !event.getItem().hasItemMeta()
                || !event.getItem().getItemMeta().hasLore()) return;
        NBTItem nbtItem = new NBTItem(event.getItem());
        if (VerifyTool.check(nbtItem)) event.setCancelled(true);
    }
}