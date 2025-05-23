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
package org.wpsim.peasantfamily.goals.L6Leisure;

import BESA.BDI.AgentStructuralModel.GoalBDITypes;
import BESA.BDI.AgentStructuralModel.StateBDI;
import BESA.Kernel.Agent.Event.KernellAgentEventExceptionBESA;
import com.fuzzylite.term.Trapezoid;
import com.fuzzylite.term.Triangle;
import com.fuzzylite.variable.InputVariable;
import org.wpsim.wellprodsim.base.wpsGoalBDI;
import org.wpsim.wellprodsim.wpsStart;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.data.utils.TimeConsumedBy;
import org.wpsim.peasantfamily.tasks.L6Leisure.SpendFamilyTimeTask;
import rational.RationalRole;
import rational.mapping.Believes;
import rational.mapping.Plan;

/**
 *
 * @author jairo
 */
public class SpendFamilyTimeGoal extends wpsGoalBDI {

    /**
     *
     * @return
     */
    public static SpendFamilyTimeGoal buildGoal() {
        SpendFamilyTimeTask spendFamilyTimeTask = new SpendFamilyTimeTask();
        Plan spendFamilyTimePlan = new Plan();
        spendFamilyTimePlan.addTask(spendFamilyTimeTask);
        RationalRole spendFamilyTimeRole = new RationalRole(
                "SpendFamilyTimeTask",
                spendFamilyTimePlan);
        return new SpendFamilyTimeGoal(
                wpsStart.getPlanID(),
                spendFamilyTimeRole,
                "SpendFamilyTimeTask",
                GoalBDITypes.ATTENTION_CYCLE);
    }

    /**
     *
     * @param id
     * @param role
     * @param description
     * @param type
     */
    public SpendFamilyTimeGoal(long id, RationalRole role, String description, GoalBDITypes type) {
        super(id, role, description, type);
        InputVariable peasantFamilyAffinity = new InputVariable();
        peasantFamilyAffinity.setName("peasantFamilyAffinity");
        peasantFamilyAffinity.setRange(0, 1.0);
        peasantFamilyAffinity.addTerm(new Trapezoid("low", 0, 0, 0.2, 0.4));
        peasantFamilyAffinity.addTerm(new Triangle("medium", 0.3, 0.5, 0.7));
        peasantFamilyAffinity.addTerm(new Trapezoid("high", 0.6, 0.8, 1.0, 1.0));
        engine.addInputVariable(peasantFamilyAffinity);
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
        return 1;
    }

    /**
     *
     * @param parameters
     * @return
     * @throws KernellAgentEventExceptionBESA
     */
    @Override
    public double evaluatePlausibility(Believes parameters) throws KernellAgentEventExceptionBESA {
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        if (believes.haveTimeAvailable(TimeConsumedBy.SpendFamilyTimeTask)) {
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
    public double evaluateViability(Believes parameters) throws KernellAgentEventExceptionBESA {
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
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) stateBDI.getBelieves();

        /*if (believes.isHaveEmotions()) {

            List<String> rules = wpsStart.config.getFuzzyRulesList("GoalsSpendFamilyEmotionalRules");
            RuleBlock ruleBlock = new RuleBlock();
            ruleBlock.setConjunction(new Minimum());
            ruleBlock.setDisjunction(new Maximum());
            ruleBlock.setImplication(new Minimum());
            ruleBlock.setActivation(new General());
            for (String rule : rules) {
                ruleBlock.addRule(Rule.parse(rule, engine));
            }
            engine.addRuleBlock(ruleBlock);

            EmotionalEvaluator EmotionalEvaluator = new EmotionalEvaluator("EmotionalRulesFull");

            engine.setInputValue("EmotionalState", EmotionalEvaluator.evaluate(believes.getEmotionsListCopy()));
            engine.setInputValue("peasantFamilyAffinity", believes.getPeasantProfile().getPeasantFamilyAffinity());
            engine.process();

            return engine.getOutputValue("Contribution");
        } else {*/
            return evaluateEmotionalContribution(stateBDI, believes.getPeasantProfile().getPeasantFamilyAffinity());
        //}
    }

}
