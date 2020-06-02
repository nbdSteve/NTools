package gg.steve.mc.tp.tool.utils;

import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.LoadedTool;
import gg.steve.mc.tp.tool.ToolsManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GetToolHoldingUtil {

    public static LoadedTool getHoldingTool(NBTItem item) {
        UUID toolId = UUID.fromString(item.getString("tools+.uuid"));
        return ToolsManager.getLoadedTool(toolId);
    }

    public static boolean isHoldingTool(NBTItem item) {
        if (item.getString("tools+.uuid").equalsIgnoreCase("")) return false;
        UUID toolId = UUID.fromString(item.getString("tools+.uuid"));
        if (!ToolsManager.isLoadedToolRegistered(toolId)) ToolsManager.addLoadedTool(toolId, item);
        return true;
    }

    public static boolean isStillHoldingTool(UUID toolId, ItemStack item) {
        if (item == null || item.getType().equals(Material.AIR)) return false;
        NBTItem nbtItem = new NBTItem(item);
        if (nbtItem.getString("tools+.uuid").equalsIgnoreCase("")) return false;
        return nbtItem.getString("tools+.uuid").equalsIgnoreCase(String.valueOf(toolId));
    }
}
