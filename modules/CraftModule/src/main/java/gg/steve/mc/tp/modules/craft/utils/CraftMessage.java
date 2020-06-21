package gg.steve.mc.tp.modules.craft.utils;

import gg.steve.mc.tp.managers.FileManager;
import gg.steve.mc.tp.modules.craft.CraftModule;
import gg.steve.mc.tp.utils.ColorUtil;
import gg.steve.mc.tp.utils.actionbarapi.ActionBarAPI;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public enum CraftMessage {
    CRAFTED("craft", "{amount}"),
    NONE_CRAFTED("none-crafted");

    private String path;
    private boolean actionBar;
    private List<String> placeholders;

    CraftMessage(String path, String... placeholders) {
        this.path = path;
        this.placeholders = Arrays.asList(placeholders);
        this.actionBar = FileManager.get(CraftModule.moduleConfigId).getBoolean("messages." + this.path + ".action-bar");
    }

    public void message(Player receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.actionBar) {
            for (String line : FileManager.get(CraftModule.moduleConfigId).getStringList("messages." + this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                ActionBarAPI.sendActionBar(receiver, ColorUtil.colorize(line));
            }
        } else {
            for (String line : FileManager.get(CraftModule.moduleConfigId).getStringList("messages." + this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                receiver.sendMessage(ColorUtil.colorize(line));
            }
        }
    }
}
