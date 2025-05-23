package org.wpsim.simulationcontrol.data;

import java.util.Random;

public class Coin {
    private static final Random RANDOM = new Random();

    public static boolean flipCoin() {
        return RANDOM.nextBoolean();
    }
}
