package dev.nuer.nt.gui.purchase;

import dev.nuer.nt.NTools;
import dev.nuer.nt.gui.AbstractGui;
import dev.nuer.nt.method.itemCreation.CraftItem;
import dev.nuer.nt.method.itemCreation.PurchaseTool;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.ChatColor;

public class BuySandWandsGui extends AbstractGui {

    /**
     * Constructor the create a new Gui
     */
    public BuySandWandsGui() {
        super(NTools.getFiles().get("sand_purchase_gui").getInt("sand-wand-purchase-gui.size"),
                ChatColor.translateAlternateColorCodes('&', NTools.getFiles().get("sand_purchase_gui").getString("sand-wand-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((NTools.getFiles().get("sand_purchase_gui").getInt("sand-wand-purchase-gui." + configItem + ".slot")),
                        new CraftItem((NTools.getFiles().get("sand_purchase_gui").getString("sand-wand-purchase-gui." + configItem + ".material")),
                                (NTools.getFiles().get("sand_purchase_gui").getString("sand-wand-purchase-gui." + configItem + ".name")),
                                (NTools.getFiles().get("sand_purchase_gui").getStringList("sand-wand-purchase-gui." + configItem + ".lore")), null, null,
                                (NTools.getFiles().get("sand_purchase_gui").getStringList("sand-wand-purchase-gui." + configItem + ".enchantments")), null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (NTools.getFiles().get("sand_purchase_gui").getBoolean("sand-wand-purchase-gui." + configItem + ".purchasable")) {
                                    new PurchaseTool((NTools.getFiles().get("sand_purchase_gui").getInt("sand-wand-purchase-gui." + configItem + ".price")),
                                            (NTools.getFiles().get("sand_purchase_gui").getString("sand-wand-purchase-gui." + configItem + ".material")),
                                            (NTools.getFiles().get("sand").getString("sand-wands." + configItem + ".name")),
                                            (NTools.getFiles().get("sand").getStringList("sand-wands." + configItem + ".lore")), null, null,
                                            (NTools.getFiles().get("sand").getStringList("sand-wands." + configItem + ".enchantments")), player);
                                }
                                if (NTools.getFiles().get("sand_purchase_gui").getBoolean("sand-wand-purchase-gui." + configItem + ".back-button")) {
                                    NTools.getPlugin(NTools.class).getBuyToolsGenericGui().open(player);
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
