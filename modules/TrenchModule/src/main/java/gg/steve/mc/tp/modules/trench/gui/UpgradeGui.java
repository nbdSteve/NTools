package gg.steve.mc.tp.modules.trench.gui;

import gg.steve.mc.tp.gui.AbstractGui;
import gg.steve.mc.tp.tool.LoadedTool;
import gg.steve.mc.tp.utils.GuiItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class UpgradeGui extends AbstractGui {
    private ConfigurationSection section;
    
    public UpgradeGui(ConfigurationSection section) {
        super(section, section.getString("type"), section.getInt("size"));
        this.section = section;
        List<Integer> slots = section.getIntegerList("fillers.slots");
        ItemStack filler = GuiItemUtil.createItem(section.getConfigurationSection("fillers"));
        for (Integer slot : slots) {
            setItemInSlot(slot, filler, player -> {
            });
        }
    }

    @Override
    public void refresh(LoadedTool tool) {
        for (String entry : section.getKeys(false)) {
            try {
                Integer.parseInt(entry);
            } catch (NumberFormatException e) {
                continue;
            }
            List<Integer> slots = section.getIntegerList(entry + ".slots");
            if (!GuiItemUtil.isConditional(section.getConfigurationSection(entry))) {
                ItemStack item = GuiItemUtil.createItem(section.getConfigurationSection(entry));
                for (Integer slot : slots) {
                    setItemInSlot(slot, item, player -> {
                    });
                }
                break;
            }
            boolean isConditionMet = GuiItemUtil.isConditionMet(section.getConfigurationSection(entry), tool);
            ItemStack item = GuiItemUtil.createConditionalItem(section.getConfigurationSection(entry), tool);
            for (Integer slot : slots) {
                setItemInSlot(slot, item, player -> {
                    if ((isConditionMet && !section.getBoolean(entry + ".true.upgrade.enabled"))
                    || (!isConditionMet && !section.getBoolean(entry + ".false.upgrade.enabled"))) return;
                    if (tool.getUpgradeLevel() + 1 != GuiItemUtil.getConditionLevel(section.getConfigurationSection(entry))) return;
                    if (!tool.getAbstractTool().getUpgrade().doUpgrade(player, tool)) {
                        player.closeInventory();
                        return;
                    }
                    refresh(tool);
                });
            }
        }
    }
}
