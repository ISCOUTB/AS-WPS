/**
 * ==========================================================================
 * __      __ _ __   ___  *    WellProdSim                                  *
 * \ \ /\ / /| '_ \ / __| *    @version 1.0                                 *
 *  \ V  V / | |_) |\__ \ *    @since 2023                                  *
 *   \_/\_/  | .__/ |___/ *                                                 *
 *           | |          *    @author Jairo Serrano                        *
 *           |_|          *    @author Enrique Gonzalez                     *
 * ==========================================================================
 * Social Simulator used to estimate productivity and well-being of peasant *
 * families. It is event oriented, high concurrency, heterogeneous time     *
 * management and emotional reasoning BDI.                                  *
 * ==========================================================================
 */
package org.wpsim.peasantfamily.tasks.l3development;

import org.wpsim.civicauthority.data.LandInfo;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.data.utils.TimeConsumedBy;
import org.wpsim.wellprodsim.base.wpsLandTask;
import rational.mapping.Believes;

import java.util.List;

/**
 *
 * @author jairo
 */
public class DeforestingLandTask extends wpsLandTask {

    /**
     *
     * @param parameters
     */
    @Override
    public void executeTask(Believes parameters) {
        this.setExecuted(false);
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        updateConfig(believes, 7200); // Paso 1: Configurar el tiempo de deforestación
        believes.useTime(TimeConsumedBy.DeforestingLandTask.getTime());

        int factor = 1;
        if (!believes.getPeasantFamilyHelper().isBlank())
        {
            factor = 2;
        }

        // Incluir si tiene tendencias a deforestar
        List<LandInfo> landInfos = believes.getAssignedLands();
        for (LandInfo currentLandInfo : landInfos) {
            if ("forest".equals(currentLandInfo.getKind())) {
                this.increaseWorkDone(believes,currentLandInfo.getLandName(), TimeConsumedBy.DeforestingLandTask.getTime()*factor); // Paso 2: Gastar tiempo
                if (this.isWorkDone(believes, currentLandInfo.getLandName())) { // Paso 3: Revisar si se completó el tiempo necesario
                    currentLandInfo.setKind("land");
                    //wpsReport.info(
                    //        "Finished the deforesting process " + currentLandInfo.getLandName(),
                    //        believes.getPeasantProfile().getPeasantFamilyAlias()
                    //);
                    currentLandInfo.resetElapsedWorkTime();
                }
                believes.addTaskToLog(believes.getInternalCurrentDate());
                return; // Paso 5: Retornar a la iteración
            }
        }
        believes.addTaskToLog(believes.getInternalCurrentDate());
    }

}