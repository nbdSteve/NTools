package dev.nuer.nt.method;

import dev.nuer.nt.NTools;

import java.util.ArrayList;

/**
 * Class that will add blocks to array lists and store them
 * This should help with optimization because it is only done once on start up / reload rather
 * than each time a block is broken
 */
public class AddBlocksToList {

    /**
     * Create an internal list of blocks that can / cannot be broken by a tool
     *
     * @param directory String, the name of the directory
     * @param filePath  String, the path way inside the .yml file
     * @return ArrayList<String>
     */
    public static ArrayList<String> createBlockList(String directory, String filePath) {
        ArrayList<String> blockList = new ArrayList<>();
        for (String line : NTools.getFiles().get(directory).getStringList(filePath)) {
            String block = line.toUpperCase();
            blockList.add(block);
        }
        return blockList;
    }
}
