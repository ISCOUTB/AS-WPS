package org.wpsim.agroecosystem.agent;

import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import org.wpsim.agroecosystem.Messages.AgroEcosystemMessage;

import java.util.logging.Logger;

public class CloseAgroEcosystemGuard extends GuardBESA {

    // Definimos el logger estático para esta clase
    private static final Logger logger = Logger.getLogger(CloseAgroEcosystemGuard.class.getName());

    @Override
    public synchronized void funcExecGuard(EventBESA eventBESA) {
        AgroEcosystemMessage agroEcosystemMessage = (AgroEcosystemMessage) eventBESA.getData();

        // Reemplazamos el System.out.println por logger info
        logger.info("Cerrando agent 🚩🚩🚩 " + this.agent.getAlias());

        this.agent.shutdownAgent();
    }
}
