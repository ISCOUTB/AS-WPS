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
import org.wpsim.simulationcontrol.util.ControlCurrentDate;
import org.wpsim.wellprodsim.base.wpsTask;
import org.wpsim.viewerlens.util.wpsReport;
import org.wpsim.agroecosystem.guards.AgroEcosystemGuard;
import org.wpsim.agroecosystem.messages.AgroEcosystemMessage;
import rational.mapping.Believes;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.data.utils.TimeConsumedBy;

import static org.wpsim.agroecosystem.messages.AgroEcosystemMessageType.CROP_PESTICIDE;

/**
 *
 * @author jairo
 */
public class ManagePestsTask extends wpsTask {

    /**
     *
     * @param parameters
     */
    @Override
    public void executeTask(Believes parameters) {
        this.setExecuted(false);
        //wpsReport.info("⚙️⚙️⚙️");
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        believes.addTaskToLog(believes.getInternalCurrentDate());
        believes.useTime(TimeConsumedBy.ManagePestsTask.getTime());
        //believes.setCurrentCropCare(CropCareType.NONE);

        try {
            AdmBESA.getInstance().getHandlerByAlias(
                    believes.getAlias()
            ).sendEvent(
                    new EventBESA(
                            AgroEcosystemGuard.class.getName(),
                            new AgroEcosystemMessage(
                                    CROP_PESTICIDE,
                                    "rice", // @TODO: CAMBIAR NOMBRE AL REAL
                                    believes.getInternalCurrentDate(),
                                    believes.getPeasantProfile().getPeasantFamilyAlias()
                            )
                    )
            );
            ControlCurrentDate.getInstance().setCurrentDate(
                    believes.getInternalCurrentDate()
            );
            //this.setTaskWaitingForExecution();

        } catch (ExceptionBESA ex) {
            wpsReport.error(ex, believes.getPeasantProfile().getPeasantFamilyAlias());
        }
    }

}
