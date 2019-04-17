package dev.nuer.nt.listener;

import dev.nuer.nt.external.nbtapi.NBTItem;
import dev.nuer.nt.tools.lightning.CreateLightningStrike;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractWithMob implements Listener {

    @EventHandler
    public void playerInteractMob(PlayerInteractEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.getRightClicked().getType().equals(EntityType.CREEPER)) {
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
                CreateLightningStrike.createMobStrike(player, "lightning",
                        "lightning-wands." + nbtItem.getInteger("ntool.raw.id"),
                        (Creeper) event.getRightClicked());
            }
        } catch (NullPointerException e) {
            //NBT tag is null because this is not a trench tool
        }
    }
}
