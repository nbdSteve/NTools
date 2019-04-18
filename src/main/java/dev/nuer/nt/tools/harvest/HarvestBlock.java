package dev.nuer.nt.tools.harvest;

import dev.nuer.nt.NTools;
import dev.nuer.nt.method.player.AddBlocksToPlayerInventory;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HarvestBlock {

    public static void harvestBlocks(BlockDamageEvent event, Player player, boolean sellMode, Double blockPrice, Double priceModifier) {
        ArrayList<Block> blocksToHarvest = new ArrayList<>();
        if (event.getBlock().getType().toString().equalsIgnoreCase("SUGAR_CANE_BLOCK")) {
            connectedBlockRemoval(event.getBlock().getY(), event, player, blocksToHarvest, true);
        } else if (event.getBlock().getType().toString().equalsIgnoreCase("CACTUS")) {
            connectedBlockRemoval(event.getBlock().getY(), event, player, blocksToHarvest, false);
        } else {
            blocksToHarvest.add(event.getBlock());
        }
        //need to iterate from the top down
        for (int i = blocksToHarvest.size() - 1; i >= 0; i--) {
            BlockBreakEvent blockRemoval = new BlockBreakEvent(blocksToHarvest.get(i), player);
            Bukkit.getPluginManager().callEvent(blockRemoval);
            if (!blockRemoval.isCancelled()) {
                if (sellMode) {
                    if (NTools.economy != null) {
                        AddBlocksToPlayerInventory.sellBlocks(blocksToHarvest.get(i), player);
                        double priceToDeposit = blockPrice * priceModifier;
                        NTools.economy.depositPlayer(player, priceToDeposit);
                        //Create a message
                        try {
                            if (NTools.getFiles().get("config").getBoolean("harvester-action-bar.enabled")) {
                                String message = NTools.getFiles().get("config").getString("harvester-action-bar.message").replace("{deposit}", new DecimalFormat("##.00").format(priceToDeposit * blocksToHarvest.size()));
                                PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(ChatColor.translateAlternateColorCodes('&', message)), (byte) 2);
                                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                            } else {
                                HandleSellingMessages.handleSellingMessages(player, priceToDeposit);
                            }
                        } catch (Exception e) {
                            HandleSellingMessages.handleSellingMessages(player, priceToDeposit);
                        }
                    }
                } else {
                    AddBlocksToPlayerInventory.addBlocks(blocksToHarvest.get(i), player);
                }
            }
        }
        AddBlocksToPlayerInventory.messagedPlayers.remove(player);
    }

    public static void connectedBlockRemoval(int yCoordinate, BlockDamageEvent event, Player player, ArrayList<Block> blocksToHarvest, boolean sugarCane) {
        if (sugarCane) {
            while (player.getWorld().getBlockAt(event.getBlock().getX(), yCoordinate, event.getBlock().getZ()).
                    getType().toString().equalsIgnoreCase("SUGAR_CANE_BLOCK") && yCoordinate < 256) {
                blocksToHarvest.add(player.getWorld().getBlockAt(event.getBlock().getX(), yCoordinate, event.getBlock().getZ()));
                yCoordinate++;
            }
        } else {
            while (player.getWorld().getBlockAt(event.getBlock().getX(), yCoordinate, event.getBlock().getZ()).
                    getType().toString().equalsIgnoreCase("CACTUS") && yCoordinate < 256) {
                blocksToHarvest.add(player.getWorld().getBlockAt(event.getBlock().getX(), yCoordinate, event.getBlock().getZ()));
                yCoordinate++;
            }
        }
    }
}
