package gg.steve.mc.tp.modules.trench.tool;

import gg.steve.mc.tp.integration.SellIntegrationManager;
import gg.steve.mc.tp.message.GeneralMessage;
import gg.steve.mc.tp.mode.ModeType;
import gg.steve.mc.tp.modules.trench.TrenchModule;
import gg.steve.mc.tp.tool.LoadedTool;
import gg.steve.mc.tp.tool.ToolData;
import gg.steve.mc.tp.utils.CubeUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TrenchData implements ToolData {

    public TrenchData() {
    }

    @Override
    public void onBlockBreak(BlockBreakEvent blockBreakEvent, LoadedTool loadedTool) {
        List<Block> blocks = CubeUtil.getCube(blockBreakEvent.getPlayer(), blockBreakEvent.getBlock(), loadedTool.getRadius(), TrenchModule.moduleId, false);
        if (!blocks.contains(blockBreakEvent.getBlock())) blockBreakEvent.setCancelled(true);
        if (blocks.isEmpty()) return;
        if (loadedTool.isOnCooldown(blockBreakEvent.getPlayer())) return;
        if (!loadedTool.decrementUses(blockBreakEvent.getPlayer())) return;
        boolean full = blockBreakEvent.getPlayer().getInventory().firstEmpty() == -1,
                autoSell = loadedTool.getModeChange(ModeType.SELL).getCurrentModeString(loadedTool.getCurrentMode(ModeType.SELL)).equalsIgnoreCase("sell"),
                silk = blockBreakEvent.getPlayer().getItemInHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH);
        if (full) {
            GeneralMessage.INVENTORY_FULL.message(blockBreakEvent.getPlayer());
        } else if (autoSell) {
            SellIntegrationManager.doBlockSale(blockBreakEvent.getPlayer(), blocks, loadedTool, silk);
        } else {
            for (Block block : blocks) {
                // if the player is using silk touch give them items accordingly, adds items straight to inventory
                if (!full) {
                    if (silk) {
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
        }
        if (!loadedTool.incrementBlocksMined(blockBreakEvent.getPlayer(), blocks.size() - 1)) return;
    }

    @Override
    public void onInteract(PlayerInteractEvent playerInteractEvent, LoadedTool loadedTool) {

    }
}