package Model;

import Model.StaticFiles.StaticRule;

/**
 *
 * A class that parses the current board into a string that is passed to the SaveFile class for writing to file.
 */

public class ParseToRLE {


    //TODO Add help function that slims down the extracted board to the lowest possible X and Y value.

    /**
     * Creates a RLE compatible String that represents the board.
     *
     * @param board - the current board at the time of the function call as a 2D array.
     * @return a String containing a RLE pattern, x and y values, and ruleset.
     */

    private int lowestX;
    private int biggestX;
    private int lowestY;
    private int biggestY;

    public String extractingRLE (byte[][] board) {
        String extractedString = extractingXYValue(board) + addingRules() + convertBoardToRlePattern(board);
        return extractedString;
    }

    private String extractingXYValue(byte[][] board) {
        int x = board[0].length;
        int y = board.length;
        return "x = " + x + ", y = " + y + ", ";
    }

    private String addingRules () {
        int[] bornAr = StaticRule.getBorn();
        int[] surviveAr = StaticRule.getSurvivor();
        String born = "";
        String survive = "";
        for (int i = 0; i < bornAr.length; i++) {
            born += bornAr[i];
        }
        for (int i = 0; i < surviveAr.length; i++) {
            survive += surviveAr[i];
        }
        return "rule = b" + born + "/s" + survive + "\n";

    }



    /**
     * Converts the 2D byte array to
     *
     * @param board
     * @return
     */
    private String convertBoardToRlePattern(byte[][] board) {
        String rlePatternRow = "";
        String rlePattern = "";
        for (int i = 0; i < board.length; i++) {
            if (rlePatternRow.length() >= 75) {
                rlePattern += rlePatternRow;
                rlePattern += "\n";
                rlePatternRow = "";
            }
            rlePatternRow += convertArrayRowToRleString(board, i);
        }
        rlePattern += rlePatternRow;

        rlePattern += "!";
        return rlePattern;
    }

    //Help function that take one row from the array and converts it to a string in the RLE format.
    private String convertArrayRowToRleString(byte[][] board, int col) {
        String rlePartialString = "";
        int runCount;
        int i = 0;
        char charType;
        int intType;

        while (i < board[col].length) {
            if (board[col][i] == 0) {
                intType = 0;
                charType = 'b';
            }
            else {
                intType = 1;
                charType = 'o';
            }

            runCount = getRunCount(board, col, i, intType);
            i += runCount;

            if (runCount != 1) {
                rlePartialString += Integer.toString(runCount)+ charType;
            }
            else {
                rlePartialString += charType;
            }
        }
        rlePartialString += "$";
        return rlePartialString;
    }

    //Help function that find how many repetition there is of a given byte value in the array-row.
    private int getRunCount (byte[][] board, int col, int i, int type) {
        int runCount = 0;
        while(i < board[col].length && board[col][i] == type ){
            runCount += 1;
            i++;
        }
        return runCount;
    }

    





}
