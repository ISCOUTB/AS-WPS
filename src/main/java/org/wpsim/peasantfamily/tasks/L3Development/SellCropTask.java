/**
 * ==========================================================================
 * __      __ _ __   ___  *    WellProdSim                                  *
 * \ \ /\ / /| '_ \ / __| *    @version 1.0                                 *
 * \ V  V / | |_) |\__ \ *    @since 2023                                  *
 * \_/\_/  | .__/ |___/ *                                                 *
 * | |          *    @author Jairo Serrano                        *
 * |_|          *    @author Enrique Gonzalez                     *
 * ==========================================================================
 * Social Simulator used to estimate productivity and well-being of peasant *
 * families. It is event oriented, high concurrency, heterogeneous time     *
 * management and emotional reasoning BDI.                                  *
 * ==========================================================================
 */
package org.wpsim.peasantfamily.tasks.L3Development;

import BESA.Emotional.EmotionalEvent;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.System.AdmBESA;
import BESA.Log.ReportBESA;
import org.wpsim.civicauthority.data.LandInfo;
import org.wpsim.wellprodsim.base.wpsTask;
import org.wpsim.wellprodsim.wpsStart;
import org.wpsim.marketplace.guards.MarketPlaceGuard;
import org.wpsim.marketplace.data.MarketPlaceMessage;
import rational.mapping.Believes;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.data.utils.SeasonType;
import org.wpsim.peasantfamily.data.utils.TimeConsumedBy;

import static org.wpsim.marketplace.data.MarketPlaceMessageType.SELL_CROP;

/**
 * @author jairo
 */
public class SellCropTask extends wpsTask {

    /**
     * @param parameters
     */
    @Override
    public void executeTask(Believes parameters) {
        this.setExecuted(false);
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        believes.useTime(TimeConsumedBy.HarvestCropsTask.getTime());
        believes.processEmotionalEvent(new EmotionalEvent("FAMILY", "SELLING", "FOOD"));

        for (LandInfo currentLandInfo : believes.getAssignedLands()) {
            if (currentLandInfo.getCurrentSeason().equals(SeasonType.SELL_CROP)) {
                try {
                    AdmBESA.getInstance().getHandlerByAlias(
                            wpsStart.config.getMarketAgentName()
                    ).sendEvent(
                            new EventBESA(
                                    MarketPlaceGuard.class.getName(),
                                    new MarketPlaceMessage(
                                            SELL_CROP,
                                            believes.getPeasantProfile().getPeasantFamilyAlias(),
                                            believes.getPeasantProfile().getHarvestedWeight(),
                                            currentLandInfo.getCropName(),
                                            believes.getInternalCurrentDate()
                                    )
                            )
                    );
                    // Elimina el Agente Tierra usado.
                    AdmBESA.getInstance().getHandlerByAlias(currentLandInfo.getLandName());
                    String agID = AdmBESA.getInstance().getHandlerByAlias(currentLandInfo.getLandName()).getAgId();
                    AdmBESA.getInstance().killAgent(agID, wpsStart.config.getDoubleProperty("control.passwd"));

                    currentLandInfo.setCurrentSeason(SeasonType.NONE);
                    believes.getPeasantProfile().setHarvestedWeight(0);
                    believes.setUpdatePriceList(true);
                } catch (Exception ex) {
                    ReportBESA.error(ex);
                }
            }
        }
        believes.addTaskToLog(believes.getInternalCurrentDate());
    }

}
