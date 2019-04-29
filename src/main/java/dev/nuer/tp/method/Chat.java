package dev.nuer.tp.method;

import org.bukkit.ChatColor;

/**
 * Class that handles creating colorized messages
 */
public class Chat {

    /**
     * Converts a non colored String into a colored one
     *
     * @param message String, messages to colorize
     * @return String
     */
    public static String applyColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Strips a message of its color
     *
     * @param message String, the message to strip
     * @return String
     */
    public static String stipColor(String message) {
        return ChatColor.stripColor(message);
    }
}
