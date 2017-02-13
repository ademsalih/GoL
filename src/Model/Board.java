package Model;

/**
 * Created by patrikkvarmehansen on 09/02/17.
 *
 * Board class that creates and keeps a 2D-array that's meant to represent the board before drawn.
 */
public class Board {
    private byte[][] board;
    private int cellSize;
    private int columns;
    private int rows;
    private String cellColor;

    public Board (int cellSize, int columns, int rows, String cellColor) {
        this.cellSize = cellSize;
        this.rows = rows;
        this.columns = columns;
        this.cellColor = cellColor;
        this.board = new byte[columns][rows];
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public String getCellColor() {
        return cellColor;
    }


}
