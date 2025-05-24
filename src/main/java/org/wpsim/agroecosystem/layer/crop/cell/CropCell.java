package org.wpsim.agroecosystem.layer.crop.cell;

import org.wpsim.agroecosystem.helper.Soil;
import org.wpsim.agroecosystem.automata.cell.GenericWorldLayerCell;
import org.wpsim.agroecosystem.automata.cell.LayerCellState;
import org.wpsim.agroecosystem.layer.disease.DiseaseCell;

/**
 * Crop cell abstract implementation, holds the necessary attributes (according to FAO) to model the crop cell behavior
 *
 * @param <S> Cell
 */
public abstract class CropCell<S extends LayerCellState> extends GenericWorldLayerCell<S> {

    /**
     *
     */
    protected double cropFactorIni;

    /**
     *
     */
    protected double cropFactorMid;

    /**
     *
     */
    protected double cropFactorEnd;

    /**
     *
     */
    protected double degreeDaysMid;

    /**
     *
     */
    protected double degreeDaysEnd;

    /**
     *
     */
    protected int cropArea;

    /**
     *
     */
    protected boolean isActive;

    /**
     *
     */
    protected DiseaseCell diseaseCell;

    /**
     *
     */
    protected double maximumRootDepth;

    /**
     *
     */
    protected double depletionFraction;

    /**
     *
     */
    protected Soil soilType;

    // TAW

    /**
     *
     */
    protected double totalAvailableWater;

    //RAW

    /**
     *
     */

    protected double readilyAvailableWater;

    /**
     *
     */
    protected String agentPeasantId;

    /**
     *
     */
    protected boolean harvestReady = false;

    /**
     *
     * @param cropFactorIni
     * @param cropFactorMid
     * @param cropFactorEnd
     * @param degreeDaysMid
     * @param degreeDaysEnd
     * @param cropArea
     * @param maximumRootDepth
     * @param depletionFraction
     * @param soilType
     * @param isActive
     * @param diseaseCell
     * @param agentPeasantId
     */
    public CropCell(double cropFactorIni,
                    double cropFactorMid,
                    double cropFactorEnd,
                    double degreeDaysMid,
                    double degreeDaysEnd,
                    int cropArea,
                    double maximumRootDepth,
                    double depletionFraction,
                    Soil soilType,
                    boolean isActive,
                    DiseaseCell diseaseCell,
                    String agentPeasantId) {
        this.cropFactorIni = cropFactorIni;
        this.cropFactorMid = cropFactorMid;
        this.cropFactorEnd = cropFactorEnd;
        this.degreeDaysMid = degreeDaysMid;
        this.degreeDaysEnd = degreeDaysEnd;
        this.cropArea = cropArea;
        this.isActive = isActive;
        this.diseaseCell = diseaseCell;
        this.maximumRootDepth = maximumRootDepth;
        this.depletionFraction = depletionFraction;
        this.soilType = soilType;
        this.agentPeasantId = agentPeasantId;
        this.calculateTAWRAW();
    }

    /**
     *
     */
    public CropCell() {
    }

    //Equations from https://www.fao.org/3/x0490e/x0490e0e.htm#total%20available%20water%20(taw)  equations 82, 83
    private void calculateTAWRAW() {
        this.totalAvailableWater = 1000 * (this.soilType.getWaterContentAtFieldCapacity() - this.soilType.getWaterContentAtWiltingPoint()) * this.maximumRootDepth;
        this.readilyAvailableWater = this.depletionFraction * this.totalAvailableWater;
    }

    /**
     *
     * @return
     */
    public double getCropFactorIni() {
        return cropFactorIni;
    }

    /**
     *
     * @param cropFactorIni
     */
    public void setCropFactorIni(double cropFactorIni) {
        this.cropFactorIni = cropFactorIni;
    }

    /**
     *
     * @return
     */
    public double getCropFactorMid() {
        return cropFactorMid;
    }

    /**
     *
     * @param cropFactorMid
     */
    public void setCropFactorMid(double cropFactorMid) {
        this.cropFactorMid = cropFactorMid;
    }

    /**
     *
     * @return
     */
    public double getCropFactorEnd() {
        return cropFactorEnd;
    }

    /**
     *
     * @param cropFactorEnd
     */
    public void setCropFactorEnd(double cropFactorEnd) {
        this.cropFactorEnd = cropFactorEnd;
    }

    /**
     *
     * @return
     */
    public int getCropArea() {
        return cropArea;
    }

    /**
     *
     * @param cropArea
     */
    public void setCropArea(int cropArea) {
        this.cropArea = cropArea;
    }

    /**
     *
     * @return
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     *
     * @param active
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     *
     * @return
     */
    public double getDegreeDaysMid() {
        return degreeDaysMid;
    }

    /**
     *
     * @param degreeDaysMid
     */
    public void setDegreeDaysMid(double degreeDaysMid) {
        this.degreeDaysMid = degreeDaysMid;
    }

    /**
     *
     * @return
     */
    public double getDegreeDaysEnd() {
        return degreeDaysEnd;
    }

    /**
     *
     * @param degreeDaysEnd
     */
    public void setDegreeDaysEnd(double degreeDaysEnd) {
        this.degreeDaysEnd = degreeDaysEnd;
    }

    /**
     *
     * @return
     */
    public DiseaseCell getDiseaseCell() {
        return diseaseCell;
    }

    /**
     *
     * @param diseaseCell
     */
    public void setDiseaseCell(DiseaseCell diseaseCell) {
        this.diseaseCell = diseaseCell;
    }

    /**
     *
     * @return
     */
    public double getMaximumRootDepth() {
        return maximumRootDepth;
    }

    /**
     *
     * @param maximumRootDepth
     */
    public void setMaximumRootDepth(double maximumRootDepth) {
        this.maximumRootDepth = maximumRootDepth;
    }

    /**
     *
     * @return
     */
    public double getDepletionFraction() {
        return depletionFraction;
    }

    /**
     *
     * @param depletionFraction
     */
    public void setDepletionFraction(double depletionFraction) {
        this.depletionFraction = depletionFraction;
    }

    /**
     *
     * @return
     */
    public Soil getSoilType() {
        return soilType;
    }

    /**
     *
     * @param soilType
     */
    public void setSoilType(Soil soilType) {
        this.soilType = soilType;
    }

    /**
     *
     * @return
     */
    public double getTotalAvailableWater() {
        return totalAvailableWater;
    }

    /**
     *
     * @return
     */
    public double getReadilyAvailableWater() {
        return readilyAvailableWater;
    }

    /**
     *
     * @return
     */
    public String getAgentPeasantId() {
        return agentPeasantId;
    }

    /**
     *
     * @param agentPeasantId
     */
    public void setAgentPeasantId(String agentPeasantId) {
        this.agentPeasantId = agentPeasantId;
    }

    /**
     *
     * @return
     */
    public boolean isHarvestReady() {
        return harvestReady;
    }

    /**
     *
     * @param harvestReady
     */
    public void setHarvestReady(boolean harvestReady) {
        this.harvestReady = harvestReady;
    }
}