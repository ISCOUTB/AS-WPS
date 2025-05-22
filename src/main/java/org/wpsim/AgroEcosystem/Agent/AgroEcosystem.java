package org.wpsim.AgroEcosystem.Agent;

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
        //System.out.println("Creando WorldAgent tierra " + alias);
    }

    /**
     *
     */
    @Override
    public void setupAgent() {
        // Método vacío intencionalmente porque no se requiere configuración al iniciar el agente
        // Si se desea activar esta excepción, comentar la línea anterior y descomentar la siguiente:
        // throw new UnsupportedOperationException("setupAgent() no está implementado en AgroEcosystem");
    }


    /**
     *
     */
    @Override
    public void shutdownAgent() {
        //System.out.println("Eliminando WorldAgent tierra " + this.getAlias());
    }
}
