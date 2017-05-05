package Model.DynamicFiles;

import Model.Abstract.ParseToRLE;

import java.util.List;

public class DynamicParseToRLE extends ParseToRLE {

    List<List<Byte>> board;

    public DynamicParseToRLE(String name, String author, String c) {
        super(name, author, c);
    }

    private void setBoard(List<List<Byte>> board) {
        this.board = board;
    }

    /**
     * Setup that takes paramteres to give the parser essential information for creating a rle String
     * @param b Array holding the born conditions
     * @param s Array holding the survive conditions
     * @param board The board that is used to create the rle pattern
     */
    public void setup(int[] b, int[] s, List<List<Byte>> board) {
        setRules(b, s);
        setBoard(board);
    }

    /**
     * Finds the highest, lowest, leftmost and rightmost placement of an active cell.
     * This is to make sure that only the most relevant is stored in the rle String.
     */
    protected void findHighLowValues() {
        lowestX = board.get(0).size();
        lowestY = board.size();
        biggestX = 0;
        biggestY = 0;

        for (int y = 0; y < board.size(); y++) {
            for (int x = 0; x < board.get(y).size(); x++) {
                if (board.get(y).get(x) == 1) {
                    if (y < lowestY) {
                        lowestY = y;
                    }
                    if (y > biggestY) {
                        biggestY = y;
                    }
                    if (x < lowestX) {
                        lowestX = x;
                    }
                    if (x > biggestX) {
                        biggestX = x;
                    }
                }
            }
        }
    }

    /**
     * Gets the run count of a given cell in the board
     * @param row Current row to start from
     * @param col Current col to start from
     * @param type Cell type
     * @return Run count
     */
    protected int getRunCount (int row, int col, int type) {
        int runCount = 0;
        while(col < biggestX + 1 && board.get(row).get(col) == type ){
            runCount += 1;
            col++;
        }
        return runCount;
    }

    /**
     * Converts a single row from the board to a rle-compatible String
     * @param row The number of the row the operation should be done on
     * @return String containing the rle pattern for the given row
     */
    protected String convertBoardRowToRLEString(int row) {
        String rlePartialString = "";
        int runCount;
        int col = lowestX;
        char charType;
        int intType;

        while (col < biggestX + 1) {
            if (board.get(row).get(col) == 0) {
                intType = 0;
                charType = 'b';
            }
            else {
                intType = 1;
                charType = 'o';
            }

            runCount = getRunCount(row, col, intType);
            col += runCount;

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




}
