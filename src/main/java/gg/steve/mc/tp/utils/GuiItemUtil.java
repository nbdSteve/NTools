package gg.steve.mc.tp.utils;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.tool.LoadedTool;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class GuiItemUtil {

    public static ItemStack createItem(ConfigurationSection section) {
            ItemBuilderUtil builder = new ItemBuilderUtil(section.getString("material"), section.getString("data"));
            builder.addName(section.getString("name"));
            builder.addLore(section.getStringList("lore"));
            builder.addEnchantments(section.getStringList("enchantments"));
            builder.addItemFlags(section.getStringList("item-flags"));
            builder.addNBT();
            return builder.getItem();
    }

    public static ItemStack createConditionalItem(ConfigurationSection section, LoadedTool tool) {
        String condition = "true";
        if (!isConditionMet(section, tool)) condition = "false";
        int level = getConditionLevel(section);
        ItemBuilderUtil builder = new ItemBuilderUtil(section.getString(condition + ".material"), section.getString(condition + ".data"));
        builder.addName(section.getString(condition + ".name"));
        builder.setLorePlaceholders("{cost}", "{upgrade-level}", "{upgrade-max}");
        builder.addLore(section.getStringList(condition + ".lore"),
                ToolsPlus.formatNumber((float) tool.getAbstractTool().getUpgrade().getUpgradePriceForLevel(level)),
                ToolsPlus.formatNumber(tool.getUpgradeLevel()),
                ToolsPlus.formatNumber(tool.getAbstractTool().getMaxUpgradeLevel()));
        builder.addEnchantments(section.getStringList(condition + ".enchantments"));
        builder.addItemFlags(section.getStringList(condition + ".item-flags"));
        builder.addNBT();
        return builder.getItem();
    }

    public static boolean isConditional(ConfigurationSection section) {
        return section.getBoolean("conditional.enabled");
    }

    public static boolean isConditionMet(ConfigurationSection section, LoadedTool tool) {
        if (section.getBoolean("conditional.enabled")) {
            if (section.getString("conditional.condition").startsWith("upgrade")) {
                if (tool.getUpgradeLevel() >= getConditionLevel(section)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public static int getConditionLevel(ConfigurationSection section) {
        return Integer.parseInt(section.getString("conditional.condition").split(":")[1]);
    }
}
