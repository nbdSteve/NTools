package dev.nuer.nt.tools.tnt;

import dev.nuer.nt.ToolsPlus;
import dev.nuer.nt.external.actionbarapi.ActionBarAPI;
import dev.nuer.nt.initialize.MapInitializer;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CraftContentsOfChest {

    public static void craftChestContents(Player player, double craftingPrice, Chest chestToAlter) {
        int slot = 0;
        HashMap<Material, Integer> materialAndAmount = new HashMap<>();
        for (ItemStack item : chestToAlter.getInventory()) {
            try {
                if (!item.hasItemMeta() && MapInitializer.tntWandCraftingRecipe.get(item.getType().toString()) != null) {
                    try {
                        int currentAmount = materialAndAmount.get(item.getType());
                        materialAndAmount.put(item.getType(), item.getAmount() + currentAmount);
                    } catch (Exception e) {
                        materialAndAmount.put(item.getType(), item.getAmount());
                    }
                    chestToAlter.getInventory().setItem(slot, new ItemStack(Material.AIR));
                }
            } catch (NullPointerException e) {
                //Recipe does not contain this item
            }
            slot++;
        }
        int numberCrafted = craftItems(materialAndAmount, craftingPrice, chestToAlter);
        if (ToolsPlus.getFiles().get("config").getBoolean("tnt-wand-action-bar.enabled")) {
            //Create the action bar message
            String message = ToolsPlus.getFiles().get("config").getString("tnt-wand-action-bar.craft-message").replace("{deposit}",
                    ToolsPlus.numberFormat.format(numberCrafted));
            //Send it to the player
            ActionBarAPI.sendActionBar(player, ChatColor.translateAlternateColorCodes('&', message));
        } else {
            new PlayerMessage("chest-tnt-craft-contents", player, "{deposit}", ToolsPlus.numberFormat.format(numberCrafted));
        }
    }

    public static int craftItems(HashMap<Material, Integer> materialAndAmount, double craftingPrice, Chest chestToAlter) {
        for (Material item : materialAndAmount.keySet()) {
            double amountRequired = MapInitializer.tntWandCraftingRecipe.get(item.toString()) * craftingPrice;
            double numberCrafted = materialAndAmount.get(item) / amountRequired;
            double remainder = materialAndAmount.get(item) % amountRequired;
            chestToAlter.getInventory().addItem(new ItemStack(Material.TNT, (int) numberCrafted));
            if (remainder > 0) {
                chestToAlter.getInventory().addItem(new ItemStack(item, (int) remainder));
            }
            return (int) numberCrafted;
        }
        return 0;
    }

    public static boolean canCraftContents(Inventory inventory, double craftingModifier) {
        for (ItemStack item : inventory) {
            if (item != null && MapInitializer.tntWandCraftingRecipe.containsKey(item.getType().toString())) {
                return item.getAmount() >= MapInitializer.tntWandCraftingRecipe.get(item.getType().toString()) * craftingModifier;
            }
        }
        return false;
    }
}
