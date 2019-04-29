package dev.nuer.tp.method.itemCreation;

import dev.nuer.tp.ToolsPlus;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles creating an ItemStack from a list of parameters
 */
public class CraftItem {
    //Store the item stack being created
    private ItemStack item;
    //Store the item meta
    private ItemMeta itemMeta;
    //Store the item lore
    private List<String> itemLore;

    /**
     * Create a new item with the desired parameters
     *
     * @param material     String, the item material
     * @param name         String, the items display name
     * @param lore         List<String>, list of strings to add as the items lore
     * @param enchantments List<String>, list of enchantments to add to the item
     * @param typeOfTool   String, the type of tool being created
     * @param idFromConfig Integer, the raw tool ID from the configuration files
     * @param player       Player, the player to give the new item to - can be null
     */
    public CraftItem(String material, String name, List<String> lore, List<String> enchantments,
                     String typeOfTool, int idFromConfig, Player player) {
        item = createItem(material);
        itemMeta = item.getItemMeta();
        if (name != null) {
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }
        addLore(lore, "debug", "debug", "debug", "debug", "debug", "debug");
        itemMeta.setLore(itemLore);
        addEnchantments(enchantments);
        item.setItemMeta(itemMeta);
        if (player != null) {
            if (typeOfTool.equalsIgnoreCase("trench") || typeOfTool.equalsIgnoreCase("tray") || typeOfTool.equalsIgnoreCase("multi")) {
                player.getInventory().addItem(NBTCreator.addToolData(item, typeOfTool, idFromConfig,
                        ToolsPlus.getFiles().get(typeOfTool).getBoolean(typeOfTool + "-tools." + idFromConfig + ".omni-tool")));
            } else {
                player.getInventory().addItem(NBTCreator.addToolData(item, typeOfTool, idFromConfig, 0));
            }
        }
    }

    /**
     * Create a new item with the desired placeholders to fill in the items lore.
     * If the item is not using one, set it to "debug"
     *
     * @param material            String, the item material
     * @param name                String, the items display name
     * @param lore                List<String>, list of strings to add as the items lore
     * @param enchantments        List<String>, list of enchantments to add to the item
     * @param typeOfTool          String, the type of tool being created
     * @param idFromConfig        Integer, the raw tool ID from the configuration files
     * @param player              Player, the player to give the new item to - can be null
     * @param modePlaceholder     String, placeholder for the tools mode
     * @param modeReplacement     String, replacement for the mode placeholder
     * @param modifierPlaceholder String, placeholder for the tools modifier
     * @param modifierReplacement String, replacement for the modifier placeholder
     * @param usesPlaceholder     String, placeholder for the tools uses
     * @param usesReplacement     String, replacement for the uses placeholder
     */
    public CraftItem(String material, String name, List<String> lore, List<String> enchantments,
                     String typeOfTool, int idFromConfig, Player player, String modePlaceholder,
                     String modeReplacement, String modifierPlaceholder, String modifierReplacement,
                     String usesPlaceholder, String usesReplacement) {
        item = createItem(material);
        itemMeta = item.getItemMeta();
        if (name != null) {
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }
        addLore(lore, modePlaceholder, modeReplacement, modifierPlaceholder, modifierReplacement, usesPlaceholder, usesReplacement);
        itemMeta.setLore(itemLore);
        addEnchantments(enchantments);
        item.setItemMeta(itemMeta);
        if (player != null) {
            if (typeOfTool.equalsIgnoreCase("trench") || typeOfTool.equalsIgnoreCase("tray") || typeOfTool.equalsIgnoreCase("multi")) {
                player.getInventory().addItem(NBTCreator.addToolData(item, typeOfTool, idFromConfig,
                        ToolsPlus.getFiles().get(typeOfTool).getBoolean(typeOfTool + "-tools." + idFromConfig + ".omni-tool")));
            } else {
                int uses;
                try {
                    uses = Integer.parseInt(usesReplacement);
                } catch (NumberFormatException e) {
                    uses = -1;
                }
                player.getInventory().addItem(NBTCreator.addToolData(item, typeOfTool, idFromConfig, uses));
            }
        }
    }

    /**
     * Creates a new item of the given material
     *
     * @param material String, material name and damage value
     * @return ItemStack
     */
    private ItemStack createItem(String material) {
        String[] materialParts = material.split(":");
        return new ItemStack(Material.valueOf(materialParts[0].toUpperCase()), 1,
                Byte.parseByte(materialParts[1]));
    }

    /**
     * Adds the desired lore to an item, replacing the appropriate placeholders
     *
     * @param loreToAdd           List<String>, the lore to add
     * @param modePlaceholder     String, placeholder for the tools mode
     * @param modeReplacement     String, replacement for the mode placeholder
     * @param modifierPlaceholder String, placeholder for the tools modifier
     * @param modifierReplacement String, replacement for the modifier placeholder
     * @param usesPlaceholder     String, placeholder for the tools uses
     * @param usesReplacement     String, replacement for the uses placeholder
     */
    private void addLore(List<String> loreToAdd, String modePlaceholder, String modeReplacement,
                         String modifierPlaceholder, String modifierReplacement, String usesPlaceholder,
                         String usesReplacement) {
        itemLore = new ArrayList<>();
        for (String lineOfLore : loreToAdd) {
            itemLore.add(ChatColor.translateAlternateColorCodes('&', lineOfLore)
                    .replace(modePlaceholder, ChatColor.translateAlternateColorCodes('&', modeReplacement))
                    .replace(modifierPlaceholder, ChatColor.translateAlternateColorCodes('&',
                            modifierReplacement))
                    .replace(usesPlaceholder, ChatColor.translateAlternateColorCodes('&', usesReplacement)));
        }
    }

    /**
     * Add a list of enchantments to an item
     *
     * @param enchantmentsToAdd List<String>, list of enchantments to add
     */
    private void addEnchantments(List<String> enchantmentsToAdd) {
        if (enchantmentsToAdd != null) {
            for (String enchantment : enchantmentsToAdd) {
                String[] enchantmentParts = enchantment.split(":");
                itemMeta.addEnchant(Enchantment.getByName(enchantmentParts[0].toUpperCase()),
                        Integer.parseInt(enchantmentParts[1]), true);
                if (enchantmentParts[0].equalsIgnoreCase("lure")) {
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
            }
        }
    }

    /**
     * Getter for the item
     *
     * @return ItemStack
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     * Getter for the items meta data
     *
     * @return ItemMeta
     */
    public ItemMeta getItemMeta() {
        return itemMeta;
    }

    /**
     * Getter for the items lore
     *
     * @return List<String>
     */
    public List<String> getItemLore() {
        return itemLore;
    }
}
