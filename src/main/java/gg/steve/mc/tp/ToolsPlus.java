package gg.steve.mc.tp;

import gg.steve.mc.tp.managers.FileManager;
import gg.steve.mc.tp.managers.SetupManager;
import gg.steve.mc.tp.module.ModuleManager;
import gg.steve.mc.tp.papi.ToolsPlusExpansion;
import gg.steve.mc.tp.player.PlayerToolManager;
import gg.steve.mc.tp.tool.ToolsManager;
import gg.steve.mc.tp.utils.LogUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;

public final class ToolsPlus extends JavaPlugin {
    private static ToolsPlus instance;
    private static String version = "2.0.0-PR1";
    private static Economy economy;
    private static DecimalFormat numberFormat = new DecimalFormat("#,###.##");

    @Override
    public void onLoad() {
        instance = this;
        ModuleManager.initialise(instance);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        SetupManager.setupFiles(new FileManager(instance));
        ModuleManager.loadInstalledModules();
        SetupManager.registerCommands(instance);
        SetupManager.registerEvents(instance);
        ToolsManager.initialiseTools();
        PlayerToolManager.initialise();
        // verify that the server is running vault so that eco features can be used
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            try {
                economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
                LogUtil.info("Vault found, hooking into economy now...");
            } catch (NullPointerException e) {
                LogUtil.info("Tried to hook into Vault but failed, please install an economy plugin e.g. EssentialsX.");
            }
        } else {
            LogUtil.info("Unable to find economy instance, disabling economy features. If you intend to use economy please install Vault and an economy plugin.");
            economy = null;
        }
        // Check that the server is running PAPI and register all expansions
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            LogUtil.info("PlaceholderAPI found, registering expansions with it now...");
            new ToolsPlusExpansion(instance).register();
        }
        LogUtil.info("Thanks for using Tools+ v" + version + ", please contact nbdSteve#0583 on discord if you find any bugs.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        PlayerToolManager.shutdown();
        ToolsManager.shutdown();
        ModuleManager.uninstalledAllModules();
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
}