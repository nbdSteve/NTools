package dev.nuer.nt.gui;

import dev.nuer.nt.NTools;
import dev.nuer.nt.event.itemMetaMethod.GetToolType;
import dev.nuer.nt.event.modeSwitchMethod.ModeSwitch;
import dev.nuer.nt.event.radiusChangeMethod.ChangeToolRadius;
import dev.nuer.nt.method.itemCreation.CraftItem;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MultiToolOptionsGui extends AbstractGui {

    public MultiToolOptionsGui() {
        super(NTools.getFiles().get("config").getInt("gui.multi-tool-options.size"),
                ChatColor.translateAlternateColorCodes('&', NTools.getFiles().get("config").getString("gui" +
                        ".multi-tool-options.name")));

        for (int i = 1; i < 54; i++) {
            try {
                final int configItem = i;
                setItemInSlot((NTools.getFiles().get("config").getInt("gui.multi-tool-options." + i +
                                ".slot")),
                        new CraftItem((NTools.getFiles().get("config").getString("gui.multi-tool-options." + i + ".material")),
                                (NTools.getFiles().get("config").getString("gui.multi-tool-options." + i +
                                        ".name")),
                                (NTools.getFiles().get("config").getStringList("gui.multi-tool-options." + i + ".lore")),
                                (NTools.getFiles().get("config").getStringList("gui.multi-tool-options." + i + ".enchantments"))).getItem(),
                        player -> {
                            try {
                                ItemStack item = player.getInventory().getItemInHand();
                                ItemMeta itemMeta = item.getItemMeta();
                                List<String> itemLore = itemMeta.getLore();
                                if (NTools.getFiles().get("config").getBoolean("gui.multi-tool-options." + configItem + ".switch-mode-when-clicked")) {
                                    player.closeInventory();
                                    ModeSwitch.switchMode((new GetToolType(itemLore, itemMeta, item).getToolTypeRawID()),
                                            (new GetToolType(itemLore, itemMeta, item).getToolType()),
                                            itemLore, itemMeta, item);
                                    new PlayerMessage("mode-switch", player);
                                }
                                if (NTools.getFiles().get("config").getBoolean("gui.multi-tool-options." + configItem + ".increase-radius-when-clicked")) {
                                    player.closeInventory();
                                    ChangeToolRadius.incrementRadius((new GetToolType(itemLore, itemMeta,
                                                    item).getToolTypeRawID()),
                                            (new GetToolType(itemLore, itemMeta, item).getToolType()),
                                            itemLore, itemMeta, item, player);
                                    new PlayerMessage("incremented-radius", player);
                                }
                                if (NTools.getFiles().get("config").getBoolean("gui.multi-tool-options." + configItem + ".decrease-radius-when-clicked")) {
                                    player.closeInventory();
                                    ChangeToolRadius.decrementRadius((new GetToolType(itemLore, itemMeta,
                                                    item).getToolTypeRawID()),
                                            (new GetToolType(itemLore, itemMeta, item).getToolType()),
                                            itemLore, itemMeta, item, player);
                                    new PlayerMessage("decremented-radius", player);
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
