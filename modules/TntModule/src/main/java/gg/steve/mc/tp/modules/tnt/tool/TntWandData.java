package gg.steve.mc.tp.modules.tnt.tool;

import gg.steve.mc.tp.modules.tnt.TntModule;
import gg.steve.mc.tp.modules.tnt.utils.TntBankUtil;
import gg.steve.mc.tp.modules.tnt.utils.TntMessage;
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

public class TntWandData implements ToolData {
    
    @Override
    public void onBlockBreak(BlockBreakEvent blockBreakEvent, PlayerTool playerTool) {
        
    }

    @Override
    public void onInteract(PlayerInteractEvent event, PlayerTool tool) {
        if (event.getClickedBlock() == null || event.getClickedBlock().getType().equals(Material.AIR)) return;
        event.setCancelled(true);
        List<Block> containers = CubeUtil.getCube(event.getPlayer(), event.getClickedBlock(), tool.getRadius(), TntModule.moduleId, true);
        if (containers.isEmpty()) return;
        List<Inventory> inventories = new ArrayList<>();
        for (Block block : containers) {
            if (!(block.getState() instanceof InventoryHolder)) continue;
            Inventory inventory = ((InventoryHolder) block.getState()).getInventory();
            if (!inventories.contains(inventory)) inventories.add(inventory);
        }
        if (inventories.isEmpty()) return;
        int amount = TntBankUtil.doTntDeposit(event.getPlayer(), inventories, tool);
        if (amount == -3){
            TntMessage.FACTIONS_PLUGIN_NOT_SUPPORTED.message(event.getPlayer());
            return;
        } else if (amount == -2) {
            TntMessage.NO_FACTION.message(event.getPlayer());
            return;
        } else if (amount == 0) return;
        if (!tool.decrementUses(event.getPlayer())) return;
        if (!tool.incrementBlocksMined(event.getPlayer(), amount)) return;
    }
}
