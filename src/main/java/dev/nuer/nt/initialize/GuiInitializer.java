package dev.nuer.nt.initialize;

import dev.nuer.nt.gui.AbstractGui;
import dev.nuer.nt.gui.BuyToolsGenericGui;
import dev.nuer.nt.gui.config.HarvesterConfigurationGui;
import dev.nuer.nt.gui.config.MultiToolConfigurationGui;
import dev.nuer.nt.gui.config.SellWandConfigurationGui;
import dev.nuer.nt.gui.purchase.*;

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

    /**
     * Creates a new instance of all Guis for the plugin
     */
    public GuiInitializer() {
        //Create the Gui instances
        multiToolConfigurationGui = new MultiToolConfigurationGui();
        harvesterConfigurationGui = new HarvesterConfigurationGui();
        sellWandConfigurationGui = new SellWandConfigurationGui();
        buyToolsGenericGui = new BuyToolsGenericGui();
        buyMultiToolsGui = new BuyMultiToolsGui();
        buyTrenchToolsGui = new BuyTrenchToolsGui();
        buyTrayToolsGui = new BuyTrayToolsGui();
        buySandWandsGui = new BuySandWandsGui();
        buyLightningWandsGui = new BuyLightningWandsGui();
        buyHarvesterToolsGui = new BuyHarvesterToolsGui();
        buySellWandsGui = new BuySellWandsGui();
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
        return null;
    }
}