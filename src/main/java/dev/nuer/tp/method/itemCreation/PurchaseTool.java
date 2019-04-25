package dev.nuer.tp.method.itemCreation;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Class that handles purchasing tools from a gui
 */
public class PurchaseTool {

    /**
     * Constructor to add an item to a player inventory and remove the price from their account
     *
     * @param price             Double, the price of the tool being bought
     * @param material          String, the item material
     * @param name              String, the items display name
     * @param lore              List<String>, list of strings to add as the items lore
     * @param modeReplacement   String, replacement for {mode} placeholder, can be null
     * @param radiusReplacement String, replacement for {radius} placeholder, can be null
     * @param enchantments      List<String>, list of enchantments to add to the item
     * @param typeOfTool        String, the type of tool being created
     * @param idFromConfig      Integer, the raw tool ID from the configuration files
     * @param player            Player, the player to give the new item to - can be null
     */
    public PurchaseTool(double price, String material, String name, List<String> lore, String modeReplacement,
                        String radiusReplacement, List<String> enchantments, String typeOfTool, int idFromConfig, Player player) {
        if (ToolsPlus.economy.getBalance(player) >= price) {
            player.closeInventory();
            ToolsPlus.economy.withdrawPlayer(player, price);
            new CraftItem(material, name, lore, modeReplacement, radiusReplacement, enchantments, typeOfTool, idFromConfig, player);
            new PlayerMessage("purchase", player, "{price}", ToolsPlus.numberFormat.format(price));
        } else {
            new PlayerMessage("insufficient", player);
        }
    }

    /**
     * Alternative Constructor, this is called for Sell Wands and Harvester Hoes because they use
     * price modifiers and the placeholder is different.
     *
     * @param price               Double, the price of the tool being bought
     * @param material            String, the item material
     * @param name                String, the items display name
     * @param lore                List<String>, list of strings to add as the items lore
     * @param modeReplacement     String, replacement for {mode} placeholder, can be null
     * @param modifierReplacement String, replacement for {modifier} placeholder, can be null
     * @param enchantments        List<String>, list of enchantments to add to the item
     * @param typeOfTool          String, the type of tool being created
     * @param idFromConfig        Integer, the raw tool ID from the configuration files
     * @param player              Player, the player to give the new item to - can be null
     * @param usePriceModifier    boolean, if the tool is using price modifiers
     */
    public PurchaseTool(double price, String material, String name, List<String> lore, String modeReplacement,
                        String modifierReplacement, List<String> enchantments, String typeOfTool,
                        int idFromConfig, Player player, boolean usePriceModifier) {
        if (ToolsPlus.economy.getBalance(player) >= price) {
            player.closeInventory();
            ToolsPlus.economy.withdrawPlayer(player, price);
            new CraftItem(material, name, lore, modeReplacement, modifierReplacement, enchantments, typeOfTool, idFromConfig, player, usePriceModifier);
            new PlayerMessage("purchase", player, "{price}", ToolsPlus.numberFormat.format(price));
        } else {
            new PlayerMessage("insufficient", player);
        }
    }
}
