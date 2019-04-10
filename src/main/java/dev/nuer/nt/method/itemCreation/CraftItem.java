package dev.nuer.nt.method.itemCreation;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CraftItem {
    private ItemStack item;
    private ItemMeta itemMeta;
    private List<String> itemLore;

    public CraftItem(String material, String name, List<String> lore, List<String> enchantments) {
        item = createItem(material);
        itemMeta = item.getItemMeta();
        if (name != null) {
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }
        addLore(lore);
        itemMeta.setLore(itemLore);
        addEnchantments(enchantments);
        item.setItemMeta(itemMeta);
    }

    public CraftItem(String material, String name, List<String> lore, String placeholder,
                     String replacement, List<String> enchantments, Player player) {
        item = createItem(material);
        itemMeta = item.getItemMeta();
        if (name != null) {
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }
        addLore(lore);
        List<String> formattedLore = new ArrayList<>();
        for (String lineOfLore : itemLore) {
            lineOfLore.replace(ChatColor.translateAlternateColorCodes('&', placeholder), replacement);
        }
        itemLore = formattedLore;
        itemMeta.setLore(itemLore);
        addEnchantments(enchantments);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    private ItemStack createItem(String material) {
        String[] materialParts = material.split(":");
        return new ItemStack(Material.valueOf(materialParts[0].toUpperCase()), 1, Byte.parseByte(materialParts[1]));
    }

    private void addLore(List<String> loreToAdd) {
        itemLore = new ArrayList<>();
        if (loreToAdd != null) {
            for (String lineOfLore : loreToAdd) {
                itemLore.add(ChatColor.translateAlternateColorCodes('&', lineOfLore));
            }
        }
    }

    private void addEnchantments(List<String> enchantmentsToAdd) {
        if (enchantmentsToAdd != null) {
            for (String enchantment : enchantmentsToAdd) {
                String[] enchantmentParts = enchantment.split(":");
                itemMeta.addEnchant(Enchantment.getByName(enchantmentParts[0].toUpperCase()),
                        Integer.parseInt(enchantmentParts[1]), true);
            }
        }
    }

    public ItemStack getItem() {
        return item;
    }

    public ItemMeta getItemMeta() {
        return itemMeta;
    }

    public List<String> getItemLore() {
        return itemLore;
    }
}
