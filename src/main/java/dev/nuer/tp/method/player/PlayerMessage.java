package dev.nuer.tp.method.player;

import dev.nuer.tp.ToolsPlus;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Class that handles sending messages to a player
 */
public class PlayerMessage {

    /**
     * Send a default message to the player
     *
     * @param filePath String, internal message path
     * @param player   Player, the player to send to
     */
    public PlayerMessage(String filePath, Player player) {
        for (String line : ToolsPlus.getFiles().get("messages").getStringList(filePath)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
        }
    }

    /**
     * Send a message that has a placeholder in it
     *
     * @param filePath    String, internal message path
     * @param player      Player, the player to send to
     * @param placeHolder String, the thing to replace
     * @param replacement String, the replacement
     */
    public PlayerMessage(String filePath, Player player, String placeHolder, String replacement) {
        for (String line : ToolsPlus.getFiles().get("messages").getStringList(filePath)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', line).replace(placeHolder,
                    replacement));
        }
    }

    /**
     * Send a message that has 2 placeholders in it
     *
     * @param filePath     String, internal message path
     * @param player       Player, the player to send to
     * @param placeHolder  String, the thing to replace
     * @param replacement  String, the replacement
     * @param placeHolder2 String, the second thing to replace
     * @param replacement2 String, the second replacement
     */
    public PlayerMessage(String filePath, Player player, String placeHolder, String replacement, String placeHolder2, String replacement2) {
        for (String line : ToolsPlus.getFiles().get("messages").getStringList(filePath)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', line).replace(placeHolder,
                    replacement).replace(placeHolder2, replacement2));
        }
    }
}
