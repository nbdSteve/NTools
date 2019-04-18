package dev.nuer.nt.method.itemCreation;

import dev.nuer.nt.external.NBTCreator;
import dev.nuer.nt.external.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
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
     * Constructor the create an item based off of the parameters
     *
     * @param material          String, the item material
     * @param name              String, the items display name
     * @param lore              List<String>, list of strings to add as the items lore
     * @param modeReplacement   String, replacement for {mode} placeholder, can be null
     * @param radiusReplacement String, replacement for {radius} placeholder, can be null
     * @param enchantments      List<String>, list of enchantments to add to the item
     * @param player            Player, player to give the new item to, can be null
     */
    public CraftItem(String material, String name, List<String> lore, String modeReplacement,
                     String radiusReplacement, List<String> enchantments, String typeOfTool, int idFromConfig, Player player) {
        item = createItem(material);
        itemMeta = item.getItemMeta();
        if (name != null) {
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }
        addLore(lore, modeReplacement, radiusReplacement, false);
        itemMeta.setLore(itemLore);
        addEnchantments(enchantments);
        item.setItemMeta(itemMeta);
        if (player != null) {
            player.getInventory().addItem(NBTCreator.addToolData(item, typeOfTool, idFromConfig));
        }
    }

    public CraftItem(String material, String name, List<String> lore, String modeReplacement,
                     String modifierReplacement, List<String> enchantments, String typeOfTool, int idFromConfig, Player player, boolean harvester) {
        item = createItem(material);
        itemMeta = item.getItemMeta();
        if (name != null) {
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }
        addLore(lore, modeReplacement, modifierReplacement, harvester);
        itemMeta.setLore(itemLore);
        addEnchantments(enchantments);
        item.setItemMeta(itemMeta);
        if (player != null) {
            player.getInventory().addItem(NBTCreator.addToolData(item, typeOfTool, idFromConfig));
        }
    }

    /**
     * Creates a new item of the given material
     *
     * @param material String, material name and damage value
     * @return
     */
    private ItemStack createItem(String material) {
        String[] materialParts = material.split(":");
        return new ItemStack(Material.valueOf(materialParts[0].toUpperCase()), 1, Byte.parseByte(materialParts[1]));
    }

    /**
     * Adds the given lore to an item
     *
     * @param loreToAdd         List<String>, the lines of lore to add
     * @param modeReplacement   String, replacement for the {mode} placeholder
     * @param radiusReplacement String, replacement for the {radius} placeholder
     */
    private void addLore(List<String> loreToAdd, String modeReplacement, String radiusReplacement, boolean harvester) {
        itemLore = new ArrayList<>();
        if (modeReplacement != null) {
            if (harvester) {
                for (String lineOfLore : loreToAdd) {
                    itemLore.add(ChatColor.translateAlternateColorCodes('&', lineOfLore)
                            .replace("{modifier}", ChatColor.translateAlternateColorCodes('&', radiusReplacement))
                            .replace("{mode}", ChatColor.translateAlternateColorCodes('&', modeReplacement)));
                }
            }  else {
                for (String lineOfLore : loreToAdd) {
                    itemLore.add(ChatColor.translateAlternateColorCodes('&', lineOfLore)
                            .replace("{radius}", ChatColor.translateAlternateColorCodes('&', modeReplacement))
                            .replace("{mode}", ChatColor.translateAlternateColorCodes('&', radiusReplacement)));
                }
            }
        } else if (loreToAdd != null) {
            for (String lineOfLore : loreToAdd) {
                itemLore.add(ChatColor.translateAlternateColorCodes('&', lineOfLore));
            }
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
            }
        }
    }

    /**
     * Getter for the item
     *
     * @return
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     * Getter for the items meta data
     *
     * @return
     */
    public ItemMeta getItemMeta() {
        return itemMeta;
    }

    /**
     * Getter for the items lore
     *
     * @return
     */
    public List<String> getItemLore() {
        return itemLore;
    }
}
