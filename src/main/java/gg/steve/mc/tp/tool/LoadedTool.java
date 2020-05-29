package gg.steve.mc.tp.tool;

import gg.steve.mc.tp.attribute.ToolAttributeType;
import gg.steve.mc.tp.attribute.types.ModeSwitchToolAttribute;
import gg.steve.mc.tp.gui.AbstractGui;
import gg.steve.mc.tp.managers.Files;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.upgrade.CurrencyType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LoadedTool {
    private AbstractTool tool;
    private UUID toolId;
    private int uses, blocksMined, upgradeLevel, modeLevel;
    private String name;
    private AbstractGui upgradeGui, usesGui, modeGui;

    public LoadedTool(UUID toolId, NBTItem item) {
        this.toolId = toolId;
        this.uses = item.getInteger("tools+.uses");
        this.blocksMined = item.getInteger("tools+.blocks");
        this.upgradeLevel = item.getInteger("tools+.upgrade-level");
        this.modeLevel = item.getInteger("tools+.mode-level");
        this.name = item.getString("tools+.name");
        this.tool = ToolsManager.getTool(this.name);
        this.upgradeGui = this.tool.getUpgradeGui();
        this.usesGui = this.tool.getUsesGui();
        this.modeGui = this.tool.getModeGui();
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

    public boolean switchMode(Player player) {
        if (!tool.getModeAttribute().isChangingEnabled()) return true;
        NBTItem nbtItem = new NBTItem(player.getItemInHand());
        int current = this.modeLevel;
        int next = tool.getModeAttribute().getNextMode(this.modeLevel);
        if (!tool.getAttributeManager().getAttribute(ToolAttributeType.MODE_SWITCH).doUpdate(player, nbtItem, this.toolId, current, next)) {
            return false;
        } else {
            this.modeLevel = next;
            return true;
        }
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

    public String getModeTypeString() {
        return tool.getModeAttribute().getCurrentModeString(this.modeLevel);
    }

    public String getCurrentModeLore() {
        return tool.getModeAttribute().getCurrentModeLore(this.modeLevel);
    }

    public String getNextModeLore() {
        if (!tool.getModeAttribute().isChangingEnabled()) return Files.CONFIG.get().getString("no-mode-change-placeholder");
        return tool.getModeAttribute().getNextModeLore(this.modeLevel);
    }

    public CurrencyType getModeCurrency() {
        if (!tool.getModeAttribute().isChangingEnabled()) return CurrencyType.NONE;
        return tool.getModeAttribute().getCurrency();
    }

    public double getModeChangePrice() {
        if (!tool.getModeAttribute().isChangingEnabled()) return 0;
        return tool.getModeAttribute().getSwitchPriceForMode(this.modeLevel);
    }

    public int getIntegerModifier() {
        return tool.getUpgrade().getIntegerModifierForLevel(this.upgradeLevel);
    }

    public boolean openUpgradeGui(Player player) {
        if (this.upgradeGui == null) return false;
        this.upgradeGui.refresh(this);
        this.upgradeGui.open(player);
        return true;
    }

    public boolean openUsesGui(Player player) {
        if (this.usesGui == null) return false;
        this.usesGui.refresh(this);
        this.usesGui.open(player);
        return true;
    }

    public boolean openModeGui(Player player) {
        if (this.modeGui == null) return false;
        this.modeGui.refresh(this);
        this.modeGui.open(player);
        return true;
    }
}
