package gg.steve.mc.tp.gui.utils;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.tool.LoadedTool;
import gg.steve.mc.tp.utils.ItemBuilderUtil;
import gg.steve.mc.tp.utils.LogUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class GuiItemUtil {

    public static ItemStack createItem(ConfigurationSection section) {
        ItemBuilderUtil builder = ItemBuilderUtil.getBuilderForMaterial(section.getString("material"), section.getString("data"));
        builder.addName(section.getString("name"));
        builder.addLore(section.getStringList("lore"));
        builder.addEnchantments(section.getStringList("enchantments"));
        builder.addItemFlags(section.getStringList("item-flags"));
        builder.addNBT();
        return builder.getItem();
    }

    public static ItemStack createItem(ConfigurationSection section, LoadedTool tool) {
        ItemBuilderUtil builder = ItemBuilderUtil.getBuilderForMaterial(section.getString("material"), section.getString("data"));
        builder.addName(section.getString("name"));
        builder.addName(section.getString("name"));
        builder.setLorePlaceholders("{current-upgrade}",
                "{next-upgrade}",
                "{upgrade-cost}",
                "{upgrade-level}",
                "{upgrade-max}",
                "{upgrade-currency-prefix}",
                "{upgrade-currency-suffix}",
                "{current-mode}",
                "{next-mode}",
                "{mode-change-cost}",
                "{mode-currency-prefix}",
                "{mode-currency-suffix}");
        builder.addLore(section.getStringList("lore"),
                tool.getAbstractTool().getUpgrade().getLoreStringForLevel(tool.getUpgradeLevel()),
                tool.getAbstractTool().getUpgrade().getLoreStringForLevel(tool.getUpgradeLevel() + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade().getUpgradePriceForLevel(tool.getUpgradeLevel())),
                ToolsPlus.formatNumber(tool.getUpgradeLevel() + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getMaxUpgradeLevel() + 1),
                tool.getAbstractTool().getUpgrade().getCurrency().getPrefix(),
                tool.getAbstractTool().getUpgrade().getCurrency().getSuffix(),
                tool.getCurrentModeLore(),
                tool.getNextModeLore(),
                ToolsPlus.formatNumber(tool.getModeChangePrice()),
                tool.getModeCurrency().getPrefix(),
                tool.getModeCurrency().getSuffix());
        builder.addEnchantments(section.getStringList("enchantments"));
        builder.addItemFlags(section.getStringList("item-flags"));
        builder.addNBT();
        return builder.getItem();
    }

    public static ItemStack createConditionalItem(ConfigurationSection section, LoadedTool tool) {
        String condition = "true";
        if (!isConditionMet(section, tool)) condition = "false";
        ItemBuilderUtil builder = ItemBuilderUtil.getBuilderForMaterial(section.getString(condition + ".material"), section.getString(condition + ".data"));
        builder.addName(section.getString(condition + ".name"));
        builder.setLorePlaceholders("{current-upgrade}",
                "{next-upgrade}",
                "{upgrade-cost}",
                "{upgrade-level}",
                "{upgrade-max}",
                "{upgrade-currency-prefix}",
                "{upgrade-currency-suffix}",
                "{current-mode}",
                "{next-mode}",
                "{mode-change-cost}",
                "{mode-currency-prefix}",
                "{mode-currency-suffix}");
        builder.addLore(section.getStringList(condition + ".lore"),
                tool.getAbstractTool().getUpgrade().getLoreStringForLevel(tool.getUpgradeLevel()),
                tool.getAbstractTool().getUpgrade().getLoreStringForLevel(tool.getUpgradeLevel() + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade().getUpgradePriceForLevel(getConditionLevel(section))),
                ToolsPlus.formatNumber(tool.getUpgradeLevel() + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getMaxUpgradeLevel() + 1),
                tool.getAbstractTool().getUpgrade().getCurrency().getPrefix(),
                tool.getAbstractTool().getUpgrade().getCurrency().getSuffix(),
                tool.getCurrentModeLore(),
                tool.getNextModeLore(),
                ToolsPlus.formatNumber(tool.getModeChangePrice()),
                tool.getModeCurrency().getPrefix(),
                tool.getModeCurrency().getSuffix());
        builder.addEnchantments(section.getStringList(condition + ".enchantments"));
        builder.addItemFlags(section.getStringList(condition + ".item-flags"));
        builder.addNBT();
        return builder.getItem();
    }

    public static boolean isConditionMet(ConfigurationSection section, LoadedTool tool) {
        if (section.getString("action").split(":")[1].equalsIgnoreCase("upgrade")) {
            if (tool.getUpgradeLevel() >= getConditionLevel(section)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public static int getConditionLevel(ConfigurationSection section) {
        return Integer.parseInt(section.getString("action").split(":")[2]);
    }
}
