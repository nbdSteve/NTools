package dev.nuer.nt.event.multiToolMethod;

import dev.nuer.nt.NTools;
import dev.nuer.nt.external.nbtapi.NBTItem;
import dev.nuer.nt.initialize.OtherMapInitializer;
import dev.nuer.nt.method.player.PlayerMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class RadiusChange {

    public static void incrementRadius(NBTItem item, int toolTypeRawID, Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(NTools.getPlugin(NTools.class), () -> {
            String radiusLineIdentifier = OtherMapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get(0);
            int lineOfLore = getLineOfLore(item, radiusLineIdentifier);
            int maxRadius = Integer.parseInt(OtherMapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get
                    (OtherMapInitializer.multiToolRadiusUnique.get(toolTypeRawID).size() - 1));
            ItemMeta metaToSet = item.getItem().getItemMeta();
            List<String> newLoreToSet = item.getItem().getItemMeta().getLore();
            double priceToUpgrade =
                    NTools.getFiles().get("multi").getInt("multi-tools." + toolTypeRawID +
                            ".upgrade-cost." + item.getInteger("ntool.multi.radius"));
            if (item.getInteger("ntool.multi.radius") + 1 <= maxRadius) {
                if (NTools.economy != null) {
                    if (item.getInteger("ntool.multi.highest.radius") < item.getInteger("ntool.multi" +
                            ".radius") + 1) {
                        if (NTools.economy.getBalance(player) >= priceToUpgrade) {
                            NTools.economy.withdrawPlayer(player, priceToUpgrade);
                            newLoreToSet.set(lineOfLore,
                                    radiusLineIdentifier + " " + OtherMapInitializer.multiToolRadiusUnique.get(
                                            item.getInteger("ntool.raw.id")).get(item.getInteger("ntool.multi" + ".radius") + 1));
                            item.setInteger("ntool.multi.radius", item.getInteger("ntool.multi.radius") + 1);
                            item.setInteger("ntool.multi.highest.radius",
                                    item.getInteger("ntool.multi.radius") + 1);
                            metaToSet.setLore(newLoreToSet);
                            item.getItem().setItemMeta(metaToSet);
                            new PlayerMessage("incremented-radius", player, "{price}",
                                    NTools.numberFormat.format(priceToUpgrade));
                        } else {
                            new PlayerMessage("insufficient", player);
                        }
                    } else {
                        new PlayerMessage("incremented-radius-no-cost", player);
                    }
                }
            } else {
                new PlayerMessage("max-radius", player);
            }
        });
    }

    public static void decrementRadius(NBTItem item, int toolTypeRawID, Player player) {
        String radiusLineIdentifier = OtherMapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get(0);
        int lineOfLore = getLineOfLore(item, radiusLineIdentifier);
        int minRadius = Integer.parseInt(OtherMapInitializer.multiToolRadiusUnique.get(toolTypeRawID).get
                (OtherMapInitializer.multiToolRadiusUnique.get(toolTypeRawID).size() - 2));
        ItemMeta metaToSet = item.getItem().getItemMeta();
        List<String> newLoreToSet = item.getItem().getItemMeta().getLore();
        if (item.getInteger("ntool.multi.radius") - 1 >= minRadius) {
            newLoreToSet.set(lineOfLore,
                    radiusLineIdentifier + " " + OtherMapInitializer.multiToolRadiusUnique.get(
                            item.getInteger("ntool.raw.id")).get(item.getInteger("ntool.multi" +
                            ".radius") + 1));
            item.setInteger("ntool.multi.radius", item.getInteger("ntool.multi.radius") - 1);
            item.setInteger("ntool.multi.highest.radius",
                    item.getInteger("ntool.multi.radius") - 1);
            metaToSet.setLore(newLoreToSet);
            item.getItem().setItemMeta(metaToSet);
            new PlayerMessage("decremented-radius", player);
        } else {
            new PlayerMessage("min-radius", player);
        }
    }

    private static int getLineOfLore(NBTItem item, String radiusLineIdentifier) {
        int loreIndex = 0;
        for (String lore : item.getItem().getItemMeta().getLore()) {
            if (lore.contains(radiusLineIdentifier)) {
                return loreIndex;
            }
            loreIndex++;
        }
        return 0;
    }
}