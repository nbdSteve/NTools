package dev.nuer.tp.tools.lightning;

import dev.nuer.tp.ToolsPlus;
import dev.nuer.tp.events.LightningWandStrikeEvent;
import dev.nuer.tp.external.nbtapi.NBTItem;
import dev.nuer.tp.method.player.PlayerMessage;
import dev.nuer.tp.tools.DecrementUses;
import dev.nuer.tp.tools.PlayerToolCooldown;
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
     * Creates a lightning strike on the ground where the player has clicked
     *
     * @param player        Player, the player using the lightning wand
     * @param directory     String, the file to get values from
     * @param filePath      String, the internal path from the configuration
     * @param blockToStrike Block, the block to strike
     * @param nbtItem       NBTItem, the item used by the player
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
                DecrementUses.decrementUses(player, "lightning", nbtItem, nbtItem.getInteger("tools+.uses"));
                PlayerToolCooldown.setPlayerOnCooldown(player, cooldownFromConfig, "lightning");
            }
            Bukkit.getPluginManager().callEvent(new LightningWandStrikeEvent(blockToStrike, player));
        }
    }

    /**
     * Creates a lightning strike on the top of the mob that the player clicked
     *
     * @param player     Player, the player using the lightning wand
     * @param directory  String, the file to get values from
     * @param filePath   String, the internal path from the configuration
     * @param clickedMob Creeper, the mob to strike
     * @param nbtItem    NBTItem, the item used by the player
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
                DecrementUses.decrementUses(player, "lightning", nbtItem, nbtItem.getInteger("tools+.uses"));
                PlayerToolCooldown.setPlayerOnCooldown(player, cooldownFromConfig, "lightning");
            }
            Bukkit.getPluginManager().callEvent(new LightningWandStrikeEvent(clickedMob.getLocation().getBlock(), player, clickedMob));
        }
    }
}
