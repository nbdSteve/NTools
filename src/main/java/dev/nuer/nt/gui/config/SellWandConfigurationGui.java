package dev.nuer.nt.gui.config;

import dev.nuer.nt.NTools;
import dev.nuer.nt.gui.AbstractGui;
import dev.nuer.nt.initialize.MapInitializer;
import dev.nuer.nt.method.itemCreation.CraftItem;
import dev.nuer.nt.method.player.PlayerMessage;
import dev.nuer.nt.tools.PriceModifier;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class SellWandConfigurationGui extends AbstractGui {

    public SellWandConfigurationGui() {
        super(NTools.getFiles().get("sell_config_gui").getInt("sell-wand-config-gui.size"),
                ChatColor.translateAlternateColorCodes('&', NTools.getFiles().get("sell_config_gui").getString("sell-wand-config-gui.name")));

        //Add all of the items from the Gui config to the Gui
        for (int i = 1; i <= 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((NTools.getFiles().get("sell_config_gui").getInt("sell-wand-config-gui." + configItem +
                                ".slot")),
                        new CraftItem((NTools.getFiles().get("sell_config_gui").getString("sell-wand-config-gui." + configItem + ".material")),
                                (NTools.getFiles().get("sell_config_gui").getString("sell-wand-config-gui." + configItem +
                                        ".name")),
                                (NTools.getFiles().get("sell_config_gui").getStringList("sell-wand-config-gui." + configItem + ".lore")), null, null,
                                (NTools.getFiles().get("sell_config_gui").getStringList("sell-wand-config-gui." + configItem + ".enchantments")), "sell", 0, null).getItem(),
                        player -> {
                            //Add the respective listeners to items based off the config
                            try {
                                ItemStack item = player.getInventory().getItemInHand();
                                ItemMeta itemMeta = item.getItemMeta();
                                List<String> itemLore = itemMeta.getLore();
                                if (NTools.getFiles().get("sell_config_gui").getBoolean("sell-wand-config-gui." + configItem + ".increase-modifier-when-clicked")) {
                                    PriceModifier.increaseHarvesterModifier(itemLore, itemMeta, item, player, MapInitializer.sellWandModifierUnique, "sell", "sell-wands.");
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
