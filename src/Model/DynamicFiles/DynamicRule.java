package Model.DynamicFiles;

import java.util.ArrayList;
import java.util.List;

import Model.Abstract.Rule;

/**
 * DynamicRule performs rules (default:Conway's rules) on the dynamic gameboard.
 */

public class DynamicRule extends Rule {


    public List<List<Byte>> currentBoard;
    private List<List<Byte>> conwaysBoard;
    private List<List<Byte>> boardOfActiveCells;

    private int rowCount;
    private int colCount;
    private int expandNum;
    private int shiftNum;
    private int start;
    private int sector2;
    private int sector3;
    private int sector4;

    private int length;

    // Booleans to help check if the boards needs shifting or expanding
    private boolean expandDown, expandRight, needsRightShift, needsDownShift;

    /**
     * Constructs a rule object
     */
    public DynamicRule () {
        this.currentBoard = new ArrayList<>();
        expandNum = 20;
        shiftNum = 10;

    }

    /**
     * Returns the current board DynamicBoard bject
     * @return The current DynamicBoard object
     */
    public List<List<Byte>> getCurrentBoard() {
        return currentBoard;
    }

    public void setCurrentBoard(List<List<Byte>> board) {
        this.currentBoard = board;
        this.rowCount = currentBoard.size() - 1;
        this.colCount = currentBoard.get(0).size() - 1;
    }

    /**
     * Returns the next generation cells as a string
     * @return String containing next generation cells
     */
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

    /**
     * Checks if the gameboard is expanded in four direction
     * @return boolean value
     */
    private boolean isExpandedAllWays() {
        return expandDown && expandRight && needsRightShift && needsDownShift;
    }

    /**
     * Returns a 2D Arraylist with the size of the gameboard
     * @return 2D List with elements of type byte
     */
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

