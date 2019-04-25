package dev.nuer.tp.gui.config;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.initialize.MapInitializer;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.player.PlayerMessage;
import dev.nuer.tp.tools.ChangeMode;
import dev.nuer.tp.tools.multi.ChangeToolRadius;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Class that handles the configuration Gui for Multi Tools
 */
public class MultiToolConfigurationGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public MultiToolConfigurationGui() {
        super(ToolsPlus.getFiles().get("multi_config_gui").getInt("multi-tool-config-gui.size"),
                ChatColor.translateAlternateColorCodes('&', ToolsPlus.getFiles().get("multi_config_gui").getString("multi-tool-config-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot(ToolsPlus.getFiles().get("multi_config_gui").getInt("multi-tool-config-gui." + configItem + ".slot"),
                        new CraftItem(ToolsPlus.getFiles().get("multi_config_gui").getString("multi-tool-config-gui." + configItem + ".material"),
                                ToolsPlus.getFiles().get("multi_config_gui").getString("multi-tool-config-gui." + configItem + ".name"),
                                ToolsPlus.getFiles().get("multi_config_gui").getStringList("multi-tool-config-gui." + configItem + ".lore"), null, null,
                                ToolsPlus.getFiles().get("multi_config_gui").getStringList("multi-tool-config-gui." + configItem + ".enchantments"), "multi", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                ItemStack item = player.getInventory().getItemInHand();
                                ItemMeta itemMeta = item.getItemMeta();
                                List<String> itemLore = itemMeta.getLore();
                                if (ToolsPlus.getFiles().get("multi_config_gui").getBoolean("multi-tool-config-gui." + configItem + ".switch-mode-when-clicked")) {
                                    ChangeMode.switchMode(itemLore, itemMeta, item, player, MapInitializer.multiToolModeUnique);
                                }
                                if (ToolsPlus.getFiles().get("multi_config_gui").getBoolean("multi-tool-config-gui." + configItem + ".increase-radius-when-clicked")) {
                                    ChangeToolRadius.incrementRadius(itemLore, itemMeta, item, player);
                                }
                                if (ToolsPlus.getFiles().get("multi_config_gui").getBoolean("multi-tool-config-gui." + configItem + ".decrease-radius-when-clicked")) {
                                    ChangeToolRadius.decrementRadius(itemLore, itemMeta, item, player);
                                }
                            } catch (NullPointerException toolNotFound) {
                                player.closeInventory();
                                new PlayerMessage("invalid-tool", player);
                            }
                        });
            } catch (NullPointerException itemNotFound) {
                //Do nothing, the item just isn't in the gui config.
            }
        }
    }
}