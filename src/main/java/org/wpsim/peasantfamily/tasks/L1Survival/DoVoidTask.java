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
package org.wpsim.peasantfamily.tasks.L1Survival;

import BESA.Emotional.EmotionalEvent;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import rational.mapping.Believes;
import rational.mapping.Task;

/**
 *
 */
public class DoVoidTask extends Task {

    @Override
    public boolean checkFinish(Believes parameters) {
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        return !believes.isWaiting();
    }

    /**
     * @param parameters
     */
    @Override
    public void executeTask(Believes parameters) {
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        //@TODO: REVISAR
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        //wpsReport.info("Adelantado " + believes.getAlias() + " esperando 50 ms", believes.getAlias());
        believes.setWait(false);
    }

    @Override
    public void interruptTask(Believes believes) {
        // Constructor vacío intencional, no se requiere inicialización adicional.
    }

    @Override
    public void cancelTask(Believes believes) {
        // Constructor vacío intencional, no se requiere inicialización adicional.
    }

}
