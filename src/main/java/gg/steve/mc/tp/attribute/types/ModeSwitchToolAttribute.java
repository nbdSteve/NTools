package gg.steve.mc.tp.attribute.types;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.attribute.AbstractToolAttribute;
import gg.steve.mc.tp.attribute.ToolAttributeType;
import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.message.GeneralMessage;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.upgrade.CurrencyType;
import gg.steve.mc.tp.tool.utils.GetToolHoldingUtil;
import gg.steve.mc.tp.utils.LogUtil;
import gg.steve.mc.tp.tool.utils.LoreUpdaterUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ModeSwitchToolAttribute extends AbstractToolAttribute {
    private Map<Integer, List<Object>> track;
    private boolean sneakSwitch;
    private CurrencyType currency;

    public ModeSwitchToolAttribute(String updateString) {
        super(ToolAttributeType.MODE_SWITCH, updateString);
    }

    public void loadData(PluginFile file) {
        track = new HashMap<>();
        int pos = 0;
        for (String entry : file.get().getStringList("mode.track")) {
            track.put(pos, new ArrayList<>());
            String[] parts = entry.split(":");
            track.get(pos).add(parts[0]);
            track.get(pos).add(parts[1]);
            track.get(pos).add(parts[2]);
            pos++;
        }
        this.sneakSwitch = file.get().getBoolean("mode.sneak-switch");
        try {
            this.currency = CurrencyType.valueOf(file.get().getString("mode.currency").toUpperCase());
        } catch (Exception e) {
            this.currency = CurrencyType.NONE;
        }
    }

    @Override
    public boolean doUpdate(Player player, NBTItem item, UUID toolId, int current, int next) {
        if (!item.getItem().hasItemMeta() || item.getItem().getItemMeta().getLore().isEmpty()) {
            LogUtil.warning("Tried to switch mode for a tool that doesn't have any lore! Aborting.");
            return false;
        }
        double cost = 0;
        if (currency.equals(CurrencyType.VAULT)) {
            cost = getSwitchPriceForMode(current);
            if (ToolsPlus.eco().getBalance(player) < cost) {
                GeneralMessage.INSUFFICIENT_FUNDS.message(player,
                        ToolsPlus.formatNumber(ToolsPlus.eco().getBalance(player)),
                        ToolsPlus.formatNumber(cost),
                        getCurrency().getPrefix(),
                        getCurrency().getSuffix());
                return false;
            }
            ToolsPlus.eco().withdrawPlayer(player, cost);
        }
        ItemStack updated = LoreUpdaterUtil.updateLore(item, "mode-level", next,
                getUpdateString().replace("{mode}", (String) track.get(current).get(2)),
                getUpdateString().replace("{mode}", (String) track.get(next).get(2)));
        if (GetToolHoldingUtil.isStillHoldingTool(toolId, player.getItemInHand())) {
            player.setItemInHand(updated);
            player.updateInventory();
            GeneralMessage.MODE_CHANGE.message(player,
                    (String) track.get(next).get(2),
                    ToolsPlus.formatNumber(cost),
                    this.currency.getPrefix(),
                    this.currency.getSuffix());
            return true;
        } else {
            LogUtil.warning("Mode switch dupe attempted by player: " + player.getName() + ", Tools+ has stopped the tool action from happening.");
            return false;
        }
    }

    public boolean isChangingEnabled() {
        return track.size() > 1;
    }

    public double getSwitchPriceForMode(int current) {
        return Double.parseDouble((String) track.get(getNextMode(current)).get(1));
    }

    public int getNextMode(int current) {
        if (current + 1 < track.size()) return current + 1;
        return 0;
    }

    public String getCurrentModeString(int current) {
        return (String) track.get(current).get(0);
    }

    public String getCurrentModeLore(int current) {
        return (String) track.get(current).get(2);
    }

    public String getNextModeLore(int current) {
        return (String) track.get(getNextMode(current)).get(2);
    }

    public boolean isSneakSwitch() {
        return sneakSwitch;
    }

    public CurrencyType getCurrency() {
        return this.currency;
    }
}
