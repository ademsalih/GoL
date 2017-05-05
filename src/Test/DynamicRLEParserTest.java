package Test;

import Model.DynamicFiles.DynamicRLEParser;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Narmatha on 05.05.2017.
 */
public class DynamicRLEParserTest {

    @Test
    public void testSimplePattern() throws Exception {
        byte[][] testArr = {{0, 0, 1, 1}, {0, 0, 1, 1}, {0, 0, 0, 0}, {1, 1, 1, 1}, {1, 0, 0, 1}};
        String rle1 = "x = 4, y = 5, rule = B3/S23\n2b2o$2b2o2$4o$o2bo!";
        StringReader sr = new StringReader(rle1);
        BufferedReader br = new BufferedReader(sr);
        List<List<Byte>> testArrDyn = new ArrayList<List<Byte>>();

        for (int y = 0; y < testArr.length; y++) {
            List<Byte> temp = new ArrayList<Byte>();
            for (byte b : testArr[y]) {
                temp.add(b);
            }
            testArrDyn.add(temp);
        }

        DynamicRLEParser rle = new DynamicRLEParser();
        rle.importBoard(br);
        List<List<Byte>> arr = rle.getBoard();

        for (int y = 0; y < testArrDyn.size(); y++) {
            for (int x = 0; x < testArrDyn.get(y).size(); x++) {
                org.junit.Assert.assertEquals(testArrDyn.get(y).get(x), arr.get(y).get(x));
            }
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
        List<List<Byte>> testArrDyn = new ArrayList<List<Byte>>();

        for (int y = 0; y < testArr.length; y++) {
            List<Byte> temp = new ArrayList<Byte>();
            for (byte b : testArr[y]) {
                temp.add(b);
            }
            testArrDyn.add(temp);
        }

        DynamicRLEParser rle = new DynamicRLEParser();
        rle.importBoard(br);
        List<List<Byte>> arr = rle.getBoard();

        for (int y = 0; y < testArrDyn.size(); y++) {
            for (int x = 0; x < testArrDyn.get(y).size(); x++) {
                org.junit.Assert.assertEquals(testArrDyn.get(y).get(x), arr.get(y).get(x));
            }
        }

    }
}
