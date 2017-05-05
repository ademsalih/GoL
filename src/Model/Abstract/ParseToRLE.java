package Model.Abstract;

/**
 * A class that parses the current board into a string that is passed to the SaveFile class for writing to file.
 *
 * Uses a help method (findHighLowValues) to determine how big the board that actually contains alive cells.
 * This helps to slim down the pattern thats written to the .rle. This should make the file smaller and will also
 * prevent a lot of bloat in the patterns that are stored.
 */

public abstract class ParseToRLE {

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

        head = "#N " + name + "\n" + "#A " + author + "\n" + comment + "\n";

    }

    protected void setRules(int[] b, int[] s) {
        this.bornAr = b;
        this.surviveAr = s;
    }

    protected void setBoard(Object board) {
        this.board = board;
    }

    /**
     * Adds #C to every new line of the comment String
     * @param c The current comment String
     * @return Parsed comment that has a #C for every new line
     */
    protected String parseComment(String c) {
        c = "#C " + c;
        return c.replace("\n", "\n#C ");
    }

    /**
     * Uses other methods to get different parts of the complete RLE Strings
     * @return Finished RLE String with all the info needed for a complete .rle file
     */
    public String extractingRLE () {
        findHighLowValues();
        String extractedString = head + getXYString() + getRulesString() + convertBoardToRlePattern();
        return extractedString;
    }

    /**
     * Gives a String representation of the X and Y of the board
     * @return String containing X and Y in the correct format according to .rle convetions.
     */
    protected String getXYString() {
        int x = biggestX - lowestX + 1;
        int y = biggestY - lowestY + 1;
        return "x = " + x + ", y = " + y + ", ";
    }

    /**
     * Gives a String containing the Rules of the active board.
     * @return String formated for .rle containing the rules
     */
    protected String getRulesString() {
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
     * Runs through the Board and gives a String containing the active pattern
     * @return String containing the board as rle pattern
     */
    protected String convertBoardToRlePattern() {
        String rlePatternRow = "";
        String rlePattern = "";
        for (int i = lowestY; i < biggestY + 1; i++) {
            if (rlePatternRow.length() >= 50) {
                rlePattern += rlePatternRow;
                rlePattern += "\n";
                rlePatternRow = "";
            }
            rlePatternRow += convertBoardRowToRLEString(i);
        }
        rlePattern += rlePatternRow;

        rlePattern += "!";
        return rlePattern;
    }

    protected abstract int getRunCount (int row, int col, int type);

    protected abstract void findHighLowValues();

    protected abstract String convertBoardRowToRLEString(int row);


}
