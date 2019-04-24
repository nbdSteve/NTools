package dev.nuer.nt.tools.tnt;

import dev.nuer.nt.NTools;
import dev.nuer.nt.external.FactionIntegration;
import dev.nuer.nt.method.player.PlayerMessage;
import dev.nuer.nt.tools.PlayerToolCooldown;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class AlterChestContents {

    public static void manipulateContents(Block clickedBlock, Player player, String directory, String filePath, double craftingModifier, boolean bank) {
        Bukkit.getScheduler().runTaskAsynchronously(NTools.getPlugin(NTools.class), () -> {
            //Get if the plugin is using shop gui plus
            boolean usingFactions = FactionIntegration.usingFactions("config");
            //Store the tool cooldown
            int cooldownFromConfig = NTools.getFiles().get(directory).getInt(filePath + ".cooldown");
            //Store the chest
            Chest chestToAlter = (Chest) clickedBlock.getState();
            //Store the chests inventory
            Inventory inventoryToQuery = chestToAlter.getInventory();
            if (!bank && !CraftContentsOfChest.canCraftContents(inventoryToQuery, craftingModifier)) {
                new PlayerMessage("contents-can-not-be-crafted", player);
                return;
            }
            if (bank && !usingFactions) {
                new PlayerMessage("invalid-config", player, "{reason}", "Cannot bank TNT without SavageFactions installed");
                return;
            }
            if (bank && usingFactions && !BankContentsOfChest.chestContainsTNT(inventoryToQuery)) {
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
                PlayerToolCooldown.setPlayerOnCooldown(player, cooldownFromConfig, "tnt");
            }
            if (bank && usingFactions) {
                BankContentsOfChest.getTNTCountForChest(player, inventoryToQuery, chestToAlter);
            } else {
                CraftContentsOfChest.craftChestContents(player, inventoryToQuery, craftingModifier, chestToAlter);
            }
        });
    }
}