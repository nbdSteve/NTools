package dev.nuer.tp.tools.smelt;

import dev.nuer.tp.events.SmeltWandConversionEvent;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.method.player.PlayerMessage;
import dev.nuer.tp.support.nbtapi.NBTItem;
import dev.nuer.tp.tools.DecrementUses;
import dev.nuer.tp.tools.PlayerToolCooldown;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

/**
 * Main method for smelting, checks cooldown and uses
 */
public class SmeltStorageContents {

    /**
     * Checks the players cooldown, decrements uses and converts a chests contents
     *
     * @param clickedBlock Block, the clicked block
     * @param player       Player, player using the tool
     * @param directory    String, file to get cooldown from
     * @param filePath     String, internal path to get cooldown from
     * @param nbtItem      NBTItem, item being used
     */
    public static void smeltContents(Block clickedBlock, Player player, String directory, String filePath, NBTItem nbtItem) {
        //Verify that the player is not on cooldown
        if (PlayerToolCooldown.isOnCooldown(player, "smelt")) return;
        //Store the tool cooldown
        int cooldownFromConfig = FileManager.get(directory).getInt(filePath + ".cooldown");
        Chest chestToAlter = (Chest) clickedBlock.getState();
        if (!SmeltContentsOfChest.canSmeltContents(chestToAlter.getInventory())) {
            new PlayerMessage("contents-can-not-be-smelted", player);
            return;
        }
        DecrementUses.decrementUses(player, "smelt", nbtItem, nbtItem.getInteger("tools+.uses"));
        PlayerToolCooldown.setPlayerOnCooldown(player, cooldownFromConfig, "smelt");
        Bukkit.getPluginManager().callEvent(new SmeltWandConversionEvent(chestToAlter, player));
    }
}