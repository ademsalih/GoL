package Model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Narmatha on 11.04.2017.
 */
public class DynamicBoard {

    Color cellColor = Color.WHITE;
    Color backgroundColor = Color.BLACK;

    //Sett denne til å være en størrelse!!!!!
    public List<List<Byte>> board = new ArrayList<List<Byte>>();

    double canvasWidth;
    double canvasHeight;
    double xCounter = 0.0;
    double yCounter = 0.0;
    int cellSize = 2;
    GraphicsContext gc;
    boolean grid = true;

    int generationCounter;

    public DynamicBoard (Canvas canvas) {
        this.canvasWidth = canvas.getWidth();
        this.canvasHeight = canvas.getHeight();
        this.gc = canvas.getGraphicsContext2D();

        for (int i = 0; i < 250; i++) {
            List<Byte> row = new ArrayList<Byte>();
            for (int j = 0; j < 350; j++) {
                row.add((byte) 0);
            }
            this.board.add(row);
        }
    }

    public void setGrid(boolean gridInput) {
        this.grid = gridInput;
    }

    public boolean getGrid() {
        return this.grid;
    }

    public void addBoard(List<List<Byte>> newBoard) {
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

    public void setBoard(List<List<Byte>> board) {this.board = board;}

    public List<List<Byte>> getBoard() {
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
        //System.out.println(rowx);
        //System.out.println(coly);
        if (board.get(rowx).get(coly) == 1){
            setCellState(rowx, coly, (byte)0);
        }else{
            setCellState(rowx, coly, (byte)1);
        }
        drawBoard();
    }

    public void mousedraggedonBoard(double x, double y, List<Point> somelist){
        int coly = (int)(x/cellSize);
        int rowx = (int)(y/cellSize);
        for ( Point p : somelist ) {
            setCellState(rowx, coly, (byte) 1);
            /*if (board.get(rowx).get(coly) == 1){
                setCellState(rowx, coly, (byte)0);
            }else{
                setCellState(rowx, coly, (byte)1);
            }*/
            drawBoard();
        }
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
        board = new ArrayList<List<Byte>>();
        drawBoard();
    }
}
