package dev.nuer.nt.event;

import dev.nuer.nt.NTools;
import dev.nuer.nt.file.LoadFile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TrenchBlockBreak {
    //Get the plugin files
    private LoadFile files = NTools.getFiles();

    @EventHandler
    public void trenchBlockBreak(BlockBreakEvent event) {
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
        String trenchToolType = null;
//        ArrayList<>
//        for (string)
    }
}
