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
package org.wpsim.peasantfamily.goals.l3development;

import BESA.BDI.AgentStructuralModel.GoalBDITypes;
import BESA.BDI.AgentStructuralModel.StateBDI;
import BESA.Kernel.Agent.Event.KernellAgentEventExceptionBESA;
import org.wpsim.civicauthority.data.LandInfo;
import org.wpsim.wellprodsim.base.wpsGoalBDI;
import org.wpsim.wellprodsim.wpsStart;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.data.utils.SeasonType;
import org.wpsim.peasantfamily.data.utils.TimeConsumedBy;
import org.wpsim.peasantfamily.tasks.l3development.HarvestCropsTask;
import rational.RationalRole;
import rational.mapping.Believes;
import rational.mapping.Plan;

/**
 *
 * @author jairo
 */
public class HarvestCropsGoal extends wpsGoalBDI {

    /**
     *
     * @return
     */
    public static HarvestCropsGoal buildGoal() {
        HarvestCropsTask harvestCropsTask = new HarvestCropsTask();
        Plan harvestCropsPlan = new Plan();
        harvestCropsPlan.addTask(harvestCropsTask);
        RationalRole harvestCropsRole = new RationalRole(
                "HarvestCropsTask",
                harvestCropsPlan);
        return new HarvestCropsGoal(
                wpsStart.getPlanID(),
                harvestCropsRole,
                "HarvestCropsTask",
                GoalBDITypes.OPORTUNITY);
    }

    /**
     *
     * @param id
     * @param role
     * @param description
     * @param type
     */
    public HarvestCropsGoal(long id, RationalRole role, String description, GoalBDITypes type) {
        super(id, role, description, type);
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
            if (currentLandInfo.getCurrentSeason().equals(SeasonType.HARVEST) &&
                    believes.getPeasantProfile().getTools() > 0 &&
                    believes.haveTimeAvailable(TimeConsumedBy.HarvestCropsTask)) {
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
        return evaluateEmotionalContribution(stateBDI, 0.99);
    }


}
