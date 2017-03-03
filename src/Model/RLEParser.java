package Model;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by patrikkvarmehansen on 03/03/17.
 */
public class RLEParser {

    public int x = 0;
    public int y = 0;

    static public void settingX () throws IOException {
        BufferedReader br = FileLoader.readFile();
        Boolean xNotFound = true;
        while (xNotFound) {
            String line = br.readLine();

            if (line.startsWith("x")) {
                Matcher matcher = Pattern.compile("\\d+").matcher(line);
                matcher.find();
                int result = Integer.valueOf(matcher.group());
                System.out.println(result);
            }
        }



    }
}
