package dev.nuer.nt.gui.config;

import dev.nuer.nt.ToolsPlus;
import dev.nuer.nt.gui.AbstractGui;
import dev.nuer.nt.initialize.MapInitializer;
import dev.nuer.nt.method.itemCreation.CraftItem;
import dev.nuer.nt.method.player.PlayerMessage;
import dev.nuer.nt.tools.ChangeMode;
import dev.nuer.nt.tools.PriceModifier;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TNTWandConfigurationGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public TNTWandConfigurationGui() {
        super(ToolsPlus.getFiles().get("tnt_config_gui").getInt("tnt-wand-config-gui.size"),
                ChatColor.translateAlternateColorCodes('&', ToolsPlus.getFiles().get("tnt_config_gui").getString("tnt-wand-config-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot(ToolsPlus.getFiles().get("tnt_config_gui").getInt("tnt-wand-config-gui." + configItem + ".slot"),
                        new CraftItem(ToolsPlus.getFiles().get("tnt_config_gui").getString("tnt-wand-config-gui." + configItem + ".material"),
                                ToolsPlus.getFiles().get("tnt_config_gui").getString("tnt-wand-config-gui." + configItem + ".name"),
                                ToolsPlus.getFiles().get("tnt_config_gui").getStringList("tnt-wand-config-gui." + configItem + ".lore"), null, null,
                                ToolsPlus.getFiles().get("tnt_config_gui").getStringList("tnt-wand-config-gui." + configItem + ".enchantments"), "tnt", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                ItemStack item = player.getInventory().getItemInHand();
                                ItemMeta itemMeta = item.getItemMeta();
                                List<String> itemLore = itemMeta.getLore();
                                if (ToolsPlus.getFiles().get("tnt_config_gui").getBoolean("tnt-wand-config-gui." + configItem + ".switch-mode-when-clicked")) {
                                    ChangeMode.switchMode(itemLore, itemMeta, item, player, MapInitializer.tntWandModeUnique);
                                }
                                if (ToolsPlus.getFiles().get("tnt_config_gui").getBoolean("tnt-wand-config-gui." + configItem + ".increase-modifier-when-clicked")) {
                                    PriceModifier.increasePriceModifier(itemLore, itemMeta, item, player, MapInitializer.tntWandModifierUnique, "tnt", "tnt-wands.");
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