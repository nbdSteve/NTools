package dev.nuer.nt.method;

import dev.nuer.nt.NTools;
import org.bukkit.ChatColor;

import java.util.HashMap;

/**
 * Class that handles generating the tools maps that are queried in the event class.
 */
public class AddToolsToMap {

    /**
     * Generates a HashMap of tools based on the filePath
     *
     * @param filePath String, path from the tools.yml
     * @return HashMap of tools and unique-lore
     */
    public static HashMap<Integer, String> createToolMap(String filePath) {
        HashMap<Integer, String> toolMap = new HashMap<>();
        for (int i = 1; i <= toolMap.size() + 1; i++) {
            if (NTools.getFiles().get("tools").getString(filePath + i + ".unique-lore") != null) {
                toolMap.put(i, ChatColor.translateAlternateColorCodes('&', NTools.getFiles().get("tools"
                ).getString(filePath + i + ".unique-lore")));
            }
        }
        return toolMap;
    }
}
