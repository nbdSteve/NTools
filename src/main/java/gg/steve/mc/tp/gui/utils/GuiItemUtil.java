package gg.steve.mc.tp.gui.utils;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.mode.ModeType;
import gg.steve.mc.tp.tool.LoadedTool;
import gg.steve.mc.tp.upgrade.UpgradeType;
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
        builder.setLorePlaceholders("{radius-current-upgrade}",
                "{radius-next-upgrade}",
                "{radius-upgrade-cost}",
                "{radius-upgrade-level}",
                "{radius-upgrade-max}",
                "{radius-upgrade-currency-prefix}",
                "{radius-upgrade-currency-suffix}",
                "{modifier-current-upgrade}",
                "{modifier-next-upgrade}",
                "{modifier-upgrade-cost}",
                "{modifier-upgrade-level}",
                "{modifier-upgrade-max}",
                "{modifier-upgrade-currency-prefix}",
                "{modifier-upgrade-currency-suffix}",
                "{tool-current-mode}",
                "{tool-next-mode}",
                "{tool-mode-change-cost}",
                "{tool-mode-currency-prefix}",
                "{tool-mode-currency-suffix}");
        LogUtil.info(tool.getModeChange(ModeType.TOOL).getCurrency().getNiceName());
        builder.addLore(section.getStringList("lore"),
                tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getLoreStringForLevel(tool.getUpgradeLevel(UpgradeType.RADIUS)),
                tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getLoreStringForLevel(tool.getUpgradeLevel(UpgradeType.RADIUS) + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getUpgradePriceForLevel(tool.getUpgradeLevel(UpgradeType.RADIUS))),
                ToolsPlus.formatNumber(tool.getUpgradeLevel(UpgradeType.RADIUS) + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getMaxLevel() + 1),
                tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getCurrency().getPrefix(),
                tool.getAbstractTool().getUpgrade(UpgradeType.RADIUS).getCurrency().getSuffix(),
                tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getLoreStringForLevel(tool.getUpgradeLevel(UpgradeType.MODIFIER)),
                tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getLoreStringForLevel(tool.getUpgradeLevel(UpgradeType.MODIFIER) + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getUpgradePriceForLevel(tool.getUpgradeLevel(UpgradeType.MODIFIER))),
                ToolsPlus.formatNumber(tool.getUpgradeLevel(UpgradeType.MODIFIER) + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getMaxLevel() + 1),
                tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getCurrency().getPrefix(),
                tool.getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getCurrency().getSuffix(),
                tool.getModeChange(ModeType.TOOL).getCurrentModeLore(tool.getCurrentMode(ModeType.TOOL)),
                tool.getModeChange(ModeType.TOOL).getNextModeLore(tool.getCurrentMode(ModeType.TOOL)),
                ToolsPlus.formatNumber(tool.getModeChange(ModeType.TOOL).getChangePriceForMode(tool.getCurrentMode(ModeType.TOOL))),
                tool.getModeChange(ModeType.TOOL).getCurrency().getPrefix(),
                tool.getModeChange(ModeType.TOOL).getCurrency().getSuffix());
        builder.addEnchantments(section.getStringList("enchantments"));
        builder.addItemFlags(section.getStringList("item-flags"));
        builder.addNBT();
        return builder.getItem();
    }

    public static ItemStack createConditionalItem(ConfigurationSection section, LoadedTool tool, UpgradeType upgrade) {
        String condition = "true";
        if (!isConditionMet(section, tool, upgrade)) condition = "false";
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
                tool.getAbstractTool().getUpgrade(upgrade).getLoreStringForLevel(tool.getUpgradeLevel(upgrade)),
                tool.getAbstractTool().getUpgrade(upgrade).getLoreStringForLevel(tool.getUpgradeLevel(upgrade) + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade(upgrade).getUpgradePriceForLevel(getConditionLevel(section))),
                ToolsPlus.formatNumber(tool.getUpgradeLevel(upgrade) + 1),
                ToolsPlus.formatNumber(tool.getAbstractTool().getUpgrade(upgrade).getMaxLevel() + 1),
                tool.getAbstractTool().getUpgrade(upgrade).getCurrency().getPrefix(),
                tool.getAbstractTool().getUpgrade(upgrade).getCurrency().getSuffix(),
                tool.getModeChange(ModeType.TOOL).getCurrentModeLore(tool.getCurrentMode(ModeType.TOOL)),
                tool.getModeChange(ModeType.TOOL).getNextModeLore(tool.getCurrentMode(ModeType.TOOL)),
                ToolsPlus.formatNumber(tool.getModeChange(ModeType.TOOL).getChangePriceForMode(tool.getCurrentMode(ModeType.TOOL))),
                tool.getModeChange(ModeType.TOOL).getCurrency().getPrefix(),
                tool.getModeChange(ModeType.TOOL).getCurrency().getSuffix());
        builder.addEnchantments(section.getStringList(condition + ".enchantments"));
        builder.addItemFlags(section.getStringList(condition + ".item-flags"));
        builder.addNBT();
        return builder.getItem();
    }

    public static boolean isConditionMet(ConfigurationSection section, LoadedTool tool, UpgradeType upgrade) {
        if (section.getString("action").split(":")[1].equalsIgnoreCase("upgrade")) {
            if (tool.getPeakUpgradeLevel(upgrade) >= getConditionLevel(section)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public static int getConditionLevel(ConfigurationSection section) {
        return Integer.parseInt(section.getString("action").split(":")[3]);
    }
}
