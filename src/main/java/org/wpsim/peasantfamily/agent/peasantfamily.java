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
package org.wpsim.peasantfamily.agent;

import BESA.BDI.AgentStructuralModel.Agent.AgentBDI;
import BESA.BDI.AgentStructuralModel.GoalBDI;
import BESA.BDI.AgentStructuralModel.StateBDI;
import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.PeriodicGuardBESA;
import BESA.Kernel.Agent.StructBESA;
import BESA.Kernel.System.AdmBESA;
import BESA.Remote.AdmRemoteImpBESA;
import BESA.Remote.Directory.AgRemoteHandlerBESA;
import BESA.Remote.Directory.RemoteAdmHandlerBESA;
import BESA.Remote.RemoteAdmBESA;
import BESA.Util.PeriodicDataBESA;
import org.wpsim.peasantfamily.goals.l1survival.DoVoidGoal;
import org.wpsim.peasantfamily.guards.fromcivicauthority.FromCivicAuthorityTrainingGuard;
import org.wpsim.peasantfamily.guards.fromcommunitydynamics.SocietyWorkerDateSyncGuard;
import org.wpsim.simulationcontrol.guards.AliveAgentGuard;
import org.wpsim.simulationcontrol.guards.DeadAgentGuard;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.data.PeasantFamilyProfile;
import org.wpsim.peasantfamily.goals.l1survival.DoHealthCareGoal;
import org.wpsim.peasantfamily.goals.l2obligation.LookForLoanGoal;
import org.wpsim.peasantfamily.goals.l2obligation.PayDebtsGoal;
import org.wpsim.peasantfamily.guards.fromsimulationcontrol.ToControlMessage;
import org.wpsim.peasantfamily.goals.l1survival.DoVitalsGoal;
import org.wpsim.peasantfamily.goals.l1survival.SeekPurposeGoal;
import org.wpsim.peasantfamily.goals.l3development.*;
import org.wpsim.peasantfamily.goals.l4skillsresources.*;
import org.wpsim.peasantfamily.goals.l5social.LookForCollaborationGoal;
import org.wpsim.peasantfamily.goals.l5social.ProvideCollaborationGoal;
import org.wpsim.peasantfamily.goals.l6leisure.SpendFamilyTimeGoal;
import org.wpsim.peasantfamily.goals.l6leisure.SpendFriendsTimeGoal;
import org.wpsim.peasantfamily.goals.l6leisure.LeisureActivitiesGoal;
import org.wpsim.peasantfamily.goals.l6leisure.WasteTimeAndResourcesGoal;
import org.wpsim.peasantfamily.guards.frombankoffice.FromBankOfficeGuard;
import org.wpsim.peasantfamily.guards.fromsimulationcontrol.FromSimulationControlGuard;
import org.wpsim.peasantfamily.guards.fromcivicauthority.FromCivicAuthorityGuard;
import org.wpsim.peasantfamily.guards.frommarketplace.FromMarketPlaceGuard;
import org.wpsim.peasantfamily.guards.fromcommunitydynamics.PeasantWorkerContractFinishedGuard;
import org.wpsim.peasantfamily.guards.fromcommunitydynamics.SocietyWorkerContractGuard;
import org.wpsim.peasantfamily.guards.fromcommunitydynamics.SocietyWorkerContractorGuard;
import org.wpsim.peasantfamily.guards.fromagroecosystem.FromAgroEcosystemGuard;
import org.wpsim.peasantfamily.periodicguards.HeartBeatGuard;
import org.wpsim.peasantfamily.guards.status.StatusGuard;
import org.wpsim.wellprodsim.wpsStart;
import org.wpsim.viewerlens.util.wpsReport;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.wpsim.wellprodsim.wpsStart.config;
import static org.wpsim.wellprodsim.wpsStart.params;

/**
 * @TODO: Patrones de comunicación
 */

/**
 * @author jairo
 */
