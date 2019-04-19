package dev.nuer.nt.listener;

import dev.nuer.nt.NTools;
import dev.nuer.nt.external.nbtapi.NBTItem;
import dev.nuer.nt.tools.lightning.CreateLightningStrike;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

    @EventHandler
    public void playerInteractGround(PlayerInteractEvent event) {
        if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
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
        //Store the location to Strike
        Block locationToStrike;
        //Check to target block based on where the user is looking / clicked
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            locationToStrike = player.getTargetBlock(null, NTools.getFiles().get("config").getInt("lightning-reach-distance"));
        } else {
            locationToStrike = event.getClickedBlock();
        }
        //Get the type of tool being used
        try {
            if (nbtItem.getBoolean("ntool.lightning")) {
                CreateLightningStrike.createStrikeGround(player, "lightning",
                        "lightning-wands." + nbtItem.getInteger(
                        "ntool.raw.id"), locationToStrike);
            }
        } catch (NullPointerException e) {
            //NBT tag is null because this is not a trench tool
        }
    }
}
