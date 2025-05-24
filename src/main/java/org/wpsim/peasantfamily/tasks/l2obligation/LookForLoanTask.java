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
package org.wpsim.peasantfamily.tasks.l2obligation;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.System.AdmBESA;
import org.wpsim.bankoffice.guards.BankOfficeGuard;
import org.wpsim.bankoffice.data.BankOfficeMessage;
import org.wpsim.peasantfamily.data.utils.TimeConsumedBy;
import org.wpsim.wellprodsim.base.wpsTask;
import org.wpsim.wellprodsim.wpsStart;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.viewerlens.util.wpsReport;
import rational.mapping.Believes;

import static org.wpsim.bankoffice.data.BankOfficeMessageType.ASK_FOR_FORMAL_LOAN;

/**
 *
 * @author jairo
 */
public class LookForLoanTask extends wpsTask {

    /**
     *
     * @param parameters
     */
    @Override
    public void executeTask(Believes parameters) {
        this.setExecuted(false);
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        believes.useTime(TimeConsumedBy.LookForLoanTask.getTime());
        //wpsReport.info("LookForLoanTask", believes.getPeasantProfile().getPeasantFamilyAlias());
        // @TODO: Se debe calcular cuanto necesitas prestar hasta que se coseche.
        try {
            //wpsReport.info("Pidiendo prestamo formal", believes.getPeasantProfile().getPeasantFamilyAlias());
            AdmBESA.getInstance().getHandlerByAlias(
                    wpsStart.config.getBankAgentName()
            ).sendEvent(
                    new EventBESA(
                            BankOfficeGuard.class.getName(),
                            new BankOfficeMessage(
                                    ASK_FOR_FORMAL_LOAN,
                                    believes.getPeasantProfile().getPeasantFamilyAlias(),
                                    2000000,
                                    believes.getInternalCurrentDate()
                            )
                    )
            );
        } catch (ExceptionBESA ex) {
            wpsReport.error(ex, believes.getPeasantProfile().getPeasantFamilyAlias());
        }
        believes.addTaskToLog(believes.getInternalCurrentDate());
    }

}
