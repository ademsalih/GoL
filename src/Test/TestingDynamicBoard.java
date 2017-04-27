package Test;

import Model.DynamicBoard;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.Test;

/**
 * Created by Narmatha on 27.04.2017.
 */
public class TestingDynamicBoard {

    @FXML public Canvas canvas;
    GraphicsContext gc;

    @Test
    public void testsetCellState(){
        DynamicBoard board = new DynamicBoard(canvas,10, 10);
        board.setCellState(5, 5, (byte) 1);
        org.junit.Assert.assertEquals(board.getCellState(5, 5), 1);
        board.setCellState(50, 50, (byte) 1);
        org.junit.Assert.assertEquals(board.getCellState(50, 50), 1);
        org.junit.Assert.assertEquals(board.getCellState(49, 49), 0);
        board.setCellState(51, 51, (byte) 1);
        org.junit.Assert.assertEquals(board.getCellState(51, 51), 1);

    }
}