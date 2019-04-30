package dev.nuer.tp.gui.purchase;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.initialize.MapInitializer;
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
        super(ToolsPlus.getFiles().get("aqua_purchase_gui").getInt("aqua-wand-purchase-gui.size"),
                Chat.applyColor(ToolsPlus.getFiles().get("aqua_purchase_gui").getString("aqua-wand-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((ToolsPlus.getFiles().get("aqua_purchase_gui").getInt("aqua-wand-purchase-gui." + configItem + ".slot")),
                        new CraftItem(ToolsPlus.getFiles().get("aqua_purchase_gui").getString("aqua-wand-purchase-gui." + configItem + ".material"),
                                ToolsPlus.getFiles().get("aqua_purchase_gui").getString("aqua-wand-purchase-gui." + configItem + ".name"),
                                ToolsPlus.getFiles().get("aqua_purchase_gui").getStringList("aqua-wand-purchase-gui." + configItem + ".lore"),
                                ToolsPlus.getFiles().get("aqua_purchase_gui").getStringList("aqua-wand-purchase-gui." + configItem + ".enchantments"), "aqua", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (ToolsPlus.getFiles().get("aqua_purchase_gui").getBoolean("aqua-wand-purchase-gui." + configItem + ".purchasable")) {
                                    new PurchaseTool(ToolsPlus.getFiles().get("aqua_purchase_gui").getInt("aqua-wand-purchase-gui." + configItem + ".price"),
                                            ToolsPlus.getFiles().get("aqua_purchase_gui").getString("aqua-wand-purchase-gui." + configItem + ".material"),
                                            ToolsPlus.getFiles().get("aqua").getString("aqua-wands." + configItem + ".name"),
                                            ToolsPlus.getFiles().get("aqua").getStringList("aqua-wands." + configItem + ".lore"),
                                            ToolsPlus.getFiles().get("aqua").getStringList("aqua-wands." + configItem + ".enchantments"), "aqua", configItem, player,
                                            "{mode}", MapInitializer.aquaWandModeUnique.get(configItem).get(1), "{radius}",
                                            MapInitializer.aquaWandRadiusUnique.get(configItem).get(ToolsPlus.getFiles().get("aqua").getInt("aqua-wands." + configItem + ".radius.starting")),
                                            "{uses}", ToolsPlus.getFiles().get("aqua").getString("aqua-wands." + configItem + ".uses.starting"));
                                }
                                if (ToolsPlus.getFiles().get("aqua_purchase_gui").getBoolean("aqua-wand-purchase-gui." + configItem + ".back-button")) {
                                    ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("generic-buy").open(player);
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
