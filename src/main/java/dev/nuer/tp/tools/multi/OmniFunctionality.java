package dev.nuer.tp.tools.multi;

import dev.nuer.tp.ToolsPlus;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class OmniFunctionality {

    public static ArrayList<String> spadeBlockTypes;
    public static ArrayList<String> pickaxeBlockTypes;
    public static ArrayList<String> axeBlockTypes;

    public static void changeToolType(Block block, Player player) {
        if (spadeBlockTypes.contains(block.getType().toString()) && !player.getItemInHand().getType().equals(Material.getMaterial("DIAMOND_SPADE"))) {
            player.getItemInHand().setType(Material.getMaterial("DIAMOND_SPADE"));
        }
        if (pickaxeBlockTypes.contains(block.getType().toString()) && !player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)) {
            player.getItemInHand().setType(Material.DIAMOND_PICKAXE);
        }
        if (axeBlockTypes.contains(block.getType().toString()) && !player.getItemInHand().getType().equals(Material.DIAMOND_AXE)) {
            player.getItemInHand().setType(Material.DIAMOND_AXE);
        }
    }

    public static void loadOmniToolBlocks() {
        spadeBlockTypes = new ArrayList<>();
        pickaxeBlockTypes = new ArrayList<>();
        axeBlockTypes = new ArrayList<>();
        for (String blockType : ToolsPlus.getFiles().get("config").getStringList("omni-config.shovel-blocks")) {
            spadeBlockTypes.add(blockType.toUpperCase());
        }
        for (String blockType : ToolsPlus.getFiles().get("config").getStringList("omni-config.pickaxe-blocks")) {
            pickaxeBlockTypes.add(blockType.toUpperCase());
        }
        for (String blockType : ToolsPlus.getFiles().get("config").getStringList("omni-config.axe-blocks")) {
            axeBlockTypes.add(blockType.toUpperCase());
        }
    }

    public static void clearOmniLists() {
        spadeBlockTypes.clear();
        pickaxeBlockTypes.clear();
        axeBlockTypes.clear();
    }
}
