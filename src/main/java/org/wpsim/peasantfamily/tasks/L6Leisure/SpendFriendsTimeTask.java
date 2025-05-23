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
package org.wpsim.peasantfamily.tasks.L6Leisure;

import BESA.Emotional.EmotionalEvent;
import org.wpsim.simulationcontrol.data.Coin;
import org.wpsim.wellprodsim.base.wpsTask;
import rational.mapping.Believes;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.data.Utils.TimeConsumedBy;

/**
 *
 * @author jairo
 */
public class SpendFriendsTimeTask extends wpsTask {

    /**
     * Executes the SpendFamilyTimeTask
     * @param parameters Believes of the agent
     */
    @Override
    public void executeTask(Believes parameters) {
        this.setExecuted(false);
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        believes.addTaskToLog(believes.getInternalCurrentDate());

        if (Coin.flipCoin()) {
            believes.useTime(TimeConsumedBy.SpendFriendsTimeTask.getTime());
        } else {
            believes.useTime(believes.getTimeLeftOnDay());
        }

        believes.processEmotionalEvent(new EmotionalEvent("FAMILY", "LEISURE", "TIME"));
        believes.processEmotionalEvent(new EmotionalEvent("FRIEND", "LEISURE", "TIME"));
    }

}
