package org.wpsim.agroecosystem.agent;

import BESA.Kernel.Agent.AgentBESA;
import BESA.Kernel.Agent.KernelAgentExceptionBESA;
import BESA.Kernel.Agent.StateBESA;
import BESA.Kernel.Agent.StructBESA;

/**
 * AgentBESA implementation for the world agent
 */
public class AgroEcosystem extends AgentBESA {
    
    private static final double TH = 0.91;
    
    /**
     *
     * @param alias
     * @param state
     * @param structAgent
     * @throws KernelAgentExceptionBESA
     */
    public AgroEcosystem(String alias, StateBESA state, StructBESA structAgent) throws KernelAgentExceptionBESA {
        super(alias, state, structAgent, TH);
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
