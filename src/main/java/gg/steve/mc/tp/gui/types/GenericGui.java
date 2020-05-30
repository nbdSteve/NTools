package gg.steve.mc.tp.gui.types;

import gg.steve.mc.tp.gui.AbstractGui;
import gg.steve.mc.tp.gui.utils.GuiItemUtil;
import gg.steve.mc.tp.mode.ModeType;
import gg.steve.mc.tp.tool.LoadedTool;
import gg.steve.mc.tp.upgrade.UpgradeType;
import gg.steve.mc.tp.utils.CommandUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GenericGui extends AbstractGui {
    private ConfigurationSection section;

    public GenericGui(ConfigurationSection section) {
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
            ItemStack item;
            List<Integer> slots = section.getIntegerList(entry + ".slots");
            switch (section.getString(entry + ".action").split(":")[0]) {
                case "condition":
                    switch (section.getString(entry + ".action").split(":")[1]) {
                        case "upgrade":
                            UpgradeType upgrade = UpgradeType.valueOf(section.getString(entry + ".action").split(":")[2].toUpperCase());
                            item = GuiItemUtil.createConditionalItem(section.getConfigurationSection(entry), tool, upgrade);
                            for (Integer slot : slots) {
                                setItemInSlot(slot, item, player -> {
                                    CommandUtil.execute(section.getStringList(entry + ".commands"), player);
                                    if (tool.getPeakUpgradeLevel(upgrade) > GuiItemUtil.getConditionLevel(section.getConfigurationSection(entry))
                                    || tool.getPeakUpgradeLevel(upgrade) >= tool.getAbstractTool().getUpgrade(upgrade).getMaxLevel()) {
                                        if (!tool.getAbstractTool().getUpgrade(upgrade).doUpgrade(player, tool)) {
                                            player.closeInventory();
                                            return;
                                        }
                                    }
                                    if (tool.getPeakUpgradeLevel(upgrade) + 1 != GuiItemUtil.getConditionLevel(section.getConfigurationSection(entry)))
                                        return;
                                    if (!tool.getAbstractTool().getUpgrade(upgrade).doUpgrade(player, tool)) {
                                        player.closeInventory();
                                        return;
                                    }
                                    refresh(tool);
                                });
                            }
                            break;
                        case "permission":
                            break;
                    }
                    break;
                case "mode-switch":
                    ModeType mode =ModeType.valueOf(section.getString(entry + ".action").split(":")[1].toUpperCase());
                    item = GuiItemUtil.createItem(section.getConfigurationSection(entry), tool);
                    for (Integer slot : slots) {
                        setItemInSlot(slot, item, player -> {
                            CommandUtil.execute(section.getStringList(entry + ".commands"), player);
                            if (!tool.getModeChange(mode).changeMode(player, tool)) {
                                player.closeInventory();
                            } else {
                                refresh(tool);
                            }
                        });
                    }
                    break;
                case "downgrade":
                    UpgradeType upgrade = UpgradeType.valueOf(section.getString(entry + ".action").split(":")[1].toUpperCase());
                    item = GuiItemUtil.createItem(section.getConfigurationSection(entry), tool);
                    for (Integer slot : slots) {
                        setItemInSlot(slot, item, player -> {
                            CommandUtil.execute(section.getStringList(entry + ".commands"), player);
                            if (!tool.getAbstractTool().getUpgrade(upgrade).isDowngrade()) return;
                            if (!tool.getAbstractTool().getUpgradeManager().getUpgrade(upgrade).doDowngrade(player, tool)) {
                                player.closeInventory();
                            } else {
                                refresh(tool);
                            }
                        });
                    }
                    break;
                case "uses":
                    break;
                case "back":
                    break;
                case "gui":
                    break;
                case "purchase":
                    break;
                case "close":
                    item = GuiItemUtil.createItem(section.getConfigurationSection(entry), tool);
                    for (Integer slot : slots) {
                        setItemInSlot(slot, item, player -> {
                            CommandUtil.execute(section.getStringList(entry + ".commands"), player);
                            player.closeInventory();
                        });
                    }
                    break;
                case "none":
                default:
                    item = GuiItemUtil.createItem(section.getConfigurationSection(entry), tool);
                    for (Integer slot : slots) {
                        setItemInSlot(slot, item, player -> CommandUtil.execute(section.getStringList(entry + ".commands"), player));
                    }
                    break;
            }
        }
    }
}
