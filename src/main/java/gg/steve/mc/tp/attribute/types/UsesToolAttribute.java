package gg.steve.mc.tp.attribute.types;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.attribute.AbstractToolAttribute;
import gg.steve.mc.tp.attribute.ToolAttributeType;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.ToolsManager;
import gg.steve.mc.tp.utils.GetToolHoldingUtil;
import gg.steve.mc.tp.utils.ItemBuilderUtil;
import gg.steve.mc.tp.utils.LogUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class UsesToolAttribute extends AbstractToolAttribute {

    public UsesToolAttribute(String updateString) {
        super(ToolAttributeType.USES, updateString);
    }

    @Override
    public boolean doUpdate(Player player, NBTItem item, UUID toolId, int current, int change) {
        if (!item.getItem().hasItemMeta() || item.getItem().getItemMeta().getLore().isEmpty()) {
            LogUtil.warning("Tried to decrement uses for a tool that doesn't have any lore! Aborting.");
            return false;
        }
        if (current + change < 1) {
            player.setItemInHand(new ItemStack(Material.AIR));
            ToolsManager.removeLoadedTool(toolId);
            // message player
            return true;
        }
        String currentLore = getUpdateString().replace("{uses}", ToolsPlus.formatNumber(current));
        current += change;
        String replacementLore = getUpdateString().replace("{uses}", ToolsPlus.formatNumber(current));
        item.setInteger("tools+.uses", current);
        ItemBuilderUtil builder = new ItemBuilderUtil(item.getItem());
        List<String> lore = builder.getLore();
        for (int i = 0; i < lore.size(); i++) {
            if (lore.get(i).contains(currentLore)) {
                String line = lore.get(i).replace(currentLore, replacementLore);
                lore.set(i, line);
                break;
            }
        }
        builder.setLore(lore);
        item.getItem().setItemMeta(builder.getItemMeta());
        if (GetToolHoldingUtil.isStillHoldingTool(toolId, player.getItemInHand())) {
            player.setItemInHand(item.getItem());
            player.updateInventory();
            return true;
        } else {
            LogUtil.warning("Uses dupe attempted by player: " + player.getName() + ", Tools+ has stopped the tool action from happening.");
            return false;
        }
    }
}