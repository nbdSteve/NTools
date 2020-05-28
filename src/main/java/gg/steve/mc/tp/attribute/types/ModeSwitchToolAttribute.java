package gg.steve.mc.tp.attribute.types;

import gg.steve.mc.tp.attribute.AbstractToolAttribute;
import gg.steve.mc.tp.attribute.ToolAttributeType;
import gg.steve.mc.tp.managers.PluginFile;
import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.utils.GetToolHoldingUtil;
import gg.steve.mc.tp.utils.ItemBuilderUtil;
import gg.steve.mc.tp.utils.LogUtil;
import org.bukkit.entity.Player;

import java.util.*;

public class ModeSwitchToolAttribute extends AbstractToolAttribute {
    private Map<Integer, List<Object>> track;
    private boolean sneakSwitch;

    public ModeSwitchToolAttribute(String updateString) {
        super(ToolAttributeType.MODE_SWITCH, updateString);
    }

    public void loadData(PluginFile file) {
        track = new HashMap<>();
        int pos = 0;
        for (String entry : file.get().getStringList("mode.track")) {
            track.put(pos, new ArrayList<>());
            String[] parts = entry.split(":");
            track.get(pos).add(parts[0]);
            track.get(pos).add(parts[1]);
            pos++;
        }
        this.sneakSwitch = file.get().getBoolean("mode.sneak-switch");
    }

    @Override
    public boolean doUpdate(Player player, NBTItem item, UUID toolId, int current, int next) {
        if (!item.getItem().hasItemMeta() || item.getItem().getItemMeta().getLore().isEmpty()) {
            LogUtil.warning("Tried to switch mode for a tool that doesn't have any lore! Aborting.");
            return false;
        }
        String currentLore = getUpdateString().replace("{mode}", (String) track.get(current).get(0));
        String replacementLore = getUpdateString().replace("{mode}", (String) track.get(next).get(0));
        item.setInteger("tools+.mode-level", next);
        ItemBuilderUtil builder = new ItemBuilderUtil(item.getItem());
        List<String> lore = builder.getLore();
        for (int i = 0; i < lore.size(); i++) {
            if (lore.get(i).contains(currentLore)) {
                String line = lore.get(i).replace(currentLore, replacementLore);
                lore.set(i, line);
                break;
            }
        }
        builder.setLore(lore);
        item.getItem().setItemMeta(builder.getItemMeta());
        if (GetToolHoldingUtil.isStillHoldingTool(toolId, player.getItemInHand())) {
            player.setItemInHand(item.getItem());
            player.updateInventory();
            return true;
        } else {
            LogUtil.warning("Mode switch dupe attempted by player: " + player.getName() + ", Tools+ has stopped the tool action from happening.");
            return false;
        }
    }

    public int getNextMode(int current) {
        if (current + 1 < track.size()) return current + 1;
        return 0;
    }

    public boolean isSneakSwitch() {
        return sneakSwitch;
    }
}
