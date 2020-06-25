package gg.steve.mc.tp.modules.harvester.tool;

import gg.steve.mc.tp.attribute.types.*;
import gg.steve.mc.tp.framework.yml.PluginFile;
import gg.steve.mc.tp.mode.types.SellModeChange;
import gg.steve.mc.tp.mode.types.ToolTypeModeChange;
import gg.steve.mc.tp.modules.harvester.HarvesterModule;
import gg.steve.mc.tp.framework.nbt.NBTItem;
import gg.steve.mc.tp.tool.AbstractTool;
import gg.steve.mc.tp.upgrade.types.ModifierUpgrade;
import gg.steve.mc.tp.upgrade.types.RadiusUpgrade;
import org.bukkit.configuration.file.YamlConfiguration;

public class HarvesterHoe extends AbstractTool {
    
    public HarvesterHoe(NBTItem item, PluginFile file) {
        super(HarvesterModule.moduleId, item, file);
        YamlConfiguration config = file.get();
        if (config.getBoolean("uses.enabled")) {
            getAttributeManager().addToolAttribute(new UsesToolAttribute(config.getString("uses.lore-update-string")));
        }
        if (config.getBoolean("blocks-mined.enabled")) {
            getAttributeManager().addToolAttribute(new BlocksMinedToolAttribute(config.getString("blocks-mined.lore-update-string")));
        }
        if (config.getBoolean("omni.enabled")) {
            getAttributeManager().addToolAttribute(new OmniToolAttribute(""));
        }
        if (config.getBoolean("cooldown.enabled")) {
            getAttributeManager().addToolAttribute(new CooldownToolAttribute(config.getInt("cooldown.duration")));
        }
        if (config.getBoolean("auto-replant.enabled")) {
            getAttributeManager().addToolAttribute(new AutoReplantToolAttribute(""));
        }
        if (config.getBoolean("cane-mined.enabled")) {
            getAttributeManager().addToolAttribute(new CaneTrackingToolAttribute(config.getString("cane-mined.lore-update-string")));
        }
        getUpgradeManager().addToolUpgrade(new RadiusUpgrade(file));
        getUpgradeManager().addToolUpgrade(new ModifierUpgrade(file));
        getModeChangeManager().addToolMode(new ToolTypeModeChange(file));
        getModeChangeManager().addToolMode(new SellModeChange(file));
    }

    @Override
    public YamlConfiguration getModuleConfig() {
        return null;
    }

    @Override
    public void loadToolData(PluginFile pluginFile) {
        setData(new HarvesterHoeData());
    }
}
