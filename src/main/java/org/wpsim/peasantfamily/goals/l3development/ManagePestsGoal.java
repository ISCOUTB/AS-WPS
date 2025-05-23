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
package org.wpsim.peasantfamily.goals.l3development;

import BESA.BDI.AgentStructuralModel.GoalBDITypes;
import BESA.BDI.AgentStructuralModel.StateBDI;
import BESA.Kernel.Agent.Event.KernellAgentEventExceptionBESA;
import org.wpsim.civicauthority.data.LandInfo;
import org.wpsim.wellprodsim.base.wpsGoalBDI;
import org.wpsim.wellprodsim.wpsStart;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.data.utils.CropCareType;
import org.wpsim.peasantfamily.data.utils.ResourceNeededType;
import org.wpsim.peasantfamily.data.utils.TimeConsumedBy;
import org.wpsim.peasantfamily.tasks.l3development.ManagePestsTask;
import rational.RationalRole;
import rational.mapping.Believes;
import rational.mapping.Plan;

/**
 *
 * @author jairo
 */
public class ManagePestsGoal extends wpsGoalBDI {

    /**
     *
     * @return
     */
    public static ManagePestsGoal buildGoal() {
        ManagePestsTask managePestsTask = new ManagePestsTask();
        Plan managePestsPlan = new Plan();
        managePestsPlan.addTask(managePestsTask);
        RationalRole managePestsRole = new RationalRole(
                "ManagePestsTask",
                managePestsPlan);
        return new ManagePestsGoal(
                wpsStart.getPlanID(),
                managePestsRole,
                "ManagePestsTask",
                GoalBDITypes.OPORTUNITY);
    }

    /**
     *
     * @param id
     * @param role
     * @param description
     * @param type
     */
    public ManagePestsGoal(long id, RationalRole role, String description, GoalBDITypes type) {
        super(id, role, description, type);
        //wpsReport.info("");
    }

    /**
     *
     * @param parameters
     * @return
     * @throws KernellAgentEventExceptionBESA
     */
    @Override
    public double evaluateViability(Believes parameters) throws KernellAgentEventExceptionBESA {
        //wpsReport.info("");
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        if (believes.getPeasantProfile().getTools() > 0
                && believes.getPeasantProfile().getSupplies() > 0) {
            return 1;
        } else {
            believes.setCurrentResourceNeededType(ResourceNeededType.PESTICIDE);
            return 0;
        }
    }

    /**
     *
     * @param parameters
     * @return
     * @throws KernellAgentEventExceptionBESA
     */
    @Override
    public double detectGoal(Believes parameters) throws KernellAgentEventExceptionBESA {
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;

        if (this.isAlreadyExecutedToday(believes)) {
            return 0;
        }

        for (LandInfo currentLandInfo : believes.getAssignedLands()) {
            if (currentLandInfo.getCurrentCropCareType().equals(CropCareType.PESTICIDE) &&
                    believes.haveTimeAvailable(TimeConsumedBy.ManagePestsTask)) {
                return 1;
            }
        }
        return 0;
    }

    /**
     *
     * @param stateBDI
     * @return
     * @throws KernellAgentEventExceptionBESA
     */
    @Override
    public double evaluateContribution(StateBDI stateBDI) throws KernellAgentEventExceptionBESA {
        return evaluateEmotionalContribution(stateBDI, 1.0);
    }
}
