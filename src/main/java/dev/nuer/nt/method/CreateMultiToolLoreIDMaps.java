package dev.nuer.nt.method;

import dev.nuer.nt.NTools;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Creates the unique lore line for a multi tool
 */
public class CreateMultiToolLoreIDMaps {

//    /**
//     * Returns a HashMap with all of the unique lore lines for a given multi tool
//     *
//     * @param filePath   the first part of the path to get from the tools.yml
//     * @return HashMap<Integer, ArrayList<String>>
//     */
//    public static HashMap<Integer, ArrayList<String>> createUniqueRadiusIDs(String filePath) {
//        HashMap<Integer, ArrayList<String>> toolUniqueRadiusIDs = new HashMap<>();
//        for (int i = 1; i <= toolUniqueRadiusIDs.size() + 1; i++) {
//            if (NTools.getFiles().get("multi").getString(filePath + i + ".radius.unique") != null) {
//                ArrayList<String> multiToolIDs = new ArrayList<>();
//                multiToolIDs.add(ChatColor.translateAlternateColorCodes('&',
//                        NTools.getFiles().get("multi").getString(filePath + i + ".radius.unique")));
//                for (String radiusID : NTools.getFiles().get("multi").getStringList(filePath + i + ".radius.radius-ids")) {
//                    multiToolIDs.add(ChatColor.translateAlternateColorCodes('&', radiusID));
//                }
//                multiToolIDs.add(NTools.getFiles().get("multi").getString(filePath + i + ".radius.min"));
//                multiToolIDs.add(NTools.getFiles().get("multi").getString(filePath + i + ".radius.max"));
//                toolUniqueRadiusIDs.put(i, multiToolIDs);
//            }
//        }
//        return toolUniqueRadiusIDs;
//    }
//
//    /**
//     * Returns a HashMap with all of the unique lore lines for a given multi tool
//     *
//     * @param filePath   the first part of the path to get from the tools.yml
//     * @return HashMap<Integer, ArrayList<String>>
//     */
//    public static HashMap<Integer, ArrayList<String>> createUniqueModeIDs(String filePath) {
//        HashMap<Integer, ArrayList<String>> toolUniqueModeIDs = new HashMap<>();
//        for (int i = 1; i <= toolUniqueModeIDs.size() + 1; i++) {
//            if (NTools.getFiles().get("multi").getString(filePath + i + ".mode.unique") != null) {
//                ArrayList<String> multiToolIDs = new ArrayList<>();
//                multiToolIDs.add(ChatColor.translateAlternateColorCodes('&',
//                        NTools.getFiles().get("multi").getString(filePath + i + ".mode.unique")));
//                multiToolIDs.add(ChatColor.translateAlternateColorCodes('&',
//                        NTools.getFiles().get("multi").getString(filePath + i + ".mode.trench")));
//                multiToolIDs.add(ChatColor.translateAlternateColorCodes('&',
//                        NTools.getFiles().get("multi").getString(filePath + i + ".mode.tray")));
//                toolUniqueModeIDs.put(i, multiToolIDs);
//            }
//        }
//        return toolUniqueModeIDs;
//    }
}
