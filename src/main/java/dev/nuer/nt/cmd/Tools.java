package dev.nuer.nt.cmd;

import dev.nuer.nt.NTools;
import dev.nuer.nt.event.itemMetaMethod.GetToolType;
import dev.nuer.nt.event.modeSwitchMethod.ModeSwitch;
import dev.nuer.nt.event.radiusChangeMethod.ChangeToolRadius;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Ignore this class for the moment, commands are just for testing
 */
public class Tools implements CommandExecutor {

    public Tools(NTools nTools) {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("tools")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("3x3")) {
                ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
                ItemMeta itemMeta = item.getItemMeta();
                List<String> itemLore = new ArrayList<>();
                itemLore.add(ChatColor.translateAlternateColorCodes('&', "&7Trench 3x3"));
                itemMeta.setLore(itemLore);
                item.setItemMeta(itemMeta);
                Player player = (Player) sender;
                player.getInventory().addItem(item);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("5x5")) {
                ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
                ItemMeta itemMeta = item.getItemMeta();
                List<String> itemLore = new ArrayList<>();
                itemLore.add(ChatColor.translateAlternateColorCodes('&', "&7Trench 5x5"));
                itemMeta.setLore(itemLore);
                item.setItemMeta(itemMeta);
                Player player = (Player) sender;
                player.getInventory().addItem(item);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("tray")) {
                ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
                ItemMeta itemMeta = item.getItemMeta();
                List<String> itemLore = new ArrayList<>();
                itemLore.add(ChatColor.translateAlternateColorCodes('&', "&7Tray 3x3"));
                itemMeta.setLore(itemLore);
                item.setItemMeta(itemMeta);
                Player player = (Player) sender;
                player.getInventory().addItem(item);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("multi")) {
                ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
                ItemMeta itemMeta = item.getItemMeta();
                List<String> itemLore = new ArrayList<>();
                itemLore.add(ChatColor.translateAlternateColorCodes('&', "&2&l**Forged**"));
                itemLore.add(ChatColor.translateAlternateColorCodes('&', "&7Mode: &e&lTRENCH"));
                itemLore.add(ChatColor.translateAlternateColorCodes('&', "&7Radius: &b&l3x3"));
                itemMeta.setLore(itemLore);
                item.setItemMeta(itemMeta);
                Player player = (Player) sender;
                player.getInventory().addItem(item);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("switch")) {
                Player player = (Player) sender;
                ItemStack item = player.getInventory().getItemInHand();
                ItemMeta itemMeta = item.getItemMeta();
                List<String> itemLore = itemMeta.getLore();
//                ChangeToolRadius.incrementRadius((new GetToolType(itemLore, itemMeta, item).getToolType()), itemLore, itemMeta, item, player);
                ModeSwitch.switchMode((new GetToolType(itemLore, itemMeta, item).getToolType()), itemLore, itemMeta, item);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("inc")) {
                Player player = (Player) sender;
                ItemStack item = player.getInventory().getItemInHand();
                ItemMeta itemMeta = item.getItemMeta();
                List<String> itemLore = itemMeta.getLore();
                ChangeToolRadius.incrementRadius((new GetToolType(itemLore, itemMeta, item).getToolType()), itemLore, itemMeta, item, player);
//                ModeSwitch.switchMode((new GetToolType(itemLore, itemMeta, item).getToolType()), itemLore, itemMeta, item);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("dec")) {
                Player player = (Player) sender;
                ItemStack item = player.getInventory().getItemInHand();
                ItemMeta itemMeta = item.getItemMeta();
                List<String> itemLore = itemMeta.getLore();
                ChangeToolRadius.decrementRadius((new GetToolType(itemLore, itemMeta, item).getToolType()), itemLore, itemMeta, item, player);
//                ModeSwitch.switchMode((new GetToolType(itemLore, itemMeta, item).getToolType()), itemLore, itemMeta, item);
            }
        }
        return true;
    }
}
