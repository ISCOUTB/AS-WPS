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
import org.wpsim.civicauthority.data.LandInfo;
import org.wpsim.peasantfamily.data.Utils.SeasonType;
import org.wpsim.wellprodsim.base.wpsTask;
import org.wpsim.viewerlens.util.wpsReport;
import org.wpsim.agroecosystem.Guards.AgroEcosystemGuard;
import org.wpsim.agroecosystem.Messages.AgroEcosystemMessage;
import rational.mapping.Believes;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.data.Utils.TimeConsumedBy;

import static org.wpsim.agroecosystem.Messages.AgroEcosystemMessageType.CROP_INFORMATION;

/**
 * @author jairo
 */
public class CheckCropsTask extends wpsTask {

    /**
     * Executes the CheckCropsTask
     *
     * @param parameters Believes of the agent
     */
    @Override
    public void executeTask(Believes parameters) {
        this.setExecuted(false);
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        believes.useTime(TimeConsumedBy.CheckCropsTask.getTime());
        believes.processEmotionalEvent(new EmotionalEvent("FAMILY", "CHECKCROPS", "FOOD"));
        for (LandInfo currentLandInfo : believes.getAssignedLands()) {
            if (currentLandInfo.getCurrentSeason().equals(SeasonType.GROWING)) {
                // @TODO: REVISAR LA LOGICA REAL
                if (!believes.isTaskExecutedOnDateWithLand(believes.getInternalCurrentDate(), "CheckCropsTask", currentLandInfo.getLandName())) {
                    try {
                        AdmBESA.getInstance().getHandlerByAlias(
                                currentLandInfo.getLandName()
                        ).sendEvent(
                                new EventBESA(
                                        AgroEcosystemGuard.class.getName(),
                                        new AgroEcosystemMessage(
                                                CROP_INFORMATION,
                                                currentLandInfo.getCropName(),
                                                believes.getInternalCurrentDate(),
                                                currentLandInfo.getLandName()
                                        )
                                )
                        );
                        //ReportBESA.info("Checkcrop " + currentLandInfo.getLandName());
                    } catch (Exception ex) {
                        wpsReport.error(ex, believes.getPeasantProfile().getPeasantFamilyAlias());
                    }
                    believes.addTaskToLog(believes.getInternalCurrentDate(), currentLandInfo.getLandName());
                }
                //wpsReport.info("enviado CROP_INFORMATION a " + currentLandInfo.getLandName(), believes.getPeasantProfile().getPeasantFamilyAlias());
            }
        }
        believes.addTaskToLog(believes.getInternalCurrentDate());
    }

}
