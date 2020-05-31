package gg.steve.mc.tp.module;

import gg.steve.mc.tp.ToolsPlus;
import gg.steve.mc.tp.module.utils.ModuleLoaderUtil;
import gg.steve.mc.tp.utils.LogUtil;
import org.apache.commons.lang.Validate;

import java.util.HashMap;
import java.util.Map;

public class ModuleManager {
    private static Map<ModuleType, ToolsPlusModule> modules;
    private static ModuleLoaderUtil loader;

    public static void initialise(ToolsPlus instance) {
        modules = new HashMap<>();
        loader = new ModuleLoaderUtil(instance);
    }

    public static void loadInstalledModules() {
        loader.registerAllModules();
        StringBuilder message = new StringBuilder();
        if (modules.size() > 0) {
            message.append(": (");
            int i = 0;
            for (ModuleType type : modules.keySet()) {
                message.append(type.getNiceName());
                if (i != modules.size() - 1) {
                    message.append(", ");
                } else {
                    message.append(")");
                }
                i++;
            }
            LogUtil.info("Successfully loaded " + modules.size() + " tool module(s)" + message + ".");
        } else {
            LogUtil.warning("<>");
            LogUtil.warning("Uh oh! You don't have any tool modules installed, the plugin is essentially useless.");
            LogUtil.warning("Please see the spigot page for reference on how to install modules, or contact nbdSteve#0583 on discord.");
            LogUtil.warning("<>");
        }
    }

    public static boolean installToolModule(ModuleType type, ToolsPlusModule module) {
        Validate.notNull(type, "ModuleType can not be null");
        Validate.notNull(module, "ToolsPlusModule can not be null");
        if (modules.containsKey(type)) {
            return false;
        }
        modules.put(type, module);
        return true;
    }

    public static boolean isInstalled(ModuleType type) {
        if (modules == null || modules.isEmpty()) return false;
        return modules.get(type) != null;
    }

    public static ToolsPlusModule getInstalledModule(ModuleType type) {
        if (!isInstalled(type)) return null;
        return modules.get(type);
    }

    public static boolean uninstallModule(ToolsPlusModule module) {
        if (modules == null || modules.isEmpty()) return false;
        return modules.remove(module.getModuleType()) != null;
    }

    public static void uninstalledAllModules() {
        if (modules == null || modules.isEmpty()) return;
        modules.clear();
    }

    public static String getModulesAsList() {
        StringBuilder message = new StringBuilder();
        if (modules.size() > 0) {
            int i = 0;
            for (ModuleType type : modules.keySet()) {
                message.append(type.getNiceName());
                if (i != modules.size() - 1) {
                    message.append(", ");
                }
                i++;
            }
        }
        return message.toString();
    }

    public static String getModuleCount() {
        if (modules == null) return "0";
        return String.valueOf(modules.size());
    }
}
