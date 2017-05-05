package Model.Abstract;

/**
 * <h1>Rule</h1>
 * <h3>The Rule class. Holds the base code for the different rule integrations.</h3>
 *
 * <p>The rules are held as 2 int arrays. This way we can run through the arrays with
 * the neighbor count and check if anything mathces. It makes it easy to do a dynamic
 * implementation of the rules since the arrays can be switch out with any number of
 * different ints</p>
 *
 *  <p>To optimize we have a extra list (boardOfActiveCells) which holds information of which
 * cells that were affected or near an affected cell of the last generation. We use this to determine
 * which cells that we need to check for neighbors.</p>
 */

public abstract class Rule {

    // The ruleset
    static private int[] survivor = {2, 3};
    static private int[] born = {3};

    /**
     * Checks if a given cellState combined with number of neighbors means the cell will survive
     * @param neighbors  number of neighbors
     * @param cellState  current cellState (dead or alive)
     * @return  a byte of 1 or 0 where 1 is alive and 0 is dead.
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
     * @return  Int array with the survive part of the rule set
     */
    public int[] getSurvivor() {
        return survivor;
    }

    /**
     * Returns an array containing the conditions for being born
     * @return  Int array with the born part of the rule set
     */
    public int[] getBorn() {
        return born;
    }

    /**
     * Sets the rules of the given board
     * @param s  Int array containing the survive part of the ruleset
     * @param b  Int array containing the born part of the ruleset
     */
    public static void setRules(int[] s, int[] b) {
        survivor = s;
        born = b;
    }

    /**
     * Counts the number of neighbors surrounding the given cell
     * @param y  y coordinate
     * @param x  x coordinate
     * @return  the number of neighbors
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

    protected abstract void checkIfExpansionIsNedded(int y, int x);

}
