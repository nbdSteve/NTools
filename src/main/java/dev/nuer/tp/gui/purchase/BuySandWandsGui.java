package dev.nuer.tp.gui.purchase;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.itemCreation.PurchaseTool;
import dev.nuer.tp.method.player.PlayerMessage;
import org.bukkit.ChatColor;

/**
 * Class that handles the purchase Gui for Sand Wands
 */
public class BuySandWandsGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public BuySandWandsGui() {
        super(ToolsPlus.getFiles().get("sand_purchase_gui").getInt("sand-wand-purchase-gui.size"),
                Chat.applyColor(ToolsPlus.getFiles().get("sand_purchase_gui").getString("sand-wand-purchase-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((ToolsPlus.getFiles().get("sand_purchase_gui").getInt("sand-wand-purchase-gui." + configItem + ".slot")),
                        new CraftItem(ToolsPlus.getFiles().get("sand_purchase_gui").getString("sand-wand-purchase-gui." + configItem + ".material"),
                                ToolsPlus.getFiles().get("sand_purchase_gui").getString("sand-wand-purchase-gui." + configItem + ".name"),
                                ToolsPlus.getFiles().get("sand_purchase_gui").getStringList("sand-wand-purchase-gui." + configItem + ".lore"),
                                ToolsPlus.getFiles().get("sand_purchase_gui").getStringList("sand-wand-purchase-gui." + configItem + ".enchantments"), "sand", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                if (ToolsPlus.getFiles().get("sand_purchase_gui").getBoolean("sand-wand-purchase-gui." + configItem + ".purchasable")) {
                                    new PurchaseTool(ToolsPlus.getFiles().get("sand_purchase_gui").getInt("sand-wand-purchase-gui." + configItem + ".price"),
                                            ToolsPlus.getFiles().get("sand_purchase_gui").getString("sand-wand-purchase-gui." + configItem + ".material"),
                                            ToolsPlus.getFiles().get("sand").getString("sand-wands." + configItem + ".name"),
                                            ToolsPlus.getFiles().get("sand").getStringList("sand-wands." + configItem + ".lore"),
                                            ToolsPlus.getFiles().get("sand").getStringList("sand-wands." + configItem + ".enchantments"), "sand", configItem, player,
                                            "debug", "debug", "debug", "debug",
                                            "{uses}", ToolsPlus.getFiles().get("sand").getString("sand-wands." + configItem + ".uses.starting"));
                                }
                                if (ToolsPlus.getFiles().get("sand_purchase_gui").getBoolean("sand-wand-purchase-gui." + configItem + ".back-button")) {
                                    ToolsPlus.getPlugin(ToolsPlus.class).getGuiByName("generic-buy").open(player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                player.closeInventory();
                                new PlayerMessage("invalid-config", player, "{reason}", "Sand Wand purchase-gui.yml");
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}
