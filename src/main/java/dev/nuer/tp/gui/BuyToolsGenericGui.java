package dev.nuer.tp.gui;

import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.managers.GuiManager;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.player.PlayerMessage;
import sun.misc.MessageUtils;

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
                                FileManager.get("config").getStringList("tool-purchase-gui." + configItem + ".enchantments")).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                boolean usePerm = FileManager.get("config").getBoolean("gui-permissions");
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-multi")) {
                                    if (usePerm) {
                                        if (player.hasPermission("tools+.gui.multi")) {
                                            GuiManager.getGui("multi-buy").open(player);
                                        } else {
                                            new PlayerMessage("no-permission", player);
                                        }
                                    } else {
                                        GuiManager.getGui("multi-buy").open(player);
                                    }
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-trench")) {
                                    if (usePerm) {
                                        if (player.hasPermission("tools+.gui.trench")) {
                                            GuiManager.getGui("trench-buy").open(player);
                                        } else {
                                            new PlayerMessage("no-permission", player);
                                        }
                                    } else {
                                        GuiManager.getGui("trench-buy").open(player);
                                    }
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-tray")) {
                                    if (usePerm) {
                                        if (player.hasPermission("tools+.gui.tray")) {
                                            GuiManager.getGui("tray-buy").open(player);
                                        } else {
                                            new PlayerMessage("no-permission", player);
                                        }
                                    } else {
                                        GuiManager.getGui("tray-buy").open(player);
                                    }
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-sand")) {
                                    if (usePerm) {
                                        if (player.hasPermission("tools+.gui.sand")) {
                                            GuiManager.getGui("sand-buy").open(player);
                                        } else {
                                            new PlayerMessage("no-permission", player);
                                        }
                                    } else {
                                        GuiManager.getGui("sand-buy").open(player);
                                    }
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-lightning")) {
                                    if (usePerm) {
                                        if (player.hasPermission("tools+.gui.lightning")) {
                                            GuiManager.getGui("lightning-buy").open(player);
                                        } else {
                                            new PlayerMessage("no-permission", player);
                                        }
                                    } else {
                                        GuiManager.getGui("lightning-buy").open(player);
                                    }
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-harvester")) {
                                    if (usePerm) {
                                        if (player.hasPermission("tools+.gui.harvester")) {
                                            GuiManager.getGui("harvester-buy").open(player);
                                        } else {
                                            new PlayerMessage("no-permission", player);
                                        }
                                    } else {
                                        GuiManager.getGui("harvester-buy").open(player);
                                    }
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-sell")) {
                                    if (usePerm) {
                                        if (player.hasPermission("tools+.gui.sell")) {
                                            GuiManager.getGui("sell-buy").open(player);
                                        } else {
                                            new PlayerMessage("no-permission", player);
                                        }
                                    } else {
                                        GuiManager.getGui("sell-buy").open(player);
                                    }
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-tnt")) {
                                    if (usePerm) {
                                        if (player.hasPermission("tools+.gui.tnt")) {
                                            GuiManager.getGui("tnt-buy").open(player);
                                        } else {
                                            new PlayerMessage("no-permission", player);
                                        }
                                    } else {
                                        GuiManager.getGui("tnt-buy").open(player);
                                    }
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-aqua")) {
                                    if (usePerm) {
                                        if (player.hasPermission("tools+.gui.aqua")) {
                                            GuiManager.getGui("aqua-buy").open(player);
                                        } else {
                                            new PlayerMessage("no-permission", player);
                                        }
                                    } else {
                                        GuiManager.getGui("aqua-buy").open(player);
                                    }
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-smelt")) {
                                    if (usePerm) {
                                        if (player.hasPermission("tools+.gui.smelt")) {
                                            GuiManager.getGui("smelt-buy").open(player);
                                        } else {
                                            new PlayerMessage("no-permission", player);
                                        }
                                    } else {
                                        GuiManager.getGui("smelt-buy").open(player);
                                    }
                                }
                                if (FileManager.get("config").getBoolean("tool-purchase-gui." + configItem + ".open-chunk")) {
                                    if (usePerm) {
                                        if (player.hasPermission("tools+.gui.chunk")) {
                                            GuiManager.getGui("chunk-buy").open(player);
                                        } else {
                                            new PlayerMessage("no-permission", player);
                                        }
                                    } else {
                                        GuiManager.getGui("chunk-buy").open(player);
                                    }
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