package gg.steve.mc.tp.utils;

import gg.steve.mc.tp.module.ToolsPlusModule;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public abstract class PluginFile {

    public abstract PluginFile load(String fileName, JavaPlugin instance);
    public abstract PluginFile load(File source, JavaPlugin instance, ToolsPlusModule module);

    public abstract void save();

    /**
     * Reloads the file, updates the values
     */
    public abstract void reload();

    /**
     * Gets the file configuration for this file
     *
     * @return FileConfiguration
     */
    public abstract YamlConfiguration get();
}
