package org.wpsim.agroecosystem.layer.disease;

import org.wpsim.agroecosystem.automata.cell.LayerCellState;

/**
 * Concrete disease cell state implementation
 */
public class DiseaseCellState implements LayerCellState {

    private double probabilityDisease;

    private boolean infected;

    /**
     *
     */
    public DiseaseCellState() {
        // Constructor vacío intencional, no se requiere inicialización adicional.
    }

    /**
     *
     * @return
     */
    public double getCurrentProbabilityDisease() {
        return probabilityDisease;
    }

    /**
     *
     * @param currentProbabilityDisease
     */
    public void setCurrentProbabilityDisease(double currentProbabilityDisease) {
        this.probabilityDisease = currentProbabilityDisease;
    }

    /**
     *
     * @return
     */
    public double getProbabilityDisease() {
        return probabilityDisease;
    }

    /**
     *
     * @param probabilityDisease
     */
    public void setProbabilityDisease(double probabilityDisease) {
        this.probabilityDisease = probabilityDisease;
    }

    /**
     *
     * @return
     */
    public boolean isInfected() {
        return infected;
    }

    /**
     *
     * @param infected
     */
    public void setInfected(boolean infected) {
        this.infected = infected;
    }
}
