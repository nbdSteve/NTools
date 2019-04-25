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
     * @param filePath the message to be sent
     * @param p        the player to send to
     */
    public PlayerMessage(String filePath, Player p) {
        for (String line : ToolsPlus.getFiles().get("messages").getStringList(filePath)) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
        }
    }

    /**
     * Send a message that has a placeholder in it
     *
     * @param filePath    the message to be sent
     * @param p           the player to send to
     * @param placeHolder the thing to replace
     * @param replacement the replacement
     */
    public PlayerMessage(String filePath, Player p, String placeHolder, String replacement) {
        for (String line : ToolsPlus.getFiles().get("messages").getStringList(filePath)) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', line).replace(placeHolder,
                    replacement));
        }
    }
}
