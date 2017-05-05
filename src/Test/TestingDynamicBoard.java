package Test;

import Model.DynamicFiles.DynamicBoard;
import org.junit.jupiter.api.Test;

/**
 * Created by Narmatha on 27.04.2017.
 */
public class TestingDynamicBoard {

    @Test
    public void testsetCellState(){
        DynamicBoard board = new DynamicBoard(10, 10);
        board.setCellState(5, 5, (byte) 1);
        org.junit.Assert.assertEquals(board.getCellState(5, 5), 1);
        board.setCellState(50, 50, (byte) 1);
        org.junit.Assert.assertEquals(board.getCellState(50, 50), 1);
        org.junit.Assert.assertEquals(board.getCellState(49, 49), 0);
        // The test fails because the cell (100,100) does not exist
        //org.junit.Assert.assertEquals(board.getCellState(100, 100), 1);

    }
}