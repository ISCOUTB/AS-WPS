package org.wpsim.agroecosystem.automata.layer;

import org.wpsim.agroecosystem.automata.cell.LayerCell;

/**
 * Abstract class that holds the structure for a layer that contains a matrix of cells
 *
 * @param <T> Type of the cell
 */
public abstract class GenericWorldLayerMatrixCell<T extends LayerCell> extends GenericWorldLayer {

    /**
     * Data structure that holds the matrix of cells
     */
    protected T[][] cellMatrix;

    /**
     * Constructor protegido con parámetro
     *
     * @param cellMatrix Matrix of cells
     */
    protected GenericWorldLayerMatrixCell(T[][] cellMatrix) {
        this.cellMatrix = cellMatrix;
    }

    /**
     * Constructor protegido para uso por subclases
     */
    protected GenericWorldLayerMatrixCell() {
    }

    /**
     * Gets the current cell matrix
     *
     * @return Cell matrix
     */
    public T[][] getCellMatrix() {
        return cellMatrix;
    }

    /**
     * Sets the cell matrix
     *
     * @param cellMatrix Matrix of cells
     */
    public void setCellMatrix(T[][] cellMatrix) {
        this.cellMatrix = cellMatrix;
    }
}
