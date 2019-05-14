package dev.nuer.tp.gui.config;

import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.managers.ToolsAttributeManager;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.player.PlayerMessage;
import dev.nuer.tp.tools.AlterToolModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Class that handles the configuration Gui for Sell Wands
 */
public class SellWandConfigurationGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public SellWandConfigurationGui() {
        super(FileManager.get("sell_config_gui").getInt("sell-wand-config-gui.size"),
                Chat.applyColor(FileManager.get("sell_config_gui").getString("sell-wand-config-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot(FileManager.get("sell_config_gui").getInt("sell-wand-config-gui." + configItem + ".slot"),
                        new CraftItem(FileManager.get("sell_config_gui").getString("sell-wand-config-gui." + configItem + ".material"),
                                FileManager.get("sell_config_gui").getString("sell-wand-config-gui." + configItem + ".name"),
                                FileManager.get("sell_config_gui").getStringList("sell-wand-config-gui." + configItem + ".lore"),
                                FileManager.get("sell_config_gui").getStringList("sell-wand-config-gui." + configItem + ".enchantments"), "sell", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                ItemStack item = player.getItemInHand();
                                ItemMeta itemMeta = item.getItemMeta();
                                List<String> itemLore = itemMeta.getLore();
                                if (FileManager.get("sell_config_gui").getBoolean("sell-wand-config-gui." + configItem + ".increase-modifier-when-clicked")) {
                                    AlterToolModifier.increasePriceModifier(itemLore, itemMeta, item, player, ToolsAttributeManager.sellWandModifierUnique, "sell", "sell-wands.");
                                }
                            } catch (NullPointerException wandNotFound) {
                                player.closeInventory();
                                new PlayerMessage("invalid-wand", player);
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}
