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
package org.wpsim.peasantfamily.tasks.l6leisure;

import BESA.Emotional.EmotionalEvent;
import org.wpsim.wellprodsim.base.wpsTask;
import rational.mapping.Believes;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.data.utils.PeasantLeisureType;

import java.util.Random;

/**
 *
 * @author jairo
 */
public class WasteTimeAndResourcesTask extends wpsTask {

    private static final Random RANDOM = new Random();

    /**
     *
     * @param parameters Believes
     */
    @Override
    public void executeTask(Believes parameters) {
        this.setExecuted(false);
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        believes.addTaskToLog(believes.getInternalCurrentDate());
        believes.useTime(believes.getTimeLeftOnDay());
        believes.getPeasantProfile().useMoney(RANDOM.nextInt(100000));
        believes.setCurrentPeasantLeisureType(PeasantLeisureType.NONE);
        believes.processEmotionalEvent(new EmotionalEvent("FAMILY", "LEISURE", "MONEY"));
    }

}