@SuppressWarnings("unchecked")
public class peasantfamily extends AgentBDI {

    private static final double BDITHRESHOLD = 0;

    private static StructBESA createStruct(StructBESA structBESA) throws ExceptionBESA {
        // Cada comportamiento es un hilo.
        structBESA.addBehavior("HeartBeatBehavior");
        structBESA.bindGuard("HeartBeatBehavior", HeartBeatGuard.class);

        structBESA.addBehavior("PeasantBehavior");
        
        //structBESA.addBehavior("FromWorldBehavior");
        structBESA.bindGuard("PeasantBehavior", FromAgroEcosystemGuard.class);

        //structBESA.addBehavior("SocietyBehavior");
        structBESA.bindGuard("PeasantBehavior", SocietyWorkerContractGuard.class);
        structBESA.bindGuard("PeasantBehavior", SocietyWorkerContractorGuard.class);
        structBESA.bindGuard("PeasantBehavior", PeasantWorkerContractFinishedGuard.class);
        structBESA.bindGuard("PeasantBehavior", SocietyWorkerDateSyncGuard.class);

        //structBESA.addBehavior("FromControlBehavior");
        structBESA.bindGuard("PeasantBehavior", FromSimulationControlGuard.class);

        //structBESA.addBehavior("FromBankBehavior");
        structBESA.bindGuard("PeasantBehavior", FromBankOfficeGuard.class);

        //structBESA.addBehavior("FromMarketBehavior");
        structBESA.bindGuard("PeasantBehavior", FromMarketPlaceGuard.class);

        //structBESA.addBehavior("FromCivicAuthorityBehavior");
        structBESA.bindGuard("PeasantBehavior", FromCivicAuthorityGuard.class);
        structBESA.bindGuard("PeasantBehavior", FromCivicAuthorityTrainingGuard.class);

        //structBESA.addBehavior("StatusBehavior");
        structBESA.bindGuard("PeasantBehavior", StatusGuard.class);

        return structBESA;
    }

    private static PeasantFamilyBelieves createBelieves(String alias, PeasantFamilyProfile profile) {
        return new PeasantFamilyBelieves(alias, profile);
    }

    private static List<GoalBDI> createGoals() {

        List<GoalBDI> goals = new ArrayList<>();

        //Level 1 Goals: Survival        
        goals.add(DoVoidGoal.buildGoal());
        goals.add(DoVitalsGoal.buildGoal());
        goals.add(SeekPurposeGoal.buildGoal());
        goals.add(DoHealthCareGoal.buildGoal());
        //goals.add(SelfEvaluationGoal.buildGoal());

        //Level 2 Goals: Obligations
        goals.add(LookForLoanGoal.buildGoal());
        goals.add(PayDebtsGoal.buildGoal());

        //Level 3 Goals: Development        
        //goals.add(AttendToLivestockGoal.buildGoal());
        goals.add(AttendReligiousEventsGoal.buildGoal());
        goals.add(CheckCropsGoal.buildGoal());
        goals.add(HarvestCropsGoal.buildGoal());
        goals.add(ManagePestsGoal.buildGoal());
        goals.add(PlantCropGoal.buildGoal());
        goals.add(PrepareLandGoal.buildGoal());
        goals.add(DeforestingLandGoal.buildGoal());
        goals.add(SellCropGoal.buildGoal());
        goals.add(SearchForHelpAndNecessityGoal.buildGoal());
        goals.add(WorkForOtherGoal.buildGoal());

        //goals.add(ProcessProductsGoal.buildGoal());
        //goals.add(SellProductsGoal.buildGoal());
        //goals.add(MaintainHouseGoal.buildGoal());

        //Level 4 Goals: Skills And Resources
        goals.add(GetPriceListGoal.buildGoal());
        goals.add(ObtainALandGoal.buildGoal());
        goals.add(ObtainSeedsGoal.buildGoal());
        goals.add(ObtainToolsGoal.buildGoal());
        goals.add(AlternativeWorkGoal.buildGoal());
        //goals.add(ObtainPesticidesGoal.buildGoal());
        //goals.add(ObtainSuppliesGoal.buildGoal());
        //goals.add(ObtainLivestockGoal.buildGoal());


        if (config.getBooleanProperty("pfagent.trainingEnabled")) {
            goals.add(GetTechAssistanceGoal.buildGoal());
        }

        if (params.irrigation == 1) {
            goals.add(IrrigateCropsGoal.buildGoal());
            goals.add(ObtainWaterGoal.buildGoal());
        }

        //Level 5 Goals: Social
        //goals.add(CommunicateGoal.buildGoal());
        goals.add(LookForCollaborationGoal.buildGoal());
        goals.add(ProvideCollaborationGoal.buildGoal());

        //Level 6 Goals: Leisure
        goals.add(SpendFamilyTimeGoal.buildGoal());
        goals.add(SpendFriendsTimeGoal.buildGoal());
        goals.add(LeisureActivitiesGoal.buildGoal());
        goals.add(WasteTimeAndResourcesGoal.buildGoal());

        return goals;
    }

