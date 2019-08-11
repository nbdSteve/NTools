package dev.nuer.tp.gui.purchase;

import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.managers.GuiManager;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.itemCreation.PurchaseTool;
import dev.nuer.tp.method.player.PlayerMessage;

/**
 * Class that handles the purchase Gui for Lightning Wands
 */
public class BuyLightningWandsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuyLightningWandsGui() {
        super(FileManager.get("lightning_purchase_gui").getInt("lightning-wand-purchase-gui.size"),
                Chat.applyColor(FileManager.get("lightning_purchase_gui").getString("lightning-wand-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot(FileManager.get("lightning_purchase_gui").getInt("lightning-wand-purchase-gui." + configItem + ".slot"),
                        new CraftItem(FileManager.get("lightning_purchase_gui").getString("lightning-wand-purchase-gui." + configItem + ".material"),
                                FileManager.get("lightning_purchase_gui").getString("lightning-wand-purchase-gui." + configItem + ".name"),
                                FileManager.get("lightning_purchase_gui").getStringList("lightning-wand-purchase-gui." + configItem + ".lore"),
                                FileManager.get("lightning_purchase_gui").getStringList("lightning-wand-purchase-gui." + configItem + ".enchantments")).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (FileManager.get("lightning_purchase_gui").getBoolean("lightning-wand-purchase-gui." + configItem + ".purchasable")) {
                                    if (FileManager.get("config").getBoolean("gui-permissions") && !player.hasPermission("tools+.gui.lightning." + configItem)) {
                                        new PlayerMessage("no-permission", player);
                                        return;
                                    }
                                    new PurchaseTool(FileManager.get("lightning_purchase_gui").getInt("lightning-wand-purchase-gui." + configItem + ".price"),
                                            FileManager.get("lightning_purchase_gui").getString("lightning-wand-purchase-gui." + configItem + ".material"),
                                            FileManager.get("lightning").getString("lightning-wands." + configItem + ".name"),
                                            FileManager.get("lightning").getStringList("lightning-wands." + configItem + ".lore"),
                                            FileManager.get("lightning").getStringList("lightning-wands." + configItem + ".enchantments"),
                                            "lightning", configItem, player, "debug", "debug", "debug", "debug",
                                            "{uses}", FileManager.get("lightning").getString("lightning-wands." + configItem + ".uses.starting"));
                                }
                                if (FileManager.get("lightning_purchase_gui").getBoolean("lightning-wand-purchase-gui." + configItem + ".back-button")) {
                                    GuiManager.getGui("generic-buy").open(player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                player.closeInventory();
                                new PlayerMessage("invalid-config", player, "{reason}", "Lightning Wand purchase-gui.yml");
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}
