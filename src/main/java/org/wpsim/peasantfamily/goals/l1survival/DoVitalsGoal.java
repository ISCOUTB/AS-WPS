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
package org.wpsim.peasantfamily.goals.l1survival;

import BESA.BDI.AgentStructuralModel.GoalBDITypes;
import BESA.BDI.AgentStructuralModel.StateBDI;
import BESA.Kernel.Agent.Event.KernellAgentEventExceptionBESA;
import org.wpsim.wellprodsim.base.wpsGoalBDI;
import org.wpsim.wellprodsim.wpsStart;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.tasks.l1survival.DoVitalsTask;
import rational.RationalRole;
import rational.mapping.Believes;
import rational.mapping.Plan;

/**
 *
 * @author jairo
 */
public class DoVitalsGoal extends wpsGoalBDI {

    /**
     *
     * @param id
     * @param role
     * @param description
     * @param type
     */
    public DoVitalsGoal(long id, RationalRole role, String description, GoalBDITypes type) {
        super(id, role, description, type);
    }

    /**
     *
     * @return
     */
    public static DoVitalsGoal buildGoal() {
        DoVitalsTask doVitalsTask = new DoVitalsTask();
        Plan doVitalsPlan = new Plan();
        doVitalsPlan.addTask(doVitalsTask);
        RationalRole doVitalsRole = new RationalRole(
                "DoVitalsTask",
                doVitalsPlan);
        return new DoVitalsGoal(
                wpsStart.getPlanID(),
                doVitalsRole,
                "DoVitalsTask",
                GoalBDITypes.SURVIVAL);
    }

    /**
     *
     * @param parameters
     * @return
     * @throws KernellAgentEventExceptionBESA
     */
    @Override
    public double evaluateViability(Believes parameters) throws KernellAgentEventExceptionBESA {
        return 1;
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

        if (believes.isNewDay()) {
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
        return 1;
    }

    /**
     *
     * @param stateBDI
     * @return
     * @throws KernellAgentEventExceptionBESA
     */
    @Override
    public double evaluateContribution(StateBDI stateBDI) throws KernellAgentEventExceptionBESA {
        //System.out.println(stateBDI.getMachineBDIParams().getPyramidGoals());
        return 0.99;
    }
}
