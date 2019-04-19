package dev.nuer.nt.initialize;

import dev.nuer.nt.method.AddBlocksToList;
import dev.nuer.nt.method.CreateInternalMaps;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that handles initializing and getting maps that are queried in events
 */
public class MapInitializer {
    //Store the trench block blacklist
    public static ArrayList<String> trenchBlockBlacklist;
    //Store the tray block whitelist
    public static ArrayList<String> trayBlockWhitelist;
    //Store the blocks that can be broken by the sand wands
    public static ArrayList<String> sandWandBlockWhitelist;
    //Store the blocks that can be broken and sold by the harvester tools
    public static HashMap<String, Double> harvesterBlockPrices;
    //Store the blocks that can be sold by sell wands, store prices as well
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
        trenchBlockBlacklist = AddBlocksToList.createBlockList("config", "trench-block-blacklist");
        trayBlockWhitelist = AddBlocksToList.createBlockList("config", "tray-block-whitelist");
        sandWandBlockWhitelist = AddBlocksToList.createBlockList("config", "sand-block-whitelist");
        harvesterBlockPrices = CreateInternalMaps.createBlockPrices("config", "harvester-block-prices");
        sellWandItemPrices = CreateInternalMaps.createBlockPrices("sell_price_list", "prices");
        //Load maps specific to multi tool ids
        multiToolModeUnique = CreateInternalMaps.createUniqueModeIDs("multi", "multi-tools.", "trench", "tray");
        multiToolRadiusUnique = CreateInternalMaps.createUniqueModifierIDs("multi", "multi-tools.", "radius");
        //Load maps specific to harvester tool ids
        harvesterModeUnique = CreateInternalMaps.createUniqueModeIDs("harvester", "harvester-tools.", "sell", "harvest");
        harvesterModifierUnique = CreateInternalMaps.createUniqueModifierIDs("harvester", "harvester-tools.", "modifier");
        //Load maps specific to sell wands
        sellWandModifierUnique = CreateInternalMaps.createUniqueModifierIDs("sell", "sell-wands.", "modifier");
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
