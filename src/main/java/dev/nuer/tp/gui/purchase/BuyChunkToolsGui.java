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
 * Class that handles the purchase Gui for Chunk Tools
 */
public class BuyChunkToolsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuyChunkToolsGui() {
        super(FileManager.get("chunk_purchase_gui").getInt("chunk-tool-purchase-gui.size"),
                Chat.applyColor(FileManager.get("chunk_purchase_gui").getString("chunk-tool-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((FileManager.get("chunk_purchase_gui").getInt("chunk-tool-purchase-gui." + configItem + ".slot")),
                        new CraftItem(FileManager.get("chunk_purchase_gui").getString("chunk-tool-purchase-gui." + configItem + ".material"),
                                FileManager.get("chunk_purchase_gui").getString("chunk-tool-purchase-gui." + configItem + ".name"),
                                FileManager.get("chunk_purchase_gui").getStringList("chunk-tool-purchase-gui." + configItem + ".lore"),
                                FileManager.get("chunk_purchase_gui").getStringList("chunk-tool-purchase-gui." + configItem + ".enchantments"), "chunk", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (FileManager.get("chunk_purchase_gui").getBoolean("chunk-tool-purchase-gui." + configItem + ".purchasable")) {
                                    new PurchaseTool(FileManager.get("chunk_purchase_gui").getInt("chunk-tool-purchase-gui." + configItem + ".price"),
                                            FileManager.get("chunk_purchase_gui").getString("chunk-tool-purchase-gui." + configItem + ".material"),
                                            FileManager.get("chunk").getString("chunk-tools." + configItem + ".name"),
                                            FileManager.get("chunk").getStringList("chunk-tools." + configItem + ".lore"),
                                            FileManager.get("chunk").getStringList("chunk-tools." + configItem + ".enchantments"), "chunk", configItem, player,
                                            "debug", "debug", "{radius}",
                                            ToolsAttributeManager.chunkToolRadiusUnique.get(configItem).get(FileManager.get("chunk").getInt("chunk-tools." + configItem + ".radius.starting")),
                                            "debug", "debug");
                                }
                                if (FileManager.get("chunk_purchase_gui").getBoolean("chunk-tool-purchase-gui." + configItem + ".back-button")) {
                                    GuiManager.getGui("generic-buy").open(player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                toolNotFound.printStackTrace();
                                player.closeInventory();
                                new PlayerMessage("invalid-config", player, "{reason}", "Chunk Tool purchase-gui.yml");
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}