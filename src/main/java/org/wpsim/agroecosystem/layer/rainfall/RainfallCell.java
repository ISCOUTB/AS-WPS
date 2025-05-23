package org.wpsim.agroecosystem.layer.rainfall;

import org.wpsim.agroecosystem.automata.cell.GenericWorldLayerCell;

/**
 * Concrete implementation of the rainfall cell
 */
public class RainfallCell extends GenericWorldLayerCell<RainfallCellState> {

    private String id;

    /**
     *
     * @param id
     */
    public RainfallCell(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

}
