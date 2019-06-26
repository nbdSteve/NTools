package dev.nuer.tp.gui.purchase;

import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.managers.GuiManager;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.itemCreation.PurchaseTool;
import dev.nuer.tp.method.player.PlayerMessage;

/**
 * Class that handles the purchase Gui for Sand Wands
 */
public class BuySandWandsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuySandWandsGui() {
        super(FileManager.get("sand_purchase_gui").getInt("sand-wand-purchase-gui.size"),
                Chat.applyColor(FileManager.get("sand_purchase_gui").getString("sand-wand-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((FileManager.get("sand_purchase_gui").getInt("sand-wand-purchase-gui." + configItem + ".slot")),
                        new CraftItem(FileManager.get("sand_purchase_gui").getString("sand-wand-purchase-gui." + configItem + ".material"),
                                FileManager.get("sand_purchase_gui").getString("sand-wand-purchase-gui." + configItem + ".name"),
                                FileManager.get("sand_purchase_gui").getStringList("sand-wand-purchase-gui." + configItem + ".lore"),
                                FileManager.get("sand_purchase_gui").getStringList("sand-wand-purchase-gui." + configItem + ".enchantments")).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (FileManager.get("sand_purchase_gui").getBoolean("sand-wand-purchase-gui." + configItem + ".purchasable")) {
                                    new PurchaseTool(FileManager.get("sand_purchase_gui").getInt("sand-wand-purchase-gui." + configItem + ".price"),
                                            FileManager.get("sand_purchase_gui").getString("sand-wand-purchase-gui." + configItem + ".material"),
                                            FileManager.get("sand").getString("sand-wands." + configItem + ".name"),
                                            FileManager.get("sand").getStringList("sand-wands." + configItem + ".lore"),
                                            FileManager.get("sand").getStringList("sand-wands." + configItem + ".enchantments"), "sand", configItem, player,
                                            "debug", "debug", "debug", "debug",
                                            "{uses}", FileManager.get("sand").getString("sand-wands." + configItem + ".uses.starting"));
                                }
                                if (FileManager.get("sand_purchase_gui").getBoolean("sand-wand-purchase-gui." + configItem + ".back-button")) {
                                    GuiManager.getGui("generic-buy").open(player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                player.closeInventory();
                                new PlayerMessage("invalid-config", player, "{reason}", "Sand Wand purchase-gui.yml");
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}