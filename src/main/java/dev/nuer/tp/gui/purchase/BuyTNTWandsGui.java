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
 * Class that handles the purchase Gui for TNT Wands
 */
public class BuyTNTWandsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuyTNTWandsGui() {
        super(FileManager.get("tnt_purchase_gui").getInt("tnt-wand-purchase-gui.size"),
                Chat.applyColor(FileManager.get("tnt_purchase_gui").getString("tnt-wand-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((FileManager.get("tnt_purchase_gui").getInt("tnt-wand-purchase-gui." + configItem + ".slot")),
                        new CraftItem(FileManager.get("tnt_purchase_gui").getString("tnt-wand-purchase-gui." + configItem + ".material"),
                                FileManager.get("tnt_purchase_gui").getString("tnt-wand-purchase-gui." + configItem + ".name"),
                                FileManager.get("tnt_purchase_gui").getStringList("tnt-wand-purchase-gui." + configItem + ".lore"),
                                FileManager.get("tnt_purchase_gui").getStringList("tnt-wand-purchase-gui." + configItem + ".enchantments"), "tnt", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (FileManager.get("tnt_purchase_gui").getBoolean("tnt-wand-purchase-gui." + configItem + ".purchasable")) {
                                    String[] modifierParts = ToolsAttributeManager.tntWandModifierUnique.get(configItem).get(FileManager.get("tnt").getInt("tnt-wands." + configItem + ".modifier.starting")).split("-");
                                    new PurchaseTool(FileManager.get("tnt_purchase_gui").getInt("tnt-wand-purchase-gui." + configItem + ".price"),
                                            FileManager.get("tnt_purchase_gui").getString("tnt-wand-purchase-gui." + configItem + ".material"),
                                            FileManager.get("tnt").getString("tnt-wands." + configItem + ".name"),
                                            FileManager.get("tnt").getStringList("tnt-wands." + configItem + ".lore"),
                                            FileManager.get("tnt").getStringList("tnt-wands." + configItem + ".enchantments"), "tnt", configItem, player,
                                            "{mode}", ToolsAttributeManager.tntWandModeUnique.get(configItem).get(1), "{modifier}", modifierParts[0],
                                            "{uses}", FileManager.get("tnt").getString("tnt-wands." + configItem + ".uses.starting"));
                                }
                                if (FileManager.get("tnt_purchase_gui").getBoolean("tnt-wand-purchase-gui." + configItem + ".back-button")) {
                                    GuiManager.getGui("generic-buy").open(player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                toolNotFound.printStackTrace();
                                player.closeInventory();
                                new PlayerMessage("invalid-config", player, "{reason}", "TNT Wand purchase-gui.yml");
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}