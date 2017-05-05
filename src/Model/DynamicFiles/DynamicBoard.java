package Model.DynamicFiles;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;
import Model.Abstract.Board;
import Model.Point;

/**
 * Represents the Dynamic gameboard
 */
public class DynamicBoard extends Board {

    public List<List<Byte>> board = new ArrayList<>();
    public List<List<Byte>> initialBoard = new ArrayList<>();

    private double canvasWidth;
    private double canvasHeight;
    private GraphicsContext gc;
    private int rowcount;
    private int columcount;
    private int margin;

    private boolean grid;

    public DynamicBoard (Canvas canvas, int x, int y) {
        this.canvasWidth = canvas.getWidth();
        this.canvasHeight = canvas.getHeight();
        this.gc = canvas.getGraphicsContext2D();
        this.margin  = 10;
        this.board = initBoard(x, y);
        this.initialBoard = initBoard(x, y);
        this.grid = false;
    }

    public DynamicBoard(int x, int y){
        this.rowcount = y;
        this.columcount = x;
        this.margin  = 20;
        this.board = initBoard(x, y);
        this.grid = false;
    }


    /**
     * Sets a given board as both the main board ("board") and also the initial board ("initialBoard")
     * @param newBoard - 2D ArrayList that should be used by the DynamicBoard class
     */
    public void addBoard(List<List<Byte>> newBoard) {

        this.board = newBoard;
        this.initialBoard = newBoard;
        this.board = centerBoard(this.board);
        this.initialBoard = centerBoard(this.initialBoard);
    }

    public void setGrid(boolean gridInput) {
        this.grid = gridInput;
    }

    public boolean getGrid() {
        return this.grid;
    }

    private List<List<Byte>> centerBoard(List<List<Byte>> board) {

        int bigMargin = margin * 4;
        int twoBigMargin = bigMargin * 2;

        List<List<Byte>> centeredBoard = new ArrayList<>();

        for (int y = 0; y < twoBigMargin + board.size(); y++) {
            List<Byte> temp = new ArrayList<>();
            centeredBoard.add(temp);
        }

        centeredBoard = addCols(twoBigMargin + board.get(0).size(), centeredBoard);


        for (int y = bigMargin; y < board.size() + bigMargin; y++) {
            for (int x = bigMargin; x < board.get(y - bigMargin).size() + bigMargin; x++) {
                centeredBoard.get(y).set(x, (board.get(y - bigMargin).get(x - bigMargin)));
            }
        }
        return centeredBoard;
    }

    private List<List<Byte>> initBoard(int x, int y) {
        List<List<Byte>> tempBoard = new ArrayList<>();
        for (int row = 0; row < x; row++) {
            List<Byte> rowList = new ArrayList<>();
            for (int col = 0; col < y; col++) {
                rowList.add((byte) 0);
            }
            tempBoard.add(rowList);
        }
        return tempBoard;
    }

    private List<List<Byte>> addCols(int numberOfCols, List<List<Byte>> board) {
        for (int i = 0; i < numberOfCols; i++) {
            for (List<Byte> byteLists : board) {
                byteLists.add((byte) 0);
            }
        }
        return board;
    }

    /**
     * Counts the number of neighbors surrounding the given cell
     * @param numberOfRows - number of rows to add on the 2D list (board)
     * @param board - The 2D list (the board) that is going be added with rows
     * @return - A 2D list (board) with added rows
     */
    private List<List<Byte>> addRows(int numberOfRows, List<List<Byte>> board) {
        for (int i = 0; i < numberOfRows; i++) {
            List<Byte> row = new ArrayList<>();
            for (int x = 0; x < board.get(0).size(); x++) {
                row.add((byte) 0 );
            }
            board.add(row);
        }
        return board;
    }

    private void expandBoard(int rowy, int colx, int y, int x) {

        if (x < (colx + margin) && y > (rowy + margin)) {
            this.board = addCols(colx - x + margin, this.board);
        }

        else if (x > (colx + margin) && y < (rowy + margin)) {
            this.board = addRows(rowy - y + margin, this.board);
        }

        else if (x < (colx + margin) && y < (rowy + margin)) {
            this.board = addCols(colx - x + margin, this.board);
            this.board = addRows(rowy - y + margin, this.board);
        }
    }

    public void setCellState(int rowy, int colx, byte value) {
        // Checks if cell is outside the margin of the board. Updates the board if needed.
        rowcount = board.size();
        columcount = board.get(0).size();
        if ((colx + margin) > columcount || (rowy + margin) > rowcount) {
            expandBoard(rowy, colx, rowcount, columcount);
        }
        this.board.get(rowy).set(colx, value);
    }

    public byte getCellState(int y, int x) throws ArrayIndexOutOfBoundsException {
        if (y >= board.size() || x >= board.get(0).size()) {
            return 2;
        }
        else if (y < 0 || x < 0){
            return 3;
        }
        else {
            return this.board.get(y).get(x);
        }
    }

    public void setBoard(List<List<Byte>> board) {
        this.board = board;

    }

    public List<List<Byte>> getBoard() {
        return board;
    }



    /**
     * Draws/undraws a cell depending on its state when we click/drag on the board
     * @param y - y coordinate
     * @param x - x coordinate
     */
    public void mouseClickedOrDraggedOnBoard(double x, double y){
        int colx = (int) ((x / cellSize));
        int rowy = (int) ((y / cellSize));

        byte cellState = getCellState(rowy, colx);

        if (cellState == 1) {
            setCellState(rowy, colx, (byte) 0);
        }
        else if (cellState == 0 || cellState == 2) {
            setCellState(rowy, colx, (byte) 1);
        }

        drawBoard();
    }


    public void drawBoard() {
        gc.setFill(backgroundColor);
        gc.fillRect(0,0,canvasWidth,canvasHeight);
        Point p = new Point();

        for (List<Byte> aBoard : board)
            for (int x = 0; x < aBoard.size(); x++) {

                if (aBoard.get(x) == 1) {
                    p.x = xCounter;
                    p.y = yCounter;
                    p.draw(gc, cellColor, cellSize);

                    xCounter += cellSize;

                } else {
                    xCounter += cellSize;
                }

                if (x == (board.get(0).size() - 1)) {
                    xCounter = 0;
                    yCounter += cellSize;
                }
            }

        yCounter = 0;

        if (grid) {
            drawGrid();
        }

        yCounter = 0;
    }

    private void drawGrid() {

        double gridSize = cellSize * 0.05;
        gc.setStroke(gridColor);
        gc.setLineWidth(gridSize);

        int i = 0;

        while (i <= canvasHeight) {
            gc.strokeLine(0, i, canvasWidth, i);
            i += cellSize;
        }
        i = 0;

        while (i <= canvasWidth) {
            gc.strokeLine(i, 0, i, canvasHeight);
            i += cellSize;
        }
    }

    public void clearBoard() {

        board = new ArrayList<>();

        for (int i = 0; i < 250; i++) {
            List<Byte> row = new ArrayList<>();
            for (int j = 0; j < 350; j++) {
                row.add((byte) 0);
            }
            this.board.add(row);
        }

        drawBoard();
    }

    public void clearInitialBoard() {

        initialBoard = new ArrayList<>();

        for (int i = 0; i < 250; i++) {
            List<Byte> row = new ArrayList<>();
            for (int j = 0; j < 350; j++) {
                row.add((byte) 0);
            }
            this.board.add(row);
        }
    }


}
