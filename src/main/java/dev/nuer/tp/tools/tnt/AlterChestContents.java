package dev.nuer.tp.tools.tnt;

import dev.nuer.tp.events.TNTWandBankEvent;
import dev.nuer.tp.events.TNTWandCraftEvent;
import dev.nuer.tp.support.FactionIntegration;
import dev.nuer.tp.support.nbtapi.NBTItem;
import dev.nuer.tp.managers.FileManager;
import dev.nuer.tp.method.player.PlayerMessage;
import dev.nuer.tp.tools.DecrementUses;
import dev.nuer.tp.tools.PlayerToolCooldown;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

/**
 * Class that handles alerting the chest contents for tnt wands
 */
public class AlterChestContents {

    /**
     * Crafts the gunpowder in a chest, or banks the tnt
     *
     * @param clickedBlock     Block, the block being clicked
     * @param player           Player, the player who clicked
     * @param directory        String, the file to read values from
     * @param filePath         String, the internal path from the configuration
     * @param craftingModifier double, the number of pieces of an item required to craft a tnt
     * @param bank             boolean, if the tool is in bank mode
     * @param nbtItem          NBTItem, the item being used
     */
    public static void manipulateContents(Block clickedBlock, Player player, String directory, String filePath,
                                          double craftingModifier, boolean bank, NBTItem nbtItem) {
        //Verify that the player is not on cooldown
        if (PlayerToolCooldown.isOnCooldown(player, "tnt")) return;
        //Get if the plugin is using shop gui plus
        boolean usingFactions = FactionIntegration.usingFactions("config");
        //Store the tool cooldown
        int cooldownFromConfig = FileManager.get(directory).getInt(filePath + ".cooldown");
        //Store the chest
        Chest chestToAlter = (Chest) clickedBlock.getState();
        if (!bank && !CraftContentsOfChest.canCraftContents(chestToAlter.getInventory(), craftingModifier)) {
            new PlayerMessage("contents-can-not-be-crafted", player);
            return;
        }
        if (bank && !usingFactions) {
            new PlayerMessage("invalid-config", player, "{reason}", "Cannot bank TNT without SavageFactions installed");
            return;
        }
        if (bank && usingFactions && !BankContentsOfChest.chestContainsTNT(chestToAlter.getInventory())) {
            new PlayerMessage("chest-does-not-contain-tnt", player);
            return;
        }
        if (usingFactions && !BankContentsOfChest.hasFaciton(player)) {
            new PlayerMessage("cannot-tnt-bank-without-faction", player);
            return;
        }
        DecrementUses.decrementUses(player, "tnt-wand", nbtItem, nbtItem.getInteger("tools+.uses"));
        PlayerToolCooldown.setPlayerOnCooldown(player, cooldownFromConfig, "tnt");
        if (bank && usingFactions) {
            Bukkit.getPluginManager().callEvent(new TNTWandBankEvent(chestToAlter, player));
        } else {
            Bukkit.getPluginManager().callEvent(new TNTWandCraftEvent(chestToAlter, player, craftingModifier));
        }
    }
}