package gg.steve.mc.tp.modules.utils;

import org.bukkit.block.Block;

import java.util.Comparator;

public class YLevelComparator implements Comparator<Block> {

    @Override
    public int compare(Block o1, Block o2) {
        return o2.getY() - o1.getY();
    }
}
