package Model;

public class Rule {

    ////INSTANCE VARIABLES
    public byte[][] currentBoard;
    public byte[][] ruledBoard;
    public byte[][] conwaysBoard;

    ////CONSTRUCTOR
    public Rule (byte[][] currentBoard) {
        this.currentBoard = currentBoard;
    }


    ////CLASS METHODS

    public byte[][] getCurrentBoard() {
        return currentBoard;
    }

    public void setCurrentBoard(byte[][] board) {
        this.currentBoard = board;
    }

    ////SIMPLE RULES, INVERSION
    public byte[][] invertBoard() {

        ruledBoard = new byte[currentBoard.length][currentBoard.length];

        for (int k = 0; k < ruledBoard.length; k++) {

            for (int l = 0; l < ruledBoard.length; l++ ) {
                if (currentBoard[k][l] == 1) {
                    ruledBoard[k][l] = 0;
                } else {
                    ruledBoard[k][l] = 1;
                }

            }
        }

        return ruledBoard;
    }


    ////CONWAYS GAME OF LIFE RULES

    public byte[][] conwaysBoardRules() {

        conwaysBoard = new byte[currentBoard.length][currentBoard.length];

        for (int y = 0; y < conwaysBoard.length; y++) {

            for (int x = 0; x < conwaysBoard.length; x++) {

                if (countNeighbor(currentBoard,y,x) < 2) {

                    conwaysBoard[y][x] = 0;

                } else if ((countNeighbor(currentBoard,y,x) == 2) && ((currentBoard[y][x]) == 1)) {

                    conwaysBoard[y][x] = 1;

                } else if ((countNeighbor(currentBoard,y,x) == 3) && (currentBoard[y][x] == 1 )) {

                    conwaysBoard[y][x] = 1;

                } else if ((countNeighbor(currentBoard,y,x) == 3) && (currentBoard[y][x] == 0)) {

                    conwaysBoard[y][x] = 1;

                } else if (countNeighbor(currentBoard,y,x) > 3) {
                    conwaysBoard[y][x] = 0;
                }

            }
        }

        return conwaysBoard;
    }

    public int countNeighbor(byte[][] board, int y, int x){

        board = currentBoard;

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

    ////COUNT NEIGHBOR METHODS

    public boolean neighborOver(int y, int x) {

        if (y - 1 != - 1) {

            if (currentBoard[y-1][x] == 1) {
                return true;
            }

        }
        return false;

    }

    public boolean neighborUnder(int y, int x) {

        int boardLength = currentBoard.length;

        if (y + 1 < boardLength) {

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

        int boardLength = currentBoard.length;

        if (x + 1 < boardLength) {

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

        int boardLength = currentBoard.length;

        if ((y - 1 != - 1) && (x + 1 < boardLength)) {

            if (currentBoard[y-1][x+1] == 1) {
                return true;
            }
        }

        return false;
    }

    public boolean neighborBottomLeft(int y, int x) {

        int boardLength = currentBoard.length;

        if ((y + 1 < boardLength) && (x - 1 != - 1)) {

            if (currentBoard[y+1][x-1] == 1) {
                return true;
            }
        }

        return false;
    }

    public boolean neighborBottomRight(int y, int x) {

        int boardLength = currentBoard.length;

        if ((y + 1 < boardLength) && (x + 1 < boardLength)) {

            if (currentBoard[y+1][x+1] == 1) {
                return true;
            }
        }

        return false;
    }

}