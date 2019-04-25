package dev.nuer.nt.tools.lightning;

import dev.nuer.nt.ToolsPlus;
import dev.nuer.nt.events.LightningWandStrikeEvent;
import dev.nuer.nt.external.nbtapi.NBTItem;
import dev.nuer.nt.method.player.PlayerMessage;
import dev.nuer.nt.tools.DecrementUses;
import dev.nuer.nt.tools.PlayerToolCooldown;
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
    public static void createStrikeGround(Player player, String directory, String filePath, Block blockToStrike,
                                           NBTItem nbtItem) {
        int cooldownFromConfig = ToolsPlus.getFiles().get(directory).getInt(filePath + ".cooldown");
        BlockIgniteEvent playerIgnite = new BlockIgniteEvent(blockToStrike,
                BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL, player);
        Bukkit.getPluginManager().callEvent(playerIgnite);
        if (!playerIgnite.isCancelled()) {
            if (PlayerToolCooldown.isOnCooldown(player, "lightning")) {
                return;
            } else {
                DecrementUses.decrementUses(player, "lightning", nbtItem, nbtItem.getInteger("ntool.uses"));
                PlayerToolCooldown.setPlayerOnCooldown(player, cooldownFromConfig, "lightning");
            }
            Bukkit.getPluginManager().callEvent(new LightningWandStrikeEvent(blockToStrike, player));
        }
    }

    /**
     * @param player
     * @param directory
     * @param filePath
     * @param clickedMob
     */
    public static void createMobStrike(Player player, String directory, String filePath, Creeper clickedMob,
                                       NBTItem nbtItem) {
        if (clickedMob.isPowered()) {
            new PlayerMessage("creeper-already-powered", player);
            return;
        }
        int cooldownFromConfig = ToolsPlus.getFiles().get(directory).getInt(filePath + ".cooldown");
        BlockIgniteEvent playerIgnite = new BlockIgniteEvent(clickedMob.getLocation().getBlock(),
                BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL, player);
        Bukkit.getPluginManager().callEvent(playerIgnite);
        if (!playerIgnite.isCancelled()) {
            if (PlayerToolCooldown.isOnCooldown(player, "lightning")) {
                return;
            } else {
                DecrementUses.decrementUses(player, "lightning", nbtItem, nbtItem.getInteger("ntool.uses"));
                PlayerToolCooldown.setPlayerOnCooldown(player, cooldownFromConfig, "lightning");
            }
            Bukkit.getPluginManager().callEvent(new LightningWandStrikeEvent(clickedMob.getLocation().getBlock(), player, clickedMob));
        }
    }
}
