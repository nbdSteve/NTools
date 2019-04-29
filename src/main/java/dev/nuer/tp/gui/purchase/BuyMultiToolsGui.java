package dev.nuer.tp.gui.purchase;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.initialize.MapInitializer;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.itemCreation.PurchaseTool;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.ChatColor;

/**
 * Class that handles the purchase Gui for Multi Tools
 */
public class BuyMultiToolsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuyMultiToolsGui() {
        super(ToolsPlus.getFiles().get("multi_purchase_gui").getInt("multi-tool-purchase-gui.size"),
                Chat.applyColor(ToolsPlus.getFiles().get("multi_purchase_gui").getString("multi-tool-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((ToolsPlus.getFiles().get("multi_purchase_gui").getInt("multi-tool-purchase-gui." + configItem + ".slot")),
                        new CraftItem(ToolsPlus.getFiles().get("multi_purchase_gui").getString("multi-tool-purchase-gui." + configItem + ".material"),
                                ToolsPlus.getFiles().get("multi_purchase_gui").getString("multi-tool-purchase-gui." + configItem + ".name"),
                                ToolsPlus.getFiles().get("multi_purchase_gui").getStringList("multi-tool-purchase-gui." + configItem + ".lore"),
                                ToolsPlus.getFiles().get("multi_purchase_gui").getStringList("multi-tool-purchase-gui." + configItem + ".enchantments"), "multi", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (ToolsPlus.getFiles().get("multi_purchase_gui").getBoolean("multi-tool-purchase-gui." + configItem + ".purchasable")) {
                                    new PurchaseTool(ToolsPlus.getFiles().get("multi_purchase_gui").getInt("multi-tool-purchase-gui." + configItem + ".price"),
                                            ToolsPlus.getFiles().get("multi_purchase_gui").getString("multi-tool-purchase-gui." + configItem + ".material"),
                                            ToolsPlus.getFiles().get("multi").getString("multi-tools." + configItem + ".name"),
                                            ToolsPlus.getFiles().get("multi").getStringList("multi-tools." + configItem + ".lore"),
                                            ToolsPlus.getFiles().get("multi").getStringList("multi-tools." + configItem + ".enchantments"), "multi", configItem, player,
                                            "{mode}", MapInitializer.multiToolModeUnique.get(configItem).get(1), "{radius}",
                                            MapInitializer.multiToolRadiusUnique.get(configItem).get(ToolsPlus.getFiles().get("multi").getInt("multi-tools." + configItem + ".radius.starting")),
                                            "debug", "debug");
                                }
                                if (ToolsPlus.getFiles().get("multi_purchase_gui").getBoolean("multi-tool-purchase-gui." + configItem + ".back-button")) {
                                    ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("generic-buy").open(player);
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