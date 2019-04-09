package dev.nuer.nt.gui;

import dev.nuer.nt.NTools;
import dev.nuer.nt.event.itemMetaMethod.GetToolType;
import dev.nuer.nt.event.modeSwitchMethod.ModeSwitch;
import dev.nuer.nt.event.radiusChangeMethod.ChangeToolRadius;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class UpgradeGui extends AbstractGui {

    public UpgradeGui() {
        super(NTools.getFiles().get("config").getInt("gui.multi-tool-options.size"),
                ChatColor.translateAlternateColorCodes('&', NTools.getFiles().get("config").getString("gui.multi-tool-options.name")));

        setItemInSlot(8, new ItemStack(Material.DIAMOND), player -> {
            player.closeInventory();
            try {
                ItemStack item = player.getInventory().getItemInHand();
                ItemMeta itemMeta = item.getItemMeta();
                List<String> itemLore = itemMeta.getLore();
                ModeSwitch.switchMode((new GetToolType(itemLore, itemMeta, item).getToolTypeRawID()),
                        (new GetToolType(itemLore, itemMeta, item).getToolType()), itemLore, itemMeta, item);
            } catch (NullPointerException toolNotFound) {
                //send player message that it is not active tool
            }
        });

        setItemInSlot(3, new ItemStack(Material.BEACON), player -> {
            player.closeInventory();
            try {
                ItemStack item = player.getInventory().getItemInHand();
                ItemMeta itemMeta = item.getItemMeta();
                List<String> itemLore = itemMeta.getLore();
                ChangeToolRadius.incrementRadius((new GetToolType(itemLore, itemMeta, item).getToolTypeRawID()),
                        (new GetToolType(itemLore, itemMeta, item).getToolType()), itemLore, itemMeta, item, player);
            } catch (NullPointerException toolNotFound) {
                //send player message that it is not active tool
            }
        });

        setItemInSlot(2, new ItemStack(Material.BARRIER), player -> {
            player.closeInventory();
            try {
                ItemStack item = player.getInventory().getItemInHand();
                ItemMeta itemMeta = item.getItemMeta();
                List<String> itemLore = itemMeta.getLore();
                ChangeToolRadius.decrementRadius((new GetToolType(itemLore, itemMeta, item).getToolTypeRawID()),
                        (new GetToolType(itemLore, itemMeta, item).getToolType()), itemLore, itemMeta, item, player);
            } catch (NullPointerException toolNotFound) {
                //send player message that it is not active tool
            }
        });
    }
}
