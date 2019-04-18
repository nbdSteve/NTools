package dev.nuer.nt.method.player;

import dev.nuer.nt.NTools;
import dev.nuer.nt.tools.harvest.HandleSellingMessages;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Class that handles adding block drops to a players inventory
 */
public class AddBlocksToPlayerInventory {
    public static ArrayList<Player> messagedPlayers = new ArrayList<>();

    /**
     * Adds the drops from the specified event to the players inventory
     *
     * @param blockToBreak the event to get the drops of
     * @param player       the player who brock the block
     */
    public static void addBlocks(Block blockToBreak, Player player) {
        if (!messagedPlayers.contains(player)) {
            messagedPlayers.add(player);
            if (player.getInventory().firstEmpty() == -1) {
                try {
                    if (NTools.getFiles().get("config").getBoolean("inventory-full-action-bar.enabled")) {
                        String message = NTools.getFiles().get("config").getString("inventory-full-action-bar.message");
                        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(ChatColor.translateAlternateColorCodes('&', message)), (byte) 2);
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                    } else {
                        new PlayerMessage("inventory-full", player);
                    }
                } catch (Exception e) {
                    new PlayerMessage("inventory-full", player);
                }
            }
        }
        for (ItemStack item : blockToBreak.getDrops()) {
            player.getInventory().addItem(item);
        }
        blockToBreak.setType(Material.AIR);
        blockToBreak.getDrops().clear();
    }

    public static void sellBlocks(Block blockToBreak, Player player) {
        if (!messagedPlayers.contains(player)) {
            messagedPlayers.add(player);
            if (player.getInventory().firstEmpty() == -1) {
                try {
                    if (NTools.getFiles().get("config").getBoolean("inventory-full-action-bar.enabled")) {
                        String message = NTools.getFiles().get("config").getString("inventory-full-action-bar.message");
                        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(ChatColor.translateAlternateColorCodes('&', message)), (byte) 2);
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                    } else {
                        new PlayerMessage("inventory-full", player);
                    }
                } catch (Exception e) {
                    new PlayerMessage("inventory-full", player);
                }
            }
        }
        blockToBreak.setType(Material.AIR);
        blockToBreak.getDrops().clear();
    }
}
