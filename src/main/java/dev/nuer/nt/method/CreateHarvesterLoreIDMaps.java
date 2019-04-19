package dev.nuer.nt.method;

import dev.nuer.nt.NTools;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateHarvesterLoreIDMaps {

    public static HashMap<Integer, ArrayList<String>> createUniqueModifierIDs(String directory, String filePath) {
        HashMap<Integer, ArrayList<String>> toolUniqueModifierIDs = new HashMap<>();
        for (int i = 1; i <= toolUniqueModifierIDs.size() + 1; i++) {
            if (NTools.getFiles().get(directory).getString(filePath + i + ".modifier.unique") != null) {
                ArrayList<String> harvesterToolIDs = new ArrayList<>();
                harvesterToolIDs.add(ChatColor.translateAlternateColorCodes('&',
                        NTools.getFiles().get(directory).getString(filePath + i + ".modifier.unique")));
                for (String modifierID : NTools.getFiles().get(directory).getStringList(filePath + i + ".modifier.lore-ids")) {
                    harvesterToolIDs.add(ChatColor.translateAlternateColorCodes('&', modifierID));
                }
                harvesterToolIDs.add(NTools.getFiles().get(directory).getString(filePath + i + ".modifier.max"));
                toolUniqueModifierIDs.put(i, harvesterToolIDs);
            }
        }
        return toolUniqueModifierIDs;
    }

    public static HashMap<Integer, ArrayList<String>> createUniqueModeIDs(String filePath) {
        HashMap<Integer, ArrayList<String>> toolUniqueModeIDs = new HashMap<>();
        for (int i = 1; i <= toolUniqueModeIDs.size() + 1; i++) {
            if (NTools.getFiles().get("harvester").getString(filePath + i + ".mode.unique") != null) {
                ArrayList<String> harvesterToolIDs = new ArrayList<>();
                harvesterToolIDs.add(ChatColor.translateAlternateColorCodes('&',
                        NTools.getFiles().get("harvester").getString(filePath + i + ".mode.unique")));
                harvesterToolIDs.add(ChatColor.translateAlternateColorCodes('&',
                        NTools.getFiles().get("harvester").getString(filePath + i + ".mode.sell")));
                harvesterToolIDs.add(ChatColor.translateAlternateColorCodes('&',
                        NTools.getFiles().get("harvester").getString(filePath + i + ".mode.harvest")));
                toolUniqueModeIDs.put(i, harvesterToolIDs);
            }
        }
        return toolUniqueModeIDs;
    }

    public static HashMap<String, Double> createBlockPrices(String directory, String filePath) {
        HashMap<String, Double> blockPrices = new HashMap<>();
        for (String block : NTools.getFiles().get(directory).getStringList(filePath)) {
            String[] blockAndPrice = block.split(":");
            blockPrices.put(blockAndPrice[0].toUpperCase(), Double.parseDouble(blockAndPrice[1]));
        }
        return blockPrices;
    }
}
