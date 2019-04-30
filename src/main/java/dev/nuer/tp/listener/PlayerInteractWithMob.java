package dev.nuer.tp.listener;

import dev.nuer.tp.external.nbtapi.NBTItem;
import dev.nuer.tp.tools.lightning.CreateLightningStrike;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Class that handles plugin events cause by a player interacting with a mob
 */
public class PlayerInteractWithMob implements Listener {

    /**
     * Method for Lighting Wands
     *
     * @param event PlayerInteractEntityEvent
     */
    @EventHandler
    public void playerInteractMob(PlayerInteractEntityEvent event) {
        //Check if the event is cancelled
        if (event.isCancelled()) return;
        //Verify that the mob is a creeper
        if (!event.getRightClicked().getType().equals(EntityType.CREEPER)) return;
        //Store the player
        Player player = event.getPlayer();
        //If the players item doesn't have meta / lore, return
        if (!player.getItemInHand().hasItemMeta() || !player.getItemInHand().getItemMeta().hasLore()) return;
        //Create a new nbt object
        NBTItem nbtItem = new NBTItem(player.getItemInHand());
        //Get the type of tool being used
        try {
            if (nbtItem.getBoolean("tools+.lightning")) {
                CreateLightningStrike.createMobStrike(player, "lightning",
                        "lightning-wands." + nbtItem.getInteger("tools+.raw.id"),
                        (Creeper) event.getRightClicked(), nbtItem);
            }
        } catch (NullPointerException e) {
            //NBT tag is null because this is not a trench tool
        }
    }
}