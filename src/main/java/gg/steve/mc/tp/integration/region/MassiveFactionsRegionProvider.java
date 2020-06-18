package gg.steve.mc.tp.integration.region;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class MassiveFactionsRegionProvider extends AbstractRegionProvider {

    public MassiveFactionsRegionProvider() {
        super(RegionProviderType.MASSIVE_FACTIONS, "MassiveCore");
    }

    @Override
    public boolean isBreakAllowed(Player player, Block block) {
        if (!isEnabled()) return true;
        if (Bukkit.getPluginManager().getPlugin("Factions") == null) return true;
        MPlayer mPlayer = MPlayer.get(player);
        if (BoardColl.get().getFactionAt(PS.valueOf(block)).getName().equalsIgnoreCase("Wilderness")
                || BoardColl.get().getFactionAt(PS.valueOf(block)).equals(mPlayer.getFaction())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isBreakAllowed(Player player, Location location) {
        if (!isEnabled()) return true;
        if (Bukkit.getPluginManager().getPlugin("Factions") == null) return true;
        MPlayer mPlayer = MPlayer.get(player);
        if (BoardColl.get().getFactionAt(PS.valueOf(location)).getName().equalsIgnoreCase("Wilderness")
                || BoardColl.get().getFactionAt(PS.valueOf(location)).equals(mPlayer.getFaction()))
            return true;
        return false;
    }
}
