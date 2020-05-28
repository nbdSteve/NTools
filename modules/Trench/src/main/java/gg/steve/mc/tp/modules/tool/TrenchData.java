package gg.steve.mc.tp.modules.tool;

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
    public void onBlockBreak(BlockBreakEvent event, LoadedTool tool) {
        if (!tool.decrementUses(event.getPlayer())) return;
        List<Block> blocks = CubeUtil.getCube(event.getBlock(), tool.getAbstractTool().getUpgrade().getIntegerModifierForLevel(tool.getUpgradeLevel()));
        for (Block block : blocks) {
            // if the player is using silk touch give them items accordingly, adds items straight to inventory
            if (event.getPlayer().getItemInHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
                event.getPlayer().getInventory().addItem(new ItemStack(block.getType(), 1, block.getData()));
            } else {
                for (ItemStack item : block.getDrops(event.getPlayer().getItemInHand())) {
                    event.getPlayer().getInventory().addItem(item);
                }
            }
            // clear drops and remove the block
            block.getDrops().clear();
            block.setType(Material.AIR);
        }
        if (!tool.incrementBlocksMined(event.getPlayer(), blocks.size())) return;
    }
}
