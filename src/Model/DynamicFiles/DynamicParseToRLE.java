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

    public void setup(int[] b, int[] s, List<List<Byte>> board) {
        setRules(b, s);
        setBoard(board);
    }

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
        while(col < biggestX + 1 && board.get(row).get(col) == type ){
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
