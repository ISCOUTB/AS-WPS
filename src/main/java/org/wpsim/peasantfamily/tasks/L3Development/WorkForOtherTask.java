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
import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.System.AdmBESA;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.data.Utils.TimeConsumedBy;
import org.wpsim.peasantfamily.guards.FromCommunityDynamics.PeasantWorkerContractFinishedGuard;
import org.wpsim.wellprodsim.base.wpsTask;
import org.wpsim.communitydynamics.data.CommunityDynamicsDataMessage;
import rational.mapping.Believes;

/**
 *
 * @author jairo
 */
public class WorkForOtherTask extends wpsTask {

    /**
     *
     * @param parameters
     */
    @Override
    public void executeTask(Believes parameters) {
        this.setExecuted(false);
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        believes.addTaskToLog(believes.getInternalCurrentDate());
        believes.useTime(TimeConsumedBy.WorkForOtherTask.getTime());
        believes.processEmotionalEvent(new EmotionalEvent("FAMILY", "WORK", "MONEY"));
        believes.decreaseDaysToWorkForOther();
        //System.out.println(believes.getPeasantProfile().getPeasantFamilyAlias() + " sigo trabajando, faltan " + believes.getDaysToWorkForOther() + " dias, para " + believes.getContractor());
        if (believes.getDaysToWorkForOther() == 0) {
            //System.out.println(believes.getPeasantProfile().getPeasantFamilyAlias() + " Recibiendo pago por el contrato da " + believes.getContractor());
            try {
                AdmBESA.getInstance().getHandlerByAlias(
                        believes.getContractor()
                ).sendEvent(
                        new EventBESA(PeasantWorkerContractFinishedGuard.class.getName(),
                                new CommunityDynamicsDataMessage(
                                        believes.getPeasantProfile().getPeasantFamilyAlias(),
                                        believes.getPeasantProfile().getPeasantFamilyAlias(),
                                        5
                                )
                        )
                );
            } catch (ExceptionBESA ex) {
                System.out.println(ex.getMessage());
            }

            believes.setContractor("");
            believes.setPeasantFamilyHelper("");
            believes.setDaysToWorkForOther(0);
            believes.setAskedForContractor(false);
            believes.getPeasantProfile().increaseMoney(250000);

        }
    }

}
