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
package org.wpsim.peasantfamily.goals.l4skillsresources;

import BESA.BDI.AgentStructuralModel.GoalBDITypes;
import BESA.BDI.AgentStructuralModel.StateBDI;
import BESA.Kernel.Agent.Event.KernellAgentEventExceptionBESA;
import org.wpsim.wellprodsim.base.wpsGoalBDI;
import org.wpsim.wellprodsim.wpsStart;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.data.utils.ResourceNeededType;
import org.wpsim.peasantfamily.data.utils.TimeConsumedBy;
import org.wpsim.peasantfamily.tasks.l4skillsresources.ObtainPesticidesTask;
import rational.RationalRole;
import rational.mapping.Believes;
import rational.mapping.Plan;

/**
 *
 * @author jairo
 */
public class ObtainPesticidesGoal extends wpsGoalBDI {

    /**
     *
     * @return
     */
    public static ObtainPesticidesGoal buildGoal() {
        ObtainPesticidesTask obtainPesticidesTask = new ObtainPesticidesTask();
        Plan obtainPesticidesPlan = new Plan();
        obtainPesticidesPlan.addTask(obtainPesticidesTask);
        RationalRole obtainPesticidesRole = new RationalRole(
                "ObtainPesticidesTask",
                obtainPesticidesPlan);
        return new ObtainPesticidesGoal(
                wpsStart.getPlanID(),
                obtainPesticidesRole,
                "ObtainPesticidesTask",
                GoalBDITypes.REQUIREMENT);
    }

    /**
     *
     * @param id
     * @param role
     * @param description
     * @param type
     */
    public ObtainPesticidesGoal(long id, RationalRole role, String description, GoalBDITypes type) {
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
        if (believes.getPeasantProfile().getSuppliesAvailability() > 0) {
            return 1;
        } else {
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

        //wpsReport.info("PlantingSeason=" + believes.getProfile().isPlantingSeason());
        if (believes.getCurrentResourceNeededType() == ResourceNeededType.PESTICIDE) {
            return 1;
        } else {
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
    public double evaluatePlausibility(Believes parameters) throws KernellAgentEventExceptionBESA {
        //wpsReport.info("");
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        if (believes.haveTimeAvailable(TimeConsumedBy.ObtainPesticidesTask)) {
            return 1;
        } else {
            return 0;
        }
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
