package dev.nuer.nt.gui;

import dev.nuer.nt.NTools;
import dev.nuer.nt.method.itemCreation.CraftItem;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.ChatColor;

/**
 * Class that handles the standard gui opened with /tools
 */
public class BuyToolsGenericGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuyToolsGenericGui() {
        super(NTools.getFiles().get("config").getInt("tool-purchase-gui.size"),
                ChatColor.translateAlternateColorCodes('&', NTools.getFiles().get("config").getString("tool-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((NTools.getFiles().get("config").getInt("tool-purchase-gui." + configItem + ".slot")),
                        new CraftItem((NTools.getFiles().get("config").getString("tool-purchase-gui." + configItem + ".material")),
                                (NTools.getFiles().get("config").getString("tool-purchase-gui." + configItem + ".name")),
                                (NTools.getFiles().get("config").getStringList("tool-purchase-gui." + configItem + ".lore")), null, null,
                                (NTools.getFiles().get("config").getStringList("tool-purchase-gui." + configItem + ".enchantments")), "null", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (NTools.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-multi")) {
                                    NTools.getPlugin(NTools.class).getGuiByName("multi-buy").open(player);
                                }
                                if (NTools.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-trench")) {
                                    NTools.getPlugin(NTools.class).getGuiByName("trench-buy").open(player);
                                }
                                if (NTools.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-tray")) {
                                    NTools.getPlugin(NTools.class).getGuiByName("tray-buy").open(player);
                                }
                                if (NTools.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-sand")) {
                                    NTools.getPlugin(NTools.class).getGuiByName("sand-buy").open(player);
                                }
                                if (NTools.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-lightning")) {
                                    NTools.getPlugin(NTools.class).getGuiByName("lightning-buy").open(player);
                                }
                                if (NTools.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-harvester")) {
                                    NTools.getPlugin(NTools.class).getGuiByName("harvester-buy").open(player);
                                }
                                if (NTools.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-sell")) {
                                    NTools.getPlugin(NTools.class).getGuiByName("sell-buy").open(player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                player.closeInventory();
                                new PlayerMessage("invalid-config", player, "{reason}", "Generic purchase gui ntools.yml");
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}