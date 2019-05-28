package dev.nuer.tp.method.player;

import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.method.Chat;
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
        for (String line : FileManager.get("messages").getStringList(filePath)) {
            player.sendMessage(Chat.applyColor(line));
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
        for (String line : FileManager.get("messages").getStringList(filePath)) {
            player.sendMessage(Chat.applyColor(line).replace(placeHolder,
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
    public PlayerMessage(String filePath, Player player,
                         String placeHolder, String replacement,
                         String placeHolder2, String replacement2) {
        for (String line : FileManager.get("messages").getStringList(filePath)) {
            player.sendMessage(Chat.applyColor(line)
                    .replace(placeHolder, replacement)
                    .replace(placeHolder2, replacement2));
        }
    }

    /**
     * Send a message that has 3 placeholders in it
     *
     * @param filePath     String, internal message path
     * @param player       Player, the player to send to
     * @param placeHolder  String, the thing to replace
     * @param replacement  String, the replacement
     * @param placeHolder2 String, the second thing to replace
     * @param replacement2 String, the second replacement
     * @param placeHolder3 String, the third thing to replace
     * @param replacement3 String, the third replacement
     */
    public PlayerMessage(String filePath, Player player,
                         String placeHolder, String replacement,
                         String placeHolder2, String replacement2,
                         String placeHolder3, String replacement3) {
        for (String line : FileManager.get("messages").getStringList(filePath)) {
            player.sendMessage(Chat.applyColor(line)
                    .replace(placeHolder, replacement)
                    .replace(placeHolder2, replacement2)
                    .replace(placeHolder3, replacement3));
        }
    }

    /**
     * Send a message that has 4 placeholders in it
     *
     * @param filePath     String, internal message path
     * @param player       Player, the player to send to
     * @param placeHolder  String, the thing to replace
     * @param replacement  String, the replacement
     * @param placeHolder2 String, the second thing to replace
     * @param replacement2 String, the second replacement
     * @param placeHolder3 String, the third thing to replace
     * @param replacement3 String, the third replacement
     * @param placeHolder4 String, the fourth thing to replace
     * @param replacement4 String, the fourth replacement
     */
    public PlayerMessage(String filePath, Player player,
                         String placeHolder, String replacement,
                         String placeHolder2, String replacement2,
                         String placeHolder3, String replacement3,
                         String placeHolder4, String replacement4) {
        for (String line : FileManager.get("messages").getStringList(filePath)) {
            player.sendMessage(Chat.applyColor(line)
                    .replace(placeHolder, replacement)
                    .replace(placeHolder2, replacement2)
                    .replace(placeHolder3, replacement3)
                    .replace(placeHolder4, replacement4));
        }
    }
}
