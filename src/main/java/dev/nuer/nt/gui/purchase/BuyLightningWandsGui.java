package dev.nuer.nt.gui.purchase;

import dev.nuer.nt.NTools;
import dev.nuer.nt.gui.AbstractGui;
import dev.nuer.nt.method.itemCreation.CraftItem;
import dev.nuer.nt.method.itemCreation.PurchaseTool;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.ChatColor;

/**
 * Class that handles the purchase Gui for Lightning Wands
 */
public class BuyLightningWandsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuyLightningWandsGui() {
        super(NTools.getFiles().get("lightning_purchase_gui").getInt("lightning-wand-purchase-gui.size"),
                ChatColor.translateAlternateColorCodes('&', NTools.getFiles().get("lightning_purchase_gui").getString("lightning-wand-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((NTools.getFiles().get("lightning_purchase_gui").getInt("lightning-wand-purchase-gui." + configItem + ".slot")),
                        new CraftItem((NTools.getFiles().get("lightning_purchase_gui").getString("lightning-wand-purchase-gui." + configItem + ".material")),
                                (NTools.getFiles().get("lightning_purchase_gui").getString("lightning-wand-purchase-gui." + configItem + ".name")),
                                (NTools.getFiles().get("lightning_purchase_gui").getStringList("lightning-wand-purchase-gui." + configItem + ".lore")), null, null,
                                (NTools.getFiles().get("lightning_purchase_gui").getStringList("lightning-wand-purchase-gui." + configItem + ".enchantments")), "lightning", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (NTools.getFiles().get("lightning_purchase_gui").getBoolean("lightning-wand-purchase-gui." + configItem + ".purchasable")) {
                                    new PurchaseTool((NTools.getFiles().get("lightning_purchase_gui").getInt("lightning-wand-purchase-gui." + configItem + ".price")),
                                            (NTools.getFiles().get("lightning_purchase_gui").getString("lightning-wand-purchase-gui." + configItem + ".material")),
                                            (NTools.getFiles().get("lightning").getString("lightning-wands." + configItem + ".name")),
                                            (NTools.getFiles().get("lightning").getStringList("lightning-wands." + configItem + ".lore")), null, null,
                                            (NTools.getFiles().get("lightning").getStringList("lightning-wands." + configItem + ".enchantments")), "lightning", configItem, player);
                                }
                                if (NTools.getFiles().get("lightning_purchase_gui").getBoolean("lightning-wand-purchase-gui." + configItem + ".back-button")) {
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
