package dev.nuer.tp.method;

import dev.nuer.tp.support.nbtapi.NBTItem;

public class VerifyTool {

    public static boolean check(NBTItem nbtItem) {
        try {
            if (nbtItem.getBoolean("tools+.multi")) return true;
        } catch (NullPointerException e) {
            //Tool is not a multi tool
        }
        try {
            if (nbtItem.getBoolean("tools+.trench")) return true;
        } catch (NullPointerException e) {
            //Tool is not a trench tool
        }
        try {
            if (nbtItem.getBoolean("tools+.tray")) return true;
        } catch (NullPointerException e) {
            //Tool is not a tray tool
        }
        try {
            if (nbtItem.getBoolean("tools+.sand")) return true;
        } catch (NullPointerException e) {
            //Tool is not a sand wand
        }
//        try {
//            if (nbtItem.getBoolean("tools+.lightning")) return true;
//        } catch (NullPointerException e) {
//            //Tool is not a lightning wand
//        }
        try {
            if (nbtItem.getBoolean("tools+.harvester")) return true;
        } catch (NullPointerException e) {
            //Tool is not a harvester tool
        }
        try {
            if (nbtItem.getBoolean("tools+.sell")) return true;
        } catch (NullPointerException e) {
            //Tool is not a sell wand
        }
        try {
            if (nbtItem.getBoolean("tools+.tnt")) return true;
        } catch (NullPointerException e) {
            //Tool is not a tnt wand
        }
        try {
            if (nbtItem.getBoolean("tools+.aqua")) return true;
        } catch (NullPointerException e) {
            //Tool is not a aqua wand
        }
        try {
            if (nbtItem.getBoolean("tools+.smelt")) return true;
        } catch (NullPointerException e) {
            //Tool is not a smelt wand
        }
        return false;
    }
}
