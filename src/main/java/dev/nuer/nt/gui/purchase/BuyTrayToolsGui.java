package dev.nuer.nt.gui.purchase;

import dev.nuer.nt.NTools;
import dev.nuer.nt.gui.AbstractGui;
import dev.nuer.nt.method.itemCreation.CraftItem;
import dev.nuer.nt.method.itemCreation.PurchaseTool;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.ChatColor;

public class BuyTrayToolsGui extends AbstractGui {

    /**
     * Constructor to create a the Gui
     */
    public BuyTrayToolsGui() {
        super(NTools.getFiles().get("config").getInt("gui.tray-buy.size"),
                ChatColor.translateAlternateColorCodes('&', NTools.getFiles().get("config").getString("gui.tray-buy.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((NTools.getFiles().get("config").getInt("gui.tray-buy." + configItem + ".slot")),
                        new CraftItem((NTools.getFiles().get("config").getString("gui.tray-buy." + configItem + ".material")),
                                (NTools.getFiles().get("config").getString("gui.tray-buy." + configItem + ".name")),
                                (NTools.getFiles().get("config").getStringList("gui.tray-buy." + configItem + ".lore")), null, null,
                                (NTools.getFiles().get("config").getStringList("gui.tray-buy." + configItem + ".enchantments")), null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (NTools.getFiles().get("config").getBoolean("gui.tray-buy." + configItem + ".purchasable")) {
                                    new PurchaseTool((NTools.getFiles().get("config").getInt("gui.tray-buy." + configItem + ".price")),
                                            (NTools.getFiles().get("config").getString("gui.tray-buy." + configItem + ".material")),
                                            (NTools.getFiles().get("tools").getString("tray." + configItem + ".name")),
                                            (NTools.getFiles().get("tools").getStringList("tray." + configItem + ".lore")), null, null,
                                            (NTools.getFiles().get("tools").getStringList("tray." + configItem + ".enchantments")), player);
                                }
                                if (NTools.getFiles().get("config").getBoolean("gui.tray-buy." + configItem + ".back-button")) {
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
