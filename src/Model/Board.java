package Model;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by patrikkvarmehansen on 09/02/17.
 *
 * Board class that creates and keeps a 2D-array that's meant to represent the board before drawn.
 */
public class Board {

    byte[][] board = {
            {1,0,1,0,1,0,1,0,1,0},
            {0,1,0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0,1,0},
            {0,1,0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0,1,0},
            {0,1,0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0,1,0},
            {0,1,0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0,1,0},
            {0,1,0,1,0,1,0,1,0,1},
    };

    double xCounter = 0;

    double yCounter = 0;

    double cellWidth;

    double cellHeight;

    double canvasWidth;

    GraphicsContext gc;


    public Board (Canvas canvas) {
        this.cellHeight = Math.floor(canvasWidth / board.length);
        this.cellWidth = Math.floor(canvasWidth / board.length);
        this.gc = canvas.getGraphicsContext2D();
        this.canvasWidth = canvas.getWidth();
    }

    public void drawBoard() {

        for (int a = 0; a < board.length; a++) {

            for (int b = 0; b < board.length; b++) {

                if (board[a][b] == 1) {

                    gc.fillRect(xCounter, yCounter, cellWidth, cellHeight);
                    xCounter += cellWidth;

                } else {

                    xCounter += cellWidth;

                }
                if ((xCounter % (cellWidth*board.length) == 0.0) && (xCounter != 0.0)) {

                    xCounter = 0.0;
                    yCounter += cellHeight;

                }

            }

        }
    }



}
