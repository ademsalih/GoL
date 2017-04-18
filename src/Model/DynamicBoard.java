package Model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Narmatha on 11.04.2017.
 */
public class DynamicBoard {

    Color cellColor = Color.WHITE;
    Color backgroundColor = Color.BLACK;

    //Sett denne til å være en størrelse!!!!!
    public ArrayList<ArrayList<Byte>> board;




    double canvasWidth;
    double canvasHeight;
    double xCounter;
    double yCounter;
    int cellSize;
    GraphicsContext gc;

    int generationCounter;

    public DynamicBoard (Canvas canvas) {
        this.canvasWidth = canvas.getWidth();
        this.canvasHeight = canvas.getHeight();
        this.xCounter = 0.0;
        this.yCounter = 0.0;
        this.cellSize = 5;
        this.gc = canvas.getGraphicsContext2D();
    }

    public void addBoard(ArrayList<ArrayList<Byte>> newBoard) {
        for (int y = 0; y < newBoard.size(); y++) {
            for (int x = 0; x < newBoard.get(0).size(); x++) {
                this.board.get(y).set(x, newBoard.get(y).get(x));
            }
        }

    }

    public void setCellState(int x, int y, byte value) {
        //Hvis cellen (x,y) er definert utenfor brettet, skal brettet
        //automatisk utvides slik at brettet inneholder cellen (x,y).
        board.get(x).set(y, value);
    }

    //public byte getCellState(int x, int y) { //
      //  return List??

    //}

    public void setCellSize(int a) { this.cellSize = a; }

    public void setBoard(ArrayList<ArrayList<Byte>> board) {this.board = board;}

    public ArrayList<ArrayList<Byte>> getBoard() {
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
        if (board.get(coly).get(rowx) == 1){
            setCellState(rowx, coly, (byte)0);
        }else{
            setCellState(rowx, coly, (byte)1);
        }
        drawBoard();
    }

    public void drawBoard() {

        gc.setFill(backgroundColor);
        gc.fillRect(0,0,canvasWidth,canvasHeight);

        for (int y = 0; y < board.size(); y++ ) {

            for (int x = 0; x < board.get(0).size(); x++) {

                if (board.get(y).get(x) == 1) {
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

                if (x == (board.get(0).size() - 1 )) {
                    xCounter = 0;
                    yCounter += cellSize;
                }

            }

        }

        yCounter = 0;
    }

    public void clearBoard(){
        board = new ArrayList<ArrayList<Byte>>();
        drawBoard();
    }
}
