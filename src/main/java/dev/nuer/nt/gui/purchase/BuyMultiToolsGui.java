package dev.nuer.nt.gui.purchase;

import dev.nuer.nt.ToolsPlus;
import dev.nuer.nt.gui.AbstractGui;
import dev.nuer.nt.initialize.MapInitializer;
import dev.nuer.nt.method.itemCreation.CraftItem;
import dev.nuer.nt.method.itemCreation.PurchaseTool;
import dev.nuer.nt.method.player.PlayerMessage;
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
                ChatColor.translateAlternateColorCodes('&', ToolsPlus.getFiles().get("multi_purchase_gui").getString("multi-tool-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((ToolsPlus.getFiles().get("multi_purchase_gui").getInt("multi-tool-purchase-gui." + configItem + ".slot")),
                        new CraftItem(ToolsPlus.getFiles().get("multi_purchase_gui").getString("multi-tool-purchase-gui." + configItem + ".material"),
                                ToolsPlus.getFiles().get("multi_purchase_gui").getString("multi-tool-purchase-gui." + configItem + ".name"),
                                ToolsPlus.getFiles().get("multi_purchase_gui").getStringList("multi-tool-purchase-gui." + configItem + ".lore"), null, null,
                                ToolsPlus.getFiles().get("multi_purchase_gui").getStringList("multi-tool-purchase-gui." + configItem + ".enchantments"), "multi", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (ToolsPlus.getFiles().get("multi_purchase_gui").getBoolean("multi-tool-purchase-gui." + configItem + ".purchasable")) {
                                    new PurchaseTool(ToolsPlus.getFiles().get("multi_purchase_gui").getInt("multi-tool-purchase-gui." + configItem + ".price"),
                                            ToolsPlus.getFiles().get("multi_purchase_gui").getString("multi-tool-purchase-gui." + configItem + ".material"),
                                            ToolsPlus.getFiles().get("multi").getString("multi-tools." + configItem + ".name"),
                                            ToolsPlus.getFiles().get("multi").getStringList("multi-tools." + configItem + ".lore"),
                                            MapInitializer.multiToolRadiusUnique.get(configItem).get(ToolsPlus.getFiles().get("multi").getInt("multi-tools." + configItem + ".radius.starting")),
                                            MapInitializer.multiToolModeUnique.get(configItem).get(1),
                                            ToolsPlus.getFiles().get("multi").getStringList("multi-tools." + configItem + ".enchantments"), "multi", configItem, player);
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