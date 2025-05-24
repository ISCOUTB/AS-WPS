/**
 * ==========================================================================
 * __      __ _ __   ___  * WellProdSim                                  *
 * \ \ /\ / /| '_ \ / __| * @version 1.0                                 *
 * \ V  V / | |_) |\__ \  * @since 2023                                  *
 * \_/\_/  | .__/ |___/   * *
 * | |                    * @author Jairo Serrano                        *
 * |_|                    * @author Enrique Gonzalez                     *
 * ==========================================================================
 * Social Simulator used to estimate productivity and well-being of peasant *
 * families. It is event oriented, high concurrency, heterogeneous time     *
 * management and emotional reasoning BDI.                                  *
 * ==========================================================================
 */
package org.wpsim.simulationcontrol.util;

public class SimulationParams {
    private String mode;
    private String env;
    private int money = -1;
    private int land = -1;
    private double personality = -1.0;
    private int tools = -1;
    private int seeds = -1;
    private int water = -1;
    private int irrigation = -1;
    private int emotions = -1;
    private int training = -1;
    private int nodes = 0;
    private int steptime = 50;
    private String world;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getLand() {
        return land;
    }

    public void setLand(int land) {
        this.land = land;
    }

    public double getPersonality() {
        return personality;
    }

    public void setPersonality(double personality) {
        this.personality = personality;
    }

    public int getTools() {
        return tools;
    }

    public void setTools(int tools) {
        this.tools = tools;
    }

    public int getSeeds() {
        return seeds;
    }

    public void setSeeds(int seeds) {
        this.seeds = seeds;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getIrrigation() {
        return irrigation;
    }

    public void setIrrigation(int irrigation) {
        this.irrigation = irrigation;
    }

    public int getEmotions() {
        return emotions;
    }

    public void setEmotions(int emotions) {
        this.emotions = emotions;
    }

    public int getTraining() {
        return training;
    }

    public void setTraining(int training) {
        this.training = training;
    }

    public int getNodes() {
        return nodes;
    }

    public void setNodes(int nodes) {
        this.nodes = nodes;
    }

    public int getSteptime() {
        return steptime;
    }

    public void setSteptime(int steptime) {
        this.steptime = steptime;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }
}