package dev.nuer.tp.gui.purchase;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.itemCreation.PurchaseTool;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.ChatColor;

/**
 * Class that handles the purchase Gui for Lightning Wands
 */
public class BuyLightningWandsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuyLightningWandsGui() {
        super(ToolsPlus.getFiles().get("lightning_purchase_gui").getInt("lightning-wand-purchase-gui.size"),
                ChatColor.translateAlternateColorCodes('&', ToolsPlus.getFiles().get("lightning_purchase_gui").getString("lightning-wand-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot(ToolsPlus.getFiles().get("lightning_purchase_gui").getInt("lightning-wand-purchase-gui." + configItem + ".slot"),
                        new CraftItem(ToolsPlus.getFiles().get("lightning_purchase_gui").getString("lightning-wand-purchase-gui." + configItem + ".material"),
                                ToolsPlus.getFiles().get("lightning_purchase_gui").getString("lightning-wand-purchase-gui." + configItem + ".name"),
                                ToolsPlus.getFiles().get("lightning_purchase_gui").getStringList("lightning-wand-purchase-gui." + configItem + ".lore"),
                                ToolsPlus.getFiles().get("lightning_purchase_gui").getStringList("lightning-wand-purchase-gui." + configItem + ".enchantments"), "lightning", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (ToolsPlus.getFiles().get("lightning_purchase_gui").getBoolean("lightning-wand-purchase-gui." + configItem + ".purchasable")) {
                                    new PurchaseTool(ToolsPlus.getFiles().get("lightning_purchase_gui").getInt("lightning-wand-purchase-gui." + configItem + ".price"),
                                            ToolsPlus.getFiles().get("lightning_purchase_gui").getString("lightning-wand-purchase-gui." + configItem + ".material"),
                                            ToolsPlus.getFiles().get("lightning").getString("lightning-wands." + configItem + ".name"),
                                            ToolsPlus.getFiles().get("lightning").getStringList("lightning-wands." + configItem + ".lore"),
                                            ToolsPlus.getFiles().get("lightning").getStringList("lightning-wands." + configItem + ".enchantments"),
                                            "lightning", configItem, player, "debug", "debug", "debug", "debug",
                                            "{uses}", ToolsPlus.getFiles().get("lightning").getString("lightning-wands." + configItem + ".uses.starting"));
                                }
                                if (ToolsPlus.getFiles().get("lightning_purchase_gui").getBoolean("lightning-wand-purchase-gui." + configItem + ".back-button")) {
                                    ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("generic-buy").open(player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                player.closeInventory();
                                new PlayerMessage("invalid-config", player, "{reason}", "Lightning Wand purchase-gui.yml");
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}
