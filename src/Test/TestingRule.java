package test;

import Model.Rule;
import org.junit.jupiter.api.Test;

/**
 * This class tests most of the Rule class methods and
 * the nextGeneration method from the Controller class. (ikke ferdig kommentert)
 */

public class TestingRule {

    @Test
    public void testToString(){
        byte[][] board = {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };

        Rule rule = new Rule(board);
        org.junit.Assert.assertEquals(rule.toString(),"100010001");

    }

    @Test
    public void testToString1(){

        byte[][] board1 = {
                {0,1,0},
                {1,1,1},
                {0,1,0}
        };

        Rule rule1 = new Rule(board1);
        org.junit.Assert.assertEquals(rule1.toString(),"010111010");
    }

    @Test
    public void testToString2() {

        byte[][] board2 = {
                {0,0,1,0,0},
                {0,1,1,1,0},
                {1,1,0,1,1},
                {0,1,1,1,0},
                {0,0,1,0,0}
        };

        Rule rule2 = new Rule(board2);
        org.junit.Assert.assertEquals(rule2.toString(),"0010001110110110111000100");
    }

    @Test
    public void testNextGeneration(){
        byte[][] board = {
                { 0, 0, 0, 0 },
                { 0, 0, 1, 0 },
                { 0, 1, 1, 0 },
                { 0, 0, 0, 0 }
        };

        Rule rule = new Rule(board);
        rule.setCurrentBoard(rule.conwaysBoardRules());
        org.junit.Assert.assertEquals(rule.toString(),"0000011001100000");

    }

    @Test
    public void testNextGeneration1(){

        byte[][] board1 = {
                {0,1,0},
                {1,1,1},
                {0,1,0}
        };

        Rule rule1 = new Rule(board1);
        rule1.setCurrentBoard(rule1.conwaysBoardRules());
        org.junit.Assert.assertEquals(rule1.toString(),"111101111");
    }

    @Test
    public void testNextGeneration2(){

        byte[][] board2 = {
                {0,0,1,0,0},
                {0,1,1,1,0},
                {1,1,0,1,1},
                {0,1,1,1,0},
                {0,0,1,0,0}
        };

        Rule rule2 = new Rule(board2);
        rule2.setCurrentBoard(rule2.conwaysBoardRules());
        org.junit.Assert.assertEquals(rule2.toString(),"0111010001100011000101110");
    }

    @Test
    public void testInvertBoard(){
        byte[][] board = {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };

        Rule rule = new Rule(board);
        rule.setCurrentBoard(rule.invertBoard());
        org.junit.Assert.assertEquals(rule.toString(),"011101110");

    }

    @Test
    public void testCountNeighbor(){
        byte[][] board = {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };

        Rule rule = new Rule(board);
        int c = rule.countNeighbor(board, 1, 1);
        org.junit.Assert.assertEquals(c,2);

    }

    @Test
    public void testCountNeighbor1(){

        byte[][] board1 = {
                {0,1,0},
                {1,1,1},
                {0,1,0}
        };

        Rule rule1 = new Rule(board1);
        int c1 = rule1.countNeighbor(board1, 0, 0);
        org.junit.Assert.assertEquals(c1,3);

    }

    @Test
    public void testCountNeighbor2() {

        byte[][] board2 = {
                {0,0,1,0,0},
                {0,1,1,1,0},
                {1,1,0,1,1},
                {0,1,1,1,0},
                {0,0,1,0,0}
        };

        Rule rule2 = new Rule(board2);
        int c2 = rule2.countNeighbor(board2, 2, 2);
        org.junit.Assert.assertEquals(c2,8);

    }
}
