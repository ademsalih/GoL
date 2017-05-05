package Test;

/**
 * Created by patrikkvarmehansen on 04/04/17.
 */

import Model.StaticFiles.StaticRLEParser;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.StringReader;


class StaticRLEParserTest {

    /**
     * SIMPLE TEST
     */


    @Test
    public void testSimplePattern() throws Exception {
        byte[][] testArr = {{0, 0, 1, 1}, {0, 0, 1, 1}, {0, 0, 0, 0}, {1, 1, 1, 1}, {1, 0, 0, 1}};
        String rle1 = "x = 4, y = 5, rule = B3/S23\n2b2o$2b2o2$4o$o2bo!";
        StringReader sr = new StringReader(rle1);
        BufferedReader br = new BufferedReader(sr);
        StaticRLEParser rle = new StaticRLEParser();
        rle.importBoard(br);
        byte[][] arr = rle.getBoard();

        for (int i = 0; i < arr.length; i++) {
            org.junit.Assert.assertArrayEquals(testArr[i], arr[i]);
        }

    }

    @Test
    public void testMediumPattern() throws Exception {
        byte[][] testArr = {{0,0,0,0,0,0,1,0,0,0,0,0,0},
                {0,0,0,0,0,1,0,1,0,0,0,0,0},
                {0,0,0,0,0,1,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,1,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,1,1,0,0,0,0,0,0,0,1,1,0},
                {1,0,0,1,0,0,0,0,0,1,0,0,1},
                {0,1,1,0,0,0,0,0,0,0,1,1,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,1,0,0,0,0,0,0},
                {0,0,0,0,0,1,0,1,0,0,0,0,0},
                {0,0,0,0,0,1,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,1,0,0,0,0,0,0}};
        String rle1 = "x = 13, y = 13, rule = B3/S23\n6bo6b$5bobo5b$5bobo5b$6bo6b2$b2o7b2ob$o2bo5bo2bo$b2o7b2ob2$6bo6b$5bobo5b$5bobo5b$6bo!";
        StringReader sr = new StringReader(rle1);
        BufferedReader br = new BufferedReader(sr);
        StaticRLEParser rle = new StaticRLEParser();
        rle.importBoard(br);
        byte[][] arr = rle.getBoard();

        for (int i = 0; i < arr.length; i++) {
            org.junit.Assert.assertArrayEquals(testArr[i], arr[i]);
        }

    }

}
