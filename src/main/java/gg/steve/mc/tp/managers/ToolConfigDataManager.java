package gg.steve.mc.tp.managers;

import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.module.ModuleType;
import gg.steve.mc.tp.utils.LogUtil;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ToolConfigDataManager {
    private static List<Material> trenchBlacklist;
    private static List<Material> trayWhitelist;
    private static List<Material> sellableContainers;

    private ToolConfigDataManager() throws IllegalAccessException {
        throw new IllegalAccessException("Manager class cannot be instantiated.");
    }

    public static void initialise() {
        if (ModuleManager.isInstalled(ModuleType.TRENCH))
            trenchBlacklist = new ArrayList<>(convertStringsToMaterials(Files.TRENCH_CONFIG.get().getStringList("blacklist")));
        if (ModuleManager.isInstalled(ModuleType.TRAY))
            trayWhitelist = new ArrayList<>(convertStringsToMaterials(Files.TRAY_CONFIG.get().getStringList("whitelist")));
        if (ModuleManager.isInstalled(ModuleType.SELL))
            sellableContainers = new ArrayList<>(convertStringsToMaterials(Files.SELL_CONFIG.get().getStringList("sellable-containers")));
    }

    public static void shutdown() {
        if (trenchBlacklist != null && !trenchBlacklist.isEmpty()) trenchBlacklist.clear();
        if (trayWhitelist != null && !trayWhitelist.isEmpty()) trayWhitelist.clear();
        if (sellableContainers != null && !sellableContainers.isEmpty()) sellableContainers.clear();
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

    public static List<Material> getTrenchBlacklist() {
        return trenchBlacklist;
    }

    public static List<Material> getTrayWhitelist() {
        return trayWhitelist;
    }

    public static List<Material> getSellableContainers() {
        return sellableContainers;
    }
}
