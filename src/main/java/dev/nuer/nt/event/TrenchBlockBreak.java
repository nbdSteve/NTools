package dev.nuer.nt.event;

import dev.nuer.nt.NTools;
import dev.nuer.nt.file.LoadFile;
import dev.nuer.nt.method.BlockInBorderCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TrenchBlockBreak implements Listener {
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
        Integer trenchToolType;
        try {
            for (trenchToolType = 1; trenchToolType <= 54; trenchToolType++) {
                if (itemLore.contains(ChatColor.translateAlternateColorCodes('&',
                        NTools.getFiles().get("Tools").getString("trench." + trenchToolType + ".unique-lore")))) {
                    break;
                }
            }
        } catch (NullPointerException toolDoesNotExist) {
            return;
        }
        //Store the break radius for the tool
        int radiusX = -(files.get("tools").getInt("trench." + trenchToolType + ".break-radius"));
        int radiusY = -(files.get("tools").getInt("trench." + trenchToolType + ".break-radius"));
        int radiusZ = -(files.get("tools").getInt("trench." + trenchToolType + ".break-radius"));
        int radius = (files.get("tools").getInt("trench." + trenchToolType + ".break-radius"));
        //Store the list of blocks that should not be broken by the tool
        while (radiusY < radius + 1) {
            while (radiusZ < radius + 1) {
                while (radiusX < radius + 1) {
                    //Creating a new event to check if the radial blocks can be broken
                    //This should support any plugin
                    BlockPlaceEvent trenchRadiusBreak =
                            new BlockPlaceEvent(event.getBlock().getRelative(radiusX, radiusY, radiusZ),
                                    event.getBlock().getRelative(radiusX, radiusY, radiusZ).getState(),
                                    event.getBlock().getRelative(radiusX, radiusY, radiusZ),
                                    player.getItemInHand(), player, false);
                    String current = trenchRadiusBreak.getBlock().getType().toString();
                    Bukkit.getPluginManager().callEvent(trenchRadiusBreak);
                    //Check if the event is cancelled, if true then don't break that block
                    if (trenchRadiusBreak.isCancelled()) {
                    } else if (NTools.getTrenchBlockBlacklist().contains(current)) {
                        event.setCancelled(true);
                    } else if (BlockInBorderCheck.isInsideBorder(trenchRadiusBreak.getBlock(),
                            player)) {

                    } else {
                        for (ItemStack item : trenchRadiusBreak.getBlock().getDrops()) {
                            player.getInventory().addItem(item);
                        }
                        trenchRadiusBreak.getBlock().setType(Material.AIR);
                        trenchRadiusBreak.getBlock().getDrops().clear();
                    }
                    radiusX++;
                }
                radiusX = -(files.get("Tools").getInt("trench." + trenchToolType + ".break-radius"));
                radiusZ++;
            }
            radiusZ = -(files.get("Tools").getInt("trench." + trenchToolType + ".break-radius"));
            radiusY++;
        }
    }
}
