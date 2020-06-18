package gg.steve.mc.tp.integration.region;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class FactionsUuidRegionProvider extends AbstractRegionProvider {

    public FactionsUuidRegionProvider() {
        super(RegionProviderType.FACTIONS_UUID, "Factions");
    }

    @Override
    public boolean isBreakAllowed(Player player, Block block) {
        if (!isEnabled()) return true;
        try {
            if (FPlayers.getInstance().getByPlayer(player).getFaction() == null) return false;
            Faction faction = FPlayers.getInstance().getByPlayer(player).getFaction();
            FLocation fLocation = new FLocation(block.getLocation());
            if (Board.getInstance().getFactionAt(fLocation).isWilderness() || faction == Board.getInstance().getFactionAt(fLocation))
                return true;
        } catch (NoClassDefFoundError e) {
            if (Bukkit.getPluginManager().getPlugin("MassiveCore") != null) return true;
        }
        return false;
    }

    @Override
    public boolean isBreakAllowed(Player player, Location location) {
        if (!isEnabled()) return true;
        try {
            if (FPlayers.getInstance().getByPlayer(player).getFaction() == null) return false;
            Faction faction = FPlayers.getInstance().getByPlayer(player).getFaction();
            FLocation fLocation = new FLocation(location);
            if (Board.getInstance().getFactionAt(fLocation).isWilderness() || faction == Board.getInstance().getFactionAt(fLocation))
                return true;
        } catch (NoClassDefFoundError e) {
            if (Bukkit.getPluginManager().getPlugin("MassiveCore") != null) return true;
        }
        return false;
    }
}
