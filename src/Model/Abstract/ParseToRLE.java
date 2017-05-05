package Model.Abstract;

import java.util.List;

/**
 *
 * A class that parses the current board into a string that is passed to the SaveFile class for writing to file.
 */

public abstract class ParseToRLE {

    /**
     * Creates a RLE compatible String with meta and board data.
     *
     * @param board - the current board at the time of the function call as a 2D array.
     * @return a String containing a RLE pattern, x and y values, and ruleset.
     */
    protected int lowestX;
    protected int biggestX;
    protected int lowestY;
    protected int biggestY;

    int[] bornAr;
    int[] surviveAr;

    String name, author, comment, head;

    Object board;

    public ParseToRLE(String name, String author, String c) {
        this.name = name;
        this.author = author;
        this.comment = parseComment(c);
        System.out.println(comment);

        head = "#N " + name + "\n" + "#A " + author + "\n" + comment + "\n";

    }

    protected void setRules(int[] b, int[] s) {
        this.bornAr = b;
        this.surviveAr = s;
    }

    protected void setBoard(Object board) {
        this.board = board;
    }

    protected String parseComment(String c) {
        c = "#C " + c;
        return c.replace("\n", "\n#C ");
    }

    public String extractingRLE () {
        findHighLowValues();
        String extractedString = head + extractingXYValue() + addingRules() + convertBoardToRlePattern();
        return extractedString;
    }

    protected String extractingXYValue() {
        int x = biggestX - lowestX + 1;
        int y = biggestY - lowestY + 1;
        return "x = " + x + ", y = " + y + ", ";
    }

    protected String addingRules () {
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


    protected String convertBoardToRlePattern() {
        String rlePatternRow = "";
        String rlePattern = "";
        for (int i = lowestY; i < biggestY + 1; i++) {
            if (rlePatternRow.length() >= 50) {
                rlePattern += rlePatternRow;
                rlePattern += "\n";
                rlePatternRow = "";
            }
            rlePatternRow += convertArrayRowToRleString(i);
        }
        rlePattern += rlePatternRow;

        rlePattern += "!";
        return rlePattern;
    }


    //Help function that find how many repetition there is of a given byte value in the array-row.
    protected abstract int getRunCount (int row, int col, int type);

    protected abstract void findHighLowValues();

    //Help function that take one row from the array and converts it to a string in the RLE format.
    protected abstract String convertArrayRowToRleString(int row);


}
