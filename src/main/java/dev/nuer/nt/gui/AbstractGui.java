package dev.nuer.nt.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public abstract class AbstractGui {
    public static HashMap<UUID, AbstractGui> inventoriesByID = new HashMap<>();
    public static HashMap<UUID, UUID> openInventories = new HashMap<>();

    private UUID inventoryID;
    private Inventory inventory;
    private HashMap<Integer, inventoryClickActions> clickActions;

    public AbstractGui(int inventorySize, String inventoryName) {
        inventoryID = UUID.randomUUID();
        inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
        clickActions = new HashMap<>();
        inventoriesByID.put(getInventoryID(), this);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public UUID getInventoryID() {
        return inventoryID;
    }

    public interface inventoryClickActions {
        void itemClick(Player player);
    }

    public void setItemInSlot(int guiSlot, ItemStack item, inventoryClickActions clickAction) {
        inventory.setItem(guiSlot, item);
        if (clickAction != null) {
            clickActions.put(guiSlot, clickAction);
        }
    }

    public void open(Player player) {
        player.openInventory(inventory);
        openInventories.put(player.getUniqueId(), getInventoryID());
    }

    public static HashMap<UUID, AbstractGui> getInventoriesByID() {
        return inventoriesByID;
    }

    public static HashMap<UUID, UUID> getOpenInventories() {
        return openInventories;
    }

    public HashMap<Integer, inventoryClickActions> getClickActions() {
        return clickActions;
    }

    public void delete() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (openInventories.get(player.getUniqueId()).equals(getInventoryID())) {
                player.closeInventory();
            }
        }
        inventoriesByID.remove(getInventoryID());
    }
}
