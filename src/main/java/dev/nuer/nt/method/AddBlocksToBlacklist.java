package dev.nuer.nt.method;

import dev.nuer.nt.NTools;

import java.util.ArrayList;

/**
 * Class that will add blocks to array lists and store them
 * This should help with optimization because it is only done once on start up / reload rather
 * than each time a block is broken
 */
public class AddBlocksToBlacklist {

    /**
     * Add a list of blocks to the trench blacklist
     *
     * @return
     */
    public static ArrayList<String> addTrenchBlacklist() {
        ArrayList<String> blockBlacklist = new ArrayList<>();
        for (String line :
                NTools.getFiles().get("config").getStringList("trench-block-blacklist")) {
            String block = line.toUpperCase();
            blockBlacklist.add(block);
        }
        return blockBlacklist;
    }

    public static ArrayList<String> createBlocklist(String filePath) {
        ArrayList<String> blocklist = new ArrayList<>();
        for (String line :
                NTools.getFiles().get("config").getStringList(filePath)) {
            String block = line.toUpperCase();
            blocklist.add(block);
        }
        return blocklist;
    }

    /**
     * Add a list of blocks to the tray whitelist
     *
     * @return
     */
    public static ArrayList<String> addTrayWhitelist() {
        ArrayList<String> blockWhitelist = new ArrayList<>();
        for (String line : NTools.getFiles().get("config").getStringList("tray-block-whitelist")) {
            String block = line.toUpperCase();
            blockWhitelist.add(block);
        }
        return blockWhitelist;
    }
}
