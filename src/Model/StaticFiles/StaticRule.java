package Model.StaticFiles;

import Model.Abstract.Rule;

/**
 * This class handles the rules for the application. A board object is
 * imported, manipulated and returned to the calling method.
 */

public class StaticRule extends Rule {

    public byte[][] currentBoard;
    public byte[][] conwaysBoard;
    private static int[] survivor = {2, 3};
    private static int[] born = {3};

    public byte[][] boardOfActiveCells;

    /**
     * Constructs a StaticRule object
     * @param currentBoard
     */
    public StaticRule(byte[][] currentBoard) {
        this.currentBoard = currentBoard;
    }

    public byte[][] getCurrentBoard() {
        return this.currentBoard;
    }

    public void setCurrentBoard(byte[][] board) {
        this.currentBoard = board;
    }

    /**
     * Returns the next generation cells as a string
     * @return String containing next generation cells
     */
    @Override
    public String toString(){
        String output = "";

        for (int row = 0; row < currentBoard.length; row++) {
            for (int col = 0; col < currentBoard[0].length; col++){
                output = output + currentBoard[row][col];
            }
        }
        return output;
    }

    public void checkRule (int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println("");
    }

    public void checkRules () {
        checkRule(survivor);
        checkRule(born);
    }

    public byte checkIfOnOrOff(int neighbors, int cellState) {
        if (cellState == 1) {
            for (int s : survivor) {
                if (s == neighbors) {
                    return 1;
                }
            }
        }
        else if (cellState == 0) {
            for (int b : born) {
                if (b == neighbors) {
                    return 1;
                }
            }
        }

        return 0;
    }

    /**
     * Returns a ruled 2D list
     * @return 2D List with elements of type byte
     */
    public byte[][] conwaysBoardRules() {

        conwaysBoard = new byte[currentBoard.length][currentBoard[0].length];

        for (int y = 0; y < conwaysBoard.length; y++) {

            for (int x = 0; x < conwaysBoard[0].length; x++) {

                if (boardOfActiveCells[y][x] == 1) {
                    int cellState = currentBoard[y][x];
                    conwaysBoard[y][x] = checkIfOnOrOff(countNeighbor(y, x), cellState);
                }
            }
        }
        return conwaysBoard;
    }

    /**
     * Counts the neighbors of a cell and returns the number of neighbors
     * @param y position
     * @param x position
     * @return number of neighbors
     */
    public int countNeighbor(int y, int x){

        int neighborsCounter = 0;

        if (neighborTopLeft(y,x)) {
            neighborsCounter += 1;
        }
        if (neighborOver(y,x)) {
            neighborsCounter += 1;
        }
        if (neighborTopRight(y,x)) {
            neighborsCounter += 1;
        }
        if (neighborLeft(y,x)) {
            neighborsCounter += 1;
        }
        if (neighborRight(y,x)) {
            neighborsCounter += 1;
        }
        if (neighborBottomLeft(y,x)) {
            neighborsCounter += 1;
        }
        if (neighborUnder(y,x)) {
            neighborsCounter += 1;
        }
        if (neighborBottomRight(y,x)) {
            neighborsCounter += 1;
        }

        return neighborsCounter;

    }

    /**
     * Checks if the cell in the specified position has an alive cell over that position
     * @param y position
     * @param x position
     * @return boolean value
     */
    public boolean neighborOver(int y, int x) {

        if (y - 1 != - 1) {

            if (currentBoard[y-1][x] == 1) {
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

        if (y + 1 < currentBoard.length) {

            if (currentBoard[y+1][x] == 1) {
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

            if (currentBoard[y][x-1] == 1) {
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

        if (x + 1 < currentBoard[0].length) {

            if (currentBoard[y][x+1] == 1) {
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

            if (currentBoard[y-1][x-1] == 1) {
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

        if ((y - 1 != - 1) && (x + 1 < currentBoard[0].length)) {

            if (currentBoard[y-1][x+1] == 1) {
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

        if ((y + 1 < currentBoard.length) && (x - 1 != - 1)) {

            if (currentBoard[y+1][x-1] == 1) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void checkIfExpansionIsNedded(int y, int x) {

    }


    /**
     * Checks if the cell in the specified position has an alive cell to bottom right of that position
     * @param y position
     * @param x position
     * @return boolean value
     */
    public boolean neighborBottomRight(int y, int x) {

        if ((y + 1 < currentBoard.length) && (x + 1 < currentBoard[0].length)) {

            if (currentBoard[y+1][x+1] == 1) {
                return true;
            }
        }

        return false;
    }

    /**
     * Calculates the cells that are active. Active cell is either alive itself or has at least one neighbor
     */
    public void calculateBoardOfActiveCells() {
        boardOfActiveCells = new byte[currentBoard.length][currentBoard[0].length];

        for (int y = 0; y < boardOfActiveCells.length; y++) {
            for (int x = 0; x < boardOfActiveCells[0].length; x++) {

                if (currentBoard[y][x] == 1) {
                    boardOfActiveCells[y][x] = 1;
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

        if ((y - 1 != - 1) && (x - 1 != - 1) && currentBoard[y-1][x-1] == 0) {
            boardOfActiveCells[y-1][x-1] = 1;
        }

    }

    /**
     * Marks the top cell of the specified position
     * @param y y-position
     * @param x x-position
     */
    public void markTop(int y, int x) {

        if (y - 1 != - 1 && currentBoard[y-1][x] == 0) {
            boardOfActiveCells[y-1][x] = 1;
        }
    }

    /**
     * Marks the top right cell of the specified position
     * @param y y-position
     * @param x x-position
     */
    public void markTopRight(int y, int x) {

        if ((y - 1 != - 1) && (x + 1 < currentBoard[0].length) && (currentBoard[y-1][x+1] == 0) ) {
            boardOfActiveCells[y-1][x+1] = 1;
        }
    }

    /**
     * Marks the left cell of the specified position
     * @param y y-position
     * @param x x-position
     */
    public void markLeft(int y, int x) {

        if (x - 1 != - 1 && (currentBoard[y][x-1] == 0)) {
            boardOfActiveCells[y][x-1] = 1;
        }
    }

    /**
     * Marks the right cell of the specified position
     * @param y y-position
     * @param x x-position
     */
    public void markRight(int y, int x) {

        if (x + 1 < currentBoard[0].length && currentBoard[y][x+1] == 0) {
            boardOfActiveCells[y][x+1] = 1;
        }
    }

    /**
     * Marks the bottom left cell of the specified position
     * @param y y-position
     * @param x x-position
     */
    public void markBottomLeft(int y, int x) {

        if ((y + 1 < currentBoard.length) && (x - 1 != - 1) && (currentBoard[y+1][x-1] == 0) ) {
            boardOfActiveCells[y+1][x-1] = 1;
        }

    }

    /**
     * Marks the bottom cell of the specified position
     * @param y y-position
     * @param x x-position
     */
    public void markBottom(int y, int x) {

        if (y + 1 < currentBoard.length && currentBoard[y+1][x] == 0) {
            boardOfActiveCells[y+1][x] = 1;
        }
    }

    /**
     * Marks the bottom right cell of the specified position
     * @param y y-position
     * @param x x-position
     */
    public void markBottomRight(int y, int x) {

        if ((y + 1 < currentBoard.length) && (x + 1 < currentBoard[0].length) && (currentBoard[y+1][x+1] == 0) ) {
            boardOfActiveCells[y+1][x+1] = 1;
        }
    }

}
