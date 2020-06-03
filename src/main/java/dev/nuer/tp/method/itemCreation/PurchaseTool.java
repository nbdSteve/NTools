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
     * Alternative Constructor, this is called for Sell Wands and Harvester Hoes because they use
     * price modifiers and the placeholder is different.
     *
     * @param price               Double, the price of the tool being bought
     * @param material            String, the item material
     * @param name                String, the items display name
     * @param lore                List<String>, list of strings to add as the items lore
     * @param enchantments        List<String>, list of enchantments to add to the item
     * @param typeOfTool          String, the type of tool being created
     * @param idFromConfig        Integer, the raw tool ID from the configuration files
     * @param idFromConfig        Integer, the raw tool ID from the configuration files
     * @param player              Player, the player to give the new item to - can be null
     * @param modePlaceholder     String, placeholder for the tools mode
     * @param modeReplacement     String, replacement for the mode placeholder
     * @param modifierPlaceholder String, placeholder for the tools modifier
     * @param modifierReplacement String, replacement for the modifier placeholder
     * @param usesPlaceholder     String, placeholder for the tools uses
     * @param usesReplacement     String, replacement for the uses placeholder
     */
    public PurchaseTool(double price, String material, String name, List<String> lore, List<String> enchantments, String typeOfTool,
                        int idFromConfig, Player player, String modePlaceholder, String modeReplacement, String modifierPlaceholder,
                        String modifierReplacement, String usesPlaceholder, String usesReplacement) {
        if (ToolsPlus.economy.getBalance(player) >= price) {
            player.closeInventory();
            ToolsPlus.economy.withdrawPlayer(player, price);
            new CraftItem(material, name, lore, enchantments, typeOfTool, idFromConfig, player, modePlaceholder,
                    modeReplacement, modifierPlaceholder, modifierReplacement, usesPlaceholder, usesReplacement);
            new PlayerMessage("purchase", player, "{price}", ToolsPlus.numberFormat.format(price));
        } else {
            new PlayerMessage("insufficient", player);
        }
    }
}
