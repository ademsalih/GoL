package Model.DynamicFiles;

import java.util.ArrayList;
import java.util.List;

import Model.Abstract.Rule;

public class DynamicRule extends Rule {

    ////INSTANCE VARIABLES
    public List<List<Byte>> currentBoard;
    private List<List<Byte>> conwaysBoard;
    private List<List<Byte>> boardOfActiveCells;

    private int rowCount;
    private int colCount;
    private int expandNum;
    private int shiftNum;

    private boolean expandDown, expandRight, needsRightShift, needsDownShift;
    //private boolean boardHasBeenInit;
    //private boolean init;

    public DynamicRule () {
        this.currentBoard = new ArrayList<>();
        expandNum = 50;
        shiftNum = 10;

    }

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
        StringBuilder output = new StringBuilder();

        for (List<Byte> aCurrentBoard : currentBoard)
            for (int col = 0; col < currentBoard.get(0).size(); col++) {
                output.append(aCurrentBoard.get(col));
            }
        return output.toString();
    }

    ////CONWAYS GAME OF LIFE RULES

    private boolean isExpandedAllWays() {
        return expandDown && expandRight && needsRightShift && needsDownShift;
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

    private int start;
    private int sector2;
    private int sector3;
    private int sector4;

    private int length;


    public List<List<Byte>> applyBoardRules() {


        conwaysBoard = initBoard();

        if (needsDownShift && needsRightShift) {
            shiftAllRight();
            shiftAllDown();
        }
        else if (needsDownShift) {
            shiftAllDown();
        }
        else if (needsRightShift) {
            shiftAllRight();
        }

        if (expandRight && expandDown) {
            addColsToAllBoards();
            addRowsToAllBoards();
        }
        else if (expandDown) {
            addRowsToAllBoards();
        }
        else if(expandRight) {
            addColsToAllBoards();
        }

        setBooleansFalse();


        start = 0;
        length = conwaysBoard.size();
        int sectorSize = length / 8;
        sector2 = sectorSize;
        sector3 = sectorSize *2;
        sector4 = sectorSize *3;

        Thread thread1 = new Thread(this::sector1);
        Thread thread2 = new Thread(this::sector2);
        Thread thread3 = new Thread(this::sector3);
        Thread thread4 = new Thread(this::sector4);

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

    private void setBooleansFalse() {
        expandDown = false;
        expandRight = false;
        needsDownShift = false;
        needsRightShift = false;

    }


    private void sector1() {
        for (int y = start; y < sector2; y++) {
            for (int x = 0; x < currentBoard.get(0).size(); x++) {

                if (boardOfActiveCells.get(y).get(x) == 1) {
                    int cellState = currentBoard.get(y).get(x);
                    if (cellState == 1 && !isExpandedAllWays()) {
                        checkIfShiftIsNeeded(y, x);
                        checkIfExpansionIsNedded(y, x);
                    }
                    conwaysBoard.get(y).set(x,checkIfOnOrOff(countNeighbor( y, x), cellState));
                }
            }
        }
    }

    private void sector2() {

        for (int y = sector2; y < sector3; y++) {
            for (int x = 0; x < currentBoard.get(0).size(); x++) {

                if (boardOfActiveCells.get(y).get(x) == 1) {
                    int cellState = currentBoard.get(y).get(x);
                    if (cellState == 1 && !isExpandedAllWays()) {
                        checkIfShiftIsNeeded(y, x);
                        checkIfExpansionIsNedded(y, x);

                    }
                    conwaysBoard.get(y).set(x,checkIfOnOrOff(countNeighbor( y, x), cellState));
                }
            }
        }

    }

    private void sector3() {

        for (int y = sector3; y < sector4; y++) {
            for (int x = 0; x < currentBoard.get(0).size(); x++) {

                if (boardOfActiveCells.get(y).get(x) == 1) {
                    int cellState = currentBoard.get(y).get(x);
                    if (cellState == 1 && !isExpandedAllWays()) {
                        checkIfShiftIsNeeded(y, x);
                        checkIfExpansionIsNedded(y, x);
                    }
                    conwaysBoard.get(y).set(x,checkIfOnOrOff(countNeighbor( y, x), cellState));
                }
            }
        }

    }

    private void sector4() {

        for (int y = sector4; y < length; y++) {
            for (int x = 0; x < currentBoard.get(0).size(); x++) {

                if (boardOfActiveCells.get(y).get(x) == 1) {
                    int cellState = currentBoard.get(y).get(x);
                    if (cellState == 1 && !isExpandedAllWays()) {
                        checkIfShiftIsNeeded(y, x);
                        checkIfExpansionIsNedded(y, x);
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


    private List<List<Byte>> addCols (List<List<Byte>> board, int numberOfCols) {
        for (int i = 0; i < numberOfCols; i++) {
            for (List<Byte> byteLists : board) {
                byteLists.add((byte) 0);
            }
        }
        return board;
    }

    private List<List<Byte>> addRows(List<List<Byte>> board, int numberOfRows) {
        for (int i = 0; i < numberOfRows; i++) {
            List<Byte> row = new ArrayList<>();
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


    protected void checkIfExpansionIsNedded(int y, int x) {

    // Checks if a cell is close to the edge
        if (y >= (rowCount - 1) || x >= (colCount - 1)) {
            if ((y == (rowCount)) && (x != (colCount))) {
                expandDown = true;
            } else if ((x == (colCount)) && (y != (rowCount))) {
                expandRight = true;
            } else if ((x == (colCount)) && (y == (rowCount))) {
                expandRight = true;
                expandDown = true;
            }
        }
    }

    private void shiftAllRight() {
        addColsToAllBoards();
        currentBoard = shiftBoardRight(currentBoard, shiftNum);
        conwaysBoard = shiftBoardRight(conwaysBoard, shiftNum);
        boardOfActiveCells = shiftBoardRight(boardOfActiveCells, shiftNum);
    }

    private void shiftAllDown() {
        addRowsToAllBoards();
        currentBoard = shifBoardDown(currentBoard, shiftNum);
        conwaysBoard = shifBoardDown(conwaysBoard, shiftNum);
        boardOfActiveCells = shifBoardDown(boardOfActiveCells, shiftNum);
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
