package dev.nuer.tp.managers;

import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.gui.BuyToolsGenericGui;
import dev.nuer.tp.gui.config.*;
import dev.nuer.tp.gui.purchase.*;

/**
 * Class that handles creating and getting a the plugins Gui's
 */
public class GuiManager {
    //Instance of multi tool options gui
    private static MultiToolConfigurationGui multiToolConfigurationGui;
    //Instance of harvester config gui
    private static HarvesterConfigurationGui harvesterConfigurationGui;
    //Instance of sell wand config gui
    private static SellWandConfigurationGui sellWandConfigurationGui;
    //Instance of tnt wand config gui
    private static TNTWandConfigurationGui tntWandConfigurationGui;
    //Instance of aqua wand config gui
    private static AquaWandConfigurationGui aquaWandConfigurationGui;
    //Instance of chunk tool config gui
    private static ChunkToolConfigurationGui chunkToolConfigurationGui;
    //Instance of multi tools gui
    private static BuyMultiToolsGui buyMultiToolsGui;
    //Instance of generic buy gui
    private static BuyToolsGenericGui buyToolsGenericGui;
    //Instance of trench tools gui
    private static BuyTrenchToolsGui buyTrenchToolsGui;
    //Instance of tray tools gui
    private static BuyTrayToolsGui buyTrayToolsGui;
    //Instance of tray tools gui
    private static BuySandWandsGui buySandWandsGui;
    //Instance of lightning wands gui
    private static BuyLightningWandsGui buyLightningWandsGui;
    //Instance of harvester gui
    private static BuyHarvesterToolsGui buyHarvesterToolsGui;
    //Instance of sell gui
    private static BuySellWandsGui buySellWandsGui;
    //Instance of tnt purchase gui
    private static BuyTNTWandsGui buyTNTWandsGui;
    //Instance of aqua purchase gui
    private static BuyAquaWandsGui buyAquaWandsGui;
    //Instance of smelt purchase gui
    private static BuySmeltWandsGui buySmeltWandsGui;
    //Instance of chunk purchase gui
    private static BuyChunkToolsGui buyChunkToolsGui;

    /**
     * Creates a new instance of all Guis for the plugin
     */
    public static void load() {
        //Create the Gui instances
        multiToolConfigurationGui = new MultiToolConfigurationGui();
        harvesterConfigurationGui = new HarvesterConfigurationGui();
        sellWandConfigurationGui = new SellWandConfigurationGui();
        tntWandConfigurationGui = new TNTWandConfigurationGui();
        aquaWandConfigurationGui = new AquaWandConfigurationGui();
        chunkToolConfigurationGui = new ChunkToolConfigurationGui();
        buyToolsGenericGui = new BuyToolsGenericGui();
        buyMultiToolsGui = new BuyMultiToolsGui();
        buyTrenchToolsGui = new BuyTrenchToolsGui();
        buyTrayToolsGui = new BuyTrayToolsGui();
        buySandWandsGui = new BuySandWandsGui();
        buyLightningWandsGui = new BuyLightningWandsGui();
        buyHarvesterToolsGui = new BuyHarvesterToolsGui();
        buySellWandsGui = new BuySellWandsGui();
        buyTNTWandsGui = new BuyTNTWandsGui();
        buyAquaWandsGui = new BuyAquaWandsGui();
        buySmeltWandsGui = new BuySmeltWandsGui();
        buyChunkToolsGui = new BuyChunkToolsGui();
    }

    /**
     * Gets the specified gui based off of the name
     *
     * @param guiName String, gui name
     * @return AbstractGui
     */
    public static AbstractGui getGui(String guiName) {
        if (guiName.equalsIgnoreCase("multi-config")) return multiToolConfigurationGui;
        if (guiName.equalsIgnoreCase("multi-buy")) return buyMultiToolsGui;
        if (guiName.equalsIgnoreCase("generic-buy")) return buyToolsGenericGui;
        if (guiName.equalsIgnoreCase("trench-buy")) return buyTrenchToolsGui;
        if (guiName.equalsIgnoreCase("tray-buy")) return buyTrayToolsGui;
        if (guiName.equalsIgnoreCase("sand-buy")) return buySandWandsGui;
        if (guiName.equalsIgnoreCase("lightning-buy")) return buyLightningWandsGui;
        if (guiName.equalsIgnoreCase("harvester-buy")) return buyHarvesterToolsGui;
        if (guiName.equalsIgnoreCase("harvester-config")) return harvesterConfigurationGui;
        if (guiName.equalsIgnoreCase("sell-buy")) return buySellWandsGui;
        if (guiName.equalsIgnoreCase("sell-config")) return sellWandConfigurationGui;
        if (guiName.equalsIgnoreCase("tnt-buy")) return buyTNTWandsGui;
        if (guiName.equalsIgnoreCase("tnt-config")) return tntWandConfigurationGui;
        if (guiName.equalsIgnoreCase("aqua-buy")) return buyAquaWandsGui;
        if (guiName.equalsIgnoreCase("aqua-config")) return aquaWandConfigurationGui;
        if (guiName.equalsIgnoreCase("smelt-buy")) return buySmeltWandsGui;
        if (guiName.equalsIgnoreCase("chunk-config")) return chunkToolConfigurationGui;
        if (guiName.equalsIgnoreCase("chunk-buy")) return buyChunkToolsGui;
        return null;
    }
}