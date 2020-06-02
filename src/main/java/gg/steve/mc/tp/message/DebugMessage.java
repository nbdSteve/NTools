package gg.steve.mc.tp.message;

import gg.steve.mc.tp.managers.Files;
import gg.steve.mc.tp.utils.ColorUtil;
import gg.steve.mc.tp.utils.actionbarapi.ActionBarAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public enum DebugMessage {
    INVALID_COMMAND("invalid-command"),
    INCORRECT_ARGS("incorrect-args"),
    RELOAD_ALL_MODULES("reload-all-modules", "{module-count}"),
    RELOAD_MODULE("reload-module", "{module}"),
    LIST_MODULES("list-modules", "{modules-number}", "{modules-list}"),
    MODULE_INFO("module-info", "{module}", "{author}", "{version}", "{identifier}", "{tool-amount}"),
    MODULE_NOT_INSTALLED("module-not-installed"),
    MODULE_ALREADY_INSTALLED("module-already-installed"),
    INVALID_MODULE("invalid-module"),
    MODULE_INSTALLED("module-installed", "{module}"),
    MODULE_UN_INSTALLED("module-un-installed", "{module}"),
    INSUFFICIENT_PERMISSION("insufficient-permission", "{node}");

    private String path;
    private boolean actionBar;
    private List<String> placeholders;

    DebugMessage(String path, String... placeholders) {
        this.path = path;
        this.placeholders = Arrays.asList(placeholders);
        this.actionBar = Files.MESSAGES.get().getBoolean(this.path + ".action-bar");
    }

    public void message(Player receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.actionBar) {
            for (String line : Files.DEBUG.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                ActionBarAPI.sendActionBar(receiver, ColorUtil.colorize(line));
            }
        } else {
            for (String line : Files.DEBUG.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                receiver.sendMessage(ColorUtil.colorize(line));
            }
        }
    }

    public void message(CommandSender receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.actionBar && receiver instanceof Player) {
            for (String line : Files.DEBUG.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replaceAll(this.placeholders.get(i), data.get(i));
                }
                ActionBarAPI.sendActionBar((Player) receiver, ColorUtil.colorize(line));
            }
        } else {
            for (String line : Files.DEBUG.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                receiver.sendMessage(ColorUtil.colorize(line));
            }
        }
    }
}
