package dev.nuer.tp.gui.purchase;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.initialize.MapInitializer;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.itemCreation.PurchaseTool;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.ChatColor;

/**
 * Class that handles the purchase Gui for Harvester Hoes
 */
public class BuyHarvesterToolsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuyHarvesterToolsGui() {
        super(ToolsPlus.getFiles().get("harvester_purchase_gui").getInt("harvester-tool-purchase-gui.size"),
                ChatColor.translateAlternateColorCodes('&', ToolsPlus.getFiles().get("harvester_purchase_gui").getString("harvester-tool-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot(ToolsPlus.getFiles().get("harvester_purchase_gui").getInt("harvester-tool-purchase-gui." + configItem + ".slot"),
                        new CraftItem(ToolsPlus.getFiles().get("harvester_purchase_gui").getString("harvester-tool-purchase-gui." + configItem + ".material"),
                                ToolsPlus.getFiles().get("harvester_purchase_gui").getString("harvester-tool-purchase-gui." + configItem + ".name"),
                                ToolsPlus.getFiles().get("harvester_purchase_gui").getStringList("harvester-tool-purchase-gui." + configItem + ".lore"), null, null,
                                ToolsPlus.getFiles().get("harvester_purchase_gui").getStringList("harvester-tool-purchase-gui." + configItem + ".enchantments"), "harvester", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (ToolsPlus.getFiles().get("harvester_purchase_gui").getBoolean("harvester-tool-purchase-gui." + configItem + ".purchasable")) {
                                    String[] modifierParts = MapInitializer.harvesterModifierUnique.get(configItem).get(ToolsPlus.getFiles().get("harvester").getInt("harvester-tools." + configItem + ".modifier.starting")).split("-");
                                    new PurchaseTool(ToolsPlus.getFiles().get("harvester_purchase_gui").getInt("harvester-tool-purchase-gui." + configItem + ".price"),
                                            ToolsPlus.getFiles().get("harvester_purchase_gui").getString("harvester-tool-purchase-gui." + configItem + ".material"),
                                            ToolsPlus.getFiles().get("harvester").getString("harvester-tools." + configItem + ".name"),
                                            ToolsPlus.getFiles().get("harvester").getStringList("harvester-tools." + configItem + ".lore"),
                                            MapInitializer.harvesterModeUnique.get(configItem).get(1), modifierParts[0],
                                            ToolsPlus.getFiles().get("harvester").getStringList("harvester-tools." + configItem + ".enchantments"), "harvester", configItem, player, true);
                                }
                                if (ToolsPlus.getFiles().get("harvester_purchase_gui").getBoolean("harvester-tool-purchase-gui." + configItem + ".back-button")) {
                                    ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("generic-buy").open(player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                player.closeInventory();
                                new PlayerMessage("invalid-config", player, "{reason}", "Harvester Hoe purchase-gui.yml");
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}