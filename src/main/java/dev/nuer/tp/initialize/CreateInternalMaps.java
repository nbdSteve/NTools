package dev.nuer.tp.initialize;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.method.Chat;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that handles creating internal maps for things like: switching tool mode lores,
 * increasing price modifiers / tool radius.
 */
public class CreateInternalMaps {

    /**
     * Method that creates a HashMap that contains the modifiers IDs for a respective tool
     *
     * @param directory    String, the directory to get the modifiers from
     * @param filePath     String, file path to get modifiers from inside the configuration
     * @param modifierType String, the type of modifier; radius, modifier
     * @return HashMap<Integer, ArrayList < String>>
     */
    public static HashMap<Integer, ArrayList<String>> createUniqueModifierIDs(String directory, String filePath, String modifierType) {
        HashMap<Integer, ArrayList<String>> toolUniqueModifierIDs = new HashMap<>();
        for (int i = 1; i <= toolUniqueModifierIDs.size() + 1; i++) {
            if (ToolsPlus.getFiles().get(directory).getString(filePath + i + "." + modifierType + ".unique") != null) {
                ArrayList<String> toolModifierIDs = new ArrayList<>();
                toolModifierIDs.add(Chat.applyColor(ToolsPlus.getFiles().get(directory).getString(filePath + i + "." + modifierType + ".unique")));
                for (String modifierID : ToolsPlus.getFiles().get(directory).getStringList(filePath + i + "." + modifierType + ".lore-ids")) {
                    toolModifierIDs.add(Chat.applyColor(modifierID));
                }
                toolModifierIDs.add(ToolsPlus.getFiles().get(directory).getString(filePath + i + "." + modifierType + ".max"));
                toolModifierIDs.add(ToolsPlus.getFiles().get(directory).getString(filePath + i + "." + modifierType + ".min"));
                toolUniqueModifierIDs.put(i, toolModifierIDs);
            }
        }
        return toolUniqueModifierIDs;
    }

    /**
     * Method that creates a HashMap that contains the mode IDs for a respective tool
     *
     * @param directory String, the directory to get the modes from
     * @param filePath  String, file path to get modes from inside the configuration
     * @param mode1     String, the first mode to add
     * @param mode2     String, the second mode to add
     * @return HashMap<Integer, ArrayList < String>>
     */
    public static HashMap<Integer, ArrayList<String>> createUniqueModeIDs(String directory, String filePath,
                                                                          String mode1, String mode2) {
        HashMap<Integer, ArrayList<String>> toolUniqueModeIDs = new HashMap<>();
        for (int i = 1; i <= toolUniqueModeIDs.size() + 1; i++) {
            if (ToolsPlus.getFiles().get(directory).getString(filePath + i + ".mode.unique") != null) {
                ArrayList<String> toolModeIDs = new ArrayList<>();
                toolModeIDs.add(Chat.applyColor(ToolsPlus.getFiles().get(directory).getString(filePath + i + ".mode.unique")));
                toolModeIDs.add(Chat.applyColor(ToolsPlus.getFiles().get(directory).getString(filePath + i + ".mode." + mode1)));
                toolModeIDs.add(Chat.applyColor(ToolsPlus.getFiles().get(directory).getString(filePath + i + ".mode." + mode2)));
                toolUniqueModeIDs.put(i, toolModeIDs);
            }
        }
        return toolUniqueModeIDs;
    }

    /**
     * Creates a HashMap of blocks and their respective sell price, used for sell wands
     *
     * @param directory String, the directory to get the prices from
     * @param filePath  String, file path to get prices from inside the configuration
     * @return HashMap<String, Double>
     */
    public static HashMap<String, Double> createBlockPrices(String directory, String filePath) {
        HashMap<String, Double> blockPrices = new HashMap<>();
        for (String block : ToolsPlus.getFiles().get(directory).getStringList(filePath)) {
            String[] blockAndPrice = block.split(":");
            blockPrices.put(blockAndPrice[0].toUpperCase(), Double.parseDouble(blockAndPrice[1]));
        }
        return blockPrices;
    }
}
