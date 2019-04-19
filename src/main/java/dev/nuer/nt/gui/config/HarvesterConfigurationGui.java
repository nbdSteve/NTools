package dev.nuer.nt.gui.config;

import dev.nuer.nt.NTools;
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

/**
 * Class that handles the configuration Gui for Harvester Hoes
 */
public class HarvesterConfigurationGui extends AbstractGui {

    /**
     * Constructor to create the Gui, add all items with their respective listeners
     */
    public HarvesterConfigurationGui() {
        super(NTools.getFiles().get("harvester_config_gui").getInt("harvester-tool-config-gui.size"),
                ChatColor.translateAlternateColorCodes('&', NTools.getFiles().get("harvester_config_gui").getString("harvester-tool-config-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((NTools.getFiles().get("harvester_config_gui").getInt("harvester-tool-config-gui." + configItem +
                                ".slot")),
                        new CraftItem((NTools.getFiles().get("harvester_config_gui").getString("harvester-tool-config-gui." + configItem + ".material")),
                                (NTools.getFiles().get("harvester_config_gui").getString("harvester-tool-config-gui." + configItem +
                                        ".name")),
                                (NTools.getFiles().get("harvester_config_gui").getStringList("harvester-tool-config-gui." + configItem + ".lore")), null, null,
                                (NTools.getFiles().get("harvester_config_gui").getStringList("harvester-tool-config-gui." + configItem + ".enchantments")), "harvester", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                ItemStack item = player.getInventory().getItemInHand();
                                ItemMeta itemMeta = item.getItemMeta();
                                List<String> itemLore = itemMeta.getLore();
                                if (NTools.getFiles().get("harvester_config_gui").getBoolean("harvester-tool-config-gui." + configItem + ".switch-mode-when-clicked")) {
                                    ChangeMode.switchMode(itemLore, itemMeta, item, player, MapInitializer.harvesterModeUnique);
                                }
                                if (NTools.getFiles().get("harvester_config_gui").getBoolean("harvester-tool-config-gui." + configItem + ".increase-modifier-when-clicked")) {
                                    PriceModifier.increasePriceModifier(itemLore, itemMeta, item, player, MapInitializer.harvesterModifierUnique, "harvester", "harvester-tools.");
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