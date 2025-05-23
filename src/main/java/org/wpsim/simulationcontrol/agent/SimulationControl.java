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
package org.wpsim.simulationcontrol.agent;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.AgentBESA;
import BESA.Kernel.Agent.KernelAgentExceptionBESA;
import BESA.Kernel.Agent.StateBESA;
import BESA.Kernel.Agent.StructBESA;
import org.wpsim.simulationcontrol.data.SimulationControlState;
import org.wpsim.simulationcontrol.guards.AliveAgentGuard;
import org.wpsim.simulationcontrol.guards.DeadContainerGuard;
import org.wpsim.simulationcontrol.guards.SimulationControlGuard;
import org.wpsim.simulationcontrol.guards.DeadAgentGuard;

/**
 *
 * @author jairo
 */
public class SimulationControl extends AgentBESA {

    /**
     *
     * @param alias
     * @param state
     * @param structAgent
     * @param passwd
     * @throws KernelAgentExceptionBESA
     */
    public SimulationControl(String alias, StateBESA state, StructBESA structAgent, double passwd) throws KernelAgentExceptionBESA {
        super(alias, state, structAgent, passwd);
    }

    public static SimulationControl createAgent(String alias, double passwd) throws ExceptionBESA{
        return new SimulationControl(alias, createState(), createStruct(new StructBESA()), passwd);
    }
    
    private static StructBESA createStruct(StructBESA structBESA) throws ExceptionBESA {
        structBESA.addBehavior("ControlAgentGuard");
        structBESA.bindGuard("ControlAgentGuard", SimulationControlGuard.class);
        //structBESA.addBehavior("AliveAgentGuard");
        structBESA.bindGuard("ControlAgentGuard", AliveAgentGuard.class);
        //structBESA.addBehavior("DeadAgentGuard");
        structBESA.bindGuard("ControlAgentGuard", DeadAgentGuard.class);
        //structBESA.addBehavior("DeadContainerGuard");
        structBESA.bindGuard("ControlAgentGuard", DeadContainerGuard.class);
        return structBESA;
    }
    
    private static SimulationControlState createState() throws ExceptionBESA {
        return new SimulationControlState();
    }

    /**
     *
     */
    @Override
    public void setupAgent() {
        System.out.println("UPDATE: " + getAlias() + " Creado");
    }

    /**
     *
     */
    @Override
    public void shutdownAgent() {
        //((SimulationControlState) state).stopScheduler();
    }
    
}
