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

public class TNTWandConfigurationGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public TNTWandConfigurationGui() {
        super(FileManager.get("tnt_config_gui").getInt("tnt-wand-config-gui.size"),
                Chat.applyColor(FileManager.get("tnt_config_gui").getString("tnt-wand-config-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot(FileManager.get("tnt_config_gui").getInt("tnt-wand-config-gui." + configItem + ".slot"),
                        new CraftItem(FileManager.get("tnt_config_gui").getString("tnt-wand-config-gui." + configItem + ".material"),
                                FileManager.get("tnt_config_gui").getString("tnt-wand-config-gui." + configItem + ".name"),
                                FileManager.get("tnt_config_gui").getStringList("tnt-wand-config-gui." + configItem + ".lore"),
                                FileManager.get("tnt_config_gui").getStringList("tnt-wand-config-gui." + configItem + ".enchantments")).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                ItemStack item = player.getItemInHand();
                                ItemMeta itemMeta = item.getItemMeta();
                                List<String> itemLore = itemMeta.getLore();
                                if (FileManager.get("tnt_config_gui").getBoolean("tnt-wand-config-gui." + configItem + ".switch-mode-when-clicked")) {
                                    ChangeMode.switchMode(itemLore, itemMeta, item, player, ToolsAttributeManager.tntWandModeUnique);
                                }
                                if (FileManager.get("tnt_config_gui").getBoolean("tnt-wand-config-gui." + configItem + ".increase-modifier-when-clicked")) {
                                    AlterToolModifier.increasePriceModifier(itemLore, itemMeta, item, player, ToolsAttributeManager.tntWandModifierUnique, "tnt", "tnt-wands.");
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