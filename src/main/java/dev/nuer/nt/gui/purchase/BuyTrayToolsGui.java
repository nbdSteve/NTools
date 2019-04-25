package dev.nuer.nt.gui.purchase;

import dev.nuer.nt.ToolsPlus;
import dev.nuer.nt.gui.AbstractGui;
import dev.nuer.nt.method.itemCreation.CraftItem;
import dev.nuer.nt.method.itemCreation.PurchaseTool;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.ChatColor;

/**
 * Class that handles the purchase Gui for Tray Tools
 */
public class BuyTrayToolsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuyTrayToolsGui() {
        super(ToolsPlus.getFiles().get("tray_purchase_gui").getInt("tray-tool-purchase-gui.size"),
                ChatColor.translateAlternateColorCodes('&', ToolsPlus.getFiles().get("tray_purchase_gui").getString("tray-tool-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((ToolsPlus.getFiles().get("tray_purchase_gui").getInt("tray-tool-purchase-gui." + configItem + ".slot")),
                        new CraftItem(ToolsPlus.getFiles().get("tray_purchase_gui").getString("tray-tool-purchase-gui." + configItem + ".material"),
                                ToolsPlus.getFiles().get("tray_purchase_gui").getString("tray-tool-purchase-gui." + configItem + ".name"),
                                ToolsPlus.getFiles().get("tray_purchase_gui").getStringList("tray-tool-purchase-gui." + configItem + ".lore"), null, null,
                                ToolsPlus.getFiles().get("tray_purchase_gui").getStringList("tray-tool-purchase-gui." + configItem + ".enchantments"), "tray", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (ToolsPlus.getFiles().get("tray_purchase_gui").getBoolean("tray-tool-purchase-gui." + configItem + ".purchasable")) {
                                    new PurchaseTool(ToolsPlus.getFiles().get("tray_purchase_gui").getInt("tray-tool-purchase-gui." + configItem + ".price"),
                                            ToolsPlus.getFiles().get("tray_purchase_gui").getString("tray-tool-purchase-gui." + configItem + ".material"),
                                            ToolsPlus.getFiles().get("tray").getString("tray-tools." + configItem + ".name"),
                                            ToolsPlus.getFiles().get("tray").getStringList("tray-tools." + configItem + ".lore"), null, null,
                                            ToolsPlus.getFiles().get("tray").getStringList("tray-tools." + configItem + ".enchantments"), "tray", configItem, player);
                                }
                                if (ToolsPlus.getFiles().get("tray_purchase_gui").getBoolean("tray-tool-purchase-gui." + configItem + ".back-button")) {
                                    ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("generic-buy").open(player);
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
