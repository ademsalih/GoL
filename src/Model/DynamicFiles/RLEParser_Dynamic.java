package Model.DynamicFiles;

import java.util.ArrayList;
import java.util.List;
import Model.Abstract.RLEParser;

/**
 * Created by patrikkvarmehansen on 29/04/17.
 */
public class RLEParser_Dynamic extends RLEParser{

    int count = 0;
    private static ArrayList<List<Byte>> arrLi;
    private List<Byte> line;

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
                    arrLi.add(line);
                    line = new ArrayList<>();
                } else {
                    updateBoard(x, (byte)0);
                    arrLi.add(line);
                    line = new ArrayList<>();
                }
            }

        }

        // Makes sure that the end of the last line is filled with 0s according to width.
        else if (cellType == '!') {
            if (xPlacement != 0) {
                updateBoard(x - xPlacement, (byte)0);
            }
            arrLi.add(line);
            line = new ArrayList<>();
        }
    }

    @Override
    public void updateBoard(int r, byte b) {
        for (int i = 0; i < r; i++) {
            if (xPlacement != x) {
                line.add(b);
            }
            if ((xPlacement + 1) == x) {
                xPlacement = -1;
                count++;
                if ((yPlacement + 1) != y) {
                    yPlacement++;
                }
            }
            xPlacement++;
        }

    }

    @Override
    public void initBoard() {
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
