package dev.nuer.nt.event.sandWand;

import dev.nuer.nt.NTools;
import dev.nuer.nt.event.itemMetaMethod.GetToolType;
import dev.nuer.nt.method.player.AddBlocksToPlayerInventory;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;

import java.util.ArrayList;

public class RemoveSandStack {

    public static void removeStack(GetToolType toolType, BlockDamageEvent event, Player player) {
        int cooldownFromConf = NTools.getFiles().get(toolType.getDirectory()).getInt(toolType.getToolType() + ".cooldown");
        if (!SandCooldownCheck.isOnSandWandCooldown(player.getUniqueId(), cooldownFromConf)) {
            int positionX = event.getBlock().getX();
            int positionY = 255;
            int positionZ = event.getBlock().getZ();
            ArrayList<Block> blocksToRemove = new ArrayList<>();
            while (positionY >= 1) {
                String currentBlockType = player.getWorld().getBlockAt(positionX, positionY, positionZ).getType().toString();
                if (NTools.sandWandBlockWhitelist.contains(currentBlockType)) {
                    blocksToRemove.add(player.getWorld().getBlockAt(positionX, positionY, positionZ));
                }
                positionY--;
            }
            SandRemovalRunnable.genericSandRemoval(player, blocksToRemove,
                    NTools.getFiles().get("sand").getLong(toolType.getToolType() + ".break-delay"));
        }
    }
}
