package dev.nuer.nt.initialize;

import dev.nuer.nt.gui.AbstractGui;
import dev.nuer.nt.gui.BuyToolsGenericGui;
import dev.nuer.nt.gui.HarvesterConfigurationGui;
import dev.nuer.nt.gui.MultiToolConfigurationGui;
import dev.nuer.nt.gui.purchase.*;

/**
 * Class that handles creating ang getting a Gui
 */
public class GuiInitializer {
    //Instance of multi tool options gui
    private MultiToolConfigurationGui multiToolConfigurationGui;
    //Instance of harvester config gui
    private HarvesterConfigurationGui harvesterConfigurationGui;
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

    /**
     * Creates a new instance of all Guis for the plugin
     */
    public GuiInitializer() {
        //Create the Gui instances
        multiToolConfigurationGui = new MultiToolConfigurationGui();
        harvesterConfigurationGui = new HarvesterConfigurationGui();
        buyToolsGenericGui = new BuyToolsGenericGui();
        buyMultiToolsGui = new BuyMultiToolsGui();
        buyTrenchToolsGui = new BuyTrenchToolsGui();
        buyTrayToolsGui = new BuyTrayToolsGui();
        buySandWandsGui = new BuySandWandsGui();
        buyLightningWandsGui = new BuyLightningWandsGui();
        buyHarvesterToolsGui = new BuyHarvesterToolsGui();
    }

    /**
     * Gets the specified gui based off of the name
     *
     * @param guiName String, gui name
     * @return
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
        return null;
    }
}