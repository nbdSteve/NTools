package gg.steve.mc.tp.tool;

import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.utils.ColorUtil;
import gg.steve.mc.tp.utils.GetToolHoldingUtil;
import gg.steve.mc.tp.utils.ItemBuilderUtil;
import gg.steve.mc.tp.utils.LogUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class LoadedTool {
    private AbstractTool tool;
    private UUID toolId;
    private int uses, blocksMined;
    private double multiplier;
    private String name;

    public LoadedTool(UUID toolId, NBTItem item) {
        this.toolId = toolId;
        this.uses = item.getInteger("tools+.uses");
        this.blocksMined = item.getInteger("tools+.blocks");
        this.multiplier = item.getDouble("tools+.multiplier");
        this.name = item.getString("tools+.name");
        this.tool = ToolsManager.getTool(this.name);
    }

    public boolean decrementUses(Player player) {
        if (tool.isUnlimitedUses()) return true;
        NBTItem nbtItem = new NBTItem(player.getItemInHand());
        if (!nbtItem.getItem().hasItemMeta() || nbtItem.getItem().getItemMeta().getLore().isEmpty()) {
            LogUtil.warning("Tried to decrement uses for a tool that doesn't have any lore! Aborting.");
            return false;
        }
        if (this.uses - 1 < 1) {
            player.setItemInHand(new ItemStack(Material.AIR));
            ToolsManager.removeLoadedTool(this.toolId);
            // message player
            return true;
        }
        String current = ColorUtil.colorize(tool.getConfig().getString("uses.lore-update-string").replace("{uses}", String.valueOf(this.uses)));
        this.uses--;
        String replacement = ColorUtil.colorize(tool.getConfig().getString("uses.lore-update-string").replace("{uses}", String.valueOf(this.uses)));
        nbtItem.setInteger("tools+.uses", this.uses);
        ItemBuilderUtil builder = new ItemBuilderUtil(nbtItem.getItem());
        List<String> lore = builder.getLore();
        for (int i = 0; i < lore.size(); i++) {
            if (lore.get(i).equalsIgnoreCase(current)) {
                lore.set(i, replacement);
                break;
            }
        }
        builder.setLore(lore);
        nbtItem.getItem().setItemMeta(builder.getItemMeta());
        if (GetToolHoldingUtil.isStillHoldingTool(this.toolId, player.getItemInHand())) {
            player.setItemInHand(nbtItem.getItem());
            player.updateInventory();
            return true;
        } else {
            LogUtil.warning("Uses dupe attempted by player: " + player.getName() + ", Tools+ has stopped the tool action from happening.");
            return false;
        }
    }

    public AbstractTool getAbstractTool() {
        return tool;
    }

    public String getName() {
        return name;
    }
}
