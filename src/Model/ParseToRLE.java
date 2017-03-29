package Model;

/**
 *
 * A class that parses the current board into a string that is used
 */
public class ParseToRLE {

    public String extractingRLE (byte[][] board) {
        String extractedString =
                extractingXYValue(board) + "\n" +
                convertBoardToRlePattern(board);

        return extractedString;
    }

    private String extractingXYValue(byte[][] board) {
        int x = board[0].length;
        int y = board.length;
        return "x = " + x + ", y = " + y + ", ";
    }

    private String addingRules () {

        return null;

    }

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
