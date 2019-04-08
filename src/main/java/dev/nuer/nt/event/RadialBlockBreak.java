package dev.nuer.nt.event;

import dev.nuer.nt.NTools;
import dev.nuer.nt.method.BlockInBorderCheck;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class RadialBlockBreak implements Listener {

    @EventHandler
    public void radialBlockBreak(BlockBreakEvent event) {
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
        GetToolType toolType = new GetToolType(itemLore);
        //Get the radius of the tool from the tools.yml
        int radiusFromConf = NTools.getFiles().get("tools").getInt(toolType.getToolType() + ".break-radius");
        //If the tool is a multi, get its current radius
        if (toolType.getIsMultiTool()) {
            radiusFromConf = toolType.getMultiToolRadius();
        }
        //Store the break radius for the tool
        int radiusX = -radiusFromConf;
        int radiusY = -radiusFromConf;
        int radiusZ = -radiusFromConf;
        int radius = radiusFromConf;
        //Store the list of blocks that should not be broken by the tool
        while (radiusY < radius + 1) {
            while (radiusZ < radius + 1) {
                while (radiusX < radius + 1) {
                    //Creating a new event to check if the radial blocks can be broken
                    //This should support any plugin
                    BlockPlaceEvent radialBreak =
                            new BlockPlaceEvent(event.getBlock().getRelative(radiusX, radiusY, radiusZ),
                                    event.getBlock().getRelative(radiusX, radiusY, radiusZ).getState(),
                                    event.getBlock().getRelative(radiusX, radiusY, radiusZ),
                                    player.getItemInHand(), player, false);
                    String current = radialBreak.getBlock().getType().toString();
                    Bukkit.getPluginManager().callEvent(radialBreak);
                    //Check if the event is cancelled, if true then don't break that block
                    if (radialBreak.isCancelled()) {
                    } else if (BlockInBorderCheck.isInsideBorder(radialBreak.getBlock(),
                            player)) {
                    } else {
                        if (toolType.getIsTrenchTool()) {
                            if (NTools.getTrenchBlockBlacklist().contains(current)) {
                                event.setCancelled(true);
                            } else {
                                AddBlocksToPlayerInventory.addBlocks(radialBreak, player);
                            }
                        } else if (toolType.getIsTrayTool()) {
                            if (!NTools.getTrayBlockWhitelist().contains(current)) {
                                event.setCancelled(true);
                            } else {
                                AddBlocksToPlayerInventory.addBlocks(radialBreak, player);
                            }
                        }
                    }
                    radiusX++;
                }
                radiusX = -radiusFromConf;
                radiusZ++;
            }
            radiusZ = -radiusFromConf;
            radiusY++;
        }
    }
}