package Model.DynamicFiles;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;
import Model.Abstract.Board;
import Model.Point;

/**
 * Created by Narmatha on 11.04.2017.
 */
public class DynamicBoard extends Board {

    public List<List<Byte>> board = new ArrayList<List<Byte>>();
    public List<List<Byte>> initialBoard = new ArrayList<List<Byte>>();

    double canvasWidth;
    double canvasHeight;
    GraphicsContext gc;
    int rowcount;
    int columcount;
    int margin;

    int generationCounter;

    public DynamicBoard (Canvas canvas, int x, int y) {
        this.canvasWidth = canvas.getWidth();
        this.canvasHeight = canvas.getHeight();
        this.gc = canvas.getGraphicsContext2D();
        this.rowcount = y;
        this.columcount = x;
        this.margin  = 20;
        this.board = initBoard(x, y);
        this.initialBoard = initBoard(x, y);
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

    private List<List<Byte>> centerBoard(List<List<Byte>> board) {

        int bigMargin = margin * 4;
        int twoBigMargin = bigMargin * 2;

        List<List<Byte>> centeredBoard = new ArrayList<>();

        for (int y = 0; y < twoBigMargin + board.size(); y++) {
            List<Byte> temp = new ArrayList<Byte>();
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

    private List<List<Byte>> addRows(int numberOfRows, List<List<Byte>> board) {
        for (int i = 0; i < numberOfRows; i++) {
            List<Byte> row = new ArrayList<Byte>();
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
        //Hvis cellen (x,y) er definert utenfor brettet, skal brettet
        //automatisk utvides slik at brettet inneholder cellen (x,y).

        // Checks if cell is outside the margin of the board. Updates the board if needed.
        this.board.get(rowy).set(colx, value);
    }

    public byte getCellState(int x, int y) {
        return this.board.get(x-1).get(y-1);

    }



    public void setBoard(List<List<Byte>> board) {
        this.board = board;

    }

    public List<List<Byte>> getBoard() {
        return board;
    }

    // Draw/undraw a cell depending on its state when we click on the board
    public void mouseClickedOrDraggedOnBoard(double x, double y){
        rowcount = board.size();
        columcount = board.get(0).size();
        int colx = (int) ((x/cellSize));
        int rowy = (int) ((y/cellSize));
        if ((colx + margin) > columcount || (rowy + margin)> rowcount) {
            expandBoard(rowy, colx, rowcount, columcount);
        }
        if (this.board.get(rowy).get(colx) == 0){
            setCellState(rowy, colx, (byte)1);
        }else if(this.board.get(rowy).get(colx) == 1) {
            setCellState(rowy, colx, (byte) 0);
        }
        drawBoard();

    }

    public void drawBoard() {

        gc.setFill(backgroundColor);
        gc.fillRect(0,0,canvasWidth,canvasHeight);
        Point p = new Point();

        for (int y = 0; y < board.size(); y++ ) {

            for (int x = 0; x < board.get(y).size(); x++) {

                if (board.get(y).get(x) == 1) {
                    p.x = xCounter;
                    p.y = yCounter;
                    p.draw(gc, cellColor, (double)cellSize);

                    xCounter += cellSize;

                } else {
                    xCounter += cellSize;
                }

                if (x == (board.get(0).size() - 1 )) {
                    xCounter = 0;
                    yCounter += cellSize;
                }
            }
        }

        yCounter = 0;
    }

    public void clearBoard(){

        board = new ArrayList<List<Byte>>();

        for (int i = 0; i < 250; i++) {
            List<Byte> row = new ArrayList<Byte>();
            for (int j = 0; j < 350; j++) {
                row.add((byte) 0);
            }
            this.board.add(row);
        }

        drawBoard();
    }
}
