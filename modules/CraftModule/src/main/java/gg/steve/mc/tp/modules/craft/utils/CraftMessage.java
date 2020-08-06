package gg.steve.mc.tp.modules.craft.utils;

import gg.steve.mc.tp.framework.utils.ColorUtil;
import gg.steve.mc.tp.framework.utils.actionbarapi.ActionBarAPI;
import gg.steve.mc.tp.framework.yml.utils.FileManagerUtil;
import gg.steve.mc.tp.modules.craft.CraftModule;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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
        this.actionBar = FileManagerUtil.get(CraftModule.moduleConfigId).getBoolean("messages." + this.path + ".action-bar");
    }

    public void message(Player receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.actionBar) {
            for (String line : FileManagerUtil.get(CraftModule.moduleConfigId).getStringList("messages." + this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                if (!ActionBarAPI.sendActionBar(receiver, ColorUtil.colorize(line))) {
                    receiver.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ColorUtil.colorize(line)));
                }
            }
        } else {
            for (String line : FileManagerUtil.get(CraftModule.moduleConfigId).getStringList("messages." + this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                receiver.sendMessage(ColorUtil.colorize(line));
            }
        }
    }
}
