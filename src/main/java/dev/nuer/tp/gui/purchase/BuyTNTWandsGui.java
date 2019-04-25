package dev.nuer.tp.gui.purchase;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.initialize.MapInitializer;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.itemCreation.PurchaseTool;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.ChatColor;

/**
 * Class that handles the purchase Gui for TNT Wands
 */
public class BuyTNTWandsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuyTNTWandsGui() {
        super(ToolsPlus.getFiles().get("tnt_purchase_gui").getInt("tnt-wand-purchase-gui.size"),
                ChatColor.translateAlternateColorCodes('&', ToolsPlus.getFiles().get("tnt_purchase_gui").getString("tnt-wand-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((ToolsPlus.getFiles().get("tnt_purchase_gui").getInt("tnt-wand-purchase-gui." + configItem + ".slot")),
                        new CraftItem(ToolsPlus.getFiles().get("tnt_purchase_gui").getString("tnt-wand-purchase-gui." + configItem + ".material"),
                                ToolsPlus.getFiles().get("tnt_purchase_gui").getString("tnt-wand-purchase-gui." + configItem + ".name"),
                                ToolsPlus.getFiles().get("tnt_purchase_gui").getStringList("tnt-wand-purchase-gui." + configItem + ".lore"), null, null,
                                ToolsPlus.getFiles().get("tnt_purchase_gui").getStringList("tnt-wand-purchase-gui." + configItem + ".enchantments"), "tnt", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (ToolsPlus.getFiles().get("tnt_purchase_gui").getBoolean("tnt-wand-purchase-gui." + configItem + ".purchasable")) {
                                    String[] modifierParts = MapInitializer.tntWandModifierUnique.get(configItem).get(ToolsPlus.getFiles().get("tnt").getInt("tnt-wands." + configItem + ".modifier.starting")).split("-");
                                    new PurchaseTool(ToolsPlus.getFiles().get("tnt_purchase_gui").getInt("tnt-wand-purchase-gui." + configItem + ".price"),
                                            ToolsPlus.getFiles().get("tnt_purchase_gui").getString("tnt-wand-purchase-gui." + configItem + ".material"),
                                            ToolsPlus.getFiles().get("tnt").getString("tnt-wands." + configItem + ".name"),
                                            ToolsPlus.getFiles().get("tnt").getStringList("tnt-wands." + configItem + ".lore"),
                                            MapInitializer.tntWandModeUnique.get(configItem).get(1), modifierParts[0],
                                            ToolsPlus.getFiles().get("tnt").getStringList("tnt-wands." + configItem + ".enchantments"), "tnt", configItem, player, true);
                                }
                                if (ToolsPlus.getFiles().get("tnt_purchase_gui").getBoolean("tnt-wand-purchase-gui." + configItem + ".back-button")) {
                                    ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("generic-buy").open(player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                toolNotFound.printStackTrace();
                                player.closeInventory();
                                new PlayerMessage("invalid-config", player, "{reason}", "TNT Wand purchase-gui.yml");
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}