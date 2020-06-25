package gg.steve.mc.tp.modules.tnt.utils;

import gg.steve.mc.tp.framework.yml.utils.FileManagerUtil;
import gg.steve.mc.tp.modules.tnt.TntModule;
import gg.steve.mc.tp.framework.utils.ColorUtil;
import gg.steve.mc.tp.framework.utils.actionbarapi.ActionBarAPI;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public enum TntMessage {
    DEPOSIT("deposit", "{amount}"),
    NO_FACTION("no-faction"),
    FACTIONS_PLUGIN_NOT_SUPPORTED("factions-plugin-not-supported"),
    NO_TNT("no-tnt");

    private String path;
    private boolean actionBar;
    private List<String> placeholders;

    TntMessage(String path, String... placeholders) {
        this.path = path;
        this.placeholders = Arrays.asList(placeholders);
        this.actionBar = FileManagerUtil.get(TntModule.moduleConfigId).getBoolean("messages." + this.path + ".action-bar");
    }

    public void message(Player receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.actionBar) {
            for (String line : FileManagerUtil.get(TntModule.moduleConfigId).getStringList("messages." + this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                ActionBarAPI.sendActionBar(receiver, ColorUtil.colorize(line));
            }
        } else {
            for (String line : FileManagerUtil.get(TntModule.moduleConfigId).getStringList("messages." + this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                receiver.sendMessage(ColorUtil.colorize(line));
            }
        }
    }
}