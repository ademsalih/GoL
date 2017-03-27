package Test;

import Model.Rule;
import org.junit.jupiter.api.Test;

/**
 * Created by Narmatha on 20.03.2017.
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
    public void testNextGeneration(){
        byte[][] board = {
                { 0, 0, 0, 0 },
                { 0, 0, 1, 0 },
                { 0, 1, 1, 0 },
                { 0, 0, 0, 0 }
        };

        byte[][] board1 = {
                {0,1,0},
                {1,1,1},
                {0,1,0}
        };

        byte[][] board2 = {
                {0,0,1,0,0},
                {0,1,1,1,0},
                {1,1,0,1,1},
                {0,1,1,1,0},
                {0,0,1,0,0}
        };


        Rule rule = new Rule(board);
        rule.setCurrentBoard(rule.conwaysBoardRules());
        org.junit.Assert.assertEquals(rule.toString(),"0000011001100000");

        Rule rule1 = new Rule(board1);
        rule1.setCurrentBoard(rule1.conwaysBoardRules());
        org.junit.Assert.assertEquals(rule1.toString(),"111101111");

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

}
