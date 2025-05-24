package org.wpsim.agroecosystem.layer.shortwaveradiation;

import org.wpsim.agroecosystem.automata.cell.GenericWorldLayerCell;

/**
 * Concrete cell implementation
 */
public class ShortWaveRadiationCell extends GenericWorldLayerCell<ShortWaveRadiationCellState> {

    private String id;

    /**
     *
     * @param id
     */
    public ShortWaveRadiationCell(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

}