    /**
     * Returns a ruled 2D list
     * @return 2D List with elements of type byte
     */
    public List<List<Byte>> applyBoardRules() {

        // Gives conway board the currentBoards size
        conwaysBoard = initBoard();

        // Does a shift if needed to all the boards in play
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

        // Expands all boards in play if needed
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

    /**
     * Sets false if expanad in one of the direction is not true
     */
    private void setBooleansFalse() {
        expandDown = false;
        expandRight = false;
        needsDownShift = false;
        needsRightShift = false;

    }

    /**
     * Performs the rules on first 1/4 of the gameboard
     */
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

    /**
     * Performs the rules on second 1/4 of the gameboard
     */
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

    /**
     * Performs the rules on third 1/4 of the gameboard
     */
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

    /**
     * Performs the rules on fourth 1/4 of the gameboard
     */
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

    /**
     * Checks if the cell in the specified position has an alive cell over that position
     * @param y position
     * @param x position
     * @return boolean value
     */
    public boolean neighborOver(int y, int x) {

        if (y - 1 != - 1) {

            if (currentBoard.get(y-1).get(x) == 1) {
                return true;
            }

        }
        return false;

    }

    /**
     * Checks if the cell in the specified position has an alive cell under that position
     * @param y position
     * @param x position
     * @return boolean value
     */
    public boolean neighborUnder(int y, int x) {

        if (y + 1 < currentBoard.size()) {

            if (currentBoard.get(y+1).get(x) == 1) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the cell in the specified position has an alive cell to the left of that position
     * @param y position
     * @param x position
     * @return boolean value
     */
    public boolean neighborLeft(int y, int x) {

        if (x - 1 != - 1) {

            if (currentBoard.get(y).get(x-1) == 1) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the cell in the specified position has an alive cell to the right of that position
     * @param y position
     * @param x position
     * @return boolean value
     */
    public boolean neighborRight(int y, int x) {

        if (x + 1 < currentBoard.get(0).size()) {

            if (currentBoard.get(y).get(x+1) == 1) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the cell in the specified position has an alive cell to top left of that position
     * @param y position
     * @param x position
     * @return boolean value
     */
    public boolean neighborTopLeft(int y, int x) {

        if ((y - 1 != - 1) && (x - 1 != - 1)) {

            if (currentBoard.get(y-1).get(x-1) == 1) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the cell in the specified position has an alive cell to top right of that position
     * @param y position
     * @param x position
     * @return boolean value
     */
    public boolean neighborTopRight(int y, int x) {

        if ((y - 1 != - 1) && (x + 1 < currentBoard.get(0).size())) {

            if (currentBoard.get(y-1).get(x+1) == 1) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the cell in the specified position has an alive cell to bottom left of that position
     * @param y position
     * @param x position
     * @return boolean value
     */
    public boolean neighborBottomLeft(int y, int x) {

        if ((y + 1 < currentBoard.size()) && (x - 1 != - 1)) {

            if (currentBoard.get(y+1).get(x-1) == 1) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the cell in the specified position has an alive cell to bottom right of that position
     * @param y position
     * @param x position
     * @return boolean value
     */
    public boolean neighborBottomRight(int y, int x) {

        if ((y + 1 < currentBoard.size()) && (x + 1 < currentBoard.get(0).size())) {

            if (currentBoard.get(y+1).get(x+1) == 1) {
                return true;
            }
        }

        return false;
    }


    /**
     * Calculates the cells that are active. Active cell is either alive itself or has at least one neighbor
     */
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

    /**
     * Marks the top left cell of the specified position
     * @param y y-position
     * @param x x-position
     */
    public void markTopLeft(int y, int x) {

        if ((y - 1 != - 1) && (x - 1 != - 1) && currentBoard.get(y-1).get(x-1) == 0) {
            boardOfActiveCells.get(y-1).set(x-1,(byte) 1);
        }

    }

    /**
     * Marks the top cell of the specified position
     * @param y y-position
     * @param x x-position
     */
    public void markTop(int y, int x) {

        if (y - 1 != - 1 && currentBoard.get(y-1).get(x) == 0) {
            boardOfActiveCells.get(y-1).set(x,(byte) 1);
        }
    }

    /**
     * Marks the top right cell of the specified position
     * @param y y-position
     * @param x x-position
     */
    public void markTopRight(int y, int x) {

        if ((y - 1 != - 1) && (x + 1 < currentBoard.get(0).size()) && (currentBoard.get(y-1).get(x+1) == 0) ) {
            boardOfActiveCells.get(y-1).set(x+1, (byte) 1);
        }
    }

    /**
     * Marks the left cell of the specified position
     * @param y y-position
     * @param x x-position
     */
    public void markLeft(int y, int x) {

        if (x - 1 != - 1 && (currentBoard.get(y).get(x-1) == 0)) {
            boardOfActiveCells.get(y).set(x-1,(byte) 1);
        }
    }

    /**
     * Marks the right cell of the specified position
     * @param y y-position
     * @param x x-position
     */
    public void markRight(int y, int x) {

        if (x + 1 < currentBoard.get(0).size() && currentBoard.get(y).get(x+1) == 0) {
            boardOfActiveCells.get(y).set(x+1, (byte) 1);
        }
    }

    /**
     * Marks the bottom left cell of the specified position
     * @param y y-position
     * @param x x-position
     */
    public void markBottomLeft(int y, int x) {

        if ((y + 1 < currentBoard.size()) && (x - 1 != - 1) && (currentBoard.get(y+1).get(x-1) == 0) ) {
            boardOfActiveCells.get(y+1).set(x-1, (byte) 1);
        }

    }

    /**
     * Marks the bottom cell of the specified position
     * @param y y-position
     * @param x x-position
     */
    public void markBottom(int y, int x) {

        if (y + 1 < currentBoard.size() && currentBoard.get(y+1).get(x) == 0) {
            boardOfActiveCells.get(y+1).set(x,(byte) 1);
        }
    }

    /**
     * Marks the bottom right cell of the specified position
     * @param y y-position
     * @param x x-position
     */
    public void markBottomRight(int y, int x) {

        if ((y + 1 < currentBoard.size()) && (x + 1 < currentBoard.get(0).size()) && (currentBoard.get(y+1).get(x+1) == 0) ) {
            boardOfActiveCells.get(y+1).set(x+1, (byte) 1);
        }
    }

    /**
     * Adds columns to the specified 2D List
     * @param board The 2D list that is going to be added on
     * @param numberOfCols Number of columns
     * @return 2D List with added columns
     */
    private List<List<Byte>> addCols (List<List<Byte>> board, int numberOfCols) {
        for (int i = 0; i < numberOfCols; i++) {
            for (List<Byte> byteLists : board) {
                byteLists.add((byte) 0);
            }
        }
        return board;
    }

    /**
     * Adds rows to the specified 2D List
     * @param board The 2D list that is going to be added on
     * @param numberOfRows Number of rows
     * @return 2D List with added rows
     */
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

    /**
     * Checks if a cell is close to the edge of the board on either the left or top site.
     * @param y Y coordinate of the cell
     * @param x X coordinate of the cell
     */
    private void checkIfShiftIsNeeded(int y, int x) {
        if ((y <= 1)) {
            needsDownShift = true;
        }
        if ((x <= 1)) {
            needsRightShift = true;
        }
    }

    /**
     * Checks if the board needs more room to the right or down.
     *
     * @param y - y coordinate of cell
     * @param x - x coordinate of cell
     */
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

    /**
     * Method that shift all Lists in play to the right
     */
    private void shiftAllRight() {
        addColsToAllBoards();
        currentBoard = shiftBoardRight(currentBoard, shiftNum);
        conwaysBoard = shiftBoardRight(conwaysBoard, shiftNum);
        boardOfActiveCells = shiftBoardRight(boardOfActiveCells, shiftNum);
    }

    /**
     * Method that shift all Lists in play downwards
     */
    private void shiftAllDown() {
        addRowsToAllBoards();
        currentBoard = shifBoardDown(currentBoard, shiftNum);
        conwaysBoard = shifBoardDown(conwaysBoard, shiftNum);
        boardOfActiveCells = shifBoardDown(boardOfActiveCells, shiftNum);
    }

    /**
     * Adds columns to all the Lists in play
     */
    private void addColsToAllBoards() {
        currentBoard = addCols(currentBoard, expandNum);
        conwaysBoard = addCols(conwaysBoard, expandNum);
        boardOfActiveCells = addCols(boardOfActiveCells, expandNum);
    }

    /**
     * Adds rows to all the Litst in play
     */
    private void addRowsToAllBoards() {
        currentBoard = addRows(currentBoard, expandNum);
        conwaysBoard = addRows(conwaysBoard, expandNum);
        boardOfActiveCells = addRows(boardOfActiveCells, expandNum);
    }


    /**
     * Shifts a given List (board) to the right and returns the shifted board
     * @param board List that has been shifted to the right
     * @param shiftNum Number of shifts
     * @return
     */
    private List<List<Byte>> shiftBoardRight(List<List<Byte>> board, int shiftNum) {
        List<List<Byte>> newBoard = new ArrayList<>();

        for (int y = 0; y < board.size(); y++) {
            List<Byte> temp = new ArrayList<>();
            newBoard.add(temp);
        }

        newBoard = addCols(newBoard, shiftNum + board.get(0).size());

        for (int y = 0; y < board.size(); y++) {
            for (int x = 0; x < board.get(y).size(); x++) {
                newBoard.get(y).set(x + shiftNum, (board.get(y).get(x)));
            }
        }

        return newBoard;
    }

    /**
     * Shifts a given List (board) down and returns the shifted board
     * @param board List that has been shifted down
     * @param shiftNum Number of shifts
     * @return
     */
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
