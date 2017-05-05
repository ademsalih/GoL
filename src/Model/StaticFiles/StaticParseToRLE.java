package Model.StaticFiles;

import Model.Abstract.ParseToRLE;

public class StaticParseToRLE extends ParseToRLE {

    byte[][] board;

    public StaticParseToRLE(String name, String author, String c) {
        super(name, author, c);
    }

    public void setup(int[] b, int[] s, byte[][] board) {
        setRules(b, s);
        setBoard(board);
    }

    private void setBoard(byte[][] board) {
        this.board = board;
    }

    protected void findHighLowValues() {
        lowestX = board[0].length;
        lowestY = board.length;
        biggestX = 0;
        biggestY = 0;

        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x] == 1) {
                    if (y < lowestY) {
                        lowestY = y;
                    }
                    else if (y > biggestY) {
                        biggestY = y;
                    }
                    if (x < lowestX) {
                        lowestX = x;
                    }
                    else if (x > biggestX) {
                        biggestX = x;
                    }
                }
            }
        }
    }

    protected int getRunCount (int row, int col, int type) {
        int runCount = 0;
        while(col < biggestX + 1 && board[row][col] == type ){
            runCount += 1;
            col++;
        }
        return runCount;
    }

    protected String convertArrayRowToRleString(int row) {
        String rlePartialString = "";
        int runCount;
        int col = lowestX;
        char charType;
        int intType;

        while (col < biggestX + 1) {
            if (board[row][col] == 0) {
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
