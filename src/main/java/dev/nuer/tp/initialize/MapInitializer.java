package dev.nuer.tp.initialize;

import dev.nuer.tp.method.AddBlocksToList;
import dev.nuer.tp.method.CreateInternalMaps;

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
    //Store the tnt crafting recipe and numbers
    public static HashMap<String, Double> tntWandCraftingRecipe;
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
    //Store the map of tnt mode unique id and raw tool id
    public static HashMap<Integer, ArrayList<String>> tntWandModeUnique;
    //Store the map of tnt modifier unique id and raw tool id
    public static HashMap<Integer, ArrayList<String>> tntWandModifierUnique;
    //Store the map of aqua wand mode unique id and raw tool id
    public static HashMap<Integer, ArrayList<String>> aquaWandModeUnique;
    //Store the map of aqua wand radius unique id and raw tool id
    public static HashMap<Integer, ArrayList<String>> aquaWandRadiusUnique;

    /**
     * Method to create the internal maps with the correct values from the configuration files
     */
    public static void initializeMaps() {
        //Load black / white list maps
        trenchBlockBlacklist = AddBlocksToList.createBlockList("config", "trench-block-blacklist");
        trayBlockWhitelist = AddBlocksToList.createBlockList("config", "tray-block-whitelist");
        sandWandBlockWhitelist = AddBlocksToList.createBlockList("config", "sand-block-whitelist");
        harvesterBlockPrices = CreateInternalMaps.createBlockPrices("config", "harvester-block-prices");
        sellWandItemPrices = CreateInternalMaps.createBlockPrices("sell_price_list", "prices");
        tntWandCraftingRecipe = CreateInternalMaps.createBlockPrices("config", "tnt-wand.crafting-recipe");
        //Load maps specific to multi tool ids
        multiToolModeUnique = CreateInternalMaps.createUniqueModeIDs("multi", "multi-tools.", "trench", "tray");
        multiToolRadiusUnique = CreateInternalMaps.createUniqueModifierIDs("multi", "multi-tools.", "radius");
        //Load maps specific to harvester tool ids
        harvesterModeUnique = CreateInternalMaps.createUniqueModeIDs("harvester", "harvester-tools.", "sell", "harvest");
        harvesterModifierUnique = CreateInternalMaps.createUniqueModifierIDs("harvester", "harvester-tools.", "modifier");
        //Load maps specific to sell wands
        sellWandModifierUnique = CreateInternalMaps.createUniqueModifierIDs("sell", "sell-wands.", "modifier");
        //Load maps specific to tnt wand ids
        tntWandModeUnique = CreateInternalMaps.createUniqueModeIDs("tnt", "tnt-wands.", "craft", "bank");
        tntWandModifierUnique = CreateInternalMaps.createUniqueModifierIDs("tnt", "tnt-wands.", "modifier");
        //Load maps specific to aqua wand ids
        aquaWandModeUnique = CreateInternalMaps.createUniqueModeIDs("aqua", "aqua-wands.", "melt", "drain");
        aquaWandRadiusUnique = CreateInternalMaps.createUniqueModifierIDs("aqua", "aqua-wands.", "radius");
    }

    /**
     * Method to clear all of the internal maps
     */
    public static void clearMaps() {
        //Clear black / white list maps
        trenchBlockBlacklist.clear();
        trayBlockWhitelist.clear();
        sandWandBlockWhitelist.clear();
        harvesterBlockPrices.clear();
        sellWandItemPrices.clear();
        tntWandCraftingRecipe.clear();
        //Clear maps specific to multi tool ids
        multiToolModeUnique.clear();
        multiToolRadiusUnique.clear();
        //Clear maps specific to harvester tool ids
        harvesterModeUnique.clear();
        harvesterModifierUnique.clear();
        //Clear maps specific to sell wand ids
        sellWandModifierUnique.clear();
        //Clear maps specific to tnt wand ids
        tntWandModeUnique.clear();
        tntWandModifierUnique.clear();
        //Clear maps specific to aqua wand ids
        aquaWandModeUnique.clear();
        aquaWandRadiusUnique.clear();
    }
}
