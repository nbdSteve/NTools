package gg.steve.mc.tp.modules.harvester.tool;

import gg.steve.mc.tp.attribute.ToolAttributeType;
import gg.steve.mc.tp.integration.sell.SellIntegrationManager;
import gg.steve.mc.tp.framework.message.GeneralMessage;
import gg.steve.mc.tp.mode.ModeType;
import gg.steve.mc.tp.modules.harvester.utils.CollectionUtil;
import gg.steve.mc.tp.modules.harvester.utils.HarvestableBlockType;
import gg.steve.mc.tp.tool.PlayerTool;
import gg.steve.mc.tp.tool.ToolData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HarvesterHoeData implements ToolData {
    private static Map<UUID, CollectionUtil> playerCollectionUtils;

    public static void initialise() {
        playerCollectionUtils = new HashMap<>();
    }

    public static CollectionUtil getCollectionUtil(Player player, PlayerTool tool) {
        if (!playerCollectionUtils.containsKey(player.getUniqueId()))
            playerCollectionUtils.put(player.getUniqueId(), new CollectionUtil(player, tool));
        return playerCollectionUtils.get(player.getUniqueId());
    }

    public static void shutdown() {
        if (playerCollectionUtils != null && !playerCollectionUtils.isEmpty()) playerCollectionUtils.clear();
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, PlayerTool tool) {
        event.setCancelled(true);
        List<Block> blocks = getCollectionUtil(event.getPlayer(), tool).getCropBlocks(event.getBlock());
        if (blocks.isEmpty()) return;
        if (tool.isOnCooldown(event.getPlayer())) return;
        if (!tool.decrementUses(event.getPlayer())) return;
        boolean full = event.getPlayer().getInventory().firstEmpty() == -1,
                autoSell = tool.getModeChange(ModeType.SELL).getCurrentModeString(tool.getCurrentMode(ModeType.SELL)).equalsIgnoreCase("sell"),
                silk = event.getPlayer().getItemInHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH),
                playersGetDrops = tool.getAbstractTool().isPlayersGetDrops();
        if (full) {
            GeneralMessage.INVENTORY_FULL.message(event.getPlayer());
        }
        if (autoSell) SellIntegrationManager.doBlockSale(event.getPlayer(), blocks, tool, silk, false);
        for (Block block : blocks) {
            // clear drops and remove the block
            BlockState crop = block.getState();
            HarvestableBlockType cropType = HarvestableBlockType.getTypeFromBlock(block);
            if (cropType.getAttributes().isAutoReplant()
                    && tool.getAbstractTool().getAttributeManager().isAttributeEnabled(ToolAttributeType.AUTO_REPLANT)) {
                switch (cropType) {
                    case CROPS:
                    case POTATO:
                    case CARROT:
                    case NETHER_WART:
                        crop.setRawData((byte) 0);
                        crop.update();
                        break;
                    case COCOA:
                        crop.setRawData((byte) (crop.getRawData() - 8));
                        crop.update();
                        break;
                    default:
                        block.getDrops().clear();
                        block.setType(Material.AIR);
                }
            } else {
                block.getDrops().clear();
                block.setType(Material.AIR);
            }
            if (playersGetDrops && !autoSell) {
                if (silk && cropType == HarvestableBlockType.MELON) {
                    event.getPlayer().getInventory().addItem(new ItemStack(Material.valueOf("MELON_BLOCK")));
                } else {
                    event.getPlayer().getInventory().addItem(cropType.getAttributes().getItemDrop());
                }
            }
        }
        if (!tool.incrementCaneMined(event.getPlayer(), getCollectionUtil(event.getPlayer(), tool).getCaneMined()))
            return;
        if (!tool.incrementBlocksMined(event.getPlayer(), blocks.size() - 1)) return;
    }

    @Override
    public void onInteract(PlayerInteractEvent playerInteractEvent, PlayerTool playerTool) {

    }
}
