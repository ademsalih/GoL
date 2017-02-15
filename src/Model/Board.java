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

    double canvasWidth;

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

    double xCounter;
    double yCounter;

    double cellWidth;
    double cellHeight;

    GraphicsContext gc;


    public Board (Canvas canvas) {
        this.canvasWidth = canvas.getWidth();
        this.xCounter = 0.0;
        this.yCounter = 0.0;
        this.cellHeight = Math.floor(canvasWidth / board.length);
        this.cellWidth = Math.floor(canvasWidth / board.length);
        this.gc = canvas.getGraphicsContext2D();
    }



    public void drawBoard() {

        System.out.println(canvasWidth/board.length);

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
