package org.wpsim.AgroEcosystem.Agent;

import BESA.Kernel.Agent.AgentBESA;
import BESA.Kernel.Agent.KernelAgentExceptionBESA;
import BESA.Kernel.Agent.StateBESA;
import BESA.Kernel.Agent.StructBESA;

/**
 * AgentBESA implementation for the world agent
 */

/**
 *
 * @param alias
 * @param state
 * @param structAgent
 * @throws KernelAgentExceptionBESA
 */

public class AgroEcosystem extends AgentBESA {
    
    private static final double TH = 0.91;
    

    public AgroEcosystem(String alias, StateBESA state, StructBESA structAgent) throws KernelAgentExceptionBESA {
        super(alias, state, structAgent, TH);

    }
    //System.out.println("Creando WorldAgent tierra " + alias);
    /**
     *
     */
    @Override
    public void setupAgent() {
    }

    /**
     *
     */
    @Override
    //System.out.println("Eliminando WorldAgent tierra " + this.getAlias());
    public void shutdownAgent() {

    }
}
