package dev.nuer.nt.method.itemCreation;

import dev.nuer.nt.NTools;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Class that handles purchasing tools from a gui
 */
public class PurchaseTool {

    /**
     * Method to subtract the price from the players balance and give them the respective tool
     *
     * @param price             Double, price of the tool
     * @param material          String, the material of the tool
     * @param name              String, tool name
     * @param lore              List<String>, tool lore
     * @param modeReplacement   String, replacement for the mode placeholder
     * @param radiusReplacement String, replacement for the radius placeholder
     * @param enchantments      List<String>, tool enchantments
     * @param player            Player, who to give the tool to
     */
    public PurchaseTool(double price, String material, String name, List<String> lore, String modeReplacement,
                        String radiusReplacement, List<String> enchantments, String typeOfTool, int idFromConfig, Player player) {
        if (NTools.economy.getBalance(player) >= price) {
            player.closeInventory();
            NTools.economy.withdrawPlayer(player, price);
            new CraftItem(material, name, lore, modeReplacement, radiusReplacement, enchantments, typeOfTool, idFromConfig, player);
            new PlayerMessage("purchase", player, "{price}", NTools.numberFormat.format(price));
        } else {
            new PlayerMessage("insufficient", player);
        }
    }
}
