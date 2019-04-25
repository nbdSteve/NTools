package dev.nuer.nt.method;

import dev.nuer.nt.ToolsPlus;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that handles creating internal maps for things like: switching tool mode lores,
 * increasing price modifiers / tool radius.
 */
public class CreateInternalMaps {

    public static HashMap<Integer, ArrayList<String>> createUniqueModifierIDs(String directory, String filePath, String modifierType) {
        HashMap<Integer, ArrayList<String>> toolUniqueModifierIDs = new HashMap<>();
        for (int i = 1; i <= toolUniqueModifierIDs.size() + 1; i++) {
            if (ToolsPlus.getFiles().get(directory).getString(filePath + i + "." + modifierType + ".unique") != null) {
                ArrayList<String> toolModifierIDs = new ArrayList<>();
                toolModifierIDs.add(ChatColor.translateAlternateColorCodes('&',
                        ToolsPlus.getFiles().get(directory).getString(filePath + i + "." + modifierType + ".unique")));
                for (String modifierID : ToolsPlus.getFiles().get(directory).getStringList(filePath + i + "." + modifierType + ".lore-ids")) {
                    toolModifierIDs.add(ChatColor.translateAlternateColorCodes('&', modifierID));
                }
                toolModifierIDs.add(ToolsPlus.getFiles().get(directory).getString(filePath + i + "." + modifierType + ".max"));
                toolModifierIDs.add(ToolsPlus.getFiles().get(directory).getString(filePath + i + "." + modifierType + ".min"));
                toolUniqueModifierIDs.put(i, toolModifierIDs);
            }
        }
        return toolUniqueModifierIDs;
    }

    public static HashMap<Integer, ArrayList<String>> createUniqueModeIDs(String directory, String filePath,
                                                                          String mode1, String mode2) {
        HashMap<Integer, ArrayList<String>> toolUniqueModeIDs = new HashMap<>();
        for (int i = 1; i <= toolUniqueModeIDs.size() + 1; i++) {
            if (ToolsPlus.getFiles().get(directory).getString(filePath + i + ".mode.unique") != null) {
                ArrayList<String> toolModeIDs = new ArrayList<>();
                toolModeIDs.add(ChatColor.translateAlternateColorCodes('&',
                        ToolsPlus.getFiles().get(directory).getString(filePath + i + ".mode.unique")));
                toolModeIDs.add(ChatColor.translateAlternateColorCodes('&',
                        ToolsPlus.getFiles().get(directory).getString(filePath + i + ".mode." + mode1)));
                toolModeIDs.add(ChatColor.translateAlternateColorCodes('&',
                        ToolsPlus.getFiles().get(directory).getString(filePath + i + ".mode." + mode2)));
                toolUniqueModeIDs.put(i, toolModeIDs);
            }
        }
        return toolUniqueModeIDs;
    }

    public static HashMap<String, Double> createBlockPrices(String directory, String filePath) {
        HashMap<String, Double> blockPrices = new HashMap<>();
        for (String block : ToolsPlus.getFiles().get(directory).getStringList(filePath)) {
            String[] blockAndPrice = block.split(":");
            blockPrices.put(blockAndPrice[0].toUpperCase(), Double.parseDouble(blockAndPrice[1]));
        }
        return blockPrices;
    }
}
