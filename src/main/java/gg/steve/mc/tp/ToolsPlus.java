package gg.steve.mc.tp;

import gg.steve.mc.tp.managers.FileManager;
import gg.steve.mc.tp.managers.SetupManager;
import gg.steve.mc.tp.modules.ModuleLoader;
import gg.steve.mc.tp.modules.ModuleType;
import gg.steve.mc.tp.modules.ToolsPlusModule;
import gg.steve.mc.tp.papi.ToolsPlusExpansion;
import gg.steve.mc.tp.utils.LogUtil;
import net.milkbowl.vault.economy.Economy;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public final class ToolsPlus extends JavaPlugin {
    private static ToolsPlus instance;
    private static String version = "2.0.0-PR1";
    private static Economy economy;
    private static DecimalFormat numberFormat = new DecimalFormat("#,###.##");
    private static ModuleLoader moduleLoader;
    private static Map<ModuleType, ToolsPlusModule> modules;

    @Override
    public void onLoad() {
        instance = this;
        moduleLoader = new ModuleLoader(instance);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        SetupManager.setupFiles(new FileManager(instance));
        ModuleLoader.loadInstalledModules();
        SetupManager.registerCommands(instance);
        SetupManager.registerEvents(instance);
        // verify that the server is running vault so that eco features can be used
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            try {
                economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
                LogUtil.info("Vault found, hooking into it now...");
            } catch (NullPointerException e) {
                LogUtil.info("Tried to hook into Vault but failed, please install an economy plugin e.g. EssentialsX.");
            }
        } else {
            LogUtil.info("Unable to find economy instance, disabling economy features. If you intend to use economy please install Vault and an economy plugin.");
            economy = null;
        }
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            LogUtil.info("PlaceholderAPI found, registering expansion with it now...");
            new ToolsPlusExpansion(instance).register();
        }
        LogUtil.info("Thanks for using Tools+ v" + version + ", please contact nbdSteve#0583 on discord if you find any bugs.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        modules.clear();
        LogUtil.info("Thanks for using Tools+ v" + version + ", please contact nbdSteve#0583 on discord if you find any bugs.");
    }

    public static ToolsPlus get() {
        return instance;
    }

    public static Economy eco() {
        return economy;
    }

    public static String formatNumber(float amount) {
        return numberFormat.format(amount);
    }

    public static Map<ModuleType, ToolsPlusModule> getModules() {
        if (modules == null) modules = new HashMap<>();
        return modules;
    }

    public static ModuleLoader getModuleLoader() {
        return moduleLoader;
    }

    public static boolean registerToolModule(ModuleType type, ToolsPlusModule module) {
        Validate.notNull(type, "ModuleType can not be null");
        Validate.notNull(module, "ToolsPlusModule can not be null");
        if (modules.containsKey(type)) {
            return false;
        }
        modules.put(type, module);
        return true;
    }
}