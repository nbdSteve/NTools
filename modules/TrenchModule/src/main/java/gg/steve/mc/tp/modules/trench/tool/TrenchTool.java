package gg.steve.mc.tp.modules.trench.tool;

import gg.steve.mc.tp.attribute.types.BlocksMinedToolAttribute;
import gg.steve.mc.tp.attribute.types.UsesToolAttribute;
import gg.steve.mc.tp.managers.Files;
import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.mode.types.SellModeChange;
import gg.steve.mc.tp.mode.types.ToolTypeModeChange;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import gg.steve.mc.tp.tool.ToolType;
import gg.steve.mc.tp.upgrade.types.ModifierUpgrade;
import gg.steve.mc.tp.upgrade.types.RadiusUpgrade;
import org.bukkit.configuration.file.YamlConfiguration;

public class TrenchTool extends AbstractTool {

    public TrenchTool(NBTItem item, PluginFile file) {
        super(ToolType.TRENCH, item, file);
        YamlConfiguration config = file.get();
        if (config.getBoolean("uses.enabled")) {
            getAttributeManager().addToolAttribute(new UsesToolAttribute(config.getString("uses.lore-update-string")));
        }
        if (config.getBoolean("blocks-mined.enabled")) {
            getAttributeManager().addToolAttribute(new BlocksMinedToolAttribute(config.getString("blocks-mined.lore-update-string")));
        }
        getUpgradeManager().addToolUpgrade(new RadiusUpgrade(file));
        getUpgradeManager().addToolUpgrade(new ModifierUpgrade(file));
        getModeChangeManager().addToolMode(new ToolTypeModeChange(file));
        getModeChangeManager().addToolMode(new SellModeChange(file));
    }

    @Override
    public YamlConfiguration getModuleConfig() {
        return Files.TRENCH_CONFIG.get();
    }

    @Override
    public void loadToolData(PluginFile pluginFile) {
        setData(new TrenchData());
    }
}
