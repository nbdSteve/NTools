package dev.nuer.nt.cmd;

import dev.nuer.nt.NTools;
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

public class Tools implements CommandExecutor {

    public Tools(NTools nTools) {
//        nTools = NTools.getPlugin(NTools.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Tools")) {
            ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
            ItemMeta itemMeta = item.getItemMeta();
            List<String> itemLore = new ArrayList<>();
            itemLore.add(ChatColor.translateAlternateColorCodes('&', "&7Trench"));
            itemMeta.setLore(itemLore);
            item.setItemMeta(itemMeta);
            Player player = (Player) sender;
            player.getInventory().addItem(item);
        }
        return true;
    }
}
