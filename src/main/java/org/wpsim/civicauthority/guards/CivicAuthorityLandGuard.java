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
package org.wpsim.civicauthority.guards;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import BESA.Kernel.System.AdmBESA;
import org.wpsim.civicauthority.data.CivicAuthorityState;
import org.wpsim.civicauthority.data.CivicAuthorityLandData;
import org.wpsim.peasantfamily.guards.fromcivicauthority.FromCivicAuthorityGuard;
import org.wpsim.peasantfamily.guards.fromcivicauthority.FromCivicAuthorityMessage;

import java.util.Map;

/**
 *
 * @author jairo
 */
public class CivicAuthorityLandGuard extends GuardBESA  {

    /**
     *
     * @param event
     */
    @Override
    public void funcExecGuard(EventBESA event) {
        CivicAuthorityLandData data = (CivicAuthorityLandData) event.getData();
        CivicAuthorityState state = (CivicAuthorityState) this.getAgent().getState();

        Map.Entry<String, Map<String, String>> assignedFarmData = state.assignLandToFamily(data.getFamilyName());
        String assignedFarmName = assignedFarmData != null ? assignedFarmData.getKey() : null;
        Map<String, String> assignedLands = assignedFarmData != null ? assignedFarmData.getValue() : null;

        try {
            //System.out.println("SENT: peasant family: " + data.getFamilyName() + " Assigned farm: " + assignedFarmName + " Assigned lands: " + assignedLands);
            AdmBESA.getInstance().getHandlerByAlias(
                    data.getFamilyName()
            ).sendEvent(
                    new EventBESA(
                            FromCivicAuthorityGuard.class.getName(),
                            new FromCivicAuthorityMessage(
                                    assignedFarmName,
                                    assignedLands
                            )
                    )
            );
        } catch (ExceptionBESA ex) {
            System.out.println(ex);
        }
    }

}
