package Model.Abstract;

import javafx.scene.paint.Color;

/**
 * The Board interface represents the gameboard
 */

public abstract class Board {

    protected Color cellColor = Color.WHITE;
    protected Color backgroundColor = Color.BLACK;
    protected Color gridColor = Color.WHITE;
    protected double cellSize = 2;
    protected double xCounter = 0.0;
    protected double yCounter = 0.0;


    protected Board(){}

    public void setCellColor(Color cellColor) {this.cellColor = cellColor;}

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getcellColor() {
        return this.cellColor;
    }

    public Color getBackgroundColor() {return this.backgroundColor;}

    public abstract void setCellState(int x, int y, byte value);

    /**
     * Draws the cells in the gameboard
     */
    public abstract void drawBoard();

    /**
     * Clears the cells in the gameboard
     */
    public abstract void clearBoard();

    public abstract byte getCellState(int x, int y);

    /**
     * Draws/undraws a cell depending on its state when we click/drag on the board
     * @param x x coordinate
     * @param y y coordinate
     */
    public abstract void mouseClickedOrDraggedOnBoard(double x, double y);

    public void setCellSize(double a) { this.cellSize = a;}

    public double getCellSize() {
        return this.cellSize;
    }

    }
