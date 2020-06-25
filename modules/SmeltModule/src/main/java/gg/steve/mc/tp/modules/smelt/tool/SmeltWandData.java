package gg.steve.mc.tp.modules.smelt.tool;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.attribute.types.CooldownToolAttribute;
import gg.steve.mc.tp.framework.utils.CubeUtil;
import gg.steve.mc.tp.modules.smelt.SmeltModule;
import gg.steve.mc.tp.modules.smelt.conversions.ItemSmeltManager;
import gg.steve.mc.tp.modules.smelt.utils.SmeltMessage;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.tool.ToolData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.List;

public class SmeltWandData implements ToolData {

    @Override
    public void onBlockBreak(BlockBreakEvent blockBreakEvent, PlayerTool playerTool) {

    }

    @Override
    public void onInteract(PlayerInteractEvent event, PlayerTool tool) {
        if (event.getClickedBlock() == null || event.getClickedBlock().getType().equals(Material.AIR)) return;
        event.setCancelled(true);
        List<Block> containers = CubeUtil.getCube(event.getPlayer(), event.getClickedBlock(), tool.getRadius(), SmeltModule.moduleId, true);
        if (containers.isEmpty()) return;
        List<Inventory> inventories = new ArrayList<>();
        for (Block block : containers) {
            if (!(block.getState() instanceof InventoryHolder)) continue;
            Inventory inventory = ((InventoryHolder) block.getState()).getInventory();
            if (!inventories.contains(inventory)) inventories.add(inventory);
        }
        if (inventories.isEmpty()) return;
        if (CooldownToolAttribute.isCooldownActive(event.getPlayer(), tool)) return;
        int smelted = ItemSmeltManager.doSmelting(inventories);
        if (smelted == 0) {
            SmeltMessage.NONE_SMELTED.message(event.getPlayer());
            return;
        }
        tool.isOnCooldown(event.getPlayer());
        SmeltMessage.SMELTED.message(event.getPlayer(), ToolsPlus.formatNumber(smelted));
        if (!tool.decrementUses(event.getPlayer())) return;
        if (!tool.incrementBlocksMined(event.getPlayer(), smelted)) return;
    }
}
