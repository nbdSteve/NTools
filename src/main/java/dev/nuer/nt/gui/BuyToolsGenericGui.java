package dev.nuer.nt.gui;

import dev.nuer.nt.NTools;
import dev.nuer.nt.method.itemCreation.CraftItem;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.ChatColor;

public class BuyToolsGenericGui extends AbstractGui {

    /**
     * Constructor to create a the Gui
     */
    public BuyToolsGenericGui() {
        super(NTools.getFiles().get("config").getInt("gui.generic-buy.size"),
                ChatColor.translateAlternateColorCodes('&', NTools.getFiles().get("config").getString("gui.generic-buy.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((NTools.getFiles().get("config").getInt("gui.generic-buy." + configItem + ".slot")),
                        new CraftItem((NTools.getFiles().get("config").getString("gui.generic-buy." + configItem + ".material")),
                                (NTools.getFiles().get("config").getString("gui.generic-buy." + configItem + ".name")),
                                (NTools.getFiles().get("config").getStringList("gui.generic-buy." + configItem + ".lore")), null, null,
                                (NTools.getFiles().get("config").getStringList("gui.generic-buy." + configItem + ".enchantments")), null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (NTools.getFiles().get("config").getBoolean("gui.generic-buy." + configItem + ".open-multi")) {
                                    NTools.getPlugin(NTools.class).getBuyMultiToolsGui().open(player);
                                }
                                if (NTools.getFiles().get("config").getBoolean("gui.generic-buy." + configItem + ".open-trench")) {
                                    NTools.getPlugin(NTools.class).getBuyTrenchToolsGui().open(player);
                                }
                                if (NTools.getFiles().get("config").getBoolean("gui.generic-buy." + configItem + ".open-tray")) {
                                    NTools.getPlugin(NTools.class).getBuyTrayToolsGui().open(player);
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