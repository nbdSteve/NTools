package dev.nuer.nt.event.sandWand;

import dev.nuer.nt.NTools;
import dev.nuer.nt.event.custom.BlockBreakBySandWand;
import dev.nuer.nt.event.itemMetaMethod.GetToolType;
import dev.nuer.nt.external.nbtapi.NBTItem;
import dev.nuer.nt.initialize.OtherMapInitializer;
import dev.nuer.nt.method.player.AddBlocksToPlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Class that handles creating the array list of blocks to be removed for the sand wands
 */
public class RemoveSandStack {

    public RemoveSandStack(BlockDamageEvent event, Player player, String directory,
                           String filePath) {
        int cooldownFromConfig = NTools.getFiles().get(directory).getInt(filePath + ".cooldown");
        Bukkit.getScheduler().runTaskAsynchronously(NTools.getPlugin(NTools.class), () -> {
            if (!SandCooldownCheck.isOnSandWandCooldown(player.getUniqueId(), cooldownFromConfig)) {
                int positionX = event.getBlock().getX();
                int positionY = 255;
                int positionZ = event.getBlock().getZ();
                ArrayList<Block> blocksToRemove = new ArrayList<>();
                while (positionY >= 1) {
                    String currentBlockType =
                            player.getWorld().getBlockAt(positionX, positionY, positionZ).getType().toString();
                    if (OtherMapInitializer.sandWandBlockWhitelist.contains(currentBlockType)) {
                        blocksToRemove.add(player.getWorld().getBlockAt(positionX, positionY, positionZ));
                    }
                    positionY--;
                }
                genericSandRemoval(player, blocksToRemove, NTools.getFiles().get(directory).getLong(filePath + ".break-delay"));
            }
        });
    }

//    /**
//     * Method to check that a block is valid and then add it to an array list to be removed
//     *
//     * @param toolType GetToolType, the type of sand wand
//     * @param event    BlockDamageEvent, the event to call this code
//     * @param player   Player, player using the sand wand
//     */
//    public static void removeStack(GetToolType toolType, BlockDamageEvent event, Player player) {
//        Bukkit.getScheduler().runTaskAsynchronously(NTools.getPlugin(NTools.class), () -> {
//            int cooldownFromConf =
//                    NTools.getFiles().get(toolType.getDirectory()).getInt(toolType.getToolType() +
//                            ".cooldown");
//            if (!SandCooldownCheck.isOnSandWandCooldown(player.getUniqueId(), cooldownFromConf)) {
//                int positionX = event.getBlock().getX();
//                int positionY = 255;
//                int positionZ = event.getBlock().getZ();
//                ArrayList<Block> blocksToRemove = new ArrayList<>();
//                while (positionY >= 1) {
//                    String currentBlockType =
//                            player.getWorld().getBlockAt(positionX, positionY, positionZ).getType().toString();
//                    if (OtherMapInitializer.sandWandBlockWhitelist.contains(currentBlockType)) {
//                        blocksToRemove.add(player.getWorld().getBlockAt(positionX, positionY, positionZ));
//                    }
//                    positionY--;
//                }
//                BlockRemovalRunnable.genericSandRemoval(player, blocksToRemove,
//                        NTools.getFiles().get("sand").getLong(toolType.getToolType() + ".break-delay"));
//            }
//        });
//    }

    /**
     * Remove blocks from the specified array with the specified delay between each removal
     *
     * @param player         Player, player who is breaking the blocks
     * @param blocksToRemove ArrayList<Block>, blocks to remove
     * @param breakDelay     Long, delay in ticks between each break
     */
    private static void genericSandRemoval(Player player, ArrayList<Block> blocksToRemove, long breakDelay) {
        new BukkitRunnable() {
            int arrayPosition = 0;

            @Override
            public void run() {
                if (arrayPosition < blocksToRemove.size()) {
                    String currentBlockType = blocksToRemove.get(arrayPosition).getType().toString();
                    if (OtherMapInitializer.sandWandBlockWhitelist.contains(currentBlockType)) {
                        BlockBreakEvent stackRemove = new BlockBreakEvent(blocksToRemove.get(arrayPosition), player);
                        Bukkit.getPluginManager().callEvent(stackRemove);
                        if (!stackRemove.isCancelled()) {
                            if (OtherMapInitializer.sandWandBlockWhitelist.contains(currentBlockType)) {
                                Bukkit.getPluginManager().callEvent(new BlockBreakBySandWand(stackRemove.getBlock(), player));
                            }
                        }
                    }
                    arrayPosition++;
                } else {
                    if (AddBlocksToPlayerInventory.messagedPlayers.contains(player)) {
                        AddBlocksToPlayerInventory.messagedPlayers.remove(player);
                    }
                    this.cancel();
                }
            }
        }.runTaskTimer(NTools.getPlugin(NTools.class), 0, breakDelay);
    }
}