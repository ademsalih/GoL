package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Narmatha on 13.04.2017.
 */
public class DynamicRule {
    ////INSTANCE VARIABLES
    public List<List<Byte>> currentBoard;
    public List<List<Byte>> ruledBoard;
    public List<List<Byte>> conwaysBoard;
    private int[] survivor = {2, 3};
    private int[] born = {3};


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

    ////SIMPLE RULES, INVERSION
    /*public ArrayList<ArrayList<Byte>> invertBoard() {

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
    }*/



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


    // Counts the neighbor of a cell and returns antall
    public int countNeighbor( int y, int x){


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


    private boolean neighborOver(int y, int x) {

        if (y - 1 != - 1) {

            if (currentBoard.get(y-1).get(x) == 1) {
                return true;
            }

        }
        return false;

    }

    private boolean neighborUnder(int y, int x) {

        int boardLength = currentBoard.size();

        if (y + 1 < boardLength) {

            if (currentBoard.get(y+1).get(x) == 1) {
                return true;
            }
        }

        return false;
    }

    private boolean neighborLeft(int y, int x) {

        if (x - 1 != - 1) {

            if (currentBoard.get(y).get(x-1) == 1) {
                return true;
            }
        }

        return false;
    }

    private boolean neighborRight(int y, int x) {

        int boardLength = currentBoard.size();

        if (x + 1 < boardLength) {

            if (currentBoard.get(y).get(x+1) == 1) {
                return true;
            }
        }

        return false;
    }

    private boolean neighborTopLeft(int y, int x) {

        if ((y - 1 != - 1) && (x - 1 != - 1)) {

            if (currentBoard.get(y-1).get(x-1) == 1) {
                return true;
            }
        }

        return false;
    }

    private boolean neighborTopRight(int y, int x) {

        int boardLength = currentBoard.size();

        if ((y - 1 != - 1) && (x + 1 < boardLength)) {

            if (currentBoard.get(y-1).get(x+1) == 1) {
                return true;
            }
        }

        return false;
    }

    private boolean neighborBottomLeft(int y, int x) {

        int boardLength = currentBoard.size();

        if ((y + 1 < boardLength) && (x - 1 != - 1)) {

            if (currentBoard.get(y+1).get(x-1) == 1) {
                return true;
            }
        }

        return false;
    }

    private boolean neighborBottomRight(int y, int x) {

        int boardLength = currentBoard.size();

        if ((y + 1 < boardLength) && (x + 1 < boardLength)) {

            if (currentBoard.get(y+1).get(x+1) == 1) {
                return true;
            }
        }

        return false;
    }

}
