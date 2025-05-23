package org.wpsim.bankoffice.agent;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.AgentBESA;
import BESA.Kernel.Agent.KernelAgentExceptionBESA;
import BESA.Kernel.Agent.StateBESA;
import BESA.Kernel.Agent.StructBESA;
import org.wpsim.bankoffice.data.BankOfficeState;
import org.wpsim.bankoffice.guards.BankOfficeGuard;

/**
 * ==========================================================================
 * __      __ _ __   ___  * WellProdSim                                  *
 * \ \ /\ / /| '_ \ / __| * @version 1.0                                 *
 * \ V  V / | |_) |\__ \ * @since 2023                                  *
 * \_/\_/  | .__/ |___/ * *
 * | |          * @author Jairo Serrano                        *
 * |_|          * @author Enrique Gonzalez                     *
 * ==========================================================================
 * Social Simulator used to estimate productivity and well-being of peasant *
 * families. It is event oriented, high concurrency, heterogeneous time     *
 * management and emotional reasoning BDI.                                  *
 * ==========================================================================
 */
/**
 *
 * @author jairo
 */
public class BankOffice extends AgentBESA {

    /**
     *
     * @param alias
     * @param state
     * @param structAgent
     * @param passwd
     * @throws KernelAgentExceptionBESA
     */
    public BankOffice(String alias, StateBESA state, StructBESA structAgent, double passwd) throws KernelAgentExceptionBESA {
        super(alias, state, structAgent, passwd);
    }

    public static BankOffice createBankAgent(String alias, double passwd) throws ExceptionBESA{
        return new BankOffice(alias, createState(), createStruct(new StructBESA()), passwd);
    }

    private static StructBESA createStruct(StructBESA structBESA) throws ExceptionBESA {
        structBESA.addBehavior("BankAgentGuard");
        structBESA.bindGuard("BankAgentGuard", BankOfficeGuard.class);
        return structBESA;
    }

    private static BankOfficeState createState() throws ExceptionBESA {
        return new BankOfficeState();
    }

    /**
     *
     */
    @Override
    public void setupAgent() { // Noncompliant - method is empty
    }

    /**
     *
     */
    @Override
    public void shutdownAgent() { // Noncompliant - method is empty
    }

}