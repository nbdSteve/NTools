package dev.nuer.tp.gui;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.player.PlayerMessage;
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
                Chat.applyColor(ToolsPlus.getFiles().get("config").getString("tool-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot(ToolsPlus.getFiles().get("config").getInt("tool-purchase-gui." + configItem + ".slot"),
                        new CraftItem(ToolsPlus.getFiles().get("config").getString("tool-purchase-gui." + configItem + ".material"),
                                ToolsPlus.getFiles().get("config").getString("tool-purchase-gui." + configItem + ".name"),
                                ToolsPlus.getFiles().get("config").getStringList("tool-purchase-gui." + configItem + ".lore"),
                                ToolsPlus.getFiles().get("config").getStringList("tool-purchase-gui." + configItem + ".enchantments"), "null", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (ToolsPlus.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-multi")) {
                                    ToolsPlus.instance.getGuiByName("multi-buy").open(player);
                                }
                                if (ToolsPlus.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-trench")) {
                                    ToolsPlus.instance.getGuiByName("trench-buy").open(player);
                                }
                                if (ToolsPlus.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-tray")) {
                                    ToolsPlus.instance.getGuiByName("tray-buy").open(player);
                                }
                                if (ToolsPlus.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-sand")) {
                                    ToolsPlus.instance.getGuiByName("sand-buy").open(player);
                                }
                                if (ToolsPlus.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-lightning")) {
                                    ToolsPlus.instance.getGuiByName("lightning-buy").open(player);
                                }
                                if (ToolsPlus.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-harvester")) {
                                    ToolsPlus.instance.getGuiByName("harvester-buy").open(player);
                                }
                                if (ToolsPlus.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-sell")) {
                                    ToolsPlus.instance.getGuiByName("sell-buy").open(player);
                                }
                                if (ToolsPlus.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-tnt")) {
                                    ToolsPlus.instance.getGuiByName("tnt-buy").open(player);
                                }
                                if (ToolsPlus.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".open-aqua")) {
                                    ToolsPlus.instance.getGuiByName("aqua-buy").open(player);
                                }
                                if (ToolsPlus.getFiles().get("config").getBoolean("tool-purchase-gui." + configItem + ".exit-gui")) {
                                    player.closeInventory();
                                }
                            } catch (NullPointerException toolNotFound) {
                                player.closeInventory();
                                new PlayerMessage("invalid-config", player, "{reason}", "Generic purchase gui tools+.yml");
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}