package dev.nuer.nt.method;

import dev.nuer.nt.NTools;
import org.bukkit.ChatColor;

import java.util.HashMap;

/**
 * Creates the unique lore line for a multi tool
 */
public class GetMultiToolUnique {

    /**
     * Returns a HashMap with all of the unique lore lines for a given multi tool
     *
     * @param filePath   the first part of the path to get from the tools.yml
     * @param filePathID the second part of the path to get from the tools.yml
     * @return HashMap<Integer, String>
     */
    public static HashMap<Integer, String> createUniqueLore(String filePath, String filePathID) {
        HashMap<Integer, String> toolUniqueLore = new HashMap<>();
        for (int i = 1; i <= toolUniqueLore.size() + 1; i++) {
            if (NTools.getFiles().get("tools").getString(filePath + i + filePath) != null) {
                toolUniqueLore.put(i, ChatColor.translateAlternateColorCodes('&', NTools.getFiles().get(
                        "tools").getString(filePath + i + filePathID)));
            }
        }
        return toolUniqueLore;
    }
}
