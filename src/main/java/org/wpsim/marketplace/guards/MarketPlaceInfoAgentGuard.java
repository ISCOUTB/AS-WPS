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
package org.wpsim.marketplace.guards;

import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import org.wpsim.marketplace.data.MarketPlaceState;
import org.wpsim.marketplace.data.MarketPlaceMessage;

/**
 *
 * @author jairo
 */
public class MarketPlaceInfoAgentGuard extends GuardBESA {
    /**
     *
     * @param event
     */
    @Override
    public void funcExecGuard(EventBESA event) {
        MarketPlaceMessage marketPlaceMessage = (MarketPlaceMessage) event.getData();
        MarketPlaceState state = (MarketPlaceState) this.agent.getState();

        switch (marketPlaceMessage.getMessageType()){
            case DECREASE_CROP_PRICE -> state.decreaseCropPrice(marketPlaceMessage.getQuantity());
            case INCREASE_CROP_PRICE -> state.increaseCropPrice(marketPlaceMessage.getQuantity());
            case INCREASE_TOOLS_PRICE -> state.increaseToolsPrice(marketPlaceMessage.getQuantity());
            case DECREASE_TOOLS_PRICE -> state.decreaseToolsPrice(marketPlaceMessage.getQuantity());
            case DECREASE_SEEDS_PRICE -> state.decreaseSeedsPrice(marketPlaceMessage.getQuantity());
            case INCREASE_SEEDS_PRICE -> state.increaseSeedsPrice(marketPlaceMessage.getQuantity());
        }

    }

}