    /**
     * @param alias
     * @param peasantProfile
     * @throws ExceptionBESA
     */
    public peasantfamily(String alias, PeasantFamilyProfile peasantProfile) throws ExceptionBESA {
        super(alias, createBelieves(alias, peasantProfile), createGoals(), BDITHRESHOLD, createStruct(new StructBESA()));
        //wpsReport.info("Starting " + alias + " " + peasantProfile.getPeasantKind(), alias);
    }

    /**
     *
     */
    @Override
    public void setupAgentBDI() {
        //wpsReport.debug("Setup " + this.getAlias(), this.getAlias());
        // Internal HeartBeat ping
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) ((StateBDI) this.getState()).getBelieves();
        try {
            AdmBESA.getInstance().getHandlerByAlias(
                    getAlias()
            ).sendEvent(
                    new EventBESA(
                            HeartBeatGuard.class.getName(),
                            new PeriodicDataBESA(
                                    wpsStart.params.steptime,
                                    PeriodicGuardBESA.START_PERIODIC_CALL
                            )
                    )
            );
        } catch (ExceptionBESA ex) {
            System.out.println(ex.getMessage());
        }
        // External Alive Ping
        try {
            AdmBESA.getInstance().getHandlerByAlias(
                    config.getControlAgentName()
            ).sendEvent(
                    new EventBESA(
                            AliveAgentGuard.class.getName(),
                            new ToControlMessage(
                                    believes.getPeasantProfile().getPeasantFamilyAlias(),
                                    believes.getCurrentDay()
                            )
                    )
            );
        } catch (ExceptionBESA ex) {
            System.out.println(ex.getMessage());
        }

    }

    /**
     *
     */
    @Override
    public synchronized void shutdownAgentBDI() {
        System.out.println("Shutdown " + this.getAlias());
        // Anuncio de que el agente está muerto
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) ((StateBDI) this.getState()).getBelieves();
        wpsReport.mental(Instant.now() + "," + believes.toCSV(), this.getAlias());
        wpsReport.ws(believes.toJson(), believes.getAlias());
        //Eliminar el agente
        try {
            AdmBESA.getInstance().getHandlerByAlias(
                    config.getControlAgentName()
            ).sendEvent(
                    new EventBESA(
                            DeadAgentGuard.class.getName(),
                            new ToControlMessage(
                                    believes.getPeasantProfile().getPeasantFamilyAlias(),
                                    believes.getCurrentDay()
                            )
                    )
            );
            AdmBESA.getInstance().killAgent(
                    AdmBESA.getInstance().getHandlerByAlias(
                            this.getAlias()
                    ).getAgId(),
                    config.getDoubleProperty("control.passwd")
            );
        } catch (Exception ex) {
            System.err.println(ex.getMessage() + " " + believes.getAlias());
        }
    }

}
