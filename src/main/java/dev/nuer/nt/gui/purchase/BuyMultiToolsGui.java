package dev.nuer.nt.gui.purchase;

import dev.nuer.nt.NTools;
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
        super(NTools.getFiles().get("multi_purchase_gui").getInt("multi-tool-purchase-gui.size"),
                ChatColor.translateAlternateColorCodes('&', NTools.getFiles().get("multi_purchase_gui").getString("multi-tool-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((NTools.getFiles().get("multi_purchase_gui").getInt("multi-tool-purchase-gui." + configItem + ".slot")),
                        new CraftItem((NTools.getFiles().get("multi_purchase_gui").getString("multi-tool-purchase-gui." + configItem + ".material")),
                                (NTools.getFiles().get("multi_purchase_gui").getString("multi-tool-purchase-gui." + configItem + ".name")),
                                (NTools.getFiles().get("multi_purchase_gui").getStringList("multi-tool-purchase-gui." + configItem + ".lore")), null, null,
                                (NTools.getFiles().get("multi_purchase_gui").getStringList("multi-tool-purchase-gui." + configItem + ".enchantments")), "multi", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (NTools.getFiles().get("multi_purchase_gui").getBoolean("multi-tool-purchase-gui." + configItem + ".purchasable")) {
                                    new PurchaseTool((NTools.getFiles().get("multi_purchase_gui").getInt("multi-tool-purchase-gui." + configItem + ".price")),
                                            (NTools.getFiles().get("multi_purchase_gui").getString("multi-tool-purchase-gui." + configItem + ".material")),
                                            (NTools.getFiles().get("multi").getString("multi-tools." + configItem + ".name")),
                                            (NTools.getFiles().get("multi").getStringList("multi-tools." + configItem + ".lore")),
                                            (MapInitializer.multiToolRadiusUnique.get(configItem).get(NTools.getFiles().get("multi").getInt("multi-tools." + configItem + ".radius.starting"))),
                                            (MapInitializer.multiToolModeUnique.get(configItem).get(1)),
                                            (NTools.getFiles().get("multi").getStringList("multi-tools." + configItem + ".enchantments")), "multi", configItem, player);
                                }
                                if (NTools.getFiles().get("multi_purchase_gui").getBoolean("multi-tool-purchase-gui." + configItem + ".back-button")) {
                                    NTools.getPlugin(NTools.class).getGuiByName("generic-buy").open(player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                player.closeInventory();
                                toolNotFound.printStackTrace();
                                new PlayerMessage("invalid-config", player);
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}