package dev.nuer.tp.gui.purchase;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.initialize.MapInitializer;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.itemCreation.PurchaseTool;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.ChatColor;

/**
 * Class that handles the purchase Gui for Sell Wands
 */
public class BuySellWandsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuySellWandsGui() {
        super(ToolsPlus.getFiles().get("sell_purchase_gui").getInt("sell-wand-purchase-gui.size"),
                ChatColor.translateAlternateColorCodes('&', ToolsPlus.getFiles().get("sell_purchase_gui").getString("sell-wand-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot(ToolsPlus.getFiles().get("sell_purchase_gui").getInt("sell-wand-purchase-gui." + configItem + ".slot"),
                        new CraftItem(ToolsPlus.getFiles().get("sell_purchase_gui").getString("sell-wand-purchase-gui." + configItem + ".material"),
                                ToolsPlus.getFiles().get("sell_purchase_gui").getString("sell-wand-purchase-gui." + configItem + ".name"),
                                ToolsPlus.getFiles().get("sell_purchase_gui").getStringList("sell-wand-purchase-gui." + configItem + ".lore"), null, null,
                                ToolsPlus.getFiles().get("sell_purchase_gui").getStringList("sell-wand-purchase-gui." + configItem + ".enchantments"), "sell", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (ToolsPlus.getFiles().get("sell_purchase_gui").getBoolean("sell-wand-purchase-gui." + configItem + ".purchasable")) {
                                    String[] modifierParts = MapInitializer.sellWandModifierUnique.get(configItem).get(ToolsPlus.getFiles().get("sell").getInt("sell-wands." + configItem + ".modifier.starting")).split("-");
                                    new PurchaseTool(ToolsPlus.getFiles().get("sell_purchase_gui").getInt("sell-wand-purchase-gui." + configItem + ".price"),
                                            ToolsPlus.getFiles().get("sell_purchase_gui").getString("sell-wand-purchase-gui." + configItem + ".material"),
                                            ToolsPlus.getFiles().get("sell").getString("sell-wands." + configItem + ".name"),
                                            ToolsPlus.getFiles().get("sell").getStringList("sell-wands." + configItem + ".lore"), "debug", modifierParts[0],
                                            ToolsPlus.getFiles().get("sell").getStringList("sell-wands." + configItem + ".enchantments"), "sell", configItem, player, true);
                                }
                                if (ToolsPlus.getFiles().get("sell_purchase_gui").getBoolean("sell-wand-purchase-gui." + configItem + ".back-button")) {
                                    ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("generic-buy").open(player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                player.closeInventory();
                                new PlayerMessage("invalid-config", player, "{reason}", "Sell Wand purchase-gui.yml");
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}
