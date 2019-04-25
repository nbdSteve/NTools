package dev.nuer.nt.tools.tnt;

import dev.nuer.nt.ToolsPlus;
import dev.nuer.nt.events.TNTWandBankEvent;
import dev.nuer.nt.events.TNTWandCraftEvent;
import dev.nuer.nt.external.FactionIntegration;
import dev.nuer.nt.external.nbtapi.NBTItem;
import dev.nuer.nt.method.player.PlayerMessage;
import dev.nuer.nt.tools.DecrementUses;
import dev.nuer.nt.tools.PlayerToolCooldown;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

public class AlterChestContents {

    public static void manipulateContents(Block clickedBlock, Player player, String directory, String filePath,
                                          double craftingModifier, boolean bank, NBTItem nbtItem) {
        Bukkit.getScheduler().runTaskAsynchronously(ToolsPlus.getPlugin(ToolsPlus.class), () -> {
            //Get if the plugin is using shop gui plus
            boolean usingFactions = FactionIntegration.usingFactions("config");
            //Store the tool cooldown
            int cooldownFromConfig = ToolsPlus.getFiles().get(directory).getInt(filePath + ".cooldown");
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
            if (PlayerToolCooldown.isOnCooldown(player, "tnt")) {
                return;
            } else {
                DecrementUses.decrementUses(player, "tnt", nbtItem, nbtItem.getInteger("ntool.uses"));
                PlayerToolCooldown.setPlayerOnCooldown(player, cooldownFromConfig, "tnt");
            }
            if (bank && usingFactions) {
                Bukkit.getPluginManager().callEvent(new TNTWandBankEvent(chestToAlter, player));
            } else {
                Bukkit.getPluginManager().callEvent(new TNTWandCraftEvent(chestToAlter, player, craftingModifier));
            }
        });
    }
}