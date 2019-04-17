package dev.nuer.nt.gui.purchase;

import dev.nuer.nt.NTools;
import dev.nuer.nt.gui.AbstractGui;
import dev.nuer.nt.method.itemCreation.CraftItem;
import dev.nuer.nt.method.itemCreation.PurchaseTool;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.ChatColor;

public class BuyTrenchToolsGui extends AbstractGui {

    /**
     * Constructor to create a the Gui
     */
    public BuyTrenchToolsGui() {
        super(NTools.getFiles().get("trench_purchase_gui").getInt("trench-tool-purchase-gui.size"),
                ChatColor.translateAlternateColorCodes('&', NTools.getFiles().get("trench_purchase_gui").getString("trench-tool-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((NTools.getFiles().get("trench_purchase_gui").getInt("trench-tool-purchase-gui." + configItem + ".slot")),
                        new CraftItem((NTools.getFiles().get("trench_purchase_gui").getString("trench-tool-purchase-gui." + configItem + ".material")),
                                (NTools.getFiles().get("trench_purchase_gui").getString("trench-tool-purchase-gui." + configItem + ".name")),
                                (NTools.getFiles().get("trench_purchase_gui").getStringList("trench-tool-purchase-gui." + configItem + ".lore")), null, null,
                                (NTools.getFiles().get("trench_purchase_gui").getStringList("trench-tool-purchase-gui." + configItem + ".enchantments")), "trench", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (NTools.getFiles().get("trench_purchase_gui").getBoolean("trench-tool-purchase-gui." + configItem + ".purchasable")) {
                                    new PurchaseTool((NTools.getFiles().get("trench_purchase_gui").getInt("trench-tool-purchase-gui." + configItem + ".price")),
                                            (NTools.getFiles().get("trench_purchase_gui").getString("trench-tool-purchase-gui." + configItem + ".material")),
                                            (NTools.getFiles().get("trench").getString("trench-tools." + configItem + ".name")),
                                            (NTools.getFiles().get("trench").getStringList("trench-tools." + configItem + ".lore")), null, null,
                                            (NTools.getFiles().get("trench").getStringList("trench-tools." + configItem + ".enchantments")), "trench", configItem, player);
                                }
                                if (NTools.getFiles().get("trench_purchase_gui").getBoolean("trench-tool-purchase-gui." + configItem + ".back-button")) {
                                    NTools.getPlugin(NTools.class).getGuiByName("generic-buy").open(player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                player.closeInventory();
                                new PlayerMessage("invalid-config", player);
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}
