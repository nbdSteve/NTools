package gg.steve.mc.tp.gui.types;

import gg.steve.mc.tp.gui.AbstractGui;
import gg.steve.mc.tp.tool.LoadedTool;
import gg.steve.mc.tp.utils.CommandUtil;
import gg.steve.mc.tp.gui.utils.GuiItemUtil;
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
                    item = GuiItemUtil.createConditionalItem(section.getConfigurationSection(entry), tool);
                    for (Integer slot : slots) {
                        setItemInSlot(slot, item, player -> {
                            CommandUtil.execute(section.getStringList(entry + ".commands"), player);
                            if (tool.getUpgradeLevel() + 1 != GuiItemUtil.getConditionLevel(section.getConfigurationSection(entry)))
                                return;
                            if (!tool.getAbstractTool().getUpgrade().doUpgrade(player, tool)) {
                                player.closeInventory();
                                return;
                            }
                            refresh(tool);
                        });
                    }
                    break;
                case "mode-switch":
                    item = GuiItemUtil.createItem(section.getConfigurationSection(entry), tool);
                    for (Integer slot : slots) {
                        setItemInSlot(slot, item, player -> {
                            CommandUtil.execute(section.getStringList(entry + ".commands"), player);
                            if (!tool.switchMode(player)) {
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
