package gg.steve.mc.tp.modules.smelt.conversions;

import gg.steve.mc.tp.framework.utils.LogUtil;
import gg.steve.mc.tp.framework.yml.utils.FileManagerUtil;
import gg.steve.mc.tp.modules.smelt.SmeltModule;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemSmeltManager {
    private static Map<ItemStack, ItemStack> conversions;

    public static void initialise() {
        conversions = new HashMap<>();
        YamlConfiguration config = FileManagerUtil.get(SmeltModule.moduleConfigId);
        for (String entry : config.getStringList("conversions")) {
            String[] raw = entry.split("-")[0].split(":");
            String[] smelted = entry.split("-")[1].split(":");
            try {
                conversions.put(new ItemStack(Material.valueOf(raw[0].toUpperCase()), 1, Short.parseShort(raw[1])),
                        new ItemStack(Material.valueOf(smelted[0].toUpperCase()), 1, Short.parseShort(smelted[1])));
            } catch (Exception e) {
                LogUtil.warning("Invalid smelt conversion for entry: " + entry);
            }
        }
    }

    public static void shutdown() {
        if (conversions != null && !conversions.isEmpty()) conversions.clear();
    }

    public static int doSmelting(List<Inventory> inventories) {
        int smelted = 0;
        for (Inventory inventory : inventories) {
            for (int slot = 0; slot < inventory.getSize(); slot++) {
                ItemStack slotItem = inventory.getItem(slot);
                if (slotItem == null || slotItem.getType() == Material.AIR || !hasSmeltedType(slotItem)) continue;
                ItemStack smeltedItem = getSmeltedType(slotItem);
                int amount = slotItem.getAmount();
                smelted += amount;
                smeltedItem.setAmount(amount);
                inventory.setItem(slot, smeltedItem);
            }
        }
        return smelted;
    }
    
    public static boolean hasSmeltedType(ItemStack item) {
        for (ItemStack cached : conversions.keySet()) {
            if (isMatch(item, cached)) return true;
        }
        return false;
    }

    public static ItemStack getSmeltedType(ItemStack item) {
        for (ItemStack cached : conversions.keySet()) {
            if (isMatch(item, cached)) return conversions.get(cached);
        }
        return null;
    }

    public static boolean isMatch(ItemStack i1, ItemStack i2) {
        return i1.getType() == i2.getType() && i1.getDurability() == i2.getDurability();
    }
}
