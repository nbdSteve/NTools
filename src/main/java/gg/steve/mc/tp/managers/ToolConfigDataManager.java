package gg.steve.mc.tp.managers;

import gg.steve.mc.tp.utils.LogUtil;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ToolConfigDataManager {
    private static ArrayList<Material> trenchBlacklist;
    private static ArrayList<Material> trayWhitelist;

    public static void initialise() {
        trenchBlacklist = new ArrayList<>(convertStringsToMaterials(Files.TRENCH_CONFIG.get().getStringList("blacklist")));
        trayWhitelist = new ArrayList<>(convertStringsToMaterials(Files.TRAY_CONFIG.get().getStringList("whitelist")));
    }

    public static void shutdown() {

    }

    public static List<Material> convertStringsToMaterials(List<String> list) {
        List<Material> materials = new ArrayList<>();
        for (String material : list) {
            try {
                materials.add(Material.getMaterial(material.toUpperCase()));
            } catch (Exception e) {
                LogUtil.warning("Unable to add material: " + material.toUpperCase() + ", to a list / map. Please change this item to its bukkit name.");
            }
        }
        return materials;
    }

    public static ArrayList<Material> getTrenchBlacklist() {
        return trenchBlacklist;
    }

    public static ArrayList<Material> getTrayWhitelist() {
        return trayWhitelist;
    }
}
