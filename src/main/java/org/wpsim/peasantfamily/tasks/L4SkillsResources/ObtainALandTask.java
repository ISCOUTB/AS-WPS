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
import org.wpsim.civicauthority.guards.CivicAuthorityLandGuard;
import org.wpsim.civicauthority.data.CivicAuthorityLandData;
import org.wpsim.wellprodsim.base.wpsTask;
import org.wpsim.wellprodsim.wpsStart;
import org.wpsim.viewerlens.util.wpsReport;
import rational.mapping.Believes;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.data.utils.PeasantActivityType;
import org.wpsim.peasantfamily.data.utils.TimeConsumedBy;

/**
 * @author jairo
 */
public class ObtainALandTask extends wpsTask {

    /**
     * @param parameters
     */
    @Override
    public void executeTask(Believes parameters) {
        this.setExecuted(false);
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        believes.useTime(TimeConsumedBy.ObtainALandTask.getTime());

        try {
            AdmBESA.getInstance().getHandlerByAlias(
                    wpsStart.config.getGovernmentAgentName()
            ).sendEvent(
                    new EventBESA(
                            CivicAuthorityLandGuard.class.getName(),
                            new CivicAuthorityLandData(
                                    believes.getAlias()
                            )
                    )
            );
            //System.out.println("Enviando mensaje al gobierno para obtener tierra " + believes.getPeasantProfile().getPeasantFamilyAlias());
        } catch (ExceptionBESA ex) {
            wpsReport.error(ex, "ObtainALandTask");
        }

        /*while (believes.equals("")) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                wpsReport.error(ex, "ObtainALandTask");
            }
        }*/

        // @TODO: setFarmName lo cambia el gobierno o el campesino
        believes.getPeasantProfile().setFarmName(true);
        believes.setCurrentActivity(PeasantActivityType.PRICE_LIST);
        believes.getPeasantProfile().setHousing(1);
        believes.getPeasantProfile().setServicesPresence(1);
        believes.getPeasantProfile().setHousingSize(1);
        believes.getPeasantProfile().setHousingLocation(1);
        believes.getPeasantProfile().setFarmDistance(1);

        //wpsReport.info("🥬 " + believes.getPeasantProfile().getPeasantFamilyAlias() + " ya tiene tierra " + believes.getPeasantProfile().getPeasantFamilyLandAlias(), believes.getPeasantProfile().getPeasantFamilyAlias());
        believes.addTaskToLog(believes.getInternalCurrentDate());
    }

}
