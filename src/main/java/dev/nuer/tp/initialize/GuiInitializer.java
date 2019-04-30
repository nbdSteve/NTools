package dev.nuer.tp.initialize;

import dev.nuer.tp.gui.AbstractGui;
import dev.nuer.tp.gui.BuyToolsGenericGui;
import dev.nuer.tp.gui.config.*;
import dev.nuer.tp.gui.purchase.*;

/**
 * Class that handles creating and getting a the plugins Gui's
 */
public class GuiInitializer {
    //Instance of multi tool options gui
    private MultiToolConfigurationGui multiToolConfigurationGui;
    //Instance of harvester config gui
    private HarvesterConfigurationGui harvesterConfigurationGui;
    //Instance of sell wand config gui
    private SellWandConfigurationGui sellWandConfigurationGui;
    //Instance of tnt wand config gui
    private TNTWandConfigurationGui tntWandConfigurationGui;
    //Instance of aqua wand config gui
    private AquaWandConfigurationGui aquaWandConfigurationGui;
    //Instance of multi tools gui
    private BuyMultiToolsGui buyMultiToolsGui;
    //Instance of generic buy gui
    private BuyToolsGenericGui buyToolsGenericGui;
    //Instance of trench tools gui
    private BuyTrenchToolsGui buyTrenchToolsGui;
    //Instance of tray tools gui
    private BuyTrayToolsGui buyTrayToolsGui;
    //Instance of tray tools gui
    private BuySandWandsGui buySandWandsGui;
    //Instance of lightning wands gui
    private BuyLightningWandsGui buyLightningWandsGui;
    //Instance of harvester gui
    private BuyHarvesterToolsGui buyHarvesterToolsGui;
    //Instance of sell gui
    private BuySellWandsGui buySellWandsGui;
    //Instance of tnt purchase gui
    private BuyTNTWandsGui buyTNTWandsGui;
    //Instance of aqua purchase gui
    private BuyAquaWandsGui buyAquaWandsGui;

    /**
     * Creates a new instance of all Guis for the plugin
     */
    public GuiInitializer() {
        //Create the Gui instances
        multiToolConfigurationGui = new MultiToolConfigurationGui();
        harvesterConfigurationGui = new HarvesterConfigurationGui();
        sellWandConfigurationGui = new SellWandConfigurationGui();
        tntWandConfigurationGui = new TNTWandConfigurationGui();
        aquaWandConfigurationGui = new AquaWandConfigurationGui();
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
    }

    /**
     * Gets the specified gui based off of the name
     *
     * @param guiName String, gui name
     * @return AbstractGui
     */
    public AbstractGui getGuiByName(String guiName) {
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
        return null;
    }
}