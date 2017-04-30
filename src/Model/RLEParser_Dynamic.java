package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrikkvarmehansen on 29/04/17.
 */
public class RLEParser_Dynamic extends RLEParser{

    int count = 0;
    private static ArrayList<List<Byte>> arrLi;
    private List<Byte> line;

    @Override
    void initiateBoardUpdate(int runCount, char cellType) {
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
                    if (yPlacement == 4) {
                        System.out.println("");
                    }
                    arrLi.add(line);
                    line.clear();

                } else {
                    updateBoard(x, (byte)0);
                    arrLi.add(line);
                    line.clear();
                }
            }
            //System.out.println(arrLi.toString());

        }

        // Makes sure that the end of the last line is filled with 0s according to width.
        else if (cellType == '!') {
            updateBoard(x - xPlacement, (byte)0);
        }
    }

    @Override
    void updateBoard(int r, byte b) {
        for (int i = 0; i < r; i++) {
            if (xPlacement != x) {
                line.add(b);
            }
            if ((xPlacement + 1) == x) {
                xPlacement = -1;
                System.out.print(count);
                System.out.print(line.toString());
                System.out.println("\n");
                count++;
                if ((yPlacement + 1) != y) {
                    yPlacement++;
                }
            }
            xPlacement++;
        }

    }

    @Override
    void initBoard() {
        arrLi = new ArrayList<>();
        line = new ArrayList<>();
    }


    /**
     * Returns the board
     *
     * @return - ArrayList containing the gameboard.
     */
    public ArrayList<List<Byte>> getBoard() {
        return arrLi;
    }

}
