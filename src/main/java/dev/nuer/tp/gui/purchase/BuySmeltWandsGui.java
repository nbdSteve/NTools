package dev.nuer.tp.gui.purchase;

import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.managers.GuiManager;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.itemCreation.PurchaseTool;
import dev.nuer.tp.method.player.PlayerMessage;

/**
 * Class that handles the purchase Gui for Smelt Wands
 */
public class BuySmeltWandsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuySmeltWandsGui() {
        super(FileManager.get("smelt_purchase_gui").getInt("smelt-wand-purchase-gui.size"),
                Chat.applyColor(FileManager.get("smelt_purchase_gui").getString("smelt-wand-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((FileManager.get("smelt_purchase_gui").getInt("smelt-wand-purchase-gui." + configItem + ".slot")),
                        new CraftItem(FileManager.get("smelt_purchase_gui").getString("smelt-wand-purchase-gui." + configItem + ".material"),
                                FileManager.get("smelt_purchase_gui").getString("smelt-wand-purchase-gui." + configItem + ".name"),
                                FileManager.get("smelt_purchase_gui").getStringList("smelt-wand-purchase-gui." + configItem + ".lore"),
                                FileManager.get("smelt_purchase_gui").getStringList("smelt-wand-purchase-gui." + configItem + ".enchantments")).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (FileManager.get("smelt_purchase_gui").getBoolean("smelt-wand-purchase-gui." + configItem + ".purchasable")) {
                                    if (FileManager.get("config").getBoolean("gui-permissions") && !player.hasPermission("tools+.gui.smelt." + configItem)) {
                                        new PlayerMessage("no-permission", player);
                                        return;
                                    }
                                    new PurchaseTool(FileManager.get("smelt_purchase_gui").getInt("smelt-wand-purchase-gui." + configItem + ".price"),
                                            FileManager.get("smelt_purchase_gui").getString("smelt-wand-purchase-gui." + configItem + ".material"),
                                            FileManager.get("smelt").getString("smelt-wands." + configItem + ".name"),
                                            FileManager.get("smelt").getStringList("smelt-wands." + configItem + ".lore"),
                                            FileManager.get("smelt").getStringList("smelt-wands." + configItem + ".enchantments"), "smelt", configItem, player,
                                            "debug", "debug", "debug", "debug",
                                            "{uses}", FileManager.get("smelt").getString("smelt-wands." + configItem + ".uses.starting"));
                                }
                                if (FileManager.get("smelt_purchase_gui").getBoolean("smelt-wand-purchase-gui." + configItem + ".back-button")) {
                                    GuiManager.getGui("generic-buy").open(player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                player.closeInventory();
                                new PlayerMessage("invalid-config", player, "{reason}", "Smelt Wand purchase-gui.yml");
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}