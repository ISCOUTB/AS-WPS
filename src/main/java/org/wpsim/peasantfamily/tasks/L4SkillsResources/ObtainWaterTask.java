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
package org.wpsim.peasantfamily.tasks.L4SkillsResources;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.System.AdmBESA;
import org.wpsim.marketplace.guards.MarketPlaceGuard;
import org.wpsim.marketplace.data.MarketPlaceMessage;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.data.utils.ResourceNeededType;
import org.wpsim.peasantfamily.data.utils.TimeConsumedBy;
import org.wpsim.wellprodsim.base.wpsTask;
import org.wpsim.wellprodsim.wpsStart;
import org.wpsim.viewerlens.util.wpsReport;
import rational.mapping.Believes;

import static org.wpsim.marketplace.data.MarketPlaceMessageType.BUY_WATER;

/**
 *
 * @author jairo
 */
public class ObtainWaterTask extends wpsTask {

    /**
     *
     * @param parameters
     */
    @Override
    public void executeTask(Believes parameters) {
        this.setExecuted(false);
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        believes.addTaskToLog(believes.getInternalCurrentDate());
        //wpsReport.info("🚰🚰🚰 Comprando Agua", believes.getPeasantProfile().getPeasantFamilyAlias());
        believes.useTime(TimeConsumedBy.ObtainWaterTask.getTime());
        try {
            AdmBESA.getInstance().getHandlerByAlias(
                    wpsStart.config.getMarketAgentName()
            ).sendEvent(
                    new EventBESA(
                            MarketPlaceGuard.class.getName(),
                            new MarketPlaceMessage(
                                    BUY_WATER,
                                    believes.getPeasantProfile().getPeasantFamilyAlias(),
                                    100,
                                    believes.getInternalCurrentDate()
                            )
                    )
            );
        } catch (ExceptionBESA ex) {
            wpsReport.error(ex, "obtainWaterTask");
        }
        believes.setCurrentResourceNeededType(ResourceNeededType.NONE);
    }

}
