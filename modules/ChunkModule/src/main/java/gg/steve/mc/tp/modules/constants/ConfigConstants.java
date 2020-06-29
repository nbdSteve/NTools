package gg.steve.mc.tp.modules.constants;

import gg.steve.mc.tp.framework.yml.utils.FileManagerUtil;
import gg.steve.mc.tp.modules.ChunkModule;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigConstants {
    public static double minTPS;
    public static int maxPlayerTasks, blocksRemovedPerPeriod;
    public static long delay, period;

    public static void initialise() {
        YamlConfiguration config = FileManagerUtil.get(ChunkModule.moduleConfigId);
        minTPS = config.getDouble("minimum-tps-for-initiation");
        maxPlayerTasks = config.getInt("max-tasks-per-player");
        blocksRemovedPerPeriod = config.getInt("chunk-removal.blocks-removed-per-period");
        delay = config.getLong("chunk-removal.delay");
        period = config.getLong("chunk-removal.period");
    }
}
