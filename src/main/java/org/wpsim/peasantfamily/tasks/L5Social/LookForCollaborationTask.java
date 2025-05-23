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
package org.wpsim.peasantfamily.tasks.L5Social;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.System.AdmBESA;
import org.wpsim.wellprodsim.base.wpsTask;
import org.wpsim.communitydynamics.data.CommunityDynamicsDataMessage;
import org.wpsim.communitydynamics.guards.CommunityDynamicsRequestHelpGuard;
import org.wpsim.wellprodsim.wpsStart;
import rational.mapping.Believes;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.data.Utils.TimeConsumedBy;

/**
 *
 * @author jairo
 */
public class LookForCollaborationTask extends wpsTask {

    /**
     *
     * @param parameters
     */
    @Override
    public void executeTask(Believes parameters) {
        this.setExecuted(false);
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        believes.useTime(TimeConsumedBy.LookForCollaborationTask.getTime());
        believes.addTaskToLog(believes.getInternalCurrentDate());
        believes.setAskedForCollaboration(true);

        try {
            AdmBESA.getInstance().getHandlerByAlias(
                    wpsStart.config.getSocietyAgentName()
            ).sendEvent(
                    new EventBESA(CommunityDynamicsRequestHelpGuard.class.getName(),
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
    }
}
