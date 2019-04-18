package dev.nuer.nt.tools.lightning;

import dev.nuer.nt.NTools;
import dev.nuer.nt.events.LightningWandStrikeEvent;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockIgniteEvent;

/**
 * Class that handles creating a lightning strike for the lightning wands
 */
public class CreateLightningStrike {

    /**
     * @param player
     * @param directory
     * @param filePath
     * @param blockToStrike
     */
    public static void createStrikeGround(Player player, String directory, String filePath,
                                          Block blockToStrike) {
        int cooldownFromConfig = NTools.getFiles().get(directory).getInt(filePath + ".cooldown");
        if (!LightningCooldownCheck.isOnLightningWandCooldown(player.getUniqueId(), cooldownFromConfig, player)) {
            BlockIgniteEvent playerIgnite = new BlockIgniteEvent(blockToStrike,
                    BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL, player);
            Bukkit.getPluginManager().callEvent(playerIgnite);
            if (!playerIgnite.isCancelled()) {
                Bukkit.getPluginManager().callEvent(new LightningWandStrikeEvent(blockToStrike, player));
            }
        }
    }

    /**
     * @param player
     * @param directory
     * @param filePath
     * @param clickedMob
     */
    public static void createMobStrike(Player player, String directory, String filePath, Creeper clickedMob) {
        if (clickedMob.isPowered()) {
            new PlayerMessage("creeper-already-powered", player);
            return;
        }
        int cooldownFromConfig = NTools.getFiles().get(directory).getInt(filePath + ".cooldown");
        if (!LightningCooldownCheck.isOnLightningWandCooldown(player.getUniqueId(), cooldownFromConfig, player)) {
            BlockIgniteEvent playerIgnite = new BlockIgniteEvent(clickedMob.getLocation().getBlock(),
                    BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL, player);
            Bukkit.getPluginManager().callEvent(playerIgnite);
            if (!playerIgnite.isCancelled()) {
                Bukkit.getPluginManager().callEvent(new LightningWandStrikeEvent(clickedMob.getLocation().getBlock(), player, clickedMob));
            }
        }
    }
}
