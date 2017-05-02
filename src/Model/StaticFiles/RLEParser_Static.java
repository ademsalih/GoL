package Model.StaticFiles;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Model.Abstract.RLEParser;

/**
 * A class that parses a .rle file and returns it as a 2D byte array through the importBoard method.
 * It can also return the X and Y value if needed later on.
 */

public class RLEParser_Static extends RLEParser {

    private byte[][] arr;


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

    @Override
    public void initBoard() {
        arr = new byte[y][x];
    }

    /**
     * Returns the board
     *
     * @return - byte[][] containing the gameboard.
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



