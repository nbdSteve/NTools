package dev.nuer.nt.initialize;

import dev.nuer.nt.method.AddBlocksToBlacklist;
import dev.nuer.nt.method.CreateHarvesterLoreIDMaps;
import dev.nuer.nt.method.CreateMultiToolLoreIDMaps;

import java.util.ArrayList;
import java.util.HashMap;

public class MapInitializer {
    //Store the trench block blacklist
    public static ArrayList<String> trenchBlockBlacklist;
    //Store the tray block whitelist
    public static ArrayList<String> trayBlockWhitelist;
    //Store the blocks that can be broken by the sand wands
    public static ArrayList<String> sandWandBlockWhitelist;
    //Store the blocks that can be broken and sold by the harvester tools
    public static HashMap<String, Double> harvesterBlockPrices;
    //
    public static HashMap<String, Double> sellWandItemPrices;
    //Store the map of multi tool unique lore and raw tool id
    public static HashMap<Integer, ArrayList<String>> multiToolModeUnique;
    //Store the map of multi tool unique radius id and raw tool id
    public static HashMap<Integer, ArrayList<String>> multiToolRadiusUnique;
    //Store the map of harvester mode unique id and raw tool id
    public static HashMap<Integer, ArrayList<String>> harvesterModeUnique;
    //Store the map of harvester price modifier id and raw tool id
    public static HashMap<Integer, ArrayList<String>> harvesterModifierUnique;
    //Store the map of sell wand price modifier id and raw tool id
    public static HashMap<Integer, ArrayList<String>> sellWandModifierUnique;

    public static void initializeMaps() {
        //Load black / white list maps
        trenchBlockBlacklist = AddBlocksToBlacklist.createBlockList("config", "trench-block-blacklist");
        trayBlockWhitelist = AddBlocksToBlacklist.createBlockList("config", "tray-block-whitelist");
        sandWandBlockWhitelist = AddBlocksToBlacklist.createBlockList("config", "sand-block-whitelist");
        harvesterBlockPrices = CreateHarvesterLoreIDMaps.createBlockPrices("config", "harvester-block-prices");
        sellWandItemPrices = CreateHarvesterLoreIDMaps.createBlockPrices("sell_price_list", "prices");
        //Load maps specific to multi tool ids
        multiToolModeUnique = CreateMultiToolLoreIDMaps.createUniqueModeIDs("multi-tools.");
        multiToolRadiusUnique = CreateMultiToolLoreIDMaps.createUniqueRadiusIDs("multi-tools.");
        //Load maps specific to harvester tool ids
        harvesterModeUnique = CreateHarvesterLoreIDMaps.createUniqueModeIDs("harvester-tools.");
        harvesterModifierUnique = CreateHarvesterLoreIDMaps.createUniqueModifierIDs("harvester", "harvester-tools.");
        //Load maps specific to sell wands
        sellWandModifierUnique = CreateHarvesterLoreIDMaps.createUniqueModifierIDs("sell", "sell-wands.");
    }

    public static void clearMaps() {
        //Clear black / white list maps
        trenchBlockBlacklist.clear();
        trayBlockWhitelist.clear();
        sandWandBlockWhitelist.clear();
        harvesterBlockPrices.clear();
        sellWandItemPrices.clear();
        //Clear maps specific to multi tool ids
        multiToolModeUnique.clear();
        multiToolRadiusUnique.clear();
        //Clear maps specific to harvester tool ids
        harvesterModeUnique.clear();
        harvesterModifierUnique.clear();
        //Clear maps specific to sell wand ids
        sellWandModifierUnique.clear();
    }
}
