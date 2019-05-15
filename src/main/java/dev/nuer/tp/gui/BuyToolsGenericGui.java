package dev.nuer.tp.gui;

import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.managers.GuiManager;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.player.PlayerMessage;

/**
 * Class that handles the standard gui opened with /tools
 */
public class BuyToolsGenericGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuyToolsGenericGui() {
        super(FileManager.get("config").getInt("tool-purchase-gui.size"),
                Chat.applyColor(FileManager.get("config").getString("tool-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot(FileManager.get("config").getInt("tool-purchase-gui." + configItem + ".slot"),
                        new CraftItem(FileManager.get("config").getString("tool-purchase-gui." + configItem + ".material"),
                                FileManager.get("config").getString("tool-purchase-gui." + configItem + ".name"),
                                FileManager.get("config").getStringList("tool-purchase-gui." + configItem + ".lore"),
                                FileManager.get("config").getStringList("tool-purchase-gui." + configItem + ".enchantments"), "null", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-multi")) {
                                    GuiManager.getGui("multi-buy").open(player);
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-trench")) {
                                    GuiManager.getGui("trench-buy").open(player);
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-tray")) {
                                    GuiManager.getGui("tray-buy").open(player);
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-sand")) {
                                    GuiManager.getGui("sand-buy").open(player);
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-lightning")) {
                                    GuiManager.getGui("lightning-buy").open(player);
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-harvester")) {
                                    GuiManager.getGui("harvester-buy").open(player);
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-sell")) {
                                    GuiManager.getGui("sell-buy").open(player);
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-tnt")) {
                                    GuiManager.getGui("tnt-buy").open(player);
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-aqua")) {
                                    GuiManager.getGui("aqua-buy").open(player);
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-smelt")) {
                                    GuiManager.getGui("smelt-buy").open(player);
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".exit-gui")) {
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