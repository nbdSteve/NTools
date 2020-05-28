package gg.steve.mc.tp.modules.tool;

import gg.steve.mc.tp.attribute.types.BlocksMinedToolAttribute;
import gg.steve.mc.tp.attribute.types.UsesToolAttribute;
import gg.steve.mc.tp.managers.Files;
import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.modules.gui.UpgradeGui;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import gg.steve.mc.tp.tool.ToolType;
import gg.steve.mc.tp.upgrade.AbstractUpgrade;
import org.bukkit.configuration.file.YamlConfiguration;

public class TrenchTool extends AbstractTool {

    public TrenchTool(AbstractUpgrade upgrade, NBTItem item, PluginFile file) {
        super(ToolType.TRENCH, upgrade, item, file);
        YamlConfiguration config = file.get();
        if (config.getBoolean("uses.enabled")) {
            getAttributeManager().addToolAttribute(new UsesToolAttribute(config.getString("uses.lore-update-string")));
        }
        if (config.getBoolean("blocks-mined.enabled")) {
            getAttributeManager().addToolAttribute(new BlocksMinedToolAttribute(config.getString("blocks-mined.lore-update-string")));
        }
        setUpgradeGui(new UpgradeGui(file.get().getConfigurationSection("upgrade-gui")));
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
