package org.wpsim.perturbationgenerator.periodicguards;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.PeriodicGuardBESA;
import BESA.Kernel.System.AdmBESA;
import org.wpsim.simulationcontrol.util.ControlCurrentDate;
import org.wpsim.marketplace.data.MarketPlaceMessage;
import org.wpsim.marketplace.data.MarketPlaceMessageType;
import org.wpsim.marketplace.guards.MarketPlaceInfoAgentGuard;
import org.wpsim.wellprodsim.config.wpsConfig;
import org.wpsim.wellprodsim.util.wpsCSV;
import org.wpsim.wellprodsim.wpsStart;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NaturalPhenomena extends PeriodicGuardBESA {

    private static final Random RANDOM = new Random();

    public NaturalPhenomena() {
        super();
        wpsCSV.log("NaturalPhenomena", "Agent,CurrentDate,randomIncreaseOrDecreaseType,randomNumber");
    }

    @Override
    public void funcPeriodicExecGuard(EventBESA eventBESA) {
        if (Math.random() < wpsConfig.getInstance().getDoubleProperty("perturbation.probability.value")) {
            try {
                MarketPlaceMessageType randomIncreaseOrDecreaseType = selectRandomIncreaseOrDecrease();
                int randomNumber = selectRandomNumber();
                AdmBESA.getInstance().getHandlerByAlias(
                        wpsStart.config.getMarketAgentName()
                ).sendEvent(
                        new EventBESA(
                                MarketPlaceInfoAgentGuard.class.getName(),
                                new MarketPlaceMessage(
                                        randomIncreaseOrDecreaseType,
                                        randomNumber,
                                        ControlCurrentDate.getInstance().getCurrentDate()
                                )
                        )
                );
                wpsCSV.log(
                        "NaturalPhenomena",
                        "Market," +
                                ControlCurrentDate.getInstance().getCurrentDate() +
                                "," + randomIncreaseOrDecreaseType +
                                "," + randomNumber
                );
            } catch (ExceptionBESA e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static MarketPlaceMessageType selectRandomIncreaseOrDecrease() {
        List<MarketPlaceMessageType> increaseDecreaseMessages = Arrays.asList(
                MarketPlaceMessageType.INCREASE_TOOLS_PRICE,
                MarketPlaceMessageType.INCREASE_SEEDS_PRICE,
                MarketPlaceMessageType.INCREASE_CROP_PRICE,
                MarketPlaceMessageType.DECREASE_TOOLS_PRICE,
                MarketPlaceMessageType.DECREASE_SEEDS_PRICE,
                MarketPlaceMessageType.DECREASE_CROP_PRICE
        );
        int index = RANDOM.nextInt(increaseDecreaseMessages.size());
        return increaseDecreaseMessages.get(index);
    }

    public static int selectRandomNumber() {
        int randomNumber = RANDOM.nextInt(32);
        return 5 + (randomNumber * 5);
    }
}
