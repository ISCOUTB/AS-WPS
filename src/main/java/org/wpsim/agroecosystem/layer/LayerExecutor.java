package org.wpsim.agroecosystem.layer;

import org.wpsim.agroecosystem.automata.layer.WorldLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Pojo that contains all the world layers and executes them in a specific order
 */
public class LayerExecutor {

    private List<WorldLayer> layers = new ArrayList<>();

    /**
     *
     */
    public LayerExecutor() {
        // Constructor vacío intencional, no se requiere inicialización adicional.
    }

    /**
     *
     * @param layers
     */
    public void addLayer(WorldLayer... layers) {
        this.layers.addAll(Arrays.asList(layers));
    }

    /**
     *
     * @param params
     */
    public void executeLayers(LayerFunctionParams params) {
        for (WorldLayer layer : this.layers) {
            layer.executeLayer(params);
        }
    }
}
