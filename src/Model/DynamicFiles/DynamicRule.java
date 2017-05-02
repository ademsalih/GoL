package Model.DynamicFiles;

import java.util.ArrayList;
import java.util.List;
import Model.Abstract.Rule;

public class DynamicRule extends Rule {

    ///////////////////////////////
    //public byte[][] boardOfActiveCells;
    ///////////////////////////////

    ////INSTANCE VARIABLES
    public List<List<Byte>> currentBoard;
    public List<List<Byte>> conwaysBoard;

    ////CONSTRUCTOR
    // MÅ DEKLARERE ALLE BOARDDENE MED NULL HER!
    public DynamicRule (List<List<Byte>> currentBoard) {
        this.currentBoard = currentBoard;
    }


    //bare for å sjekke push

    ////CLASS METHODS

    public List<List<Byte>> getCurrentBoard() {
        return currentBoard;
    }

    public void setCurrentBoard(ArrayList<List<Byte>> board) {
        this.currentBoard = board;
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

    public List<List<Byte>> conwaysBoardRules() {

        conwaysBoard = new ArrayList<List<Byte>>();
        for (int i = 0; i < currentBoard.size() ; i++) {
            List<Byte> row = new ArrayList<Byte>();
            for (int j = 0; j < currentBoard.get(0).size(); j++) {
                row.add((byte) 0);
            }
            this.conwaysBoard.add(row);
        }

        for (int y = 0; y < conwaysBoard.size(); y++) {
            for (int x = 0; x < conwaysBoard.get(0).size(); x++) {

                int cellState = currentBoard.get(y).get(x);
                conwaysBoard.get(y).set(x,checkIfOnOrOff(countNeighbor( y, x), cellState));
            }
        }
        return conwaysBoard;
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
    /*public void calculateBoardOfActiveCells() {
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
                    markBottomLeft(y,x);
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
    }*/
    ////////////////////////////////////////////////////////////////

}
