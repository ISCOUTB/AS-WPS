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
package org.wpsim.peasantfamily.tasks.L1Survival;

import BESA.Emotional.EmotionalEvent;
import BESA.Emotional.Semantics;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.data.Utils.TimeConsumedBy;
import org.wpsim.peasantfamily.emotions.EmotionalEvaluator;
import org.wpsim.wellprodsim.base.wpsTask;
import rational.mapping.Believes;

/**
 *
 * @author jairo
 */
public class DoHealthCareTask extends wpsTask {

    /**
     * @param parameters
     */
    @Override
    public void executeTask(Believes parameters) {
        this.setExecuted(false);
        double factor = 1;
        EmotionalEvaluator evaluator = new EmotionalEvaluator("EmotionalRulesFull");
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        if (believes.isHaveEmotions()) {
            factor = evaluator.emotionalFactor(believes.getEmotionsListCopy(), Semantics.Emotions.Happiness);
        }
        believes.useTime(TimeConsumedBy.DoHealthCareTask.getTime());
        believes.getPeasantProfile().increaseHealth(factor);
        believes.processEmotionalEvent(new EmotionalEvent("FAMILY", "DOVITALS", "FOOD"));
        believes.addTaskToLog(believes.getInternalCurrentDate());
    }

    /**
     * @param parameters
     */
    @Override
    public void interruptTask(Believes parameters) {
        // Constructor vacío intencional, no se requiere inicialización adicional.
    }

    /**
     * @param parameters
     */
    @Override
    public void cancelTask(Believes parameters) {
        // Constructor vacío intencional, no se requiere inicialización adicional.
    }
}

