package dev.nuer.nt.initialize;

import dev.nuer.nt.NTools;
import dev.nuer.nt.method.AddToolsToMap;

import java.util.HashMap;

public class ToolMapInitializer {
    //Store the map of trench tools, queried in the event class
    public static HashMap<Integer, String> trenchTools;
    //Store the map of tray tools, queried in the event class
    public static HashMap<Integer, String> trayTools;
    //Store the map of multi tools, queried in the event class
    public static HashMap<Integer, String> multiTools;
    //Store the map of sand wands
    public static HashMap<Integer, String> sandWands;

    public static void initializeToolMaps() {
        //Load all tools from config into maps
        trenchTools = AddToolsToMap.createToolMap("trench", "trench-tools.");
        trayTools = AddToolsToMap.createToolMap("tray", "tray-tools.");
        multiTools = AddToolsToMap.createToolMap("multi", "multi-tools.");
        sandWands = AddToolsToMap.createToolMap("sand", "sand-wands.");
        //Log that the operation was successful
        NTools.LOGGER.info("[NTools] Successfully loaded all tools from configuration into internal maps.");
    }

    public static void clearToolMaps() {
        //Clear all tool maps
        trenchTools.clear();
        trayTools.clear();
        multiTools.clear();
        sandWands.clear();
        //Log that the operation was successful
        NTools.LOGGER.info("[NTools] Successfully cleared all tools from internal maps.");
    }
}
