package dev.nuer.tp.tools;

import dev.nuer.tp.managers.FileManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Class that handles omni functionality for tools, this is switching what type of tool the player is holding
 * based on the block they are breaking
 */
public class OmniFunctionality {
    //Store the blocks to be broken by shovels
    public static ArrayList<String> spadeBlockTypes;
    //Store the blocks to be broken by pickaxes
    public static ArrayList<String> pickaxeBlockTypes;
    //Store the blocks to be broken by axes
    public static ArrayList<String> axeBlockTypes;

    /**
     * Method to change the tool that the player is currently holding, updates in their hand
     *
     * @param block  Block, the block being broken
     * @param player Player, the player breaking
     */
    public static void changeToolType(Block block, Player player) {
        String itemInHand = player.getItemInHand().getType().toString();
        String shovelMaterial = "_" + FileManager.get("omni_config").getString("omni-config.shovel-material-name").toUpperCase();
        String[] materialType;
        try {
            materialType = itemInHand.split("_");
        } catch (Exception notATool) {
            return;
        }
        if (spadeBlockTypes.contains(block.getType().toString()) && !player.getItemInHand().getType().equals(Material.getMaterial(materialType[0] + shovelMaterial))) {
            player.getItemInHand().setType(Material.getMaterial(materialType[0] + shovelMaterial));
        }
        if (pickaxeBlockTypes.contains(block.getType().toString()) && !player.getItemInHand().getType().equals(Material.getMaterial(materialType[0] + "_PICKAXE"))) {
            player.getItemInHand().setType(Material.getMaterial(materialType[0] + "_PICKAXE"));
        }
        if (axeBlockTypes.contains(block.getType().toString()) && !player.getItemInHand().getType().equals(Material.getMaterial(materialType[0] + "_AXE"))) {
            player.getItemInHand().setType(Material.getMaterial(materialType[0] + "_AXE"));
        }
    }

    /**
     * Method to load the blocks broken by respective tools from the configuration
     */
    public static void loadOmniToolBlocks() {
        spadeBlockTypes = new ArrayList<>();
        pickaxeBlockTypes = new ArrayList<>();
        axeBlockTypes = new ArrayList<>();
        for (String blockType : FileManager.get("omni_config").getStringList("omni-config.shovel-blocks")) {
            spadeBlockTypes.add(blockType.toUpperCase());
        }
        for (String blockType : FileManager.get("omni_config").getStringList("omni-config.pickaxe-blocks")) {
            pickaxeBlockTypes.add(blockType.toUpperCase());
        }
        for (String blockType : FileManager.get("omni_config").getStringList("omni-config.axe-blocks")) {
            axeBlockTypes.add(blockType.toUpperCase());
        }
    }

    /**
     * Clears the tools blocks lists
     */
    public static void clearOmniLists() {
        spadeBlockTypes.clear();
        pickaxeBlockTypes.clear();
        axeBlockTypes.clear();
    }
}
