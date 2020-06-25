package gg.steve.mc.tp.modules.craft.recipes;

import gg.steve.mc.tp.framework.utils.LogUtil;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Recipe {
    private ItemStack crafted;
    private List<ItemStack> parts;

    public Recipe(String entry) {
        parts = new ArrayList<>();
        String[] itemSplit = entry.split("-");
        if (itemSplit.length <= 1) {
            LogUtil.warning("Invalid recipe for entry: " + entry);
        }
        for (int i = 0; i < itemSplit.length; i++) {
            String[] item = itemSplit[i].split(":");
            Material material = Material.STONE;
            try {
                material = Material.valueOf(item[0].toUpperCase());
            } catch (Exception e) {
                try {
                    material = Material.valueOf("LEGACY_" + item[0].toUpperCase());
                } catch (Exception e1) {
                    LogUtil.warning("Invalid material for entry: " + Arrays.toString(item));
                }
            }
            Short data = 0;
            try {
                data = Short.parseShort(item[1]);
            } catch (Exception e) {
                LogUtil.warning("Invalid data value for entry: " + Arrays.toString(item));
            }
            int amount = 1;
            try {
                amount = Integer.parseInt(item[2]);
            } catch (Exception e) {
                LogUtil.warning("Invalid amount for entry: " + Arrays.toString(item));
            }
            ItemStack itemStack = new ItemStack(material, amount, data);
            if (i == 0) {
                crafted = itemStack;
            } else {
                parts.add(itemStack);
            }
        }
    }

    public int doCrafting(Inventory inventory) {
        Map<String, Integer> compound = new HashMap<>();
        Map<String, List<Integer>> slots = new HashMap<>();
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            if (inventory.getItem(slot) == null) continue;
            ItemStack slotItem = inventory.getItem(slot);
            for (ItemStack part : this.parts) {
                if (!isMatch(slotItem, part)) continue;
                if (compound.containsKey(getItemString(slotItem))) {
                    int current = compound.get(getItemString(slotItem));
                    compound.remove(getItemString(slotItem));
                    compound.put(getItemString(slotItem), current + slotItem.getAmount());
                } else {
                    compound.put(getItemString(slotItem), slotItem.getAmount());
                    slots.put(getItemString(slotItem), new ArrayList<>());
                }
                slots.get(getItemString(slotItem)).add(slot);
            }
        }
        int amount = getMaxCraftable(compound);
        if (amount == 0) return 0;
        for (ItemStack part : this.parts) {
            for (int slot : slots.get(getItemString(part))) {
                inventory.clear(slot);
            }
            if ((compound.get(getItemString(part)) - (getMaxCraftable(compound) * part.getAmount())) == 0)
                continue;
            inventory.addItem(new ItemStack(part.getType(), (compound.get(getItemString(part)) - (getMaxCraftable(compound) * part.getAmount())), part.getDurability()));
        }
        for (int i = 0; i < amount; i++) {
            inventory.addItem(crafted);
        }
        return crafted.getAmount() * amount;
    }

    public boolean isMatch(ItemStack i1, ItemStack i2) {
        return i1.getType() == i2.getType() && i1.getDurability() == i2.getDurability();
    }

    public String getItemString(ItemStack i1) {
        return i1.getType().name() + ":" + i1.getDurability();
    }

    public int getMaxCraftable(Map<String, Integer> compound) {
        int amount = 0;
        for (ItemStack part : this.parts) {
            if (!compound.containsKey(getItemString(part))) return 0;
            try {
                if (compound.get(getItemString(part)) < part.getAmount()) return 0;
            } catch (NullPointerException e) {
                return 0;
            }
            int temp = (int) Math.floor(compound.get(getItemString(part)) / part.getAmount());
            if (temp < amount || amount == 0) {
                amount = temp;
            }
        }
        return amount;
    }
}
