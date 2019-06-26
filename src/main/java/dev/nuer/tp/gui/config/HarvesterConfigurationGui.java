package dev.nuer.tp.gui.config;

import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.managers.ToolsAttributeManager;
import dev.nuer.tp.method.Chat;
import dev.nuer.tp.method.itemCreation.CraftItem;
import dev.nuer.tp.method.player.PlayerMessage;
import dev.nuer.tp.tools.AlterToolModifier;
import dev.nuer.tp.tools.ChangeMode;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Class that handles the configuration Gui for Harvester Hoes
 */
public class HarvesterConfigurationGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public HarvesterConfigurationGui() {
        super(FileManager.get("harvester_config_gui").getInt("harvester-tool-config-gui.size"),
                Chat.applyColor(FileManager.get("harvester_config_gui").getString("harvester-tool-config-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot(FileManager.get("harvester_config_gui").getInt("harvester-tool-config-gui." + configItem + ".slot"),
                        new CraftItem(FileManager.get("harvester_config_gui").getString("harvester-tool-config-gui." + configItem + ".material"),
                                FileManager.get("harvester_config_gui").getString("harvester-tool-config-gui." + configItem + ".name"),
                                FileManager.get("harvester_config_gui").getStringList("harvester-tool-config-gui." + configItem + ".lore"),
                                FileManager.get("harvester_config_gui").getStringList("harvester-tool-config-gui." + configItem + ".enchantments")).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                ItemStack item = player.getItemInHand();
                                ItemMeta itemMeta = item.getItemMeta();
                                List<String> itemLore = itemMeta.getLore();
                                if (FileManager.get("harvester_config_gui").getBoolean("harvester-tool-config-gui." + configItem + ".switch-mode-when-clicked")) {
                                    ChangeMode.switchMode(itemLore, itemMeta, item, player, ToolsAttributeManager.harvesterModeUnique);
                                }
                                if (FileManager.get("harvester_config_gui").getBoolean("harvester-tool-config-gui." + configItem + ".increase-modifier-when-clicked")) {
                                    AlterToolModifier.increasePriceModifier(itemLore, itemMeta, item, player, ToolsAttributeManager.harvesterModifierUnique, "harvester", "harvester-tools.");
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