package Model.StaticFiles;


import Model.Abstract.RLEParser;


/**
 * Handels the parsing of the .rle file.
 * The static implementation uses a 2D byte array to hold the board data.
 */
public class StaticRLEParser extends RLEParser {

    private byte[][] arr;

    /**
     * Initiates the update of the array (board) with a given run count and cell type
     * @param runCount Number of implementations of the given cell type
     * @param cellType What kind of cell that should be added to the board
     */
    @Override
    public void initiateBoardUpdate(int runCount, char cellType) {
        if (cellType == 'b') {
            updateBoard(runCount, (byte)0);
        } else if (cellType == 'o') {
            updateBoard(runCount, (byte)1);
        } else if (cellType == '$') {
            for (int i = 0; i < runCount; i++) {
                if (i == 0) {
                    if (xPlacement != 0) {
                        updateBoard((x - xPlacement), (byte)0);
                    }
                } else {
                    updateBoard(x, (byte)0);
                }
            }
        }

        // Makes sure that the end of the last line is filled with 0s according to width.
        else if (cellType == '!' && xPlacement != 0) {
            updateBoard(x - xPlacement, (byte)0);
        }
    }

    /**
     * Does the actual updating of the array (board) values
     *
     * @param r  int that holds the runcount. This is the number of placements the byte should have.
     * @param b  byte that holds the value of the cells that is placed on the board.
     */
    @Override
    public void updateBoard(int r, byte b) {
        for (int i = 0; i < r; i++) {
            if (xPlacement != x) {
                arr[yPlacement][xPlacement] = b;
            }
            if ((xPlacement + 1) == x) {
                xPlacement = -1;
                if ((yPlacement + 1) != y) {
                    yPlacement++;
                }
            }
            xPlacement++;
        }
    }

    /**
     * Creates the array (board) that the rle pattern is added to
     */
    @Override
    public void initBoard() {
        arr = new byte[y][x];
    }

    /**
     * Returns the array (board)
     *
     * @return byte[][] containing the gameboard.
     */
    public byte[][] getBoard() {
        return arr;
    }

    /**
     * Prints the board to console
     *
     */
    @Override
    public String toString() {
        String toString = "";
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                toString += arr[i][j];
            }
            toString += "\n";
        }
        return  toString;
    }
}



