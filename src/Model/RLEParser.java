package Model;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by patrikkvarmehansen on 03/03/17.
 */
public class RLEParser {

    private int x = 0;
    private int y = 0;

    //Uses regex to extract the x value from the rle file
    static public void settingX () throws IOException {
        BufferedReader br = FileLoader.readFile();
        Boolean xNotFound = true;
        while (xNotFound) {
            String line = br.readLine();
            if (line.startsWith("x")) {
                Matcher matcher = Pattern.compile("\\d+").matcher(line);
                matcher.find();
                int result = Integer.valueOf(matcher.group());
                //this.x = result;
                System.out.println(result);
                xNotFound = false;
            }
        }

    }
    //Uses regex to extract the y value from the rle file
    public void settingY () throws IOException {
        BufferedReader br = FileLoader.readFile();
        Boolean xNotFound = true;
        while (xNotFound) {
            String line = br.readLine();
            if (line.startsWith("x")) {
                Matcher matcher = Pattern.compile("\\d+").matcher(line);
                matcher.find(5);
                int result = Integer.valueOf(matcher.group());
                //this.x = result;
                System.out.println(result);
                xNotFound = false;
            }
        }
    }

    


}
