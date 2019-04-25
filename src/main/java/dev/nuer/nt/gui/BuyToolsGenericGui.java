package dev.nuer.nt.gui;

import dev.nuer.nt.ToolsPlus;
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
        super(ToolsPlus.getFiles().get("config").getInt("tool-purchase-gui.size"),
                ChatColor.translateAlternateColorCodes('&', ToolsPlus.getFiles().get("config").getString("tool-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot(ToolsPlus.getFiles().get("config").getInt("tool-purchase-gui." + configItem + ".slot"),
                        new CraftItem(ToolsPlus.getFiles().get("config").getString("tool-purchase-gui." + configItem + ".material"),
                                ToolsPlus.getFiles().get("config").getString("tool-purchase-gui." + configItem + ".name"),
                                ToolsPlus.getFiles().get("config").getStringList("tool-purchase-gui." + configItem + ".lore"), null, null,
                                ToolsPlus.getFiles().get("config").getStringList("tool-purchase-gui." + configItem + ".enchantments"), "null", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (ToolsPlus.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-multi")) {
                                    ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("multi-buy").open(player);
                                }
                                if (ToolsPlus.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-trench")) {
                                    ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("trench-buy").open(player);
                                }
                                if (ToolsPlus.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-tray")) {
                                    ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("tray-buy").open(player);
                                }
                                if (ToolsPlus.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-sand")) {
                                    ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("sand-buy").open(player);
                                }
                                if (ToolsPlus.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-lightning")) {
                                    ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("lightning-buy").open(player);
                                }
                                if (ToolsPlus.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-harvester")) {
                                    ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("harvester-buy").open(player);
                                }
                                if (ToolsPlus.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-sell")) {
                                    ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("sell-buy").open(player);
                                }
                                if (ToolsPlus.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-tnt")) {
                                    ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("tnt-buy").open(player);
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