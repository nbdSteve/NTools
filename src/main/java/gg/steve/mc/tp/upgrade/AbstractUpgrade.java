package gg.steve.mc.tp.upgrade;

import gg.steve.mc.tp.tool.LoadedTool;
import gg.steve.mc.tp.utils.ColorUtil;
import gg.steve.mc.tp.managers.PluginFile;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractUpgrade {
    private CurrencyType currency;
    private Map<Integer, List<Object>> track;
    private boolean enabled;
    private String updateString;

    public AbstractUpgrade(CurrencyType currency, PluginFile file, boolean enabled) {
        this.currency = currency;
        this.track = new HashMap<>();
        int pos = 0;
        for (String entry : file.get().getStringList("upgrade.track")) {
            track.put(pos, new ArrayList<>());
            String[] parts = entry.split(":");
            track.get(pos).add(parts[0]);
            track.get(pos).add(parts[1]);
            track.get(pos).add(parts[2]);
            pos++;
        }
        this.enabled = enabled;
        this.updateString = ColorUtil.colorize(file.get().getString("upgrade.lore-update-string"));
    }

    public double getModifierForLevel(int level) {
        if (this.track == null || this.track.isEmpty() || !track.containsKey(level)) return 0.0;
        return Double.parseDouble((String) this.track.get(level).get(0));
    }

    public int getIntegerModifierForLevel(int level) {
        if (this.track == null || this.track.isEmpty() || !track.containsKey(level)) return 0;
        return Integer.parseInt((String) this.track.get(level).get(0));
    }

    public double getUpgradePriceForLevel(int level) {
        if (this.track == null || this.track.isEmpty() || !track.containsKey(level)) return 0.0;
        return Double.parseDouble((String) this.track.get(level).get(1));
    }

    public String getLoreStringForLevel(int level) {
        if (this.track == null || this.track.isEmpty() || !track.containsKey(level)) return "debug";
        return (String) this.track.get(level).get(2);
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getUpdateString() {
        return updateString;
    }

    public int getMaxLevel() {
        return track.size() - 1;
    }

    public abstract boolean doUpgrade(Player player, LoadedTool tool);
}