package Model;

/**
 * Created by patrikkvarmehansen on 09/02/17.
 *
 * Board class that creates and keeps a 2D-array that's meant to represent the board before drawn.
 */
public class Board {
    private byte[][] board;

    //Creates a board object that has a 2D array that corresponds to the number of rows and columns.
    public Board (int columns, int rows) {
        this.board = new byte[columns][rows];
    }
}
