package gg.steve.mc.tp.permission;

import gg.steve.mc.tp.managers.Files;
import org.bukkit.command.CommandSender;

public enum PermissionNode {
    // cmd
    RELOAD("command.reload"),
    GIVE("command.give"),
    HELP("command.help"),
    MODULE_LIST("command.module-list"),
    TOOL_LIST("command.tool-list"),
    // gui
    USE("gui.use"),
    TRENCH("gui.trench");

    private String path;

    PermissionNode(String path) {
        this.path = path;
    }

    public String get() {
        return Files.PERMISSIONS.get().getString(this.path);
    }

    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(get());
    }

    public static boolean isPurchasePerms() {
        return Files.PERMISSIONS.get().getBoolean("purchase.enabled");
    }
}
