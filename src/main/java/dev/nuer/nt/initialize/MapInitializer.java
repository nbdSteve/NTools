package dev.nuer.nt.initialize;

import dev.nuer.nt.method.AddBlocksToBlacklist;
import dev.nuer.nt.method.GetMultiToolLoreID;

import java.util.ArrayList;
import java.util.HashMap;

public class MapInitializer {
    //Store the trench block blacklist
    public static ArrayList<String> trenchBlockBlacklist;
    //Store the tray block whitelist
    public static ArrayList<String> trayBlockWhitelist;
    //Store the blocks that can be broken by the sand wands
    public static ArrayList<String> sandWandBlockWhitelist;
    //Store the map of multi tool unique lore and raw tool id
    public static HashMap<Integer, ArrayList<String>> multiToolModeUnique;
    //Store the map of multi tool unique radius id and raw tool id
    public static HashMap<Integer, ArrayList<String>> multiToolRadiusUnique;

    public static void initializeMaps() {
        //Load black / white list maps
        trenchBlockBlacklist = AddBlocksToBlacklist.createBlockList("config", "trench-block-blacklist");
        trayBlockWhitelist = AddBlocksToBlacklist.createBlockList("config", "tray-block-whitelist");
        sandWandBlockWhitelist = AddBlocksToBlacklist.createBlockList("config", "sand-block-whitelist");
        //Load maps specific to multi tool ids
        multiToolModeUnique = GetMultiToolLoreID.createUniqueModeIDs("multi-tools.");
        multiToolRadiusUnique = GetMultiToolLoreID.createUniqueRadiusIDs("multi-tools.");
    }

    public static void clearMaps() {
        //Clear black / white list maps
        trenchBlockBlacklist.clear();
        trayBlockWhitelist.clear();
        sandWandBlockWhitelist.clear();
        //Clear maps specific to multi tool ids
        multiToolModeUnique.clear();
        multiToolRadiusUnique.clear();
    }
}
