package org.wpsim.agroecosystem.agent;

import BESA.Kernel.Agent.StateBESA;
import org.wpsim.agroecosystem.layer.LayerExecutor;
import org.wpsim.agroecosystem.layer.LayerFunctionParams;
import org.wpsim.agroecosystem.layer.crop.CropLayer;
import org.wpsim.agroecosystem.layer.disease.DiseaseLayer;
import org.wpsim.agroecosystem.layer.evapotranspiration.EvapotranspirationLayer;
import org.wpsim.agroecosystem.layer.rainfall.RainfallLayer;
import org.wpsim.agroecosystem.layer.shortwaveradiation.ShortWaveRadiationLayer;
import org.wpsim.agroecosystem.layer.temperature.TemperatureLayer;

/**
 * Class that holds the world state, in this case the cellular automaton layers
 */
public class AgroEcosystemState extends StateBESA {
    private TemperatureLayer temperatureLayer;
    private ShortWaveRadiationLayer shortWaveRadiationLayer;
    private CropLayer cropLayer;
    private DiseaseLayer diseaseLayer;
    private EvapotranspirationLayer evapotranspirationLayer;
    private RainfallLayer rainfallLayer;
    private LayerExecutor layerExecutor = new LayerExecutor();

    /**
     *
     * @param temperatureLayer
     * @param shortWaveRadiationLayer
     * @param cropLayer
     * @param diseaseLayer
     * @param evapotranspirationLayer
     * @param rainfallLayer
     */
    public AgroEcosystemState(
            TemperatureLayer temperatureLayer,
            ShortWaveRadiationLayer shortWaveRadiationLayer,
            CropLayer cropLayer,
            DiseaseLayer diseaseLayer,
            EvapotranspirationLayer evapotranspirationLayer,
            RainfallLayer rainfallLayer) {
        this.temperatureLayer = temperatureLayer;
        this.shortWaveRadiationLayer = shortWaveRadiationLayer;
        this.cropLayer = cropLayer;
        this.diseaseLayer = diseaseLayer;
        this.evapotranspirationLayer = evapotranspirationLayer;
        this.rainfallLayer = rainfallLayer;
        layerExecutor.addLayer(this.shortWaveRadiationLayer, this.temperatureLayer, this.evapotranspirationLayer, this.rainfallLayer, this.diseaseLayer, this.cropLayer);
    }

    /**
     *
     * @return
     */
    public TemperatureLayer getTemperatureLayer() {
        return temperatureLayer;
    }

    /**
     *
     * @return
     */
    public ShortWaveRadiationLayer getShortWaveRadiationLayer() {
        return shortWaveRadiationLayer;
    }

    /**
     *
     * @return
     */
    public CropLayer getCropLayer() {
        return cropLayer;
    }

    /**
     *
     * @return
     */
    public DiseaseLayer getDiseaseLayer() {
        return diseaseLayer;
    }

    /**
     *
     * @return
     */
    public EvapotranspirationLayer getEvapotranspirationLayer() {
        return evapotranspirationLayer;
    }

    /**
     *
     * @return
     */
    public RainfallLayer getRainfallLayer() {
        return rainfallLayer;
    }

    /**
     *
     * @return
     */
    public LayerExecutor getLayerExecutor() {
        return layerExecutor;
    }

    /**
     *
     * @param eventDate
     */
    public void lazyUpdateCropsForDate(String eventDate) {
        this.layerExecutor.executeLayers(new LayerFunctionParams(eventDate));
    }
}
