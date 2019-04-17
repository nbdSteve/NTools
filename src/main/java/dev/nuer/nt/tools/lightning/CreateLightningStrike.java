package dev.nuer.nt.tools.lightning;

import dev.nuer.nt.NTools;
import dev.nuer.nt.events.LightningWandStrikeEvent;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockIgniteEvent;

public class CreateLightningStrike {

    public static void createStrikeGround(Player player, String directory, String filePath, Block blockToStrike) {
        int cooldownFromConfig = NTools.getFiles().get(directory).getInt(filePath + ".cooldown");
        if (!LightningCooldownCheck.isOnSandWandCooldown(player.getUniqueId(), cooldownFromConfig)) {
            BlockIgniteEvent playerIgnite = new BlockIgniteEvent(blockToStrike, BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL, player);
            Bukkit.getPluginManager().callEvent(playerIgnite);
            if (!playerIgnite.isCancelled()) {
                Bukkit.getPluginManager().callEvent(new LightningWandStrikeEvent(blockToStrike, player));
            }
        }
    }
}
