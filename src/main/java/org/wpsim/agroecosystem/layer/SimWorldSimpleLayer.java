package org.wpsim.agroecosystem.layer;

import org.wpsim.agroecosystem.helper.MonthlyDataLoader;
import org.wpsim.agroecosystem.helper.WorldConfiguration;
import org.wpsim.agroecosystem.automata.cell.LayerCell;
import org.wpsim.agroecosystem.automata.layer.GenericWorldLayerUniqueCell;
import org.wpsim.agroecosystem.layer.data.MonthData;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Abstract implementation for the layers, used for this specific world simulation
 *
 * @param <C> type of cell
 */
public abstract class SimWorldSimpleLayer<C extends LayerCell> extends GenericWorldLayerUniqueCell<C> {

    /**
     *
     */
    protected List<MonthData> monthlyData;

    /**
     *
     */
    protected Random random;

    /**
     *
     */
    protected WorldConfiguration worldConfig = WorldConfiguration.getPropsInstance();

    /**
     *
     * @param dataFile
     */
    public SimWorldSimpleLayer(String dataFile) {
        this.loadYearDataFromFile(dataFile);
        this.random = new Random();
    }

    /**
     *
     * @param month
     * @return
     */
    protected double calculateGaussianFromMonthData(int month) {
        MonthData monthData = this.monthlyData.get(month);
        return this.random.nextGaussian() * monthData.getStandardDeviation() + monthData.getAverage();
    }

    /**
     *
     * @param dataFile
     */
    protected void loadYearDataFromFile(String dataFile) {
        try {
            this.monthlyData = MonthlyDataLoader.loadMonthlyDataFile(dataFile);
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception.getMessage());
        }
    }

}
