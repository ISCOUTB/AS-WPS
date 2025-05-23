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
package org.wpsim.marketplace.agent;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.AgentBESA;
import BESA.Kernel.Agent.KernelAgentExceptionBESA;
import BESA.Kernel.Agent.StateBESA;
import BESA.Kernel.Agent.StructBESA;
import org.wpsim.marketplace.data.MarketPlaceState;
import org.wpsim.marketplace.guards.MarketPlaceGuard;
import org.wpsim.marketplace.guards.MarketPlaceInfoAgentGuard;

/**
 *
 * @author jairo
 */
public class MarketPlace extends AgentBESA {

    /**
     *
     * @param alias
     * @param state
     * @param structAgent
     * @param passwd
     * @throws KernelAgentExceptionBESA
     */
    public MarketPlace(String alias, StateBESA state, StructBESA structAgent, double passwd) throws KernelAgentExceptionBESA {
        super(alias, state, structAgent, passwd);
    }

    public static MarketPlace createAgent(String alias, double passwd) throws ExceptionBESA{
        return new MarketPlace(alias, createState(), createStruct(new StructBESA()), passwd);
    }
    
    private static StructBESA createStruct(StructBESA structBESA) throws ExceptionBESA {
        structBESA.addBehavior("MarketAgentGuard");
        structBESA.bindGuard("MarketAgentGuard", MarketPlaceGuard.class);
        structBESA.bindGuard("MarketAgentGuard", MarketPlaceInfoAgentGuard.class);
        return structBESA;
    }
    
    private static MarketPlaceState createState() throws ExceptionBESA {
        return new MarketPlaceState();
    }
    
    /**
     *
     */
    @Override
    public void setupAgent() {
        // Constructor vacío intencional, no se requiere inicialización adicional.
    }

    /**
     *
     */
    @Override
    public void shutdownAgent() {
        // Constructor vacío intencional, no se requiere inicialización adicional.
    }
    
}
