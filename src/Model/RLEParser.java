package Model;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that parses a .rle file and returns it as a 2D byte array through the getStaticBoard method.
 * It can also return the X and Y value if needed later on.
 */

public class RLEParser {

    //TODO Add function that checks all new lines for zeroes and has a runcount for empty lines.
    //TODO - Separate data from logic?
    //TODO - Read metadata

    // Board & Rule variables
    private int x;
    private int y;
    private int xPlacement;
    private int yPlacement;
    private int[] born;
    private int[] survive;
    private byte[][] arr;

    // Metadata
    private String patternName;
    private String author;
    private String comment;

    private char cellType;
    private int runCount;

    /**
     * Returns the X value (Number of Columns) of the Board
     *
     * @return - Number of columns
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y value (Number of rows) of the Board from the RLE-file loaded into the given RLEParser-object
     *
     * @return - Number of rows
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the name of the pattern if it was found in the metadata
     *
     * @return - String containing the name of the pattern
     */
    public String getPatternName() {
        return patternName;
    }

    /**
     * Returns the name of the author if it was found in the metadata
     *
     * @return - String containing the name of the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the a comment if it was found in the metadata
     *
     * @return - String containing comment extracted from the metadata
     */
    public String getComment() {
        return comment;
    }

    /**
     * Returns the byte array
     *
     * @return - byte[][] containing the gameboard. 
     */
    public byte[][] getArr() {
        return arr;
    }

