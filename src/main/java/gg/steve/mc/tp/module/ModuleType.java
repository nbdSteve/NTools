package gg.steve.mc.tp.module;

import gg.steve.mc.tp.managers.Files;
import gg.steve.mc.tp.managers.SetupManager;
import gg.steve.mc.tp.managers.PluginFile;

public enum ModuleType {
    TRENCH("TrenchModule");

    private String moduleName;

    ModuleType(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public static ModuleType getModuleForTool(PluginFile file) {
        return ModuleType.valueOf(file.get().getString("type").toUpperCase());
    }

    public void loadModuleFiles() {
        for (Files file : Files.values()) {
            if (file.name().startsWith(name())) {
                file.load(SetupManager.getFileManager());
            }
        }
    }
}