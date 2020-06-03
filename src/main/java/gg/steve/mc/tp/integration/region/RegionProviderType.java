package gg.steve.mc.tp.integration.region;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public enum RegionProviderType {
    WORLDGUARD(new WorldGuardRegionProvider()),
    FACTIONS_UUID(new FactionsUuidRegionProvider()),
//    GUILDS,
    FACTIONS_X(new FactionsXRegionProvider()),
    LANDS(new LandsRegionProvider()),
    GRIEF_PREVENTION(new GriefPreventionRegionProvider());

    private AbstractRegionProvider regionProvider;

    RegionProviderType(AbstractRegionProvider regionProvider) {
        this.regionProvider = regionProvider;
    }

    public AbstractRegionProvider getRegionProvider() {
        return regionProvider;
    }

    public boolean isBreakAllowed(Player player, Block block) {
        return regionProvider.isBreakAllowed(player, block);
    }

    public boolean isBreakAllowed(Player player, Location location) {
        return regionProvider.isBreakAllowed(player, location);
    }
}