    /**
     * Main method of the class. Calls on all the other methods.
     * Returns a 2D byte array after loading in and parsing a .rle file.
     * @return - 2D byte array that is a representation of the rle pattern from the .rle file.
     * @throws IOException
     */
    public byte[][] getStaticBoard() {
        try {
            BufferedReader br = ReadFile.readFileFromDisk();
            if (br != null) {
                try {
                    // Sets up the buffered reader to easily go back and forth while looking for metadata, xy and rules.
                    // 1000 chars as limit to be on the safe side.
                    br.mark(1000);
                    findMetaData(br);
                    String xYRulesLine = findXYandRulesLine(br);
                    findXY(xYRulesLine);
                    findRules(xYRulesLine);
                    arr = new byte[y][x];
                    xPlacement = 0;
                    yPlacement = 0;
                    String line;
                    while ((line = getLine(br)) != null) {
                        setByteArrayFromRlePattern(line);
                    }
                } catch (PatternFormatException e) {
                    FileHandling.alert("An error occured while reading the .rle file");

                }

                br.close();
                return arr;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Reads the Meta data from the RLE file an stores them in various variables
     * that can be accessed through get-methods.
     * @param br - BufferedReader that contains the RLE-file
     */
    private void findMetaData(BufferedReader br) {
        this.patternName = metaDataFinder(br, "#n");
        this.author = metaDataFinder(br, "#o");
        this.comment = metaDataFinder(br, "#c");
    }



    /**
     * General method to be used to find different types of metadata
     * @param br - BufferedReader that hold the RLE-file
     * @param lineStart - String thats used to find the start of the line of a given segment of metadata
     * @return - String that holds the given metadata
     */
    private String metaDataFinder(BufferedReader br, String lineStart) {
        try {
            boolean notEndOfMeta = true;
            String line;
            String metaData = "";
            while (notEndOfMeta && (line = br.readLine()) != null) {
                line = line.toLowerCase();
                if (line.startsWith(lineStart)) {
                    metaData += line.substring(2);
                }
                else if (line.startsWith("x")) {
                    notEndOfMeta = false;
                }
            }
            br.reset();
            return metaData;
        }
        catch (IOException e) {
            FileHandling.alert("Error trying to locate metadata");
        }
        return null;
    }

    /**
     *
     * @param br - BufferedReader that holds the rle string.
     * @return - String containing the x & y (dimentions) and the rules.
     * @throws IOException
     * @throws PatternFormatException
     */
    private String findXYandRulesLine(BufferedReader br) throws IOException, PatternFormatException {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("x")) {
                return line;
            }
        }
        throw new PatternFormatException("X, Y or Rules not found in file.");
    }

    /**
     * Finds the X & Y (Dimentions) of the board and stores them as the x and y variables
     * @param line - String that holds X & Y
     * @throws IOException
     * @throws PatternFormatException
     */
    private void findXY(String line) throws IOException, PatternFormatException {
        String test = "x ?= ?(\\d+),? ?y ?= ?(\\d+)";
        Matcher matcher = Pattern.compile(test, Pattern.CASE_INSENSITIVE).matcher(line);
        if (matcher.find()) {
            this.x = Integer.parseInt(matcher.group(1));
            this.y = Integer.parseInt(matcher.group(2));
        }
        else {
            throw new PatternFormatException("Couldn't find X or/and Y-value.");
        }
    }

    /**
     * Finds the rules and converts them into array that is stored in the survive and born variables
     * @param line - String that holds the rules
     * @throws PatternFormatException
     */
    private void findRules(String line) throws PatternFormatException {
        String test = "rule ?= ?b?(\\d+)/s?(\\d+)";
        line = line.toLowerCase();
        Matcher matcher = Pattern.compile(test, Pattern.CASE_INSENSITIVE).matcher(line);
        if (matcher.find()) {
            if (line.indexOf('b') != -1 || line.indexOf('s') != -1) {
                born = convertRuleToArray(Integer.parseInt(matcher.group(1)));
                survive = convertRuleToArray(Integer.parseInt(matcher.group(2)));
            }
            else {
                survive = convertRuleToArray(Integer.parseInt(matcher.group(1)));
                born = convertRuleToArray(Integer.parseInt(matcher.group(2)));
            }
        }
        else {
            throw new PatternFormatException("Couldn't find rules");
        }

    }

    /**
     *
     * @param br
     * @return
     * @throws IOException
     */
    private String getLine(BufferedReader br) throws IOException {
        String line;
        if ((line = br.readLine()) != null) {
            return line;
        }
        return null;
    }

    private boolean startNotFound(String line) {
        if (line.substring(0, 1).matches("[0-9ob$]")) {
            return false;
        } else {
            return true;
        }
    }

    //Runs through the rlePattern and updates the local variable boardInBitString using the updateBitString method.
    public void setByteArrayFromRlePattern(String rlePattern) {
        for (int i = 0; i < rlePattern.length(); i++) {

            int tempRunCount;
            char tempCellType;

            if ((tempRunCount = getRunCount(rlePattern, i)) != 0) {
                runCount = tempRunCount;
                i += iUpdate(runCount);
            }
            else if ((tempCellType = getCellType(rlePattern, i)) != 'a') {
                cellType = tempCellType;
                if (i == 0 || prevCharIsNotInt(rlePattern, i)) {
                    runCount = 1;
                }
                updateArray(runCount, cellType);
            }
        }
    }

    private int[] convertRuleToArray(int r) {
        String a = Integer.toString(r);
        int[] rule = new int[a.length()];
        for (int i = 0; i < a.length(); i++) {
            rule[i] = Character.digit(a.charAt(i), 10);
        }
        return rule;
    }


    //Updates the BitString with the number of 1s or 0s that is needed according to the latest reading from the RLE-string
    private void updateArray(int runCount, char cellType) {
        if (cellType == 'b') {
            updateArray2(runCount, (byte)0);
        } else if (cellType == 'o') {
            updateArray2(runCount, (byte)1);
        } else if (cellType == '$') {
            for (int i = 0; i < runCount; i++) {
                if (i == 0) {
                    if (xPlacement != 0) {
                         updateArray2((x - xPlacement), (byte)0);
                    }
                } else {
                    updateArray2(x, (byte)0);
                }
            }
        }
        else if (cellType == '!') {
            updateArray2(x - xPlacement, (byte)0);
        }
    }

    private void updateArray2 (int r, byte b){
        for (int i = 0; i < r; i++) {
            if  (xPlacement != x) {
                arr[yPlacement][xPlacement] = b;
            }
            if ((xPlacement+1) == x){
                xPlacement = -1;
                if ((yPlacement + 1) != y) {
                    yPlacement++;
                }
            }
            xPlacement++;

        }
    }

    private int getRunCount(String line, int i) {
        String runCount = "";
        if (line.substring(i, i + 1).matches("\\d")) {
            runCount += (char) line.charAt(i);
            if (line.substring(i + 1, i + 2).matches("\\d")) {
                runCount += (char) line.charAt(i + 1);
                if (line.substring(i + 2, i + 3).matches("\\d")) {
                    runCount += (char) line.charAt(i + 2);
                    if (line.substring(i + 3, i + 4).matches("\\d")) {
                        runCount += (char) line.charAt(i + 3);
                    }
                }
            }
        } else {
            return 0;
        }
        int runCountInt = Integer.parseInt(runCount);
        return runCountInt;
    }

    private char getCellType(String line, int i) {
        if (line.substring(i, i + 1).matches("[bo$]")) {
            return line.charAt(i);
        }
        return 'a';
    }

    private int iUpdate(int runCount) {
        int numberOfDigits = String.valueOf(runCount).length();
        if (numberOfDigits == 2) {
            return 1;
        }
        else if (numberOfDigits == 3) {
            return 2;
        }
        else if (numberOfDigits == 4) {
            return 3;
        }
        else {
            return 0;
        }
    }

    private boolean prevCharIsNotInt(String rlePattern, int i) {
        char cha = rlePattern.charAt(i - 1);
        if (Character.isDigit(cha)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Import method that returns a 2D ArrayList
     *
     * @return - 2D ArrayList representation of the game board.
     */
    public ArrayList<List<Byte>> importAsList() {
        getStaticBoard();
        ArrayList<List<Byte>> arrLi = convertToArrayList(arr);
        return arrLi;
    }

    /**
     * Converts the input 2D byte array to a 2D ArrayList of Byte objects.
     * @param b - The 2D byte array that needs to be converted
     * @return - ArrayList of byte objects used for further processing
     */
    private ArrayList<List<Byte>> convertToArrayList(byte[][] b) {
        ArrayList<List<Byte>> byteArrayList = new ArrayList<List<Byte>>();

        for (int y = 0; y < b.length; y++) {
            List<Byte> oneDim = new ArrayList<Byte>();
            for (int x = 0; x < b[0].length; x++) {
                oneDim.add(b[y][x]);
            }
            byteArrayList.add(oneDim);
        }
        System.out.println(byteArrayList);
        return byteArrayList;
    }

    /**
     * Gives the number of neighbors needed for a cell to be born
     *
     * @return - Number of needed neighbors for cell to be born
     */
    public int[] getBorn() {
        return born;
    }

    /**
     * Gives the number of neighbors needed for a cell to survive
     *
     * @return - Number of needed neighbors to survive
     */
    public int[] getSurvive() {
        return survive;
    }

    /**
     * Prints the board to console
     *
     */
    @Override
    public String toString() {
        String toString = "";
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                toString += arr[i][j];
            }
            toString += "\n";
        }
        return  toString;
    }

    //FOR TESTING ONLY
    public void tester(String xYRulesLine, String line) throws Exception {
        try {
            findXY(xYRulesLine);
            findRules(xYRulesLine);
            arr = new byte[y][x];
            xPlacement = 0;
            yPlacement = 0;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PatternFormatException e) {
            e.printStackTrace();
        }
        setByteArrayFromRlePattern(line);
    }
}


