package Model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class draws the board and fills the cells depending on the cells' state
 *
 */

public class Board {

    Color cellColor = Color.WHITE;
    Color backgroundColor = Color.BLACK;

    public static byte[][] initialBoard = new byte[100][140];
    public static byte[][] board = new byte[100][140];

    double canvasWidth;
    double canvasHeight;
    double xCounter;
    double yCounter;
    int cellSize;
    GraphicsContext gc;

    int generationCounter;

    public Board (Canvas canvas) {
        this.canvasWidth = canvas.getWidth();
        this.canvasHeight = canvas.getHeight();
        this.xCounter = 0.0;
        this.yCounter = 0.0;
        this.cellSize = 2;
        this.gc = canvas.getGraphicsContext2D();
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

    public void setBoardValues(int x, int y, byte value) {
        board[x][y] = value;
    }

    public byte getBoardValues(int x, int y){
        return board[x][y];
    }

    public void setCellSize(int a) {
        this.cellSize = a;
    }

    public byte[][] getBoard() {
        return board;
    }

    public void setCellColor(Color cellColor) {
        this.cellColor = cellColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getcellColor() {
        return this.cellColor;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }


    // Draw/undraw a cell depending on its state when we click on the board
    public void mouseclickedonBoard(double x, double y){
        int coly = (int)(x/cellSize);
        int rowx = (int)(y/cellSize);
        if (board[rowx][coly] == 1){
            setBoardValues(rowx, coly, (byte)0);
        }else{
            setBoardValues(rowx, coly, (byte)1);
        }
        drawBoard();

    }

    public void drawBoard() {

        gc.setFill(backgroundColor);
        gc.fillRect(0,0,canvasWidth,canvasHeight);

        for (int y = 0; y < board.length; y++ ) {

            for (int x = 0; x < board[0].length; x++) {

                if (board[y][x] == 1) {
                    Point p = new Point();
                    p.x = xCounter;
                    p.y = yCounter;
                    p.draw(gc, cellColor, (double)cellSize);

                    xCounter += cellSize;

                } else {
                    Point p = new Point();
                    p.x = xCounter;
                    p.y = yCounter;
                    p.draw(gc, backgroundColor, (double)cellSize);

                    xCounter += cellSize;
                }

                if (x == (board[0].length - 1 )) {
                    xCounter = 0;
                    yCounter += cellSize;
                }

            }

        }

        yCounter = 0;
    }

    public void clearBoard(){
        this.board = new byte[100][140];
        this.initialBoard = new byte[100][140];
        drawBoard();
    }



}
