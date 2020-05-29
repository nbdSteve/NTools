package gg.steve.mc.tp.modules.trench.tool;

import gg.steve.mc.tp.message.GeneralMessage;
import gg.steve.mc.tp.tool.LoadedTool;
import gg.steve.mc.tp.tool.ToolData;
import gg.steve.mc.tp.utils.CubeUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TrenchData implements ToolData {

    public TrenchData() {
    }

    @Override
    public void onBlockBreak(BlockBreakEvent blockBreakEvent, LoadedTool loadedTool) {
        List<Block> blocks = CubeUtil.getCube(blockBreakEvent.getBlock(), loadedTool.getIntegerModifier(), loadedTool.getModeTypeString());
        if (!blocks.contains(blockBreakEvent.getBlock())) blockBreakEvent.setCancelled(true);
        if (blocks.isEmpty()) return;
        if (!loadedTool.decrementUses(blockBreakEvent.getPlayer())) return;
        boolean full;
        if (full = (blockBreakEvent.getPlayer().getInventory().firstEmpty() == -1)) {
            GeneralMessage.INVENTORY_FULL.message(blockBreakEvent.getPlayer());
        }
        for (Block block : blocks) {
            // if the player is using silk touch give them items accordingly, adds items straight to inventory
            if (!full) {
                if (blockBreakEvent.getPlayer().getItemInHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
                    blockBreakEvent.getPlayer().getInventory().addItem(new ItemStack(block.getType(), 1, block.getData()));
                } else {
                    for (ItemStack item : block.getDrops(blockBreakEvent.getPlayer().getItemInHand())) {
                        blockBreakEvent.getPlayer().getInventory().addItem(item);
                    }
                }
            }
            // clear drops and remove the block
            block.getDrops().clear();
            block.setType(Material.AIR);
        }
        if (!loadedTool.incrementBlocksMined(blockBreakEvent.getPlayer(), blocks.size())) return;
    }
}
