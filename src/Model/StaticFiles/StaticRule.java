package Model.StaticFiles;

import Model.Abstract.Rule;

/* This class handles the rules for the application. A board object is
 * imported, manipulated and returned to the calling method.
 *
 */

public class StaticRule extends Rule {

    public byte[][] currentBoard;
    public byte[][] conwaysBoard;
    private static int[] survivor = {2, 3};
    private static int[] born = {3};

    public byte[][] boardOfActiveCells;


    public StaticRule(byte[][] currentBoard) {
        this.currentBoard = currentBoard;
    }


    public byte[][] getCurrentBoard() {
        return this.currentBoard;
    }

    public void setCurrentBoard(byte[][] board) {
        this.currentBoard = board;
    }

    // Returns next generation values
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

    public static void setRules(int[] s, int[] b) {
        survivor = s;
        born = b;
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

    // Conways Game of life Rules (B3S23)
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


    // Counts the neighbor of a cell and returns value
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


    public boolean neighborOver(int y, int x) {

        if (y - 1 != - 1) {

            if (currentBoard[y-1][x] == 1) {
                return true;
            }

        }
        return false;

    }

    public boolean neighborUnder(int y, int x) {

        if (y + 1 < currentBoard.length) {

            if (currentBoard[y+1][x] == 1) {
                return true;
            }
        }

        return false;
    }

    public boolean neighborLeft(int y, int x) {

        if (x - 1 != - 1) {

            if (currentBoard[y][x-1] == 1) {
                return true;
            }
        }

        return false;
    }

    public boolean neighborRight(int y, int x) {

        if (x + 1 < currentBoard[0].length) {

            if (currentBoard[y][x+1] == 1) {
                return true;
            }
        }

        return false;
    }

    public boolean neighborTopLeft(int y, int x) {

        if ((y - 1 != - 1) && (x - 1 != - 1)) {

            if (currentBoard[y-1][x-1] == 1) {
                return true;
            }
        }

        return false;
    }


    public boolean neighborTopRight(int y, int x) {

        if ((y - 1 != - 1) && (x + 1 < currentBoard[0].length)) {

            if (currentBoard[y-1][x+1] == 1) {
                return true;
            }
        }

        return false;
    }

    public boolean neighborBottomLeft(int y, int x) {

        if ((y + 1 < currentBoard.length) && (x - 1 != - 1)) {

            if (currentBoard[y+1][x-1] == 1) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void expandBoardIfNeeded(int y, int x) {

    }

    @Override
    public void addRows(int margin) {

    }

    @Override
    public void addCols(int margin) {

    }

    public boolean neighborBottomRight(int y, int x) {

        if ((y + 1 < currentBoard.length) && (x + 1 < currentBoard[0].length)) {

            if (currentBoard[y+1][x+1] == 1) {
                return true;
            }
        }

        return false;
    }

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

    public void markTopLeft(int y, int x) {

        if ((y - 1 != - 1) && (x - 1 != - 1) && currentBoard[y-1][x-1] == 0) {
            boardOfActiveCells[y-1][x-1] = 1;
        }

    }

    public void markTop(int y, int x) {

        if (y - 1 != - 1 && currentBoard[y-1][x] == 0) {
            boardOfActiveCells[y-1][x] = 1;
        }
    }

    public void markTopRight(int y, int x) {

        if ((y - 1 != - 1) && (x + 1 < currentBoard[0].length) && (currentBoard[y-1][x+1] == 0) ) {
            boardOfActiveCells[y-1][x+1] = 1;
        }
    }

    public void markLeft(int y, int x) {

        if (x - 1 != - 1 && (currentBoard[y][x-1] == 0)) {
            boardOfActiveCells[y][x-1] = 1;
        }
    }

    public void markRight(int y, int x) {

        if (x + 1 < currentBoard[0].length && currentBoard[y][x+1] == 0) {
            boardOfActiveCells[y][x+1] = 1;
        }
    }

    public void markBottomLeft(int y, int x) {

        if ((y + 1 < currentBoard.length) && (x - 1 != - 1) && (currentBoard[y+1][x-1] == 0) ) {
            boardOfActiveCells[y+1][x-1] = 1;
        }

    }

    public void markBottom(int y, int x) {

        if (y + 1 < currentBoard.length && currentBoard[y+1][x] == 0) {
            boardOfActiveCells[y+1][x] = 1;
        }
    }

    public void markBottomRight(int y, int x) {

        if ((y + 1 < currentBoard.length) && (x + 1 < currentBoard[0].length) && (currentBoard[y+1][x+1] == 0) ) {
            boardOfActiveCells[y+1][x+1] = 1;
        }
    }

}
