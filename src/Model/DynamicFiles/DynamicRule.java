package Model.DynamicFiles;

import java.util.ArrayList;
import java.util.List;
import Model.Abstract.Rule;

public class DynamicRule extends Rule {

    ////INSTANCE VARIABLES
    public List<List<Byte>> currentBoard;
    public List<List<Byte>> conwaysBoard;
    public List<List<Byte>> boardOfActiveCells;

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

    public List<List<Byte>> conwaysBoardRules() {
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


        for (int y = 0; y < currentBoard.size(); y++) {
            for (int x = 0; x < currentBoard.get(0).size(); x++) {

                int cellState = currentBoard.get(y).get(x);

                if (cellState == 1 && !isExpandedAllWays()) {
                    expandBoardIfNeeded(y, x);
                }

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
                    markBottomLeft(y,x);
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
