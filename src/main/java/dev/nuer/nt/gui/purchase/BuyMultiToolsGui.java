package dev.nuer.nt.gui.purchase;

import dev.nuer.nt.NTools;
import dev.nuer.nt.gui.AbstractGui;
import dev.nuer.nt.method.itemCreation.CraftItem;
import dev.nuer.nt.method.itemCreation.PurchaseTool;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.ChatColor;

public class BuyMultiToolsGui extends AbstractGui {

    /**
     * Constructor to create a the Gui
     */
    public BuyMultiToolsGui() {
        super(NTools.getFiles().get("config").getInt("gui.multi-buy.size"),
                ChatColor.translateAlternateColorCodes('&', NTools.getFiles().get("config").getString("gui.multi-buy.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((NTools.getFiles().get("config").getInt("gui.multi-buy." + configItem + ".slot")),
                        new CraftItem((NTools.getFiles().get("config").getString("gui.multi-buy." + configItem + ".material")),
                                (NTools.getFiles().get("config").getString("gui.multi-buy." + configItem + ".name")),
                                (NTools.getFiles().get("config").getStringList("gui.multi-buy." + configItem + ".lore")), null, null,
                                (NTools.getFiles().get("config").getStringList("gui.multi-buy." + configItem + ".enchantments")), null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (NTools.getFiles().get("config").getBoolean("gui.multi-buy." + configItem + ".purchasable")) {
                                    new PurchaseTool((NTools.getFiles().get("config").getInt("gui.multi-buy." + configItem + ".price")),
                                            (NTools.getFiles().get("config").getString("gui.multi-buy." + configItem + ".material")),
                                            (NTools.getFiles().get("tools").getString("multi-tool." + configItem + ".name")),
                                            (NTools.getFiles().get("tools").getStringList("multi-tool." + configItem + ".lore")),
                                            (NTools.getMultiToolRadiusUnique().get(configItem).get(1)),
                                            (NTools.getMultiToolModeUnique().get(configItem).get(1)),
                                            (NTools.getFiles().get("tools").getStringList("multi-tool." + configItem + ".enchantments")), player);
                                }
                                if (NTools.getFiles().get("config").getBoolean("gui.multi-buy." + configItem + ".back-button")) {
                                    NTools.getPlugin(NTools.class).getBuyToolsGenericGui().open(player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                player.closeInventory();
                                new PlayerMessage("invalid-tool", player);
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}