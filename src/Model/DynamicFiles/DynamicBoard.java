package Model.DynamicFiles;

import Model.Abstract.Board;
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
    int margin;

    int generationCounter;

    public DynamicBoard (Canvas canvas, int x, int y) {
        this.canvasWidth = canvas.getWidth();
        this.canvasHeight = canvas.getHeight();
        this.gc = canvas.getGraphicsContext2D();
        this.rowcount = y;
        this.columcount = x;
        this.margin  = 10;

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

    private void addCols(int numberOfCols) {
        for (int i = 0; i < numberOfCols; i++) {
            for (List<Byte> byteLists : board) {
                byteLists.add((byte) 0);
            }
        }
    }

    private void addRows(int numberOfRows) {
        for (int i = 0; i < numberOfRows; i++) {
            List<Byte> row = new ArrayList<Byte>();
            for (int x = 0; x < board.get(0).size(); x++) {
                row.add((byte) 0 );
            }
            board.add(row);
        }
    }

    private void expandBoardIfNeeded(int rowy, int colx) {
        int x = board.get(0).size();
        int y = board.size();

        if ((x - colx) < margin && (y - rowy) > margin) {
            addCols(margin);
        }

        else if ((x - colx) > margin && (y - rowy) < margin) {
            System.out.println("ExpandY");
            addRows(margin);
        }

        else if ((x - colx) < margin && (y - rowy) < margin) {
            addCols(margin);
            addRows(margin);
        }

        if (x < colx && y > rowy) {
            addCols(colx - x + margin);
        }

        else if (x > colx && y < rowy) {
            addRows(rowy - y + margin);
        }

        else if (x < colx && y < rowy) {
            addCols(colx - x + margin);
            addRows(rowy - y + margin);
        }
    }

    public void setCellState(int rowy, int colx, byte value) {
        //Hvis cellen (x,y) er definert utenfor brettet, skal brettet
        //automatisk utvides slik at brettet inneholder cellen (x,y).

        // Checks if cell is outside the margin of the board. Updates the board if needed.
        expandBoardIfNeeded(rowy, colx);

        this.board.get(rowy).set(colx, value);
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

        //System.out.println(rowy + "   " + colx);

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
