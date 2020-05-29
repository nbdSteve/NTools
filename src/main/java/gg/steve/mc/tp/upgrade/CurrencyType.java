package gg.steve.mc.tp.upgrade;

import gg.steve.mc.tp.managers.Files;

public enum CurrencyType {
    VAULT("vault"),
    XP_RAW("xp-raw"),
    XP_LEVEL("xp-level"),
    BLOCKS_MINED("blocks-mined"),
    NONE("");

    private String type;

    CurrencyType(String type) {
        this.type = type;
    }

    public String getNiceName() {
        StringBuilder builder = new StringBuilder();
        builder.append(name(), 0, 1);
        builder.append(name().substring(1).toLowerCase());
        return builder.toString();
    }

    public String getPrefix() {
        if (name().equalsIgnoreCase("none")) return type;
        return Files.CONFIG.get().getString("currency." + type + ".prefix");
    }

    public String getSuffix() {
        if (name().equalsIgnoreCase("none")) return type;
        return Files.CONFIG.get().getString("currency." + type + ".suffix");
    }
}
