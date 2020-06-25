package gg.steve.mc.tp.modules.smelt.utils;

import gg.steve.mc.tp.framework.utils.ColorUtil;
import gg.steve.mc.tp.framework.utils.actionbarapi.ActionBarAPI;
import gg.steve.mc.tp.framework.yml.utils.FileManagerUtil;
import gg.steve.mc.tp.modules.smelt.SmeltModule;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public enum SmeltMessage {
    SMELTED("smelt", "{amount}"),
    NONE_SMELTED("none-smelted");

    private String path;
    private boolean actionBar;
    private List<String> placeholders;

    SmeltMessage(String path, String... placeholders) {
        this.path = path;
        this.placeholders = Arrays.asList(placeholders);
        this.actionBar = FileManagerUtil.get(SmeltModule.moduleConfigId).getBoolean("messages." + this.path + ".action-bar");
    }

    public void message(Player receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.actionBar) {
            for (String line : FileManagerUtil.get(SmeltModule.moduleConfigId).getStringList("messages." + this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                ActionBarAPI.sendActionBar(receiver, ColorUtil.colorize(line));
            }
        } else {
            for (String line : FileManagerUtil.get(SmeltModule.moduleConfigId).getStringList("messages." + this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                receiver.sendMessage(ColorUtil.colorize(line));
            }
        }
    }
}
