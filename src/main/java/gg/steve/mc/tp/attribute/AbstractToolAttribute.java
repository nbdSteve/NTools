package gg.steve.mc.tp.attribute;

import gg.steve.mc.tp.nbt.NBTItem;
import gg.steve.mc.tp.utils.ColorUtil;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class AbstractToolAttribute {
    private ToolAttributeType type;
    private String updateString;

    public AbstractToolAttribute(ToolAttributeType type, String updateString) {
        this.type = type;
        this.updateString = ColorUtil.colorize(updateString);
    }

    public ToolAttributeType getType() {
        return type;
    }

    public String getUpdateString() {
        return updateString;
    }

    public abstract boolean doUpdate(Player player, NBTItem item, UUID toolId, int current, int change);
}
