package gg.steve.mc.tp.modules.craft.tool;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.attribute.types.CooldownToolAttribute;
import gg.steve.mc.tp.modules.craft.CraftModule;
import gg.steve.mc.tp.modules.craft.recipes.RecipeManager;
import gg.steve.mc.tp.modules.craft.utils.CraftMessage;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.tool.ToolData;
import gg.steve.mc.tp.utils.CubeUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.List;

public class CraftWandData implements ToolData {

    @Override
    public void onBlockBreak(BlockBreakEvent blockBreakEvent, PlayerTool playerTool) {

    }

    @Override
    public void onInteract(PlayerInteractEvent event, PlayerTool tool) {
        if (event.getClickedBlock() == null || event.getClickedBlock().getType().equals(Material.AIR)) return;
        event.setCancelled(true);
        List<Block> containers = CubeUtil.getCube(event.getPlayer(), event.getClickedBlock(), tool.getRadius(), CraftModule.moduleId, true);
        if (containers.isEmpty()) return;
        List<Inventory> inventories = new ArrayList<>();
        for (Block block : containers) {
            if (!(block.getState() instanceof InventoryHolder)) continue;
            Inventory inventory = ((InventoryHolder) block.getState()).getInventory();
            if (!inventories.contains(inventory)) inventories.add(inventory);
        }
        if (inventories.isEmpty()) return;
        if (CooldownToolAttribute.isCooldownActive(event.getPlayer(), tool)) return;
        int crafted = RecipeManager.doCrafting(inventories);
        if (crafted == 0) {
            CraftMessage.NONE_CRAFTED.message(event.getPlayer());
            return;
        }
        tool.isOnCooldown(event.getPlayer());
        CraftMessage.CRAFTED.message(event.getPlayer(), ToolsPlus.formatNumber(crafted));
        if (!tool.decrementUses(event.getPlayer())) return;
        if (!tool.incrementBlocksMined(event.getPlayer(), crafted)) return;
    }
}
