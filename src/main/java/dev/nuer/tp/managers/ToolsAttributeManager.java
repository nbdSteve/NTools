package dev.nuer.tp.managers;

import dev.nuer.tp.method.CreateInternalMaps;
import dev.nuer.tp.method.AddBlocksToList;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that handles initializing and getting maps that are queried in events
 */
public class ToolsAttributeManager {
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
    //Store the smelt conversions
    public static HashMap<Material, Material> smeltBlockConversions;
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
    //Store the map of chunk wand radius unique id and raw tool id
    public static HashMap<Integer, ArrayList<String>> chunkToolRadiusUnique;

    /**
     * Method to create the internal maps with the correct values from the configuration files
     */
    public static void load() {
        //Load black / white list maps
        trenchBlockBlacklist = AddBlocksToList.createBlockList("config", "trench-block-blacklist");
        trayBlockWhitelist = AddBlocksToList.createBlockList("config", "tray-block-whitelist");
        sandWandBlockWhitelist = AddBlocksToList.createBlockList("config", "sand-block-whitelist");
        harvesterBlockPrices = CreateInternalMaps.createBlockPrices("harvester_tool_config", "block-prices");
        sellWandItemPrices = CreateInternalMaps.createSellWandPrices("sell_price_list", "prices");
        tntWandCraftingRecipe = CreateInternalMaps.createBlockPrices("config", "tnt-wand.crafting-recipe");
        smeltBlockConversions = CreateInternalMaps.loadSmeltItemConversions("smelt_item_conversions", "conversions");
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
        //Load maps specific to chunk wand ids
        chunkToolRadiusUnique = CreateInternalMaps.createUniqueModifierIDs("chunk", "chunk-wands.", "radius");
    }

    /**
     * Method to clear all of the internal maps
     */
    public static void clear() {
        //Clear black / white list maps
        trenchBlockBlacklist.clear();
        trayBlockWhitelist.clear();
        sandWandBlockWhitelist.clear();
        harvesterBlockPrices.clear();
        sellWandItemPrices.clear();
        tntWandCraftingRecipe.clear();
        smeltBlockConversions.clear();
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
        //Clear maps specific to chunk wands
        chunkToolRadiusUnique.clear();
    }
}
