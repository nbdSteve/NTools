package dev.nuer.tp.gui.purchase;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.itemCreation.PurchaseTool;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.ChatColor;

/**
 * Class that handles the purchase Gui for Trench Tools
 */
public class BuyTrenchToolsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuyTrenchToolsGui() {
        super(ToolsPlus.getFiles().get("trench_purchase_gui").getInt("trench-tool-purchase-gui.size"),
                ChatColor.translateAlternateColorCodes('&', ToolsPlus.getFiles().get("trench_purchase_gui").getString("trench-tool-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((ToolsPlus.getFiles().get("trench_purchase_gui").getInt("trench-tool-purchase-gui." + configItem + ".slot")),
                        new CraftItem(ToolsPlus.getFiles().get("trench_purchase_gui").getString("trench-tool-purchase-gui." + configItem + ".material"),
                                ToolsPlus.getFiles().get("trench_purchase_gui").getString("trench-tool-purchase-gui." + configItem + ".name"),
                                ToolsPlus.getFiles().get("trench_purchase_gui").getStringList("trench-tool-purchase-gui." + configItem + ".lore"), null, null,
                                ToolsPlus.getFiles().get("trench_purchase_gui").getStringList("trench-tool-purchase-gui." + configItem + ".enchantments"), "trench", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (ToolsPlus.getFiles().get("trench_purchase_gui").getBoolean("trench-tool-purchase-gui." + configItem + ".purchasable")) {
                                    new PurchaseTool(ToolsPlus.getFiles().get("trench_purchase_gui").getInt("trench-tool-purchase-gui." + configItem + ".price"),
                                            ToolsPlus.getFiles().get("trench_purchase_gui").getString("trench-tool-purchase-gui." + configItem + ".material"),
                                            ToolsPlus.getFiles().get("trench").getString("trench-tools." + configItem + ".name"),
                                            ToolsPlus.getFiles().get("trench").getStringList("trench-tools." + configItem + ".lore"), null, null,
                                            ToolsPlus.getFiles().get("trench").getStringList("trench-tools." + configItem + ".enchantments"), "trench", configItem, player);
                                }
                                if (ToolsPlus.getFiles().get("trench_purchase_gui").getBoolean("trench-tool-purchase-gui." + configItem + ".back-button")) {
                                    ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("generic-buy").open(player);
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
