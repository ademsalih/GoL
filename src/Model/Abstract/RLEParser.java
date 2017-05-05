package Model.Abstract;


import Model.PopUps;
import Model.PatternFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Model.ReadFile;

/**
 * <h1>RLE Parser</h1>
 * <h3>Handles the parsing of the .rle files and returns both meta data and a game of life board.</h3>
 *
 * <p>Tried to do as much of the data processing without including the specific board type
 * it was destinied to.</p>
 *
 * <p>Unfortunately only the name is used from the metadata, but for future use there are methods
 * for getting the author and comment.</p>
 */
public abstract class RLEParser {

    // Board & Rule variables
    protected int x, y, xPlacement, yPlacement;
    private int[] born;
    private int[] survive;

    // Metadata
    private String patternName;
    private String author;
    private String comment;

    private char cellType;
    private int runCount;

    /**
     * Returns the X value (Number of Columns) of the Board
     *
     * @return Number of columns
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y value (Number of rows) of the Board from the RLE-file loaded into the given RLEParser_Static-object
     *
     * @return Number of rows
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the name of the pattern if it was found in the metadata
     *
     * @return String containing the name of the pattern
     */
    public String getPatternName() {
        return patternName;
    }

    /**
     * Returns the name of the author if it was found in the metadata
     *
     * @return String containing the name of the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the a comment if it was found in the metadata
     *
     * @return String containing comment extracted from the metadata
     */
    public String getComment() {
        return comment;
    }

    /**
     * Imports the board from Disk.
     * Uses the importBoard method to create a object that holds the board
     * @throws IOException
     */
    public void importFromDisk() throws IOException {
        BufferedReader br = ReadFile.readFileFromDisk();
        importBoard(br);
    }


    /**
     * Imports the board from a URL
     * Uses the importBoard method to create a object that holds the board
     *
     * @param url  String that contains the url.
     * @throws IOException
     */
    public void importFromURL(String url) throws IOException {
        BufferedReader br = ReadFile.readFileFromUrl(url);

        if (br != null) {
            importBoard(br);
        }
        else throw new IOException("Unable to locate or read from URL");

    }


    /**
     * Imports a RLE-board from a given sources.
     * Subclasses defines what kind of container the board is held as.
     *
     * @param br  BufferedReader holding the RLE-File
     */
    public void importBoard(BufferedReader br) {
        try {
            if (br != null) {
                try {
                    // Sets up the buffered reader to easily go back and forth while looking for metadata, xy and rules.
                    // 1000 chars as limit to be on the safe side.
                    br.mark(1000);

                    findMetaData(br);

                    // Stores the line containing X and Y and Rules on a String
                    String xYRulesLine = findXYandRulesLine(br);
                    findXY(xYRulesLine);
                    findRules(xYRulesLine);

                    initBoard();
                    xPlacement = 0;
                    yPlacement = 0;
                    String line;
                    while ((line = getLine(br)) != null) {
                        findCountCellThenUpdate(line);
                    }
                } catch (PatternFormatException e) {
                    PopUps.alert("An error occured while reading the .rle file");

                }

                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the Meta data from the RLE file an stores them in various variables
     * that can be accessed through getmethods.
     * @param br  BufferedReader that contains the RLE-file
     */
    private void findMetaData(BufferedReader br) {
        this.patternName = metaDataFinder(br, "#n");
        this.author = metaDataFinder(br, "#o");
        this.comment = metaDataFinder(br, "#c");
    }

    /**
     * General method to be used to find different types of metadata
     * @param br  BufferedReader that hold the RLE-file
     * @param lineStart  String thats used to find the start of the line of a given segment of metadata
     * @return  String that holds the given metadata
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
            PopUps.alert("Error trying to locate metadata");
        }
        return null;
    }

    /**
     *
     * @param br  BufferedReader that holds the rle string.
     * @return  String containing the x & y (dimentions) and the rules.
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
     * @param line  String that holds X & Y
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
     * @param line  String that holds the rules
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

    /**
     * Runs through the rlePattern and updates the local variable boardInBitString using the updateBitString method.
     */
    public void findCountCellThenUpdate(String rlePattern) {
        for (int i = 0; i < rlePattern.length(); i++) {

            int tempRunCount;
            char tempCellType;

            if ((tempRunCount = getRunCount(rlePattern, i)) != 0) {
                runCount = tempRunCount;
                i += numberOfDigits(runCount);
            }
            else if ((tempCellType = getCellType(rlePattern, i)) != 'a') {
                cellType = tempCellType;
                if (i == 0 || prevCharIsNotInt(rlePattern, i)) {
                    runCount = 1;
                }
                initiateBoardUpdate(runCount, cellType);
            }
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
        if (line.substring(i, i + 1).matches("[bo$!]")) {
            return line.charAt(i);
        }
        return 'a';
    }

    private int numberOfDigits(int runCount) {
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

    private int[] convertRuleToArray(int r) {
        String a = Integer.toString(r);
        int[] rule = new int[a.length()];
        for (int i = 0; i < a.length(); i++) {
            rule[i] = Character.digit(a.charAt(i), 10);
        }
        return rule;
    }

    /**
     * Converts the input 2D byte array to a 2D ArrayList of Byte objects.
     * @param b  The 2D byte array that needs to be converted
     * @return  ArrayList of byte objects used for further processing
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
     * @return  Number of needed neighbors for cell to be born
     */
    public int[] getBorn() {
        return born;
    }

    /**
     * Gives the number of neighbors needed for a cell to survive
     *
     * @return  Number of needed neighbors to survive
     */
    public int[] getSurvive() {
        return survive;
    }


    /**
     * Abstract method were the different methods for updating array or list is implementet.
     *
     * @param r  int that holds the runcount. This is the number of placements the byte should have.
     * @param b  byte that holds the value of the cells that is placed on the board.
     */
    public abstract void updateBoard(int r, byte b);

    /**
     * Initialisation of the board.
     */
    public abstract void initBoard();

    //Updates the BitString with the number of 1s or 0s that is needed according to the latest reading from the RLE-string
    public abstract void initiateBoardUpdate(int runCount, char cellType);

    public abstract Object getBoard();

}


