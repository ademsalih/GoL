package Model;

/**
 * Created by Narmatha on 29.04.2017.
 */
public abstract class Rule {
    static private int[] survivor = {2, 3};
    static private int[] born = {3};

    public Rule(){}

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

    public static int[] getSurvivor() {
        return survivor;
    }

    public static int[] getBorn() {
        return born;
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

    abstract boolean neighborOver(int y, int x);

    abstract boolean neighborUnder(int y, int x);

    abstract boolean neighborRight(int y, int x);

    abstract boolean neighborLeft(int y, int x);

    abstract boolean neighborTopLeft(int y, int x);

    abstract boolean neighborTopRight(int y, int x);

    abstract boolean neighborBottomRight(int y, int x);

    abstract boolean neighborBottomLeft(int y, int x);


}
