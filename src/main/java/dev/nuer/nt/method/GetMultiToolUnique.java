package dev.nuer.nt.method;

import dev.nuer.nt.NTools;
import org.bukkit.ChatColor;

import java.util.HashMap;

public class GetMultiToolUnique {

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
