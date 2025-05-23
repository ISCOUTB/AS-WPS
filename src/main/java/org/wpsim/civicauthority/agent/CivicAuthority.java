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
package org.wpsim.civicauthority.agent;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.*;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.System.AdmBESA;
import BESA.Util.PeriodicDataBESA;
import org.wpsim.civicauthority.data.CivicAuthorityState;
import org.wpsim.civicauthority.guards.CivicAuthorityHelpGuard;
import org.wpsim.civicauthority.guards.CivicAuthorityLandGuard;
import org.wpsim.civicauthority.guards.CivicAuthorityReleaseLandGuard;
import org.wpsim.civicauthority.guards.TrainingOfferGuard;
import org.wpsim.wellprodsim.wpsStart;

/**
 *
 * @author jairo
 */
public class CivicAuthority extends AgentBESA {

    /**
     *
     * @param alias
     * @param state
     * @param structAgent
     * @param passwd
     * @throws KernelAgentExceptionBESA
     */
    public CivicAuthority(String alias, StateBESA state, StructBESA structAgent, double passwd) throws KernelAgentExceptionBESA {
        super(alias, state, structAgent, passwd);
    }

    public static CivicAuthority createAgent(String alias, double passwd) throws ExceptionBESA {
        return new CivicAuthority(alias, createState(), createStruct(new StructBESA()), passwd);
    }

    private static StructBESA createStruct(StructBESA structBESA) throws ExceptionBESA {
        structBESA.addBehavior("GovernmentAgentBehavior");
        structBESA.bindGuard("GovernmentAgentBehavior", CivicAuthorityHelpGuard.class);
        structBESA.bindGuard("GovernmentAgentBehavior", CivicAuthorityLandGuard.class);
        structBESA.bindGuard("GovernmentAgentBehavior", CivicAuthorityReleaseLandGuard.class);

        //structBESA.addBehavior("TrainingOfferBehavior");
        structBESA.bindGuard("GovernmentAgentBehavior", TrainingOfferGuard.class);

        return structBESA;
    }

    private static CivicAuthorityState createState() throws ExceptionBESA {
        return new CivicAuthorityState();
    }

    /**
     *
     */
    @Override
    public void setupAgent() {
        try {
            AdmBESA.getInstance().getHandlerByAlias(
                    wpsStart.config.getGovernmentAgentName()
            ).sendEvent(
                    new EventBESA(
                            TrainingOfferGuard.class.getName(),
                            new PeriodicDataBESA(
                                    800,
                                    PeriodicGuardBESA.START_PERIODIC_CALL
                            )
                    )
            );
        } catch (ExceptionBESA e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     */
    @Override
    public void shutdownAgent() {
        // Constructor vacío intencional, no se requiere inicialización adicional.
    }

}
