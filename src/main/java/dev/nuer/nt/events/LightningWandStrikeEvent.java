package dev.nuer.nt.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LightningWandStrikeEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Block blockToStrike;
    private Player player;
    private boolean cancel;

    public LightningWandStrikeEvent(Block blockToStrike, Player player) {
        this.blockToStrike = blockToStrike;
        this.player = player;
    }

    public Block getBlockToStrike() {
        return blockToStrike;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
