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
package org.wpsim.peasantfamily.data.utils;

/**
 *
 */
public enum PeasantActivityType {

    /**
     *
     */
    FREE,
    /**
     *
     */
    BUSY,
    /**
     *
     */
    NONE,
    /**
     *
     */
    LEISURE,
    /**
     *
     */
    PRICE_LIST,
    /**
     *
     */
    PTW,
    /**
     *
     */
    BLOCKED

}
