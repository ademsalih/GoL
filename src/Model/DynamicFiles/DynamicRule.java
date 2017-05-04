package Model.DynamicFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import Model.Abstract.Rule;
import javafx.concurrent.Task;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class DynamicRule extends Rule {

    ////INSTANCE VARIABLES
    public List<List<Byte>> currentBoard;
    public List<List<Byte>> conwaysBoard;
    public List<List<Byte>> boardOfActiveCells;

    Thread thread1;
    Thread thread2;
    Thread thread3;
    Thread thread4;
    Thread thread5;
    Thread thread6;
    Thread thread7;
    Thread thread8;

    private int rowCount;
    private int colCount;
    private int expandNum;
    private int xOffSet;
    private int yOffSet;

    private boolean expandedDown, expandedRight, expandedLeft, expandedUp;

    ////CONSTRUCTOR
    // MÅ DEKLARERE ALLE BOARDDENE MED NULL HER!
    public DynamicRule () {
        this.currentBoard = new ArrayList<>();
        expandNum = 50;

        expandedDown = false;
        expandedUp = false;

        xOffSet = 0;
        yOffSet = 0;
    }


    //bare for å sjekke push

    ////CLASS METHODS

    public List<List<Byte>> getCurrentBoard() {
        return currentBoard;
    }

    public void setCurrentBoard(List<List<Byte>> board) {
        this.currentBoard = board;
        this.rowCount = currentBoard.size() - 1;
        this.colCount = currentBoard.get(0).size() - 1;
    }

    //Retunerer next generation verdier
    @Override
    public String toString(){
        String output = "";

        for (int row = 0; row < currentBoard.size(); row++) {
            for (int col = 0; col < currentBoard.get(0).size(); col++){
                output = output + currentBoard.get(row).get(col);
            }
        }
        return output;
    }

    ////CONWAYS GAME OF LIFE RULES

    private boolean isExpandedAllWays() {
        if (expandedDown && expandedRight) {
            return true;
        }
        return false;
    }

    public void initiateBoard() {
        conwaysBoard = new ArrayList<List<Byte>>();
        for (int i = 0; i < currentBoard.size() ; i++) {
            List<Byte> row = new ArrayList<Byte>();
            for (int j = 0; j < currentBoard.get(0).size(); j++) {
                row.add((byte) 0);
            }
            this.conwaysBoard.add(row);
        }
    }

    /*
    public List<List<Byte>> conwaysBoardRules() {
        expandedDown = false;
        expandedRight = false;

        long start = System.currentTimeMillis();

        conwaysBoard = new ArrayList<List<Byte>>();

        for (int y = 0; y < currentBoard.size(); y++) {
            List<Byte> row = new ArrayList<Byte>();
            for (int x = 0; x < currentBoard.get(0).size(); x++) {

                if (boardOfActiveCells.get(y).get(x) == 1) {

                    int cellState = currentBoard.get(y).get(x);
                    if (cellState == 1 && !isExpandedAllWays()) {
                        expandBoardIfNeeded(y, x);
                    }
                    conwaysBoard.get(y).set(x,checkIfOnOrOff(countNeighbor( y, x), cellState));
                }
            }
        }
        long stop = System.currentTimeMillis();

        System.out.println("TID" + (stop - start));
        return conwaysBoard;
    }
    */


    private int start;
    private int sectorSize;
    private int sector2;
    private int sector3;
    private int sector4;
    private int sector5;
    private int sector6;
    private int sector7;
    private int sector8;

    private int length;


    public List<List<Byte>> applyBoardRules() {
        expandedDown = false;
        expandedRight = false;

        conwaysBoard = new ArrayList<List<Byte>>();
        for (int i = 0; i < currentBoard.size() ; i++) {
            List<Byte> row = new ArrayList<Byte>();
            for (int j = 0; j < currentBoard.get(0).size(); j++) {
                row.add((byte) 0);
            }
            this.conwaysBoard.add(row);
        }

        start = 0;
        length = conwaysBoard.size();
        sectorSize = length/8;
        sector2 = sectorSize;
        sector3 = sectorSize*2;
        sector4 = sectorSize*3;
        sector5 = sectorSize*4;
        sector6 = sectorSize*5;
        sector7 = sectorSize*6;
        sector8 = sectorSize*7;

        thread1 = new Thread(this::sector1);
        thread2 = new Thread(this::sector2);
        thread3 = new Thread(this::sector3);
        thread4 = new Thread(this::sector4);
        /*thread5 = new Thread(this::sector5);
        thread6 = new Thread(this::sector6);
        thread7 = new Thread(this::sector7);
        thread8 = new Thread(this::sector8);*/

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        /*thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();*/

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            /*thread5.join();
            thread6.join();
            thread7.join();
            thread8.join();*/
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        printAliveCells();
        return conwaysBoard;
    }



    public void printAliveCells(){
        int n=0;
        for (int i = 0; i < currentBoard.size() ; i++) {
            for (int j = 0; j < currentBoard.get(0).size(); j++) {
                if (conwaysBoard.get(i).get(j) == 1) n++;
            }
        }
        System.out.println("ALIVE CELLS: " + n);
    }

    public void sector1() {
        for (int y = start; y < sector2; y++) {
            for (int x = 0; x < currentBoard.get(0).size(); x++) {

                if (boardOfActiveCells.get(y).get(x) == 1) {
                    int cellState = currentBoard.get(y).get(x);
                    if (cellState == 1 && !isExpandedAllWays()) {
                        expandBoardIfNeeded(y, x);
                    }
                    conwaysBoard.get(y).set(x,checkIfOnOrOff(countNeighbor( y, x), cellState));
                }
            }
        }
    }

    public void sector2() {

        for (int y = sector2; y < sector3; y++) {
            for (int x = 0; x < currentBoard.get(0).size(); x++) {

                if (boardOfActiveCells.get(y).get(x) == 1) {
                    int cellState = currentBoard.get(y).get(x);
                    if (cellState == 1 && !isExpandedAllWays()) {
                        expandBoardIfNeeded(y, x);
                    }
                    conwaysBoard.get(y).set(x,checkIfOnOrOff(countNeighbor( y, x), cellState));
                }
            }
        }

    }

    public void sector3() {

        for (int y = sector3; y < sector4; y++) {
            for (int x = 0; x < currentBoard.get(0).size(); x++) {

                if (boardOfActiveCells.get(y).get(x) == 1) {
                    int cellState = currentBoard.get(y).get(x);
                    if (cellState == 1 && !isExpandedAllWays()) {
                        expandBoardIfNeeded(y, x);
                    }
                    conwaysBoard.get(y).set(x,checkIfOnOrOff(countNeighbor( y, x), cellState));
                }
            }
        }

    }

    public void sector4() {

        for (int y = sector4; y < length; y++) {
            for (int x = 0; x < currentBoard.get(0).size(); x++) {

                if (boardOfActiveCells.get(y).get(x) == 1) {
                    int cellState = currentBoard.get(y).get(x);
                    if (cellState == 1 && !isExpandedAllWays()) {
                        expandBoardIfNeeded(y, x);
                    }
                    conwaysBoard.get(y).set(x,checkIfOnOrOff(countNeighbor( y, x), cellState));
                }
            }
        }

    }

    public void sector5() {

        for (int y = sector5; y < length; y++) {
            for (int x = 0; x < currentBoard.get(0).size(); x++) {

                if (boardOfActiveCells.get(y).get(x) == 1) {
                    int cellState = currentBoard.get(y).get(x);
                    if (cellState == 1 && !isExpandedAllWays()) {
                        expandBoardIfNeeded(y, x);
                    }
                    conwaysBoard.get(y).set(x,checkIfOnOrOff(countNeighbor( y, x), cellState));
                }
            }
        }

    }

    public void sector6() {

        for (int y = sector6; y < length; y++) {
            for (int x = 0; x < currentBoard.get(0).size(); x++) {

                if (boardOfActiveCells.get(y).get(x) == 1) {
                    int cellState = currentBoard.get(y).get(x);
                    if (cellState == 1 && !isExpandedAllWays()) {
                        expandBoardIfNeeded(y, x);
                    }
                    conwaysBoard.get(y).set(x,checkIfOnOrOff(countNeighbor( y, x), cellState));
                }
            }
        }

    }

    public void sector7() {

        for (int y = sector7; y < length; y++) {
            for (int x = 0; x < currentBoard.get(0).size(); x++) {

                if (boardOfActiveCells.get(y).get(x) == 1) {
                    int cellState = currentBoard.get(y).get(x);
                    if (cellState == 1 && !isExpandedAllWays()) {
                        expandBoardIfNeeded(y, x);
                    }
                    conwaysBoard.get(y).set(x,checkIfOnOrOff(countNeighbor( y, x), cellState));
                }
            }
        }

    }

    public void sector8() {

        for (int y = sector8; y < length; y++) {
            for (int x = 0; x < currentBoard.get(0).size(); x++) {

                if (boardOfActiveCells.get(y).get(x) == 1) {
                    int cellState = currentBoard.get(y).get(x);
                    if (cellState == 1 && !isExpandedAllWays()) {
                        expandBoardIfNeeded(y, x);
                    }
                    conwaysBoard.get(y).set(x,checkIfOnOrOff(countNeighbor( y, x), cellState));
                }
            }
        }

    }







    public boolean neighborOver(int y, int x) {

        if (y - 1 != - 1) {

            if (currentBoard.get(y-1).get(x) == 1) {
                return true;
            }

        }
        return false;

    }


    public boolean neighborUnder(int y, int x) {

        if (y + 1 < currentBoard.size()) {

            if (currentBoard.get(y+1).get(x) == 1) {
                return true;
            }
        }

        return false;
    }

    public boolean neighborLeft(int y, int x) {

        if (x - 1 != - 1) {

            if (currentBoard.get(y).get(x-1) == 1) {
                return true;
            }
        }

        return false;
    }

    public boolean neighborRight(int y, int x) {

        if (x + 1 < currentBoard.get(0).size()) {

            if (currentBoard.get(y).get(x+1) == 1) {
                return true;
            }
        }

        return false;
    }

    public boolean neighborTopLeft(int y, int x) {

        if ((y - 1 != - 1) && (x - 1 != - 1)) {

            if (currentBoard.get(y-1).get(x-1) == 1) {
                return true;
            }
        }

        return false;
    }

    public boolean neighborTopRight(int y, int x) {

        if ((y - 1 != - 1) && (x + 1 < currentBoard.get(0).size())) {

            if (currentBoard.get(y-1).get(x+1) == 1) {
                return true;
            }
        }

        return false;
    }

    public boolean neighborBottomLeft(int y, int x) {

        if ((y + 1 < currentBoard.size()) && (x - 1 != - 1)) {

            if (currentBoard.get(y+1).get(x-1) == 1) {
                return true;
            }
        }

        return false;
    }

    public boolean neighborBottomRight(int y, int x) {

        if ((y + 1 < currentBoard.size()) && (x + 1 < currentBoard.get(0).size())) {

            if (currentBoard.get(y+1).get(x+1) == 1) {
                return true;
            }
        }

        return false;
    }

    ////////////////////////////////////////////////////////////////

    // Calculates the cells that are active. Active cell is either alive itself or has at least one neighbor.
    public void calculateBoardOfActiveCells() {
        boardOfActiveCells = new ArrayList<List<Byte>>();
        for (int i = 0; i < currentBoard.size() ; i++) {
            List<Byte> row = new ArrayList<Byte>();
            for (int j = 0; j < currentBoard.get(0).size(); j++) {
                row.add((byte) 0);
            }
            this.boardOfActiveCells.add(row);
        }

        for (int y = 0; y < boardOfActiveCells.size(); y++) {
            for (int x = 0; x < boardOfActiveCells.get(0).size(); x++) {

                if (currentBoard.get(y).get(x) == 1) {
                    boardOfActiveCells.get(y).set(x, (byte) 1);
                    markTopLeft(y,x);
                    markTop(y,x);
                    markTopRight(y,x);
                    markLeft(y,x);
                    markRight(y,x);
                    markBottomLeft(y,x);
                    markBottom(y,x);
                    markBottomRight(y,x);
                }
            }
        }
    }

    public void markTopLeft(int y, int x) {

        if ((y - 1 != - 1) && (x - 1 != - 1) && currentBoard.get(y-1).get(x-1) == 0) {
            boardOfActiveCells.get(y-1).set(x-1,(byte) 1);
        }

    }

    public void markTop(int y, int x) {

        if (y - 1 != - 1 && currentBoard.get(y-1).get(x) == 0) {
            boardOfActiveCells.get(y-1).set(x,(byte) 1);
        }
    }

    public void markTopRight(int y, int x) {

        if ((y - 1 != - 1) && (x + 1 < currentBoard.get(0).size()) && (currentBoard.get(y-1).get(x+1) == 0) ) {
            boardOfActiveCells.get(y-1).set(x+1, (byte) 1);
        }
    }

    public void markLeft(int y, int x) {

        if (x - 1 != - 1 && (currentBoard.get(y).get(x-1) == 0)) {
            boardOfActiveCells.get(y).set(x-1,(byte) 1);
        }
    }

    public void markRight(int y, int x) {

        if (x + 1 < currentBoard.get(0).size() && currentBoard.get(y).get(x+1) == 0) {
            boardOfActiveCells.get(y).set(x+1, (byte) 1);
        }
    }

    public void markBottomLeft(int y, int x) {

        if ((y + 1 < currentBoard.size()) && (x - 1 != - 1) && (currentBoard.get(y+1).get(x-1) == 0) ) {
            boardOfActiveCells.get(y+1).set(x-1, (byte) 1);
        }

    }

    public void markBottom(int y, int x) {

        if (y + 1 < currentBoard.size() && currentBoard.get(y+1).get(x) == 0) {
            boardOfActiveCells.get(y+1).set(x,(byte) 1);
        }
    }

    public void markBottomRight(int y, int x) {

        if ((y + 1 < currentBoard.size()) && (x + 1 < currentBoard.get(0).size()) && (currentBoard.get(y+1).get(x+1) == 0) ) {
            boardOfActiveCells.get(y+1).set(x+1, (byte) 1);
        }
    }
    ////////////////////////////////////////////////////////////////

    public void addCols(int numberOfCols) {
        for (int i = 0; i < numberOfCols; i++) {
            for (List<Byte> byteLists : currentBoard) {
                byteLists.add((byte) 0);
            }
        }

        for (int i = 0; i < numberOfCols; i++) {
            for (List<Byte> byteLists : conwaysBoard) {
                byteLists.add((byte) 0);
            }
        }

        for (int i = 0; i < numberOfCols; i++) {
            for (List<Byte> byteLists : boardOfActiveCells) {
                byteLists.add((byte) 0);
            }
        }
    }

    public void addRows(int numberOfRows) {
        for (int i = 0; i < numberOfRows; i++) {
            List<Byte> row = new ArrayList<Byte>();
            for (int x = 0; x < currentBoard.get(0).size(); x++) {
                row.add((byte) 0 );
            }
            currentBoard.add(row);
        }

        for (int i = 0; i < numberOfRows; i++) {
            List<Byte> row = new ArrayList<Byte>();
            for (int x = 0; x < conwaysBoard.get(0).size(); x++) {
                row.add((byte) 0 );
            }
            conwaysBoard.add(row);
        }

        for (int i = 0; i < numberOfRows; i++) {
            List<Byte> row = new ArrayList<Byte>();
            for (int x = 0; x < boardOfActiveCells.get(0).size(); x++) {
                row.add((byte) 0 );
            }
            boardOfActiveCells.add(row);
        }


    }

    // Method works but is not in place when first thing hits the curbs.
    public void expandBoardIfNeeded(int y, int x) {

        if (y >= (rowCount - 1) || x >= (colCount - 1)) {
            if ((y == (rowCount)) && (x != (colCount)) && !expandedDown) {
                addRows(expandNum);
                expandedDown = true;
            }
            else if ((x == (colCount)) && (y != (rowCount)) && !expandedRight) {
                addCols(expandNum);
                expandedRight = true;
            }
            else if ((x == (colCount)) && (y == (rowCount)) && !expandedDown && !expandedRight) {
                addCols(expandNum);
                addRows(expandNum);
                expandedRight = true;
                expandedDown = true;
            }
        }

    }
}
