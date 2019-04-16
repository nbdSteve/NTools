package dev.nuer.nt.event.custom;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BlockBreakBySandWand extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private Block blockToBeBroken;
    private Player player;

    public BlockBreakBySandWand(Block blockToBeBroken, Player player) {
        this.blockToBeBroken = blockToBeBroken;
        this.player = player;
    }

    public Block getBlockToBeBroken() {
        return blockToBeBroken;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
