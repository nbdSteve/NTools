package gg.steve.mc.tp.modules.tool;

import gg.steve.mc.tp.managers.Files;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import gg.steve.mc.tp.tool.ToolType;
import gg.steve.mc.tp.upgrade.AbstractUpgrade;
import gg.steve.mc.tp.utils.PluginFile;
import org.bukkit.configuration.file.YamlConfiguration;

public class TrenchTool extends AbstractTool {

    public TrenchTool(AbstractUpgrade upgrade, NBTItem item, PluginFile file) {
        super(ToolType.TRENCH, upgrade, item, file);
    }

    @Override
    public boolean isRadius() {
        return true;
    }

    @Override
    public boolean isUnlimitedUses() {
        return false;
    }

    @Override
    public boolean isMultiplier() {
        return false;
    }

    @Override
    public boolean isUpgradeable() {
        return false;
    }

    @Override
    public boolean isTrackingBlocks() {
        return true;
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
