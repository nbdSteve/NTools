package gg.steve.mc.tp.tool;

import gg.steve.mc.tp.attribute.ToolAttributeType;
import gg.steve.mc.tp.nbt.NBTItem;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LoadedTool {
    private AbstractTool tool;
    private UUID toolId;
    private int uses, blocksMined, upgradeLevel;
    private String name;

    public LoadedTool(UUID toolId, NBTItem item) {
        this.toolId = toolId;
        this.uses = item.getInteger("tools+.uses");
        this.blocksMined = item.getInteger("tools+.blocks");
        this.upgradeLevel = item.getInteger("tools+.upgrade-level");
        this.name = item.getString("tools+.name");
        this.tool = ToolsManager.getTool(this.name);
    }

    public boolean decrementUses(Player player) {
        if (!tool.getAttributeManager().isAttributeEnabled(ToolAttributeType.USES)) return true;
        NBTItem nbtItem = new NBTItem(player.getItemInHand());
        int current = this.uses;
        int change = -1;
        this.uses += change;
        return tool.getAttributeManager().getAttribute(ToolAttributeType.USES).doUpdate(player, nbtItem, this.toolId, current, change);
    }

    public boolean incrementBlocksMined(Player player, int amount) {
        if (!tool.getAttributeManager().isAttributeEnabled(ToolAttributeType.BLOCKS_MINED)) return true;
        NBTItem nbtItem = new NBTItem(player.getItemInHand());
        int current = this.blocksMined;
        this.blocksMined += amount;
        return tool.getAttributeManager().getAttribute(ToolAttributeType.BLOCKS_MINED).doUpdate(player, nbtItem, this.toolId, current, amount);
    }

    public AbstractTool getAbstractTool() {
        return tool;
    }

    public String getName() {
        return name;
    }

    public int getUpgradeLevel() {
        return upgradeLevel;
    }

    public void setUpgradeLevel(int upgradeLevel) {
        this.upgradeLevel = upgradeLevel;
    }

    public UUID getToolId() {
        return toolId;
    }

    public int getUses() {
        return uses;
    }

    public int getBlocksMined() {
        return blocksMined;
    }
}
