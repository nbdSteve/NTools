package dev.nuer.nt.listener;

import dev.nuer.nt.external.nbtapi.NBTItem;
import dev.nuer.nt.tools.BreakBlocksInRadius;
import dev.nuer.nt.tools.lightning.CreateLightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractWithGround implements Listener {

    @EventHandler
    public void playerInteractGround(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
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
            if (nbtItem.getBoolean("ntool.lightning")) {
                CreateLightningStrike.createStrikeGround(player, "lightning", "lightning-wands." + nbtItem.getInteger(
                        "ntool.raw.id"), event.getClickedBlock());
            }
        } catch (NullPointerException e) {
            //NBT tag is null because this is not a trench tool
        }
    }
}
