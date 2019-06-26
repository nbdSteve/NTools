package dev.nuer.tp.gui.purchase;

import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.managers.GuiManager;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.itemCreation.PurchaseTool;
import dev.nuer.tp.method.player.PlayerMessage;

/**
 * Class that handles the purchase Gui for Tray Tools
 */
public class BuyTrayToolsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuyTrayToolsGui() {
        super(FileManager.get("tray_purchase_gui").getInt("tray-tool-purchase-gui.size"),
                Chat.applyColor(FileManager.get("tray_purchase_gui").getString("tray-tool-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((FileManager.get("tray_purchase_gui").getInt("tray-tool-purchase-gui." + configItem + ".slot")),
                        new CraftItem(FileManager.get("tray_purchase_gui").getString("tray-tool-purchase-gui." + configItem + ".material"),
                                FileManager.get("tray_purchase_gui").getString("tray-tool-purchase-gui." + configItem + ".name"),
                                FileManager.get("tray_purchase_gui").getStringList("tray-tool-purchase-gui." + configItem + ".lore"),
                                FileManager.get("tray_purchase_gui").getStringList("tray-tool-purchase-gui." + configItem + ".enchantments")).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (FileManager.get("tray_purchase_gui").getBoolean("tray-tool-purchase-gui." + configItem + ".purchasable")) {
                                    new PurchaseTool(FileManager.get("tray_purchase_gui").getInt("tray-tool-purchase-gui." + configItem + ".price"),
                                            FileManager.get("tray_purchase_gui").getString("tray-tool-purchase-gui." + configItem + ".material"),
                                            FileManager.get("tray").getString("tray-tools." + configItem + ".name"),
                                            FileManager.get("tray").getStringList("tray-tools." + configItem + ".lore"),
                                            FileManager.get("tray").getStringList("tray-tools." + configItem + ".enchantments"), "tray", configItem, player,
                                            "debug", "debug", "debug", "debug",
                                            "{uses}", FileManager.get("tray").getString("tray-tools." + configItem + ".uses.starting"));
                                }
                                if (FileManager.get("tray_purchase_gui").getBoolean("tray-tool-purchase-gui." + configItem + ".back-button")) {
                                    GuiManager.getGui("generic-buy").open(player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                player.closeInventory();
                                new PlayerMessage("invalid-config", player, "{reason}", "Tray Tool purchase-gui.yml");
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}
