package gg.steve.mc.tp.tool;

import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.upgrade.AbstractUpgrade;
import gg.steve.mc.tp.utils.LogUtil;
import gg.steve.mc.tp.utils.PluginFile;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public abstract class AbstractTool {
    private ToolType type;
    private ToolData data;
    private AbstractUpgrade upgrade;
    private NBTItem item;
    private PluginFile config;

    public AbstractTool(ToolType type, AbstractUpgrade upgrade, NBTItem item, PluginFile config) {
        this.type = type;
        this.upgrade = upgrade;
        this.item = item;
        this.config = config;
    }

    public ToolType getType() {
        return this.type;
    }

    public void setData(ToolData data) {
        this.data = data;
    }

    public YamlConfiguration getConfig() {
        return this.config.get();
    }

    public ToolData getData() {
        return data;
    }

    public AbstractUpgrade getUpgrade() {
        return upgrade;
    }

    public ItemStack getItemStack() {
        item.setString("tools+.uuid", String.valueOf(UUID.randomUUID()));
        return item.getItem();
    }

    public NBTItem getItem() {
        return item;
    }

    public int getMaxUpgradeLevel() {
        return this.upgrade.getMaxLevel();
    }

    public abstract boolean isRadius();

    public abstract boolean isUnlimitedUses();

    public abstract boolean isMultiplier();

    public abstract boolean isUpgradeable();

    public abstract boolean isTrackingBlocks();

    public abstract YamlConfiguration getModuleConfig();

    public abstract void loadToolData(PluginFile file);
}