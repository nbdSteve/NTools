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
 * Class that handles the purchase Gui for Harvester Hoes
 */
public class BuyHarvesterToolsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuyHarvesterToolsGui() {
        super(FileManager.get("harvester_purchase_gui").getInt("harvester-tool-purchase-gui.size"),
                Chat.applyColor(FileManager.get("harvester_purchase_gui").getString("harvester-tool-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot(FileManager.get("harvester_purchase_gui").getInt("harvester-tool-purchase-gui." + configItem + ".slot"),
                        new CraftItem(FileManager.get("harvester_purchase_gui").getString("harvester-tool-purchase-gui." + configItem + ".material"),
                                FileManager.get("harvester_purchase_gui").getString("harvester-tool-purchase-gui." + configItem + ".name"),
                                FileManager.get("harvester_purchase_gui").getStringList("harvester-tool-purchase-gui." + configItem + ".lore"),
                                FileManager.get("harvester_purchase_gui").getStringList("harvester-tool-purchase-gui." + configItem + ".enchantments")).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (FileManager.get("harvester_purchase_gui").getBoolean("harvester-tool-purchase-gui." + configItem + ".purchasable")) {
                                    String[] modifierParts = ToolsAttributeManager.harvesterModifierUnique.get(configItem).get(FileManager.get("harvester").getInt("harvester-tools." + configItem + ".modifier.starting")).split("-");
                                    new PurchaseTool(FileManager.get("harvester_purchase_gui").getInt("harvester-tool-purchase-gui." + configItem + ".price"),
                                            FileManager.get("harvester_purchase_gui").getString("harvester-tool-purchase-gui." + configItem + ".material"),
                                            FileManager.get("harvester").getString("harvester-tools." + configItem + ".name"),
                                            FileManager.get("harvester").getStringList("harvester-tools." + configItem + ".lore"),
                                            FileManager.get("harvester").getStringList("harvester-tools." + configItem + ".enchantments"), "harvester", configItem, player,
                                            "{mode}", ToolsAttributeManager.harvesterModeUnique.get(configItem).get(1),
                                            "{modifier}", modifierParts[0],
                                            "{uses}", FileManager.get("harvester").getString("harvester-tools." + configItem + ".uses.starting"));
                                }
                                if (FileManager.get("harvester_purchase_gui").getBoolean("harvester-tool-purchase-gui." + configItem + ".back-button")) {
                                    GuiManager.getGui("generic-buy").open(player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                player.closeInventory();
                                new PlayerMessage("invalid-config", player, "{reason}", "Harvester Hoe purchase-gui.yml");
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}