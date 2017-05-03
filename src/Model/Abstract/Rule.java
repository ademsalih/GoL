package Model.Abstract;

/**
 * Created by Narmatha on 29.04.2017.
 */
public abstract class Rule {

    // The ruleset
    static private int[] survivor = {2, 3};
    static private int[] born = {3};

    /**
     * Checks if a given cellState combined with number of neighbors means the cell will survive
     * @param neighbors - number of neighbors
     * @param cellState - current cellState (dead or alive)
     * @return - a byte of 1 or 0 where 1 is alive and 0 is dead.
     */
    protected byte checkIfOnOrOff(int neighbors, int cellState) {

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
     * Returns an array containing the conditions for survival
     * @return - 2D int array with the survive part of the rule set
     */
    public static int[] getSurvivor() {
        return survivor;
    }

    /**
     * Returns an array containing the conditions for being born
     * @return - 2D int array with the born part of the rule set
     */
    public static int[] getBorn() {
        return born;
    }

    /**
     * Sets the rules of the given board
     * @param s - 2D int array containing the survive part of the ruleset
     * @param b - 2D int array containing the born part of the ruleset
     */
    public static void setRules(int[] s, int[] b) {
        survivor = s;
        born = b;
    }

    /**
     * Counts the number of neighbors surrounding the given cell
     * @param y - y coordinate
     * @param x - x coordinate
     * @return - the number of neighbors
     */
    protected int countNeighbor( int y, int x){

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

    protected abstract boolean neighborOver(int y, int x);
    protected abstract boolean neighborUnder(int y, int x);
    protected abstract boolean neighborRight(int y, int x);
    protected abstract boolean neighborLeft(int y, int x);
    protected abstract boolean neighborTopLeft(int y, int x);
    protected abstract boolean neighborTopRight(int y, int x);
    protected abstract boolean neighborBottomRight(int y, int x);
    protected abstract boolean neighborBottomLeft(int y, int x);

    protected abstract void calculateBoardOfActiveCells();

    protected abstract void markTopLeft(int y, int x);
    protected abstract void markTopRight(int y, int x);
    protected abstract void markTop(int y, int x);
    protected abstract void markLeft(int y, int x);
    protected abstract void markRight(int y, int x);
    protected abstract void markBottomLeft(int y, int x);
    protected abstract void markBottomRight(int y, int x);
    protected abstract void markBottom(int y, int x);

    /**
     * Checks if a cell is close or at the edge of the board and expands the board if needed
     * @param y - y coordinate of cell
     * @param x - x coordinate of cell
     */
    protected abstract void expandBoardIfNeeded(int y, int x);

    /**
     * Adds a given number of rows to the board
     * @param i - number of rows to be added
     */
    /*
    protected abstract Object addRows(int i);

    /**
     * Adds a given number of columns to the board
     * @param i - number of columns to be added
     */
    /*
    protected abstract Object addCols(int i);
    */
}
