package Model.Abstract;

import javafx.scene.paint.Color;

public abstract class Board {

    public Color cellColor = Color.WHITE;
    public Color backgroundColor = Color.BLACK;
    public Color gridColor = Color.WHITE;
    public double cellSize = 2;
    public double xCounter = 0.0;
    public double yCounter = 0.0;

    public Board(){}

    public void setCellColor(Color cellColor) {this.cellColor = cellColor;}

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getcellColor() {
        return this.cellColor;
    }

    public Color getBackgroundColor() {return this.backgroundColor;}

    public abstract void setCellState(int x, int y, byte value);

    public abstract void drawBoard();

    public abstract void clearBoard();

    public abstract byte getCellState(int x, int y);

    public abstract void mouseClickedOrDraggedOnBoard(double x, double y);

    public void setCellSize(int a) { this.cellSize = a;}

    }
