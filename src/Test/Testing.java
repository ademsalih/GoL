package test;

import Model.Board;
import Model.Rule;
import org.junit.jupiter.api.Test;

/**
 * Created by Narmatha on 20.03.2017.
 */
public class Testing {

    @Test
    public void testnextGeneration(){
        byte[][] board = {
                { 0, 0, 0, 0 },
                { 0, 0, 1, 0 },
                { 0, 1, 1, 0 },
                { 0, 0, 0, 0 }
        };

        Rule rule = new Rule(board);
        rule.conwaysBoardRules();
        org.junit.Assert.assertEquals(rule.toString(),"0000011001100000");


    }
}
