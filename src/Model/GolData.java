package Model;

/**
 * Created by patrikkvarmehansen on 09/02/17.
 *
 * Class that keeps data that relates to the size of the board.
 */
public class GolData {

    //Data field
    private int cellSize;
    private int columns;
    private int rows;
    private String cellColor;

    public GolData (int cellSize, int columns, int rows, String cellColor) {
        this.cellSize = cellSize;
        this.rows = rows;
        this.columns = columns;
        this.cellColor = cellColor;
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

