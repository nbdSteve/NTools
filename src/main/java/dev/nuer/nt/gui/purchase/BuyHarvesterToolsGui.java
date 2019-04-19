package dev.nuer.nt.gui.purchase;

import dev.nuer.nt.NTools;
import dev.nuer.nt.gui.AbstractGui;
import dev.nuer.nt.initialize.MapInitializer;
import dev.nuer.nt.method.itemCreation.CraftItem;
import dev.nuer.nt.method.itemCreation.PurchaseTool;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.ChatColor;

public class BuyHarvesterToolsGui extends AbstractGui {

    public BuyHarvesterToolsGui() {
        super(NTools.getFiles().get("harvester_purchase_gui").getInt("harvester-tool-purchase-gui.size"),
                ChatColor.translateAlternateColorCodes('&', NTools.getFiles().get("harvester_purchase_gui").getString("harvester-tool-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((NTools.getFiles().get("harvester_purchase_gui").getInt("harvester-tool-purchase-gui." + configItem + ".slot")),
                        new CraftItem((NTools.getFiles().get("harvester_purchase_gui").getString("harvester-tool-purchase-gui." + configItem + ".material")),
                                (NTools.getFiles().get("harvester_purchase_gui").getString("harvester-tool-purchase-gui." + configItem + ".name")),
                                (NTools.getFiles().get("harvester_purchase_gui").getStringList("harvester-tool-purchase-gui." + configItem + ".lore")), null, null,
                                (NTools.getFiles().get("harvester_purchase_gui").getStringList("harvester-tool-purchase-gui." + configItem + ".enchantments")), "harvester", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (NTools.getFiles().get("harvester_purchase_gui").getBoolean("harvester-tool-purchase-gui." + configItem + ".purchasable")) {
                                    String[] modifierParts = MapInitializer.harvesterModifierUnique.get(configItem).get(NTools.getFiles().get("harvester").getInt("harvester-tools." + configItem + ".modifier.starting")).split("-");
                                    new PurchaseTool((NTools.getFiles().get("harvester_purchase_gui").getInt("harvester-tool-purchase-gui." + configItem + ".price")),
                                            (NTools.getFiles().get("harvester_purchase_gui").getString("harvester-tool-purchase-gui." + configItem + ".material")),
                                            (NTools.getFiles().get("harvester").getString("harvester-tools." + configItem + ".name")),
                                            (NTools.getFiles().get("harvester").getStringList("harvester-tools." + configItem + ".lore")),
                                            (MapInitializer.harvesterModeUnique.get(configItem).get(1)),
                                            (modifierParts[0]),
                                            (NTools.getFiles().get("harvester").getStringList("harvester-tools." + configItem + ".enchantments")), "harvester", configItem, player, true);
                                }
                                if (NTools.getFiles().get("harvester_purchase_gui").getBoolean("harvester-tool-purchase-gui." + configItem + ".back-button")) {
                                    NTools.getPlugin(NTools.class).getGuiByName("generic-buy").open(player);
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