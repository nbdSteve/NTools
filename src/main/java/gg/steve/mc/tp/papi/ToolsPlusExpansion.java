package gg.steve.mc.tp.papi;

import com.sun.imageio.spi.RAFImageInputStreamSpi;
import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.managers.Files;
import gg.steve.mc.tp.mode.ModeType;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.player.PlayerToolManager;
import gg.steve.mc.tp.player.ToolPlayer;
import gg.steve.mc.tp.tool.ToolsManager;
import gg.steve.mc.tp.upgrade.UpgradeType;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ToolsPlusExpansion extends PlaceholderExpansion {
    private JavaPlugin instance;

    public ToolsPlusExpansion(JavaPlugin instance) {
        this.instance = instance;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return instance.getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier() {
        return "tools+";
    }

    @Override
    public String getVersion() {
        return instance.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (identifier.contains("player")) {
            String NA = Files.CONFIG.get().getString("not-holding-tool-placeholder");
            if (!PlayerToolManager.isHoldingTool(player.getUniqueId())) {
                return NA;
            }
            ToolPlayer toolPlayer = PlayerToolManager.getToolPlayer(player.getUniqueId());
            if (identifier.equalsIgnoreCase("player_module")) {
                return toolPlayer.getLoadedTool().getAbstractTool().getModule().getNiceName();
            }
            if (identifier.equalsIgnoreCase("player_radius_lore")) {
                return toolPlayer.getLoadedTool().getAbstractTool().getUpgrade(UpgradeType.RADIUS).getLoreStringForLevel(toolPlayer.getLoadedTool().getUpgradeLevel(UpgradeType.RADIUS));
            }
            if (identifier.equalsIgnoreCase("player_radius_level")) {
                return ToolsPlus.formatNumber(toolPlayer.getLoadedTool().getUpgradeLevel(UpgradeType.RADIUS) + 1);
            }
            if (identifier.equalsIgnoreCase("player_radius_next_price")) {
//                return ToolsPlus.formatNumber(toolPlayer.getLoadedTool().getNextUpgradePrice(UpgradeType.RADIUS, toolPlayer.getLoadedTool().getUpgradeLevel(UpgradeType.RADIUS)));
                return ToolsPlus.formatNumber(toolPlayer.getLoadedTool().getAbstractTool().getUpgrade(UpgradeType.RADIUS).getUpgradePriceForLevel(toolPlayer.getLoadedTool().getUpgradeLevel(UpgradeType.RADIUS)));
            }
            if (identifier.equalsIgnoreCase("player_radius_max")) {
                return ToolsPlus.formatNumber(toolPlayer.getLoadedTool().getAbstractTool().getUpgrade(UpgradeType.RADIUS).getMaxLevel() + 1);
            }
            if (identifier.equalsIgnoreCase("player_modifier_lore")) {
                return toolPlayer.getLoadedTool().getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getLoreStringForLevel(toolPlayer.getLoadedTool().getUpgradeLevel(UpgradeType.MODIFIER));
            }
            if (identifier.equalsIgnoreCase("player_modifier_level")) {
                return ToolsPlus.formatNumber(toolPlayer.getLoadedTool().getUpgradeLevel(UpgradeType.MODIFIER) + 1);
            }
            if (identifier.equalsIgnoreCase("player_modifier_next_price")) {
                return ToolsPlus.formatNumber(toolPlayer.getLoadedTool().getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getUpgradePriceForLevel(toolPlayer.getLoadedTool().getUpgradeLevel(UpgradeType.MODIFIER)));
            }
            if (identifier.equalsIgnoreCase("player_modifier_max")) {
                return ToolsPlus.formatNumber(toolPlayer.getLoadedTool().getAbstractTool().getUpgrade(UpgradeType.MODIFIER).getMaxLevel() + 1);
            }
            if (identifier.equalsIgnoreCase("player_mode_tool_lore")) {
                return toolPlayer.getLoadedTool().getModeChange(ModeType.TOOL).getCurrentModeLore(toolPlayer.getLoadedTool().getCurrentMode(ModeType.TOOL));
            }
            if (identifier.equalsIgnoreCase("player_mode_sell_lore")) {
                return toolPlayer.getLoadedTool().getModeChange(ModeType.SELL).getCurrentModeLore(toolPlayer.getLoadedTool().getCurrentMode(ModeType.SELL));
            }
            if (identifier.equalsIgnoreCase("player_uses")) {
                return ToolsPlus.formatNumber(toolPlayer.getLoadedTool().getUses());
            }
            if (identifier.equalsIgnoreCase("player_blocks_mined")) {
                return ToolsPlus.formatNumber(toolPlayer.getLoadedTool().getBlocksMined());
            }
        } else {
            if (identifier.equalsIgnoreCase("module_count")) {
                return ModuleManager.getModuleCount();
            }
            if (identifier.equalsIgnoreCase("module_list")) {
                return ModuleManager.getModulesAsList();
            }
            if (identifier.equalsIgnoreCase("tool_count")) {
                return ToolsManager.getAbstractToolCount();
            }
            if (identifier.equalsIgnoreCase("tool_list")) {
                return ToolsManager.getAbstractToolsAsList();
            }
            if (identifier.equalsIgnoreCase("version")) {
                return instance.getDescription().getVersion();
            }
            if (identifier.equalsIgnoreCase("loaded_tool_count")) {
                return ToolsManager.getPlayerToolCount();
            }
        }
        return "debug";
    }
}
