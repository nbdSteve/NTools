package gg.steve.mc.tp.modules.lightning.utils;

import gg.steve.mc.tp.managers.FileManager;
import gg.steve.mc.tp.modules.lightning.LightningModule;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class StrikeUtil {

    public static void doStrike(Player player, Block target) {
        target.getWorld().strikeLightningEffect(target.getLocation());
        double damage = FileManager.get(LightningModule.moduleConfigId).getDouble("strike-damage");
        for (Entity entity : target.getWorld().getNearbyEntities(target.getLocation(), 1, 2, 1)) {
            if (entity instanceof Creeper) {
                Creeper creeper = (Creeper) entity;
                creeper.setPowered(true);
            } else if (entity instanceof Player) {
                EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(player, entity, EntityDamageEvent.DamageCause.ENTITY_ATTACK, damage);
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled()) continue;
                Player damagee = (Player) entity;
                damagee.damage(damage, player);
                damagee.setVelocity(damagee.getVelocity().subtract(damagee.getVelocity()));
            }
        }
    }

    public static Block getTargetBlock(Player player) {
        int distance = FileManager.get(LightningModule.moduleConfigId).getInt("strike-distance");
        try {
            return player.getWorld().getHighestBlockAt(player.getLineOfSight(null, distance).get(distance).getLocation());
        } catch (Exception e) {
            return player.getWorld().getHighestBlockAt(player.getLineOfSight(null, distance).get(player.getLineOfSight(null, distance).size() - 1).getLocation());
        }
    }
}
