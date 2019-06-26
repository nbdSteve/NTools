package dev.nuer.tp.gui.purchase;

import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.managers.GuiManager;
import dev.nuer.tp.managers.ToolsAttributeManager;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.itemCreation.PurchaseTool;
import dev.nuer.tp.method.player.PlayerMessage;

/**
 * Class that handles the purchase Gui for Sell Wands
 */
public class BuySellWandsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuySellWandsGui() {
        super(FileManager.get("sell_purchase_gui").getInt("sell-wand-purchase-gui.size"),
                Chat.applyColor(FileManager.get("sell_purchase_gui").getString("sell-wand-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot(FileManager.get("sell_purchase_gui").getInt("sell-wand-purchase-gui." + configItem + ".slot"),
                        new CraftItem(FileManager.get("sell_purchase_gui").getString("sell-wand-purchase-gui." + configItem + ".material"),
                                FileManager.get("sell_purchase_gui").getString("sell-wand-purchase-gui." + configItem + ".name"),
                                FileManager.get("sell_purchase_gui").getStringList("sell-wand-purchase-gui." + configItem + ".lore"),
                                FileManager.get("sell_purchase_gui").getStringList("sell-wand-purchase-gui." + configItem + ".enchantments")).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (FileManager.get("sell_purchase_gui").getBoolean("sell-wand-purchase-gui." + configItem + ".purchasable")) {
                                    String[] modifierParts = ToolsAttributeManager.sellWandModifierUnique.get(configItem).get(FileManager.get("sell").getInt("sell-wands." + configItem + ".modifier.starting")).split("-");
                                    new PurchaseTool(FileManager.get("sell_purchase_gui").getInt("sell-wand-purchase-gui." + configItem + ".price"),
                                            FileManager.get("sell_purchase_gui").getString("sell-wand-purchase-gui." + configItem + ".material"),
                                            FileManager.get("sell").getString("sell-wands." + configItem + ".name"),
                                            FileManager.get("sell").getStringList("sell-wands." + configItem + ".lore"),
                                            FileManager.get("sell").getStringList("sell-wands." + configItem + ".enchantments"), "sell", configItem, player,
                                            "debug", "debug", "{modifier}", modifierParts[0],
                                            "{uses}", FileManager.get("sell").getString("sell-wands." + configItem + ".uses.starting"));
                                }
                                if (FileManager.get("sell_purchase_gui").getBoolean("sell-wand-purchase-gui." + configItem + ".back-button")) {
                                    GuiManager.getGui("generic-buy").open(player);
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
