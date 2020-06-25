package gg.steve.mc.tp.modules.harvester.utils;

import gg.steve.mc.tp.framework.yml.utils.FileManagerUtil;
import gg.steve.mc.tp.modules.harvester.HarvesterModule;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CropAttributeUtil {
    private byte ripeData;
    private boolean enabled, multiDrop, autoReplant;
    private int minDrop, maxDrop;
    private Random random;
    private List<String> drops;

    public CropAttributeUtil(String name, String... drops) {
        YamlConfiguration config = FileManagerUtil.get(HarvesterModule.moduleConfigId);
        this.ripeData = (byte) config.getInt(name + ".ripe-data");
        this.enabled = config.getBoolean(name + ".enabled");
        this.multiDrop = config.getBoolean(name + ".multi-drop.enabled");
        this.autoReplant = config.getBoolean(name + ".auto-replant");
        this.minDrop = config.getInt(name + ".multi-drop.min");
        this.maxDrop = config.getInt(name + ".multi-drop.max");
        this.drops = Arrays.asList(drops);
        this.random = new Random();
    }

    public ItemStack getItemDrop() {
        for (String drop : this.drops) {
            Material material;
            String[] parts = new String[0];
            if (drop.contains(":")) {
                parts = drop.split(":");
                try {
                    material = Material.valueOf(parts[0]);
                } catch (Exception e) {
                    continue;
                }
            } else {
                try {
                    material = Material.valueOf(drop);
                } catch (Exception e) {
                    continue;
                }
            }
            if (parts.length > 1) {
                if (this.multiDrop) {
                    return new ItemStack(material, (this.random.nextInt(this.maxDrop) + this.minDrop), (byte) Integer.parseInt(parts[1]));
                } else {
                    return new ItemStack(material, this.minDrop, (byte) Integer.parseInt(parts[1]));
                }
            }
            if (this.multiDrop) {
                return new ItemStack(material, (this.random.nextInt(this.maxDrop) + this.minDrop));
            } else {
                return new ItemStack(material, this.minDrop);
            }
        }
        return new ItemStack(Material.AIR);
    }

    public byte getRipeData() {
        return ripeData;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isMultiDrop() {
        return multiDrop;
    }

    public boolean isAutoReplant() {
        return autoReplant;
    }

    public int getMinDrop() {
        return minDrop;
    }

    public int getMaxDrop() {
        return maxDrop;
    }

    public Random getRandom() {
        return random;
    }

    public List<String> getDrops() {
        return drops;
    }
}
