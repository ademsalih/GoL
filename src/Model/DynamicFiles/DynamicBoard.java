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

    double canvasWidth;
    double canvasHeight;
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
        /*
        System.out.println(newBoard.size());
        System.out.println(newBoard.get(0).size());
        this.board.clear();
        // DETTE ER EN DEL AV GRUNNEN TIL HVORFOR INNLASTINGEN AV PATTERNS IKKE FUNKER HELT
        for (int y = 0; y < newBoard.size(); y++) {
            List<Byte> oneDim = new ArrayList<Byte>();
            for (int x = 0; x < newBoard.get(0).size(); x++) {
                oneDim.add(x, (byte) 1);
            }
            this.board.add(oneDim);
        }

        for (int y = 0; y < this.board.size(); y++) {
            for (int x = 0; x < this.board.get(y).size(); x++) {
                System.out.print(this.board.get(y).get(x));
            }
            System.out.println("");
        }
        for (int y = 0; y < newBoard.size(); y++) {
            for (int x = 0; x < newBoard.get(y).size(); x++) {
                System.out.print(newBoard.get(y).get(x));
            }
            System.out.println("");
        }*/

        this.board = newBoard;


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
        else if (colx >= columcount && rowy >= rowcount ){
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



    public void setBoard(List<List<Byte>> board) {this.board = board;}

    public List<List<Byte>> getBoard() {
        return board;
    }

    // Draw/undraw a cell depending on its state when we click on the board
    public void mouseclickedordraggedonBoard(double x, double y){
        int colx = (int)(x/cellSize);
        int rowy = (int)(y/cellSize);
        //System.out.println(rowy);
        //System.out.println(colx);
        if ((colx >= columcount) || (rowy >= rowcount)){
            setCellState(rowy, colx, (byte)1);}
        else if (this.board.get(rowy).get(colx) == 0){
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
