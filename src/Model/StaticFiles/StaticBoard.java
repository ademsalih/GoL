package Model.StaticFiles;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import Model.Abstract.Board;
import Model.Point;

/**
 * This class draws the board and fills the cells depending on the cells' state
 *
 */

public class StaticBoard extends Board {

    // Testing implementation of X and Y offset
    private int xOffset = 0;
    private int yOffset = 0;

    public static byte[][] initialBoard = new byte[250][350];
    public static byte[][] board = new byte[250][350];

    double canvasWidth;
    double canvasHeight;
    GraphicsContext gc;
    boolean grid = false;

    public StaticBoard(Canvas canvas) {
        this.canvasWidth = canvas.getWidth();
        this.canvasHeight = canvas.getHeight();
        this.gc = canvas.getGraphicsContext2D();
    }

    public void setGrid(boolean gridInput) {
        this.grid = gridInput;
    }

    public boolean getGrid() {
        return this.grid;
    }

    public void addBoard(byte[][] newBoard) {
        if ((newBoard[0].length > board[0].length) || (newBoard.length > board.length)) {
            this.board = new byte[(newBoard.length + 20)][(newBoard[0].length + 20)];
            this.initialBoard = new byte[(newBoard.length + 20)][(newBoard[0].length + 20)];
        }
        int xOffSet = (board[0].length - newBoard[0].length) / 2;
        int yOffSet = (board.length - newBoard.length) / 2;

        for (int y = 0; y < newBoard.length; y++) {
            for (int x = 0; x < newBoard[0].length; x++) {
                this.board[y + yOffSet][x + xOffSet] = newBoard[y][x];
                this.initialBoard[y + yOffSet][x + xOffSet] = newBoard[y][x];
            }
        }
    }

    public void setBoard(byte[][] board) {this.board = board;}


    public void setCellState(int x, int y, byte value) {
        if(x > 250 || y > 350){
            throw new IndexOutOfBoundsException("THE POINT IS NOT LOCATED ON THE GAMEBOARD");
        }
        board[x][y] = value;
    }

    public byte getCellState(int x, int y){
        return board[x][y];
    }

    public void setCellSize(int a) {

        this.cellSize = a;
        this.xOffset  = a;
        this.yOffset = a;
    }

    public byte[][] getBoard() {
        return board;
    }


    // Draw/undraw a cell depending on its state when we click on the board
    public void mouseclickedordraggedonBoard(double x, double y){

        int colx = (int)(x/cellSize);
        int rowy = (int)(y/cellSize);

        if(rowy > 250 || colx > 350){
            setCellState(rowy, colx, (byte) 1);
        }
        else if (board[rowy][colx] == 1){
            setCellState(rowy, colx, (byte)0);
        }else{
            setCellState(rowy, colx, (byte)1);
        }
        drawBoard();

        System.out.println(colx + "   " + rowy);
    }

    public void keyClicked(){

    }

    public void drawBoard() {

        gc.setFill(backgroundColor);
        gc.fillRect(0,0,canvasWidth,canvasHeight);
        Point p = new Point();

        for (int y = 0; y < board.length; y++ ) {

            for (int x = 0; x < board[0].length; x++) {

                if (board[y][x] == 1) {
                    p.setX(xCounter);
                    p.setY(yCounter);
                    p.draw(gc, cellColor, (double)cellSize);

                    xCounter += cellSize;

                } else {
                    xCounter += cellSize;
                }

                if (x == (board[0].length - 1 )) {
                    xCounter = 0;
                    yCounter += cellSize;
                }

            }

        }

        if (grid) {
            drawGrid();
        }

        yCounter = 0;
    }



    public void drawGrid() {

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



    public void clearBoard(){
        this.board = new byte[250][350];
        this.initialBoard = new byte[250][350];
        drawBoard();
    }



}
