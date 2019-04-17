package dev.nuer.nt.event.miningTool.modeSwitchMethod;

import dev.nuer.nt.event.miningTool.GetMultiToolVariables;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Class that handles switching tool mode, and sending the player a message
 */
public class ModeSwitch {

    /**
     * Change to the tool mode and send the player a message
     *
     * @param itemLore      the lore of the item to change
     * @param itemMeta      the item meta of the item to change
     * @param item          the item to update
     * @param player        the player being affected
     */
    public static void switchMode(List<String> itemLore,
                                  ItemMeta itemMeta, ItemStack item, Player player) {
        player.closeInventory();
        GetMultiToolVariables.queryToolMode(itemLore, itemMeta, item, true);
        new PlayerMessage("mode-switch", player);
    }
}
