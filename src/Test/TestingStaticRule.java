package Test;

import Model.StaticFiles.StaticRule;
import org.junit.jupiter.api.Test;

/**
 * This class tests most of the StaticRule class methods and
 * the nextGeneration method from the Controller class. (ikke ferdig kommentert)
 */


class TestingStaticRule {

    @Test
    void testToString(){
        byte[][] board = {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };

        StaticRule staticRule = new StaticRule(board);
        org.junit.Assert.assertEquals(staticRule.toString(),"100010001");

    }

    @Test
    void testToString1(){

        byte[][] board1 = {
                {0,1,0},
                {1,1,1},
                {0,1,0}
        };

        StaticRule staticRule1 = new StaticRule(board1);
        org.junit.Assert.assertEquals(staticRule1.toString(),"010111010");
    }

    @Test
    void testToString2() {

        byte[][] board2 = {
                {0,0,1,0,0},
                {0,1,1,1,0},
                {1,1,0,1,1},
                {0,1,1,1,0},
                {0,0,1,0,0}
        };

        StaticRule staticRule2 = new StaticRule(board2);
        org.junit.Assert.assertEquals(staticRule2.toString(),"0010001110110110111000100");
    }

    @Test
    void testNextGeneration(){
        byte[][] board = {
                { 0, 0, 0, 0 },
                { 0, 0, 1, 0 },
                { 0, 1, 1, 0 },
                { 0, 0, 0, 0 }
        };

        StaticRule staticRule = new StaticRule(board);
        staticRule.calculateBoardOfActiveCells();
        staticRule.setCurrentBoard(staticRule.conwaysBoardRules());
        org.junit.Assert.assertEquals(staticRule.toString(),"0000011001100000");

    }

    @Test
    void testNextGeneration1(){

        byte[][] board1 = {
                {0,1,0},
                {1,1,1},
                {0,1,0}
        };

        StaticRule staticRule1 = new StaticRule(board1);
        staticRule1.calculateBoardOfActiveCells();
        staticRule1.setCurrentBoard(staticRule1.conwaysBoardRules());
        org.junit.Assert.assertEquals(staticRule1.toString(),"111101111");
    }

    @Test
    void testNextGeneration2(){

        byte[][] board2 = {
                {0,0,1,0,0},
                {0,1,1,1,0},
                {1,1,0,1,1},
                {0,1,1,1,0},
                {0,0,1,0,0}
        };

        StaticRule staticRule2 = new StaticRule(board2);
        staticRule2.calculateBoardOfActiveCells();
        staticRule2.setCurrentBoard(staticRule2.conwaysBoardRules());
        org.junit.Assert.assertEquals(staticRule2.toString(),"0111010001100011000101110");
    }



    @Test
    void testCountNeighbor(){
        byte[][] board = {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };

        StaticRule staticRule = new StaticRule(board);
        int c = staticRule.countNeighbor(/*board,*/ 1, 1);
        org.junit.Assert.assertEquals(c,2);

    }

    @Test
    void testCountNeighbor1(){

        byte[][] board1 = {
                {0,1,0},
                {1,1,1},
                {0,1,0}
        };

        StaticRule staticRule1 = new StaticRule(board1);
        int c1 = staticRule1.countNeighbor(/*board1,*/ 0, 0);
        org.junit.Assert.assertEquals(c1,3);

    }

    @Test
    void testCountNeighbor2() {

        byte[][] board2 = {
                {0,0,1,0,0},
                {0,1,1,1,0},
                {1,1,0,1,1},
                {0,1,1,1,0},
                {0,0,1,0,0}
        };

        StaticRule staticRule2 = new StaticRule(board2);
        int c2 = staticRule2.countNeighbor(/*board2,*/ 2, 2);
        org.junit.Assert.assertEquals(c2,8);

    }
}
