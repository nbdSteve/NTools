package dev.nuer.nt.gui;

import dev.nuer.nt.NTools;
import dev.nuer.nt.event.itemMetaMethod.GetToolType;
import dev.nuer.nt.event.miningTool.modeSwitchMethod.ModeSwitch;
import dev.nuer.nt.event.miningTool.radiusChangeMethod.ChangeToolRadius;
import dev.nuer.nt.method.itemCreation.CraftItem;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Class that handles the Gui that will allow the player to configure their multi tool
 */
public class MultiToolConfigurationGui extends AbstractGui {

    /**
     * Super constructor, add all items with their respective listeners
     */
    public MultiToolConfigurationGui() {
        super(NTools.getFiles().get("multi_config_gui").getInt("multi-tool-config-gui.size"),
                ChatColor.translateAlternateColorCodes('&', NTools.getFiles().get("multi_config_gui").getString("multi-tool-config-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((NTools.getFiles().get("multi_config_gui").getInt("multi-tool-config-gui." + configItem +
                                ".slot")),
                        new CraftItem((NTools.getFiles().get("multi_config_gui").getString("multi-tool-config-gui." + configItem + ".material")),
                                (NTools.getFiles().get("multi_config_gui").getString("multi-tool-config-gui." + configItem +
                                        ".name")),
                                (NTools.getFiles().get("multi_config_gui").getStringList("multi-tool-config-gui." + configItem + ".lore")), null, null,
                                (NTools.getFiles().get("multi_config_gui").getStringList("multi-tool-config-gui." + configItem + ".enchantments")), null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                ItemStack item = player.getInventory().getItemInHand();
                                ItemMeta itemMeta = item.getItemMeta();
                                List<String> itemLore = itemMeta.getLore();
                                if (NTools.getFiles().get("multi_config_gui").getBoolean("multi-tool-config-gui." + configItem + ".switch-mode-when-clicked")) {
                                    ModeSwitch.switchMode((new GetToolType(itemLore, itemMeta, item).getToolTypeRawID()), itemLore, itemMeta, item, player);
                                }
                                if (NTools.getFiles().get("multi_config_gui").getBoolean("multi-tool-config-gui." + configItem + ".increase-radius-when-clicked")) {
                                    ChangeToolRadius.incrementRadius(itemLore, itemMeta, item, player);
                                }
                                if (NTools.getFiles().get("multi_config_gui").getBoolean("multi-tool-config-gui." + configItem + ".decrease-radius-when-clicked")) {
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