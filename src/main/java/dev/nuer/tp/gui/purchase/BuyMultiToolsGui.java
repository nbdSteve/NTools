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
 * Class that handles the purchase Gui for Multi Tools
 */
public class BuyMultiToolsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuyMultiToolsGui() {
        super(FileManager.get("multi_purchase_gui").getInt("multi-tool-purchase-gui.size"),
                Chat.applyColor(FileManager.get("multi_purchase_gui").getString("multi-tool-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((FileManager.get("multi_purchase_gui").getInt("multi-tool-purchase-gui." + configItem + ".slot")),
                        new CraftItem(FileManager.get("multi_purchase_gui").getString("multi-tool-purchase-gui." + configItem + ".material"),
                                FileManager.get("multi_purchase_gui").getString("multi-tool-purchase-gui." + configItem + ".name"),
                                FileManager.get("multi_purchase_gui").getStringList("multi-tool-purchase-gui." + configItem + ".lore"),
                                FileManager.get("multi_purchase_gui").getStringList("multi-tool-purchase-gui." + configItem + ".enchantments")).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (FileManager.get("multi_purchase_gui").getBoolean("multi-tool-purchase-gui." + configItem + ".purchasable")) {
                                    new PurchaseTool(FileManager.get("multi_purchase_gui").getInt("multi-tool-purchase-gui." + configItem + ".price"),
                                            FileManager.get("multi_purchase_gui").getString("multi-tool-purchase-gui." + configItem + ".material"),
                                            FileManager.get("multi").getString("multi-tools." + configItem + ".name"),
                                            FileManager.get("multi").getStringList("multi-tools." + configItem + ".lore"),
                                            FileManager.get("multi").getStringList("multi-tools." + configItem + ".enchantments"), "multi", configItem, player,
                                            "{mode}", ToolsAttributeManager.multiToolModeUnique.get(configItem).get(1), "{radius}",
                                            ToolsAttributeManager.multiToolRadiusUnique.get(configItem).get(FileManager.get("multi").getInt("multi-tools." + configItem + ".radius.starting")),
                                            "{uses}", FileManager.get("multi").getString("multi-tools." + configItem + ".uses.starting"));
                                }
                                if (FileManager.get("multi_purchase_gui").getBoolean("multi-tool-purchase-gui." + configItem + ".back-button")) {
                                    GuiManager.getGui("generic-buy").open(player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                player.closeInventory();
                                toolNotFound.printStackTrace();
                                new PlayerMessage("invalid-config", player, "{reason}", "Multi Tool purchase-gui.yml");
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}