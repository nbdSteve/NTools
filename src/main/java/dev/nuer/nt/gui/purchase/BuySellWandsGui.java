package dev.nuer.nt.gui.purchase;

import dev.nuer.nt.NTools;
import dev.nuer.nt.gui.AbstractGui;
import dev.nuer.nt.initialize.MapInitializer;
import dev.nuer.nt.method.itemCreation.CraftItem;
import dev.nuer.nt.method.itemCreation.PurchaseTool;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.ChatColor;

public class BuySellWandsGui extends AbstractGui {

    /**
     * Constructor the create a new Gui
     */
    public BuySellWandsGui() {
        super(NTools.getFiles().get("sell_purchase_gui").getInt("sell-wand-purchase-gui.size"),
                ChatColor.translateAlternateColorCodes('&', NTools.getFiles().get("sell_purchase_gui").getString("sell-wand-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((NTools.getFiles().get("sell_purchase_gui").getInt("sell-wand-purchase-gui." + configItem + ".slot")),
                        new CraftItem((NTools.getFiles().get("sell_purchase_gui").getString("sell-wand-purchase-gui." + configItem + ".material")),
                                (NTools.getFiles().get("sell_purchase_gui").getString("sell-wand-purchase-gui." + configItem + ".name")),
                                (NTools.getFiles().get("sell_purchase_gui").getStringList("sell-wand-purchase-gui." + configItem + ".lore")), null, null,
                                (NTools.getFiles().get("sell_purchase_gui").getStringList("sell-wand-purchase-gui." + configItem + ".enchantments")), "sell", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (NTools.getFiles().get("sell_purchase_gui").getBoolean("sell-wand-purchase-gui." + configItem + ".purchasable")) {
                                    String[] modifierParts = MapInitializer.sellWandModifierUnique.get(configItem).get(NTools.getFiles().get("sell").getInt("sell-wands." + configItem + ".modifier.starting")).split("-");
                                    new PurchaseTool((NTools.getFiles().get("sell_purchase_gui").getInt("sell-wand-purchase-gui." + configItem + ".price")),
                                            (NTools.getFiles().get("sell_purchase_gui").getString("sell-wand-purchase-gui." + configItem + ".material")),
                                            (NTools.getFiles().get("sell").getString("sell-wands." + configItem + ".name")),
                                            (NTools.getFiles().get("sell").getStringList("sell-wands." + configItem + ".lore")), "debug", (modifierParts[0]),
                                            (NTools.getFiles().get("sell").getStringList("sell-wands." + configItem + ".enchantments")), "sell", configItem, player, true);
                                }
                                if (NTools.getFiles().get("sell_purchase_gui").getBoolean("sell-wand-purchase-gui." + configItem + ".back-button")) {
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
