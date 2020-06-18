package gg.steve.mc.tp.integration.libs;

public enum ToolsPlusLibType {
    WORLDGUARD_v7("WorldGuard-7"),
    WORLDGUARD_LEGACY("WorldGuard-Legacy");

    private String libName;

    ToolsPlusLibType(String libName) {
        this.libName = libName;
    }

    public String getLibName() {
        return libName;
    }
}
