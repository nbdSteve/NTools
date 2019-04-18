package dev.nuer.nt.tools.harvest;

import dev.nuer.nt.NTools;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.UUID;

/**
 * Class that handles sending a delayed message to players for harvester hoes
 */
public class HandleSellingMessages {

    //Store the players who are breaking blocks, and their message cooldown
    public static HashMap<UUID, Long> playersSellingByHarvest;
    //Track the current players deposit amount, before it is given to them
    public static HashMap<UUID, Double> trackPlayerDeposits;
    //Store the active message tasks associated with a player
    public static HashMap<UUID, BukkitTask> activeMessageTasks;
    //Store the message delay for the plugin
    private static int messageDelay = NTools.getFiles().get("config").getInt("harvester-selling-message-delay");

    /**
     *
     * @param player
     * @param sellPrice
     */
    public static void handleSellingMessages(Player player, double sellPrice) {
        if (playersSellingByHarvest == null) {
            playersSellingByHarvest = new HashMap<>();
        }
        if (trackPlayerDeposits == null) {
            trackPlayerDeposits = new HashMap<>();
        }
        if (activeMessageTasks == null) {
            activeMessageTasks = new HashMap<>();
        }
        updateMessageCooldown(player, sellPrice);
    }

    /**
     *
     * @param player
     * @param sellPrice
     */
    private static void updateMessageCooldown(Player player, double sellPrice) {
        //Deposit the money first
        NTools.economy.depositPlayer(player, sellPrice);
        //Increment the players total deposit, create one if they don't have one
        if (trackPlayerDeposits.get(player.getUniqueId()) != null) {
            double newCurrentTotal = trackPlayerDeposits.get(player.getUniqueId()) + sellPrice;
            trackPlayerDeposits.put(player.getUniqueId(), newCurrentTotal);
        } else {
            trackPlayerDeposits.put(player.getUniqueId(), sellPrice);
        }
        //Remove the player from the map and cancel all tasks associated
        if (playersSellingByHarvest.containsKey(player.getUniqueId())) {
            activeMessageTasks.get(player.getUniqueId()).cancel();
        } else {
            new PlayerMessage("start-selling-by-harvest", player, "{price}", new DecimalFormat("##.00").format(sellPrice));
        }
        //Update the cooldown for a player
        playersSellingByHarvest.put(player.getUniqueId(), System.currentTimeMillis() + (messageDelay * 15));
        //Run the delayed message runnable
        sendDelayedMessage(player);
    }

    /**
     *
     * @param player
     */
    private static void sendDelayedMessage(Player player) {
        activeMessageTasks.put(player.getUniqueId(), Bukkit.getScheduler().runTaskLater(NTools.getPlugin(NTools.class), () -> {
            if (playersSellingByHarvest.get(player.getUniqueId()) - System.currentTimeMillis() <= 0) {
                new PlayerMessage("bulk-deposit-by-harvest", player, "{deposit}",
                        new DecimalFormat("##.00").format(trackPlayerDeposits.get(player.getUniqueId())));
                playersSellingByHarvest.remove(player.getUniqueId());
                trackPlayerDeposits.remove(player.getUniqueId());
            }
        }, messageDelay));
    }
}
