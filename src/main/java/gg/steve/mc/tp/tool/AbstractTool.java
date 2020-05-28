package gg.steve.mc.tp.tool;

import gg.steve.mc.tp.attribute.ToolAttributeManager;
import gg.steve.mc.tp.gui.AbstractGui;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.upgrade.AbstractUpgrade;
import gg.steve.mc.tp.managers.PluginFile;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public abstract class AbstractTool {
    private ToolType type;
    private ToolData data;
    private AbstractUpgrade upgrade;
    private NBTItem item;
    private PluginFile config;
    private ToolAttributeManager attributeManager;
    private AbstractGui upgradeGui;

    public AbstractTool(ToolType type, AbstractUpgrade upgrade, NBTItem item, PluginFile config) {
        this.type = type;
        this.upgrade = upgrade;
        this.item = item;
        this.config = config;
        this.attributeManager = new ToolAttributeManager();
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

    public void setUpgradeGui(AbstractGui gui) {
        this.upgradeGui = gui;
    }

    public AbstractGui getUpgradeGui() {
        return upgradeGui;
    }

    public ToolAttributeManager getAttributeManager() {
        return attributeManager;
    }

    public abstract YamlConfiguration getModuleConfig();

    public abstract void loadToolData(PluginFile file);
}