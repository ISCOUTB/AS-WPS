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

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.System.AdmBESA;
import org.wpsim.civicauthority.data.LandInfo;
import org.wpsim.wellprodsim.base.wpsTask;
import org.wpsim.viewerlens.util.wpsReport;
import org.wpsim.agroecosystem.guards.AgroEcosystemGuard;
import org.wpsim.agroecosystem.messages.AgroEcosystemMessage;
import rational.mapping.Believes;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.data.Utils.CropCareType;
import org.wpsim.peasantfamily.data.Utils.TimeConsumedBy;

import static org.wpsim.agroecosystem.messages.AgroEcosystemMessageType.CROP_IRRIGATION;

/**
 * @author jairo
 */
public class IrrigateCropsTask extends wpsTask {

    /**
     * @param parameters
     */
    @Override
    public void executeTask(Believes parameters) {
        this.setExecuted(false);
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        believes.useTime(TimeConsumedBy.IrrigateCropsTask.getTime());

        double waterUsed = believes.getPeasantProfile().getCropSizeHA() * 30;

        for (LandInfo currentLandInfo : believes.getAssignedLands()) {
            if (currentLandInfo.getKind().equals("water")) {
                waterUsed = 0;
                //wpsReport.info("🚰🚰🚰🚰 tiene agua", believes.getPeasantProfile().getPeasantFamilyAlias());
                break;
            } else {
                //wpsReport.info("NO tiene agua", believes.getPeasantProfile().getPeasantFamilyAlias());
            }
        }

        for (LandInfo currentLandInfo : believes.getAssignedLands()) {
            if (currentLandInfo.getCurrentCropCareType().equals(CropCareType.IRRIGATION)) {
                //System.out.println("🚰🚰🚰🚰 Irrigación de cultivo " + currentLandInfo.getLandName() + " con " + waterUsed + " de " + believes.getPeasantProfile().getPeasantFamilyAlias());
                currentLandInfo.setCurrentCropCareType(CropCareType.NONE);
                believes.getPeasantProfile().useWater((int) waterUsed);
                try {
                    AdmBESA.getInstance().getHandlerByAlias(
                            currentLandInfo.getLandName()
                    ).sendEvent(
                            new EventBESA(
                                    AgroEcosystemGuard.class.getName(),
                                    new AgroEcosystemMessage(
                                        CROP_IRRIGATION,
                                        currentLandInfo.getLandName(),
                                        believes.getInternalCurrentDate(),
                                        believes.getPeasantProfile().getPeasantFamilyAlias()
                                    )
                            )
                    );
                    believes.addTaskToLog(believes.getInternalCurrentDate());
                    //wpsReport.info("🚰🚰🚰🚰 Irrigación de cultivo " + currentLandInfo.getLandName() + " con " + waterUsed, believes.getPeasantProfile().getPeasantFamilyAlias());
                } catch (ExceptionBESA ex) {
                    wpsReport.error(ex, believes.getPeasantProfile().getPeasantFamilyAlias());
                }
                return;
            }
        }
        believes.addTaskToLog(believes.getInternalCurrentDate());
    }
}
