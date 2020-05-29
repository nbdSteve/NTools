package gg.steve.mc.tp.module;

import gg.steve.mc.tp.managers.Files;
import gg.steve.mc.tp.managers.SetupManager;
import gg.steve.mc.tp.managers.PluginFile;

public enum ModuleType {
    TRENCH("TrenchModule"),
    TRAY("TrayModule");

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

    public String getNiceName() {
        StringBuilder builder = new StringBuilder();
        builder.append(name(), 0, 1);
        builder.append(name().substring(1).toLowerCase());
        return builder.toString();
    }
}