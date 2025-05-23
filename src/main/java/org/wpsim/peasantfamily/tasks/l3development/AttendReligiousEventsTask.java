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
package org.wpsim.peasantfamily.tasks.l3development;

import BESA.Emotional.EmotionalEvent;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.wellprodsim.base.wpsTask;
import rational.mapping.Believes;

/**
 *
 * @author jairo
 */
public class AttendReligiousEventsTask extends wpsTask {
    /**
     *
     * @param parameters
     */
    @Override
    public void executeTask(Believes parameters) {
        this.setExecuted(false);
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        believes.useTime(believes.getTimeLeftOnDay());
        believes.processEmotionalEvent(new EmotionalEvent("FAMILY", "SPIRITUALHEALTH", "TIME"));
        believes.processEmotionalEvent(new EmotionalEvent("FRIEND", "SPIRITUALHEALTH", "TIME"));
        believes.addTaskToLog(believes.getInternalCurrentDate());
    }

}
