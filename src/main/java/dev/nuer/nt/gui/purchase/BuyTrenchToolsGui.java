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
        super(NTools.getFiles().get("config").getInt("gui.trench-buy.size"),
                ChatColor.translateAlternateColorCodes('&', NTools.getFiles().get("config").getString("gui.trench-buy.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((NTools.getFiles().get("config").getInt("gui.trench-buy." + configItem + ".slot")),
                        new CraftItem((NTools.getFiles().get("config").getString("gui.trench-buy." + configItem + ".material")),
                                (NTools.getFiles().get("config").getString("gui.trench-buy." + configItem + ".name")),
                                (NTools.getFiles().get("config").getStringList("gui.trench-buy." + configItem + ".lore")), null, null,
                                (NTools.getFiles().get("config").getStringList("gui.trench-buy." + configItem + ".enchantments")), null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (NTools.getFiles().get("config").getBoolean("gui.trench-buy." + configItem + ".purchasable")) {
                                    new PurchaseTool((NTools.getFiles().get("config").getInt("gui.trench-buy." + configItem + ".price")),
                                            (NTools.getFiles().get("config").getString("gui.trench-buy." + configItem + ".material")),
                                            (NTools.getFiles().get("tools").getString("trench." + configItem + ".name")),
                                            (NTools.getFiles().get("tools").getStringList("trench." + configItem + ".lore")),
                                            (NTools.getMultiToolRadiusUnique().get(configItem).get(1)),
                                            (NTools.getMultiToolModeUnique().get(configItem).get(1)),
                                            (NTools.getFiles().get("tools").getStringList("trench." + configItem + ".enchantments")), player);
                                }
                                if (NTools.getFiles().get("config").getBoolean("gui.trench-buy." + configItem + ".back-button")) {
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
