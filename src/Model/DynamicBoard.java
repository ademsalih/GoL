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

    public List<List<Byte>> board = new ArrayList<List<Byte>>();

    double canvasWidth;
    double canvasHeight;
    double xCounter = 0.0;
    double yCounter = 0.0;
    int cellSize = 2;
    GraphicsContext gc;
    int rowcount;
    int columcount;

    int generationCounter;

    public DynamicBoard (Canvas canvas, int x, int y) {
        this.canvasWidth = canvas.getWidth();
        this.canvasHeight = canvas.getHeight();
        this.gc = canvas.getGraphicsContext2D();
        this.rowcount = y;
        this.columcount = x;

        for (int i = 0; i < rowcount; i++) {
            List<Byte> row = new ArrayList<Byte>();
            for (int j = 0; j < columcount; j++) {
                row.add((byte) 0);
            }
            this.board.add(row);
        }
    }

    public void addBoard(List<List<Byte>> newBoard) {
        /*for (int y = 0; y < newBoard.size(); y++) {
            for (int x = 0; x < newBoard.get(0).size(); x++) {
                this.board.get(y).set(x, newBoard.get(y).get(x));
            }
        }*/


        System.out.println(newBoard.size());
        System.out.println(newBoard.get(0).size());
        System.out.println(this.board.size());
        //System.out.println(this.board.get(0).size());
        for (int y = 0; y < newBoard.size(); y++) {
            List<Byte> oneDim = new ArrayList<Byte>();
            for (int x = 0; x < newBoard.get(0).size(); x++) {
                oneDim.add(x, (byte) x);
            }
            this.board.add(oneDim);
        }

    }

    public void setCellState(int rowy, int colx, byte value) {
        //Hvis cellen (x,y) er definert utenfor brettet, skal brettet
        //automatisk utvides slik at brettet inneholder cellen (x,y).

        // Extends the gameboard with colums

        if(colx > columcount && rowy < rowcount){
            for(int i = 0; i < rowcount; i++){
                for(int j = 0; j < colx-columcount; j++){
                    this.board.get(i).add((byte) 0);
                }
            }
        }
        // Extends the gameboard with rows

        else if (rowy > rowcount && colx < columcount){
            for(int i = 0; i < rowy-rowcount; i++){
                List<Byte> row = new ArrayList<Byte>();
                for(int j = 0; j < columcount; j++){
                    row.add((byte) 0);
                }
                this.board.add(row);
            }
        }
        // Extends the gameboard with rows and colums
        else if (colx > columcount && rowy > rowcount ){
            for(int i = 0; i < rowcount; i++){
                for(int j = 0; j < colx-columcount; j++){
                    this.board.get(i).add((byte) 0);
                }
            }
            for(int i = 0; i < rowy-rowcount; i++){
                List<Byte> row = new ArrayList<Byte>();
                for(int j = 0; j < colx; j++){
                    row.add((byte) 0);
                }
                this.board.add(row);

            }

        }
        this.board.get(rowy-1).set(colx-1, value);
    }

    public byte getCellState(int x, int y) {
        return this.board.get(x-1).get(y-1);

    }

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
        int colx = (int)(x/cellSize);
        int rowy = (int)(y/cellSize);
        //System.out.println(rowy);
        //System.out.println(colx);
        if (board.get(rowy).get(colx) == 1){
            setCellState(rowy, colx, (byte)0);
        }else{
            setCellState(rowy, colx, (byte)1);
        }
        drawBoard();
    }

    public void mousedraggedonBoard(double x, double y, List<Point> somelist){
        int colx = (int)(x/cellSize);
        int rowy = (int)(y/cellSize);
        //System.out.println(rowy);
        //System.out.println(colx);
        for ( Point p : somelist ) {
            if ((colx >= columcount) || (rowy >= rowcount)){
                setCellState(rowy, colx, (byte)1);}
        }if (this.board.get(rowy).get(colx) == 0){
            setCellState(rowy, colx, (byte)1);
        }else if(this.board.get(rowy).get(colx) == 1){
            setCellState(rowy, colx, (byte)0);
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
