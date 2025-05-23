package org.wpsim.agroecosystem.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * Data loaded from <a href="https://www.fao.org/3/x0490e/x0490e0j.htm#annex%202.%20meteorological%20tables">https://www.fao.org/3/x0490e/x0490e0j.htm#annex%202.%20meteorological%20tables</a>
 */
public class ExtraterrestrialRadiation {

    private static final HashMap<Integer, double[]> northernData = new HashMap<>();
    private static final HashMap<Integer, double[]> southernData = new HashMap<>();


    static {
        northernData.put(0, new double[]{36.2, 37.5, 37.9, 36.8, 34.8, 33.4, 33.9, 35.7, 37.3, 37.4, 36.3, 35.6});
        northernData.put(2, new double[]{36.9, 37.9, 38.0, 36.4, 34.1, 31.6, 33.1, 35.2, 37.1, 37.7, 37.0, 36.4});
        northernData.put(4, new double[]{37.6, 38.3, 38.0, 36.0, 33.4, 31.8, 32.3, 34.6, 37.0, 38.0, 37.6, 37.2});
        northernData.put(6, new double[]{38.3, 38.7, 38.0, 35.6, 32.7, 30.9, 31.5, 34.0, 36.8, 38.2, 38.2, 38.0});
        northernData.put(8, new double[]{38.9, 39.0, 37.9, 35.1, 31.9, 30.0, 30.7, 33.4, 36.6, 38.4, 38.8, 38.7});
        northernData.put(10, new double[]{39.5, 39.3, 37.8, 34.6, 31.1, 29.1, 29.8, 32.8, 36.3, 38.5, 39.3, 39.4});
        northernData.put(12, new double[]{40.1, 39.6, 37.7, 34.0, 30.2, 28.1, 28.9, 32.1, 36.0, 38.6, 39.8, 40.0});
        northernData.put(14, new double[]{40.6, 39.7, 37.5, 33.4, 29.4, 27.2, 27.9, 31.3, 35.6, 38.7, 40.2, 40.6});
        northernData.put(16, new double[]{41.1, 39.9, 37.2, 32.8, 28.5, 26.2, 27.0, 30.6, 35.2, 38.7, 40.6, 41.2});
        northernData.put(18, new double[]{41.5, 40.0, 37.0, 32.1, 27.5, 25.1, 26.0, 29.8, 34.7, 38.7, 40.9, 41.7});
        northernData.put(20, new double[]{41.9, 40.0, 36.6, 31.3, 26.6, 24.1, 25.0, 28.9, 34.2, 38.6, 41.2, 42.1});
        northernData.put(22, new double[]{42.2, 40.1, 36.2, 30.6, 25.6, 23.0, 24.0, 28.1, 33.7, 38.4, 41.4, 42.6});
        northernData.put(24, new double[]{42.5, 40.0, 35.8, 29.8, 24.6, 21.9, 22.9, 27.2, 33.1, 38.3, 41.7, 43.0});
        northernData.put(26, new double[]{42.8, 39.9, 35.3, 29.0, 23.5, 20.8, 21.8, 26.3, 32.5, 38.0, 41.8, 43.3});
        northernData.put(28, new double[]{43.0, 39.8, 34.8, 28.1, 22.5, 19.7, 20.7, 25.3, 31.8, 37.8, 41.9, 43.6});
        northernData.put(30, new double[]{43.1, 39.6, 34.3, 27.2, 21.4, 18.5, 19.6, 24.3, 31.1, 37.5, 42.0, 43.9});
        northernData.put(32, new double[]{43.3, 39.4, 33.7, 26.3, 20.3, 17.4, 18.5, 23.3, 30.4, 37.1, 42.0, 44.1});
        northernData.put(34, new double[]{43.4, 39.2, 33.0, 25.3, 19.2, 16.2, 17.4, 22.3, 29.6, 36.7, 42.0, 44.3});
        northernData.put(36, new double[]{43.4, 38.9, 32.4, 24.3, 18.1, 15.1, 16.2, 21.2, 28.8, 36.3, 42.0, 44.4});
        northernData.put(38, new double[]{43.4, 38.5, 31.7, 23.3, 16.9, 13.9, 15.1, 20.2, 28.0, 35.8, 41.9, 44.5});
        northernData.put(40, new double[]{43.4, 38.1, 30.9, 22.3, 15.8, 12.8, 13.9, 19.1, 27.1, 35.3, 41.8, 44.6});
        northernData.put(42, new double[]{43.3, 37.7, 30.1, 21.2, 14.6, 11.6, 12.8, 18.0, 26.2, 34.7, 41.6, 44.6});
        northernData.put(44, new double[]{43.2, 37.2, 29.3, 20.1, 13.5, 10.5, 11.6, 16.8, 25.2, 34.1, 41.4, 44.6});
        northernData.put(46, new double[]{43.0, 36.7, 28.4, 19.0, 12.3, 9.3, 10.4, 15.7, 24.3, 33.5, 41.1, 44.6});
        northernData.put(48, new double[]{42.9, 36.2, 27.5, 17.9, 11.1, 8.2, 9.3, 14.6, 23.3, 32.8, 40.9, 44.5});
        northernData.put(50, new double[]{42.7, 35.6, 26.6, 16.7, 10.0, 7.1, 8.2, 13.4, 22.2, 32.1, 40.6, 44.5});
        northernData.put(52, new double[]{42.5, 35.0, 25.6, 15.6, 8.8, 6.0, 7.1, 12.2, 21.2, 31.4, 40.2, 44.4});
        northernData.put(54, new double[]{42.2, 34.3, 24.6, 14.4, 7.7, 4.9, 6.0, 11.1, 20.1, 30.6, 39.9, 44.3});
        northernData.put(56, new double[]{42.0, 33.7, 23.6, 13.2, 6.6, 3.9, 4.9, 9.9, 19.0, 29.8, 39.5, 44.1});
        northernData.put(58, new double[]{41.7, 33.0, 22.6, 12.0, 5.5, 2.9, 3.9, 8.7, 17.9, 28.9, 39.1, 44.0});
        northernData.put(60, new double[]{41.5, 32.3, 21.5, 10.8, 4.4, 2.0, 2.9, 7.6, 16.7, 28.1, 38.7, 43.9});
        northernData.put(62, new double[]{41.2, 31.5, 20.4, 9.6, 3.4, 1.2, 2.0, 6.4, 15.5, 27.2, 38.3, 43.9});
        northernData.put(64, new double[]{41.0, 30.8, 19.3, 8.4, 2.4, 0.6, 1.2, 5.3, 14.4, 26.3, 38.0, 43.9});
        northernData.put(66, new double[]{40.9, 30.0, 18.1, 7.2, 1.5, 0.1, 0.5, 4.2, 13.1, 25.4, 37.6, 44.1});
        northernData.put(68, new double[]{41.0, 29.3, 16.9, 6.0, 0.8, 0.0, 0.0, 3.2, 11.9, 24.4, 37.4, 44.7});
        northernData.put(70, new double[]{41.4, 28.6, 15.8, 4.9, 0.2, 0.0, 0.0, 2.2, 10.7, 23.5, 37.3, 45.3});

        // southernData completo
        southernData.put(0, new double[]{0.0, 2.6, 10.4, 23.0, 35.2, 42.5, 39.4, 28.0, 14.9, 4.9, 0.1, 0.0});
        southernData.put(2, new double[]{0.1, 3.7, 11.7, 23.9, 35.3, 42.0, 38.9, 28.6, 16.1, 6.0, 0.7, 0.0});
        southernData.put(4, new double[]{0.6, 4.8, 12.9, 24.8, 35.6, 41.4, 38.8, 29.3, 17.3, 7.2, 1.5, 0.1});
        southernData.put(6, new double[]{1.4, 5.9, 14.1, 25.8, 35.9, 41.2, 38.8, 30.0, 18.4, 8.5, 2.4, 0.6});
        southernData.put(8, new double[]{2.3, 7.1, 15.4, 26.6, 36.3, 41.2, 39.0, 30.6, 19.5, 9.7, 3.4, 1.3});
        southernData.put(10, new double[]{3.3, 8.3, 16.6, 27.5, 36.6, 41.2, 39.2, 31.3, 20.6, 10.9, 4.4, 2.2});
        southernData.put(12, new double[]{4.3, 9.6, 17.7, 28.4, 37.0, 41.3, 39.4, 32.0, 21.7, 12.1, 5.5, 3.1});
        southernData.put(14, new double[]{5.4, 10.8, 18.9, 29.2, 37.4, 41.4, 39.6, 32.6, 22.7, 13.3, 6.7, 4.2});
        southernData.put(16, new double[]{6.5, 12.0, 20.0, 30.0, 37.8, 41.5, 39.8, 33.2, 23.7, 14.5, 7.8, 5.2});
        southernData.put(18, new double[]{7.7, 13.2, 21.1, 30.8, 38.2, 41.6, 40.1, 33.8, 24.7, 15.7, 9.0, 6.4});
        southernData.put(20, new double[]{8.9, 14.4, 22.2, 31.5, 38.5, 41.7, 40.2, 34.4, 25.7, 16.9, 10.2, 7.5});
        southernData.put(22, new double[]{10.1, 15.7, 23.3, 32.2, 38.8, 41.8, 40.4, 34.9, 26.6, 18.1, 11.4, 8.7});
        southernData.put(24, new double[]{11.3, 16.9, 24.3, 32.9, 39.1, 41.9, 40.6, 35.4, 27.5, 19.2, 12.6, 9.9});
        southernData.put(26, new double[]{12.5, 18.0, 25.3, 33.5, 39.3, 41.9, 40.7, 35.9, 28.4, 20.3, 13.9, 11.1});
        southernData.put(28, new double[]{13.8, 19.2, 26.3, 34.1, 39.5, 41.9, 40.8, 36.3, 29.2, 21.4, 15.1, 12.4});
        southernData.put(30, new double[]{15.0, 20.4, 27.2, 34.7, 39.7, 41.9, 40.8, 36.7, 30.0, 22.5, 16.3, 13.6});
        southernData.put(32, new double[]{16.2, 21.5, 28.1, 35.2, 39.9, 41.8, 40.8, 37.0, 30.7, 23.6, 17.5, 14.8});
        southernData.put(34, new double[]{17.5, 22.6, 29.0, 35.7, 40.0, 41.7, 40.8, 37.4, 31.5, 24.6, 18.7, 16.1});
        southernData.put(36, new double[]{18.7, 23.7, 29.9, 36.1, 40.0, 41.6, 40.8, 37.6, 32.1, 25.6, 19.9, 17.3});
        southernData.put(38, new double[]{19.9, 24.8, 30.7, 36.5, 40.0, 41.4, 40.7, 37.9, 32.8, 26.6, 21.1, 18.5});
        southernData.put(40, new double[]{21.1, 25.8, 31.4, 36.8, 40.0, 41.2, 40.6, 38.0, 33.4, 27.6, 22.2, 19.8});
        southernData.put(42, new double[]{22.3, 26.8, 32.2, 37.1, 40.0, 40.9, 40.4, 38.2, 33.9, 28.5, 23.3, 21.0});
        southernData.put(44, new double[]{23.4, 27.8, 32.8, 37.4, 39.9, 40.6, 40.2, 38.3, 34.5, 29.3, 24.5, 22.2});
        southernData.put(46, new double[]{24.6, 28.8, 33.5, 37.6, 39.7, 40.3, 39.9, 38.3, 34.9, 30.2, 25.5, 23.3});
        southernData.put(48, new double[]{25.7, 29.7, 34.1, 37.8, 39.5, 40.0, 39.6, 38.4, 35.4, 31.0, 26.6, 24.5});
        southernData.put(50, new double[]{26.8, 30.6, 34.7, 37.9, 39.3, 39.5, 39.3, 38.3, 35.8, 31.8, 27.7, 25.6});
        southernData.put(52, new double[]{27.9, 31.5, 35.2, 38.0, 39.0, 39.1, 38.9, 38.2, 36.1, 32.5, 28.7, 26.8});
        southernData.put(54, new double[]{28.9, 32.3, 35.7, 38.1, 38.7, 38.6, 38.5, 38.1, 36.4, 33.2, 29.6, 27.9});
        southernData.put(56, new double[]{29.9, 33.1, 36.1, 38.1, 38.4, 38.1, 38.1, 38.0, 36.7, 33.9, 30.6, 28.9});
        southernData.put(58, new double[]{30.9, 33.8, 36.5, 38.0, 38.0, 37.6, 37.6, 37.8, 36.9, 34.5, 31.5, 30.0});
        southernData.put(60, new double[]{31.9, 34.5, 36.9, 37.9, 37.6, 37.0, 37.1, 37.5, 37.1, 35.1, 32.4, 31.0});
        southernData.put(62, new double[]{32.8, 35.2, 37.2, 37.8, 37.1, 36.3, 36.5, 37.2, 37.2, 35.6, 33.3, 32.0});
        southernData.put(64, new double[]{33.7, 35.8, 37.4, 37.6, 36.6, 35.7, 35.9, 36.9, 37.3, 36.1, 34.1, 32.9});
        southernData.put(66, new double[]{34.6, 36.4, 37.6, 37.4, 36.0, 35.0, 35.3, 36.5, 37.3, 36.6, 34.9, 33.9});
        southernData.put(68, new double[]{35.4, 37.0, 37.8, 37.1, 35.4, 34.2, 34.6, 36.1, 37.3, 37.0, 35.6, 34.8});
        southernData.put(70, new double[]{36.2, 37.5, 37.9, 36.8, 34.8, 33.4, 33.9, 35.7, 37.2, 37.4, 36.3, 35.6});
    }

    public static Map<Integer, double[]> getNorthernData() {
        return northernData;
    }

    public static Map<Integer, double[]> getSouthernData() {
        return southernData;
    }
}