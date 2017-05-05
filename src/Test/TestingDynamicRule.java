package Test;

import Model.DynamicFiles.DynamicRule;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class TestingDynamicRule {

    @Test
    void testToString(){
        List<List<Byte>> board = new ArrayList<>();
        for (int row = 0; row < 5; row++) {
            List<Byte> rowList = new ArrayList<>();
            for (int col = 0; col < 5; col++) {
                rowList.add((byte) 0);
            }
            board.add(rowList);
        }

        //Glider
        for(int i = 0; i < 3; i++){
            board.get(2).set(i, (byte) 1);
        }
        board.get(1).set(2,(byte) 1);
        board.get(0).set(1,(byte) 1);

        DynamicRule dynamicRule = new DynamicRule();
        dynamicRule.currentBoard = board;
        dynamicRule.calculateBoardOfActiveCells();
        //System.out.println(dynamicRule.toString());
        dynamicRule.setCurrentBoard(dynamicRule.applyBoardRules());
        //System.out.println(dynamicRule.toString());
        org.junit.Assert.assertEquals(dynamicRule.toString(),"0000010100011000100000000");
    }

    @Test
    void testToString1() {
        List<List<Byte>> board1 = new ArrayList<>();
        for (int row = 0; row < 5; row++) {
            List<Byte> rowList = new ArrayList<>();
            for (int col = 0; col < 5; col++) {
                rowList.add((byte) 0);
            }
            board1.add(rowList);
        }

        //Diagonal line
        for (int i = 0; i < 5; i++) {
            board1.get(i).set(i, (byte) 1);
        }

        DynamicRule dynamicRule1 = new DynamicRule();
        dynamicRule1.currentBoard = board1;
        dynamicRule1.calculateBoardOfActiveCells();
        dynamicRule1.setCurrentBoard(dynamicRule1.applyBoardRules());
        org.junit.Assert.assertEquals(dynamicRule1.toString(),"0000001000001000001000000");
    }

    @Test
    void testToString2() {
        List<List<Byte>> board2 = new ArrayList<>();
        for (int row = 0; row < 3; row++) {
            List<Byte> rowList = new ArrayList<>();
            for (int col = 0; col < 3; col++) {
                rowList.add((byte) 0);
            }
            board2.add(rowList);
        }

        //Cross
        for (int i = 0; i < 3; i++) {
            board2.get(i).set(1, (byte) 1);
        }
        for (int i = 0; i < 3; i++){
            board2.get(1).set(i, (byte) 1);
        }

        DynamicRule dynamicRule2 = new DynamicRule();
        dynamicRule2.currentBoard = board2;
        dynamicRule2.calculateBoardOfActiveCells();
        dynamicRule2.setCurrentBoard(dynamicRule2.applyBoardRules());
        org.junit.Assert.assertEquals(dynamicRule2.toString(),"111101111");
    }
}
