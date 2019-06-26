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
 * Class that handles the purchase Gui for Aqua Wands
 */
public class BuyAquaWandsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuyAquaWandsGui() {
        super(FileManager.get("aqua_purchase_gui").getInt("aqua-wand-purchase-gui.size"),
                Chat.applyColor(FileManager.get("aqua_purchase_gui").getString("aqua-wand-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((FileManager.get("aqua_purchase_gui").getInt("aqua-wand-purchase-gui." + configItem + ".slot")),
                        new CraftItem(FileManager.get("aqua_purchase_gui").getString("aqua-wand-purchase-gui." + configItem + ".material"),
                                FileManager.get("aqua_purchase_gui").getString("aqua-wand-purchase-gui." + configItem + ".name"),
                                FileManager.get("aqua_purchase_gui").getStringList("aqua-wand-purchase-gui." + configItem + ".lore"),
                                FileManager.get("aqua_purchase_gui").getStringList("aqua-wand-purchase-gui." + configItem + ".enchantments")).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (FileManager.get("aqua_purchase_gui").getBoolean("aqua-wand-purchase-gui." + configItem + ".purchasable")) {
                                    new PurchaseTool(FileManager.get("aqua_purchase_gui").getInt("aqua-wand-purchase-gui." + configItem + ".price"),
                                            FileManager.get("aqua_purchase_gui").getString("aqua-wand-purchase-gui." + configItem + ".material"),
                                            FileManager.get("aqua").getString("aqua-wands." + configItem + ".name"),
                                            FileManager.get("aqua").getStringList("aqua-wands." + configItem + ".lore"),
                                            FileManager.get("aqua").getStringList("aqua-wands." + configItem + ".enchantments"), "aqua", configItem, player,
                                            "{mode}", ToolsAttributeManager.aquaWandModeUnique.get(configItem).get(1), "{radius}",
                                            ToolsAttributeManager.aquaWandRadiusUnique.get(configItem).get(FileManager.get("aqua").getInt("aqua-wands." + configItem + ".radius.starting")),
                                            "{uses}", FileManager.get("aqua").getString("aqua-wands." + configItem + ".uses.starting"));
                                }
                                if (FileManager.get("aqua_purchase_gui").getBoolean("aqua-wand-purchase-gui." + configItem + ".back-button")) {
                                    GuiManager.getGui("generic-buy").open(player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                toolNotFound.printStackTrace();
                                player.closeInventory();
                                new PlayerMessage("invalid-config", player, "{reason}", "Aqua Wand purchase-gui.yml");
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}
