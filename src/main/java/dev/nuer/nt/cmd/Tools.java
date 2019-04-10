package dev.nuer.nt.cmd;

import dev.nuer.nt.NTools;
import dev.nuer.nt.method.player.PlayerMessage;
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
        if (command.getName().equalsIgnoreCase("nt") || command.getName().equalsIgnoreCase("tools")) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    if (sender.hasPermission("ntools.gui.multi")) {
                        NTools.getPlugin(NTools.class).getMultiToolOptionsGui().open((Player) sender);
                    } else {
                        new PlayerMessage("no-permission", (Player) sender);
                    }
                } else {
                    NTools.LOGGER.info("The upgrade Gui can only be viewed by players.");
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("help")) {
                    if (sender instanceof Player) {
                        if (sender.hasPermission("ntools.help")) {
                            new PlayerMessage("help", (Player) sender);
                        } else {
                            new PlayerMessage("no-permission", (Player) sender);
                        }
                    } else {
                        NTools.LOGGER.info("The help message can only be viewed by players.");
                    }
                }
                if (args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("reload")) {
                    if (sender instanceof Player) {
                        if (sender.hasPermission("ntools.admin")) {
                            NTools.clearMaps();
                            NTools.loadToolMaps();
                            NTools.getFiles().reload();
                            new PlayerMessage("reload", (Player) sender);
                        } else {
                            new PlayerMessage("no-permission", (Player) sender);
                        }
                    } else {
                        NTools.clearMaps();
                        NTools.loadToolMaps();
                        NTools.getFiles().reload();
                        NTools.LOGGER.info("Successfully reloaded all files and maps.");
                    }
                }
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
                NTools.getPlugin(NTools.class).getMultiToolOptionsGui().open((Player) sender);
            }
        }
        return true;
    }
}
