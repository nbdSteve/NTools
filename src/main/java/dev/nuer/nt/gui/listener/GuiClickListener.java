package dev.nuer.nt.gui.listener;

import dev.nuer.nt.gui.AbstractGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GuiClickListener implements Listener {

    @EventHandler
    public void guiItemClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (AbstractGui.openInventories.get(player.getUniqueId()) != null) {
            event.setCancelled(true);
            AbstractGui gui = AbstractGui.getInventoriesByID().get(AbstractGui.openInventories.get(player.getUniqueId()));
            AbstractGui.inventoryClickActions clickAction = gui.getClickActions().get(event.getSlot());
            if (clickAction != null) {
                clickAction.itemClick(player);
            }
        }
    }

    @EventHandler
    public void guiClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        AbstractGui.openInventories.remove(player.getUniqueId());
    }

    @EventHandler
    public void guiCloseByQuit(PlayerQuitEvent event) {
        AbstractGui.openInventories.remove(event.getPlayer().getUniqueId());
    }
}
