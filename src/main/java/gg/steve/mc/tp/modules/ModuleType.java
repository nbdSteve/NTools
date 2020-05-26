package gg.steve.mc.tp.modules;

import gg.steve.mc.tp.managers.Files;
import gg.steve.mc.tp.managers.SetupManager;

public enum ModuleType {
    TRENCH("TrenchModule");

    private String moduleName;

    ModuleType(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void loadModuleFiles() {
        for (Files file : Files.values()) {
            if (file.name().startsWith(name())) {
                file.load(SetupManager.getFileManager());
            }
        }
    }
}