package gg.steve.mc.tp.tool.utils;

import gg.steve.mc.tp.gui.GuiManager;
import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.module.ModuleType;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import gg.steve.mc.tp.tool.ToolType;
import gg.steve.mc.tp.upgrade.AbstractUpgrade;
import gg.steve.mc.tp.upgrade.types.BlocksMinedUpgrade;
import gg.steve.mc.tp.upgrade.types.VaultUpgradeType;
import gg.steve.mc.tp.upgrade.types.XpLevelUpgrade;
import gg.steve.mc.tp.upgrade.types.XpRawUpgrade;
import gg.steve.mc.tp.utils.ItemBuilderUtil;
import org.bukkit.configuration.ConfigurationSection;

public class ToolLoaderUtil {
    private PluginFile file;
    private String name;
    private AbstractTool tool;
    private AbstractUpgrade upgrade;
    private NBTItem item;

    public ToolLoaderUtil(PluginFile file, String name) {
        this.file = file;
        this.name = name;
        loadUpgrade();
        ModuleType module = ModuleType.getModuleForTool(file);
        loadItem(module);
        tool = ModuleManager.getInstalledModule(module).loadTool(ToolType.valueOf(module.name()), this.upgrade, this.item, file);
        tool.setUpgradeGui(GuiManager.getGui(file.get().getString("upgrade.gui")));
//        tool.setUsesGui(GuiManager.getGui(file.get().getString("uses.gui")));
//        tool.setModeGui(GuiManager.getGui(file.get().getString("mode.gui")));
        loadToolData();
    }

    public void loadItem(ModuleType module) {
        ConfigurationSection section = this.file.get().getConfigurationSection("item");
        ItemBuilderUtil builder = ItemBuilderUtil.getBuilderForMaterial(section.getString("material"), section.getString("data"));
        builder.addName(section.getString("name"));
        builder.setLorePlaceholders("{upgrade}", "{uses}", "{mined}", "{mode}");
        builder.addLore(section.getStringList("lore"),
                this.upgrade.getLoreStringForLevel(0),
                String.valueOf(file.get().getInt("uses.starting")),
                "0", file.get().getStringList("mode.track").get(0).split(":")[2]);
        builder.addEnchantments(section.getStringList("enchantments"));
        builder.addItemFlags(section.getStringList("item-flags"));
        builder.addNBT(module, this.name, this.file);
        this.item = builder.getNbtItem();
    }

    public void loadUpgrade() {
        boolean enabled = this.file.get().getBoolean("upgrade.enabled");
        switch (this.file.get().getString("upgrade.currency")) {
            case "vault":
                this.upgrade = new VaultUpgradeType(this.file, enabled);
                break;
            case "blocks-mined":
                this.upgrade = new BlocksMinedUpgrade(this.file, enabled);
                break;
            case "xp-raw":
                this.upgrade = new XpRawUpgrade(this.file, enabled);
                break;
            case "xp-level":
                this.upgrade = new XpLevelUpgrade(this.file, enabled);
                break;
        }
    }

    public void loadToolData() {
        tool.loadToolData(this.file);
    }

    public AbstractTool getTool() {
        return tool;
    }
}
