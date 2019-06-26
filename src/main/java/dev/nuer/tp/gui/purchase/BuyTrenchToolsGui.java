package dev.nuer.tp.gui.purchase;

import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.managers.GuiManager;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.itemCreation.PurchaseTool;
import dev.nuer.tp.method.player.PlayerMessage;

/**
 * Class that handles the purchase Gui for Trench Tools
 */
public class BuyTrenchToolsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuyTrenchToolsGui() {
        super(FileManager.get("trench_purchase_gui").getInt("trench-tool-purchase-gui.size"),
                Chat.applyColor(FileManager.get("trench_purchase_gui").getString("trench-tool-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((FileManager.get("trench_purchase_gui").getInt("trench-tool-purchase-gui." + configItem + ".slot")),
                        new CraftItem(FileManager.get("trench_purchase_gui").getString("trench-tool-purchase-gui." + configItem + ".material"),
                                FileManager.get("trench_purchase_gui").getString("trench-tool-purchase-gui." + configItem + ".name"),
                                FileManager.get("trench_purchase_gui").getStringList("trench-tool-purchase-gui." + configItem + ".lore"),
                                FileManager.get("trench_purchase_gui").getStringList("trench-tool-purchase-gui." + configItem + ".enchantments")).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (FileManager.get("trench_purchase_gui").getBoolean("trench-tool-purchase-gui." + configItem + ".purchasable")) {
                                    new PurchaseTool(FileManager.get("trench_purchase_gui").getInt("trench-tool-purchase-gui." + configItem + ".price"),
                                            FileManager.get("trench_purchase_gui").getString("trench-tool-purchase-gui." + configItem + ".material"),
                                            FileManager.get("trench").getString("trench-tools." + configItem + ".name"),
                                            FileManager.get("trench").getStringList("trench-tools." + configItem + ".lore"),
                                            FileManager.get("trench").getStringList("trench-tools." + configItem + ".enchantments"), "trench", configItem, player,
                                            "debug", "debug", "debug", "debug",
                                            "{uses}", FileManager.get("trench").getString("trench-tools." + configItem + ".uses.starting"));
                                }
                                if (FileManager.get("trench_purchase_gui").getBoolean("trench-tool-purchase-gui." + configItem + ".back-button")) {
                                    GuiManager.getGui("generic-buy").open(player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                player.closeInventory();
                                new PlayerMessage("invalid-config", player, "{reason}", "Trench Tool purchase-gui.yml");
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}
