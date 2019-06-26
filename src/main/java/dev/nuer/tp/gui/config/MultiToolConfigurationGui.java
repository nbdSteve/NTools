package dev.nuer.tp.gui.config;

import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.managers.ToolsAttributeManager;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.player.PlayerMessage;
import dev.nuer.tp.tools.ChangeMode;
import dev.nuer.tp.tools.multi.ChangeToolRadius;
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
        super(FileManager.get("multi_config_gui").getInt("multi-tool-config-gui.size"),
                Chat.applyColor(FileManager.get("multi_config_gui").getString("multi-tool-config-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot(FileManager.get("multi_config_gui").getInt("multi-tool-config-gui." + configItem + ".slot"),
                        new CraftItem(FileManager.get("multi_config_gui").getString("multi-tool-config-gui." + configItem + ".material"),
                                FileManager.get("multi_config_gui").getString("multi-tool-config-gui." + configItem + ".name"),
                                FileManager.get("multi_config_gui").getStringList("multi-tool-config-gui." + configItem + ".lore"),
                                FileManager.get("multi_config_gui").getStringList("multi-tool-config-gui." + configItem + ".enchantments")).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                ItemStack item = player.getItemInHand();
                                ItemMeta itemMeta = item.getItemMeta();
                                List<String> itemLore = itemMeta.getLore();
                                if (FileManager.get("multi_config_gui").getBoolean("multi-tool-config-gui." + configItem + ".switch-mode-when-clicked")) {
                                    ChangeMode.switchMode(itemLore, itemMeta, item, player, ToolsAttributeManager.multiToolModeUnique);
                                }
                                if (FileManager.get("multi_config_gui").getBoolean("multi-tool-config-gui." + configItem + ".increase-radius-when-clicked")) {
                                    ChangeToolRadius.incrementRadius(itemLore, itemMeta, item, player, "multi", "multi-tools.", ToolsAttributeManager.multiToolRadiusUnique);
                                }
                                if (FileManager.get("multi_config_gui").getBoolean("multi-tool-config-gui." + configItem + ".decrease-radius-when-clicked")) {
                                    ChangeToolRadius.decrementRadius(itemLore, itemMeta, item, player, "multi", "multi-tools.", ToolsAttributeManager.multiToolRadiusUnique);
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