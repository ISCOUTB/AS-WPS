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

import org.wpsim.civicauthority.data.LandInfo;
import org.wpsim.wellprodsim.base.wpsLandTask;
import rational.mapping.Believes;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.data.Utils.SeasonType;
import org.wpsim.peasantfamily.data.Utils.TimeConsumedBy;

/**
 *
 * @author jairo
 */
public class PrepareLandTask extends wpsLandTask {

    /**
     *
     * @param parameters
     */
    @Override
    public void executeTask(Believes parameters) {
        this.setExecuted(false);
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        updateConfig(believes, 3360); // 56 horas para preparar una hectarea de cultivo
        believes.useTime(TimeConsumedBy.PrepareLandTask.getTime());

        //believes.processEmotionalEvent(new EmotionalEvent("FAMILY", "PLANTING", "FOOD"));

        int factor = 1;
        if (!believes.getPeasantFamilyHelper().isBlank()) {
            factor = 2;
        }

        for (LandInfo currentLandInfo : believes.getAssignedLands()) {
            if (currentLandInfo.getKind().equals("land")) {
                if (currentLandInfo.getCurrentSeason().equals(SeasonType.NONE)) {
                    //System.out.println("Preparing Planting season for " + currentLandInfo.getLandName());
                    this.increaseWorkDone(believes, currentLandInfo.getLandName(), TimeConsumedBy.PrepareLandTask.getTime() * factor);
                    believes.useTime(TimeConsumedBy.PrepareLandTask.getTime());
                    if (this.isWorkDone(believes, currentLandInfo.getLandName())) {
                        believes.getPeasantProfile().increaseSeedsNeeded(1);
                        currentLandInfo.setCurrentSeason(SeasonType.PLANTING);
                        currentLandInfo.resetElapsedWorkTime();
                    }
                    believes.addTaskToLog(believes.getInternalCurrentDate());
                    return;
                }
            }
        }
        believes.addTaskToLog(believes.getInternalCurrentDate());
    }

}
