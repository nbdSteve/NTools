package gg.steve.mc.tp.attribute.types;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.attribute.AbstractToolAttribute;
import gg.steve.mc.tp.attribute.ToolAttributeType;
import gg.steve.mc.tp.message.GeneralMessage;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.ToolsManager;
import gg.steve.mc.tp.tool.utils.GetToolHoldingUtil;
import gg.steve.mc.tp.utils.LogUtil;
import gg.steve.mc.tp.tool.utils.LoreUpdaterUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
            GeneralMessage.OUT_OF_USES.message(player, ToolsManager.getLoadedTool(toolId).getAbstractTool().getType().getNiceName());
            ToolsManager.removeLoadedTool(toolId);
            return true;
        }
        ItemStack updated = LoreUpdaterUtil.updateLore(item, "uses", current + change,
                getUpdateString().replace("{uses}", ToolsPlus.formatNumber(current)),
                getUpdateString().replace("{uses}", ToolsPlus.formatNumber(current + change)));
        if (GetToolHoldingUtil.isStillHoldingTool(toolId, player.getItemInHand())) {
            player.setItemInHand(updated);
            player.updateInventory();
            return true;
        } else {
            LogUtil.warning("Uses dupe attempted by player: " + player.getName() + ", Tools+ has stopped the tool action from happening.");
            return false;
        }
    }
}