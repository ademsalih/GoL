package test;

/**
 * Created by patrikkvarmehansen on 04/04/17.
 */

import Model.RLEParser;
import Model.Rule;
import org.junit.jupiter.api.Test;

public class RLEParsterTest {

    @Test
    public void testParser() throws Exception {

        String xY = "x = 4, y = 5, rule = B3/S23\n";
        String testString = "2b2o$2b2o2$4o$o2bo!";
        byte[] row0 = {0, 0, 1, 1};
        byte[] row1 = {0, 0, 1, 1};
        byte[] row2 = {0, 0, 0, 0};
        byte[] row3 = {1, 1, 1, 1};
        byte[] row4 = {1, 0, 0, 1};


        RLEParser rle = new RLEParser();
        rle.tester(xY, testString);

        rle.setByteArrayFromRlePattern(testString);
        org.junit.Assert.assertArrayEquals(row0, rle.arr[0]);



    }
}
