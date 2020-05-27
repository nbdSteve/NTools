package gg.steve.mc.tp.utils;

import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.module.ModuleType;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import gg.steve.mc.tp.tool.ToolType;
import gg.steve.mc.tp.upgrade.AbstractUpgrade;
import gg.steve.mc.tp.upgrade.VaultUpgradeType;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

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
        loadToolData();
    }

    public void loadItem(ModuleType module) {
        ConfigurationSection section = file.get().getConfigurationSection("item");
        ItemBuilderUtil builder;
        if (section.getString("material").startsWith("hdb")) {
            String[] parts = section.getString("material").split("-");
            try {
                builder = new ItemBuilderUtil(new HeadDatabaseAPI().getItemHead(parts[1]));
            } catch (NullPointerException e) {
                try {
                    builder = new ItemBuilderUtil(new ItemStack(Material.valueOf("SKULL_ITEM")));
                } catch (Exception e1) {
                    builder = new ItemBuilderUtil(new ItemStack(Material.valueOf("LEGACY_SKULL_ITEM")));
                }
            }
        } else {
            builder = new ItemBuilderUtil(section.getString("material"), section.getString("data"));
        }
        builder.addName(section.getString("name"));
        builder.setLorePlaceholders("{radius}", "{uses}", "{mined}");
        builder.addLore(section.getStringList("lore"),
                String.valueOf(file.get().getInt("starting-radius")),
                String.valueOf(file.get().getInt("uses.starting")),
                "0");
        builder.addEnchantments(section.getStringList("enchantments"));
        builder.addItemFlags(section.getStringList("item-flags"));
        builder.addNBT(module, this.name, file);
        this.item = builder.getNbtItem();
    }

    public void loadUpgrade() {
        this.upgrade = new VaultUpgradeType();
    }

    public void loadToolData() {
        tool.loadToolData(this.file);
    }

    public AbstractTool getTool() {
        return tool;
    }
}
