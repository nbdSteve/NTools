package gg.steve.mc.tp.modules.harvester.utils;

import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.List;

public enum HarvestableBlockType {
    CROPS(new CropAttributeUtil("crops", "WHEAT"), "CROPS", "WHEAT"),
    CARROT(new CropAttributeUtil("carrot", "CARROT_ITEM", "CARROTS"), "CARROT"),
    POTATO(new CropAttributeUtil("potato", "POTATO_ITEM", "POTATOES"), "POTATO"),
    PUMPKIN(new CropAttributeUtil("pumpkin", "PUMPKIN"), "PUMPKIN_BLOCK"),
    MELON(new CropAttributeUtil("melon", "MELON"), "MELON_BLOCK"),
    COCOA(new CropAttributeUtil("cocoa", "INK_SACK:3", "COCOA_BEANS"), "COCOA"),
    BEETROOT(new CropAttributeUtil("beetroot", "BEETROOT", "BEETROOTS"), "BEETROOT_BLOCK", "BEETROOT"),
    NETHER_WART(new CropAttributeUtil("nether_wart", "NETHER_STALK", "NETHER_WARTS", "NETHER_WART"), "NETHER_WARTS", "NETHER_WART"),
    SUGAR_CANE(new CropAttributeUtil("sugar_cane", "SUGAR_CANE"), "SUGAR_CANE_BLOCK", "SUGAR_CANE"),
    CACTUS(new CropAttributeUtil("cactus", "CACTUS"), "CACTUS");

    private CropAttributeUtil attributes;
    private List<String> materials;

    HarvestableBlockType(CropAttributeUtil attributes, String... materials) {
        this.attributes = attributes;
        this.materials = Arrays.asList(materials);
    }

    public static HarvestableBlockType getTypeFromBlock(Block block) {
        for (HarvestableBlockType type : HarvestableBlockType.values()) {
            if (!type.attributes.isEnabled()) continue;
            if (type.materials.contains(block.getType().toString()) && block.getData() >= type.attributes.getRipeData()) return type;
        }
        return null;
    }

    public CropAttributeUtil getAttributes() {
        return attributes;
    }
}
