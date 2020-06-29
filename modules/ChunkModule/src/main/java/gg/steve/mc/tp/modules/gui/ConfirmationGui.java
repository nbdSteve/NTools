package gg.steve.mc.tp.modules.gui;

import gg.steve.mc.tp.framework.gui.AbstractGui;
import gg.steve.mc.tp.framework.gui.utils.GuiItemUtil;
import gg.steve.mc.tp.framework.utils.CommandUtil;
import gg.steve.mc.tp.framework.utils.TPSUtil;
import gg.steve.mc.tp.framework.yml.utils.FileManagerUtil;
import gg.steve.mc.tp.modules.ChunkModule;
import gg.steve.mc.tp.modules.constants.ConfigConstants;
import gg.steve.mc.tp.modules.message.ChunkMessage;
import gg.steve.mc.tp.modules.mangers.ChunkRemovalManager;
import gg.steve.mc.tp.player.PlayerToolManager;
import gg.steve.mc.tp.tool.PlayerTool;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ConfirmationGui extends AbstractGui {
    private ConfigurationSection section = FileManagerUtil.get(ChunkModule.moduleConfigId).getConfigurationSection("confirmation-gui");

    public ConfirmationGui() {
        super(FileManagerUtil.get(ChunkModule.moduleConfigId).getConfigurationSection("confirmation-gui"),
                FileManagerUtil.get(ChunkModule.moduleConfigId).getString("confirmation-gui.type"),
                FileManagerUtil.get(ChunkModule.moduleConfigId).getInt("confirmation-gui.size"));
        List<Integer> slots = section.getIntegerList("fillers.slots");
        ItemStack filler = GuiItemUtil.createItem(section.getConfigurationSection("fillers"));
        for (Integer slot : slots) {
            setItemInSlot(slot, filler, player -> {
            });
        }
    }

    public void refresh(PlayerTool tool, Block clicked) {
        for (String entry : section.getKeys(false)) {
            try {
                Integer.parseInt(entry);
            } catch (NumberFormatException e) {
                continue;
            }
            ItemStack item;
            List<Integer> slots = section.getIntegerList(entry + ".slots");
            switch (section.getString(entry + ".action").split(":")[0]) {
                case "confirm":
                    item = GuiItemUtil.createItem(section.getConfigurationSection(entry));
                    for (Integer slot : slots) {
                        setItemInSlot(slot, item, player -> {
                            if (PlayerToolManager.isHoldingTool(player.getUniqueId()) && PlayerToolManager.getToolPlayer(player.getUniqueId()).getPlayerTool().getToolId().equals(tool.getToolId())) {
                                if (TPSUtil.getTPS() < ConfigConstants.minTPS) {
                                    player.closeInventory();
                                    ChunkMessage.TPS_TOO_LOW.message(player);
                                    return;
                                }
                                if (!ChunkRemovalManager.canAddTask(player.getUniqueId())) {
                                    player.closeInventory();
                                    ChunkMessage.MAX_TASKS_ACTIVE.message(player);
                                    return;
                                }
                                ChunkRemovalManager.doChunkRemoval(player, clicked, tool);
                                player.closeInventory();
                            } else {
                                player.closeInventory();
                                ChunkMessage.NOT_HOLDING_SAME_TOOL.message(player);
                                return;
                            }
                        });
                    }
                    break;
                case "decline":
                    item = GuiItemUtil.createItem(section.getConfigurationSection(entry));
                    for (Integer slot : slots) {
                        setItemInSlot(slot, item, player -> {
                            player.closeInventory();
                            ChunkMessage.DECLINE.message(player);
                            return;
                        });
                    }
                    break;
                case "none":
                default:
                    item = GuiItemUtil.createItem(section.getConfigurationSection(entry));
                    for (Integer slot : slots) {
                        setItemInSlot(slot, item, player -> {});
                    }
                    break;
            }
        }
    }

    @Override
    public void refresh(PlayerTool playerTool) {
    }
}
