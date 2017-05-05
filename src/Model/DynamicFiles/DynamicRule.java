package Model.DynamicFiles;

import java.util.ArrayList;
import java.util.List;

import Model.Abstract.Rule;

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
    private int shiftNum;
    private int xOffSet;
    private int yOffSet;
    private int test;

    private boolean expandedDown, expandedRight, expandedLeft, expandedUp, needsRightShift, needsDownShift;
    private boolean boardHasBeenInit;
    private boolean init;
    private boolean notInit;

    ////CONSTRUCTOR
    // MÅ DEKLARERE ALLE BOARDDENE MED NULL HER!
    public DynamicRule () {
        this.currentBoard = new ArrayList<>();
        expandNum = 50;
        shiftNum = 10;

        expandedDown = false;
        expandedUp = false;

        xOffSet = 0;
        yOffSet = 0;

        notInit = true;
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
        if (expandedDown && expandedRight && expandedLeft && expandedUp) {
            return true;
        }
        return false;
    }

    private List<List<Byte>> initBoard() {
        List<List<Byte>> newBoard = new ArrayList<>();
        for (int i = 0; i < currentBoard.size() ; i++) {
            List<Byte> row = new ArrayList<>();
            for (int j = 0; j < currentBoard.get(0).size(); j++) {
                row.add((byte) 0);
            }
            newBoard.add(row);
        }
        return newBoard;
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
        expandedLeft = false;
        expandedUp = false;

        conwaysBoard = initBoard();

        if (needsDownShift && needsRightShift) {
            shiftAllRight();
            shiftAllDown();
            needsRightShift = false;
            needsDownShift = false;
        }
        else if (needsDownShift) {
            shiftAllDown();
            needsDownShift = false;
        }
        else if (needsRightShift) {
            shiftAllRight();
            needsRightShift = false;
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

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return conwaysBoard;
    }


    public void sector1() {
        for (int y = start; y < sector2; y++) {
            for (int x = 0; x < currentBoard.get(0).size(); x++) {

                if (boardOfActiveCells.get(y).get(x) == 1) {
                    int cellState = currentBoard.get(y).get(x);
                    if (cellState == 1) {
                        checkIfShiftIsNeeded(y, x);
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
                    if (cellState == 1) {
                        checkIfShiftIsNeeded(y, x);
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
                    if (cellState == 1) {
                        checkIfShiftIsNeeded(y, x);
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
        boardOfActiveCells = initBoard();

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

    public List<List<Byte>> addCols (List<List<Byte>> board, int numberOfCols) {
        for (int i = 0; i < numberOfCols; i++) {
            for (List<Byte> byteLists : board) {
                byteLists.add((byte) 0);
            }
        }
        return board;
    }

    public List<List<Byte>> addRows(List<List<Byte>> board, int numberOfRows) {
        for (int i = 0; i < numberOfRows; i++) {
            List<Byte> row = new ArrayList<Byte>();
            for (int x = 0; x < board.get(0).size(); x++) {
                row.add((byte) 0 );
            }
            board.add(row);

        }
        return board;
    }


    private void checkIfShiftIsNeeded(int y, int x) {
        if ((y <= 2)) {
            needsDownShift = true;
        }
        if ((x <= 2)) {
            needsRightShift = true;
        }
    }

    protected void expandBoardIfNeeded(int y, int x) {

        // Checks if a cell is close to the edge
         {

            // All if conditions checks if cell is at the edge
            // and if an expansion of the board has allready been triggered this gen

            if (y >= (rowCount - 1) || x >= (colCount - 1)) {
                if ((y == (rowCount)) && (x != (colCount)) && !expandedDown) {
                    expandedDown = true;
                } else if ((x == (colCount)) && (y != (rowCount)) && !expandedRight) {
                    addColsToAllBoards();
                    expandedRight = true;
                } else if ((x == (colCount)) && (y == (rowCount)) && !expandedDown && !expandedRight) {
                    addColsToAllBoards();
                    addRowsToAllBoards();
                    expandedRight = true;
                    expandedDown = true;
                }
            }
            if ((y <= 1) || (x <= 1)) {
                if ((x == 0) && (y != 0) && !expandedLeft) {
                    shiftAllRight();
                } else if ((y == 0) && (x != 0) && !expandedUp) {
                    shiftAllDown();
                } else if ((y == 0) && (x == 0) && !expandedUp && !expandedLeft) {
                    shiftAllRight();
                    shiftAllDown();
                }
            }
        }
    }

    private void shiftAllRight() {
        addColsToAllBoards();
        xOffSet += expandNum;
        currentBoard = shiftBoardRight(currentBoard, shiftNum);
        conwaysBoard = shiftBoardRight(conwaysBoard, shiftNum);
        boardOfActiveCells = shiftBoardRight(boardOfActiveCells, shiftNum);
        expandedLeft = true;
    }

    private void shiftAllDown() {
        addRowsToAllBoards();
        yOffSet += expandNum;
        currentBoard = shifBoardDown(currentBoard, shiftNum);
        conwaysBoard = shifBoardDown(conwaysBoard, shiftNum);
        boardOfActiveCells = shifBoardDown(boardOfActiveCells, shiftNum);
        expandedUp = true;

    }

    private void addColsToAllBoards() {
        currentBoard = addCols(currentBoard, expandNum);
        conwaysBoard = addCols(conwaysBoard, expandNum);
        boardOfActiveCells = addCols(boardOfActiveCells, expandNum);
    }

    private void addRowsToAllBoards() {
        currentBoard = addRows(currentBoard, expandNum);
        conwaysBoard = addRows(conwaysBoard, expandNum);
        boardOfActiveCells = addRows(boardOfActiveCells, expandNum);
    }



    private List<List<Byte>> shiftBoardRight(List<List<Byte>> board, int shiftNum) {
        List<List<Byte>> newBoard = new ArrayList<>();

        for (int y = 0; y < board.size(); y++) {
            List<Byte> temp = new ArrayList<>();
            newBoard.add(temp);
        }

        newBoard = addCols(newBoard, shiftNum + board.get(0).size());

        System.out.println("shiftNum = " + shiftNum);
        System.out.println("newBoard size: y = " + newBoard.size() + " x = " + newBoard.get(0).size());
        System.out.println("board size: y = " + board.size() + " x = " + board.get(0).size());

        for (int y = 0; y < board.size(); y++) {
            for (int x = 0; x < board.get(y).size(); x++) {
                newBoard.get(y).set(x + shiftNum, (board.get(y).get(x)));
            }
        }

        return newBoard;
    }

    private List<List<Byte>> shifBoardDown(List<List<Byte>> board, int shiftNum) {
        List<List<Byte>> newBoard = new ArrayList<>();

        for (int y = 0; y < board.size() + shiftNum; y++) {
            List<Byte> temp = new ArrayList<>();
            newBoard.add(temp);
        }

        newBoard = addCols(newBoard, board.get(0).size());

        for (int y = 0; y < board.size(); y++) {
            for(int x = 0; x < board.get(y).size(); x++) {
                newBoard.get(y + shiftNum).set(x, (board.get(y).get(x)));
            }
        }

        return newBoard;
    }



}
