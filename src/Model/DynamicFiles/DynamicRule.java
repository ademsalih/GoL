package Model.DynamicFiles;

import java.util.ArrayList;
import java.util.List;
import Model.Abstract.Rule;

public class DynamicRule extends Rule {

    ////INSTANCE VARIABLES
    public List<List<Byte>> currentBoard;
    public List<List<Byte>> conwaysBoard;
    public List<List<Byte>> boardOfActiveCells;
    public byte[][] neighbors;
    public byte[][] newNeighbors;

    private int rowCount;
    private int colCount;
    private int expandNum;
    private int shiftNum;
    private int xOffSet;
    private int yOffSet;
    private int test;

    private boolean expandedDown, expandedRight, expandedLeft, expandedUp;
    private boolean boardHasBeenInit;
    private boolean init;
    private boolean notInit;

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

    private byte[][] updateNeighbors(boolean on, byte[][] n, int x, int y) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (on) {
                    if (!outOffBounds(y, x, i, j, n)) {
                        n[y+i][x+j]++;
                    }
                }
            }
        }
        return n;
    }

    private boolean outOffBounds(int y, int x, int i, int j, byte[][] arr) {
        if ((y + i) < 0 || (y + i) > arr.length) {
            return true;
        }
        else if ((x + j) < 0 || (x + j) > arr[0].length) {
            return true;
        }
        else {
            return false;
        }

    }

    public List<List<Byte>> applyBoardRules() {
        expandedDown = false;
        expandedRight = false;
        expandedLeft = false;
        expandedUp = false;

        newNeighbors = new byte[currentBoard.size()][currentBoard.get(0).size()];

        long orgTime = System.currentTimeMillis();
        if (!init) {
            conwaysBoard = initBoard();
            init = true;
        }
        System.out.println("Creating board: " + (System.currentTimeMillis() - orgTime));

        if (notInit) {
            for (int y = 0; y < currentBoard.size(); y++) {
                for (int x = 0; x < currentBoard.get(0).size(); x++) {

                    int cellState = currentBoard.get(y).get(x);

                    if (cellState == 1 && !isExpandedAllWays()) {
                        expandBoardIfNeeded(y, x);
                    }
                    /*if (boardOfActiveCells.get(y).get(x) == 1) { */
                    byte onOrOff = checkIfOnOrOff(countNeighbor(y, x), cellState);
                    conwaysBoard.get(y).set(x, onOrOff);
                    if (onOrOff == 1) {
                        newNeighbors = updateNeighbors(true, newNeighbors, x, y);
                    }


                }

            }
            notInit = false;
        }
        else {
            for (int y = 0; y < currentBoard.size(); y++) {
                for (int x = 0; x < currentBoard.get(0).size(); x++) {

                    int cellState = currentBoard.get(y).get(x);

                    if (cellState == 1 && !isExpandedAllWays()) {
                        expandBoardIfNeeded(y, x);
                    }
                    /*if (boardOfActiveCells.get(y).get(x) == 1) {*/
                    byte onOrOff = checkIfOnOrOff(neighbors[y][x], cellState);
                    conwaysBoard.get(y).set(x, onOrOff);
                    if (onOrOff == 1) {
                        newNeighbors = updateNeighbors(true, newNeighbors, x, y);
                    }



                }

            }
        }

        for (int y = 0; y < newNeighbors.length; y++) {
            for (int x = 0; x < newNeighbors[y].length; x++) {
                System.out.print(newNeighbors[y][x]);
            }
            System.out.println("");
        }

        neighbors = newNeighbors;
        return conwaysBoard;
    }

    protected boolean neighborOver(int y, int x) {

        if (y - 1 != - 1) {

            if (currentBoard.get(y-1).get(x) == 1) {
                return true;
            }

        }
        return false;

    }

    protected boolean neighborUnder(int y, int x) {

        if (y + 1 < currentBoard.size()) {

            if (currentBoard.get(y+1).get(x) == 1) {
                return true;
            }
        }

        return false;
    }

    protected boolean neighborLeft(int y, int x) {

        if (x - 1 != - 1) {

            if (currentBoard.get(y).get(x-1) == 1) {
                return true;
            }
        }

        return false;
    }

    protected boolean neighborRight(int y, int x) {

        if (x + 1 < currentBoard.get(0).size()) {

            if (currentBoard.get(y).get(x+1) == 1) {
                return true;
            }
        }

        return false;
    }

    protected boolean neighborTopLeft(int y, int x) {

        if ((y - 1 != - 1) && (x - 1 != - 1)) {

            if (currentBoard.get(y-1).get(x-1) == 1) {
                return true;
            }
        }

        return false;
    }

    protected boolean neighborTopRight(int y, int x) {

        if ((y - 1 != - 1) && (x + 1 < currentBoard.get(0).size())) {

            if (currentBoard.get(y-1).get(x+1) == 1) {
                return true;
            }
        }

        return false;
    }

    protected boolean neighborBottomLeft(int y, int x) {

        if ((y + 1 < currentBoard.size()) && (x - 1 != - 1)) {

            if (currentBoard.get(y+1).get(x-1) == 1) {
                return true;
            }
        }

        return false;
    }

    protected boolean neighborBottomRight(int y, int x) {

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


    protected void expandBoardIfNeeded(int y, int x) {

        // Checks if a cell is close to the edge
        if ((y >= (rowCount - 1) || x >= (colCount - 1))) {

            // All if conditions checks if cell is at the edge
            // and if an expansion of the board has allready been triggered this gen

            if ((y == (rowCount)) && (x != (colCount)) && !expandedDown) {
                addRowsToAllBoards();
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
            }
            else if ((y == 0) && (x != 0) && !expandedUp) {
                shiftAllDown();
            }
            else if ((y == 0) && (x == 0) && !expandedUp && !expandedLeft){
                shiftAllRight();
                shiftAllDown();
            }
        }

    }

    private void shiftAllRight() {
        addColsToAllBoards();
        xOffSet += expandNum;
        shiftArrayRight();
        currentBoard = shiftBoardRight(currentBoard, shiftNum);
        conwaysBoard = shiftBoardRight(conwaysBoard, shiftNum);
       // boardOfActiveCells = shiftBoardRight(boardOfActiveCells, shiftNum);
        expandedLeft = true;
    }

    private void shiftAllDown() {
        addRowsToAllBoards();
        yOffSet += expandNum;
        shiftArrayDown();
        currentBoard = shifBoardDown(currentBoard, shiftNum);
        conwaysBoard = shifBoardDown(conwaysBoard, shiftNum);
       // boardOfActiveCells = shifBoardDown(boardOfActiveCells, shiftNum);
        expandedUp = true;

    }

    private void addColsToAllBoards() {
        currentBoard = addCols(currentBoard, expandNum);
        conwaysBoard = addCols(conwaysBoard, expandNum);
       // boardOfActiveCells = addCols(boardOfActiveCells, expandNum);
    }

    private void addRowsToAllBoards() {
        currentBoard = addRows(currentBoard, expandNum);
        conwaysBoard = addRows(conwaysBoard, expandNum);
        //boardOfActiveCells = addRows(boardOfActiveCells, expandNum);
    }

    private void shiftArrayDown() {
        byte[][] temp = new byte[neighbors.length + expandNum][neighbors[0].length];

        for (int i = 0; i < neighbors.length + expandNum; i++) {
            for (int j = 0; j < neighbors[i].length; j++) {
                temp[i+expandNum][j] = neighbors[i][j];
            }
        }

        neighbors = temp;
    }

    private void shiftArrayRight() {
        byte[][] temp = new byte[neighbors.length][neighbors[0].length + expandNum];

        for (int i = 0; i < neighbors.length + expandNum; i++) {
            for (int j = 0; j < neighbors[i].length; j++) {
                temp[i][j + expandNum] = neighbors[i][j];
            }
        }

        neighbors = temp;
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
