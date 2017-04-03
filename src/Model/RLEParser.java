package Model;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that parses a .rle file and returns it as a 2D byte array through the importFile method.
 * It can also return the X and Y value if needed later on.
 */

public class RLEParser {

    //TODO Add function that checks all new lines for zeroes and has a runcount for empty lines.
    //TODO Enable loading of bigger patterns
    //TODO Skip step that writes whole pattern to string
    //TODO Check what happens at the end of Star Synth

    private int x;
    private int y;
    private int xPlacement;
    private int yPlacement;
    private String boardInBitString = "";
    private int[] born;
    private int[] survive;
    byte[][] arr;

    char cellType;
    int runCount;




    /**
     * Main function of RLEParser. Returns a 2D byte array after loading in and parsing through a .rle file.
     * @return a byte array that is a representation of the rle pattern from the .rle file.
     * @throws IOException
     */
    public byte[][] importFile() {
        try {
            BufferedReader br = ReadFile.readFileFromDisk();
            if (br != null) {
                try {
                    String xYRulesLine = findXYandRulesLine(br);
                    findXY(xYRulesLine);
                    findRules(xYRulesLine);
                    arr = new byte[y][x];
                    xPlacement = 0;
                    yPlacement = 0;
                    String line;
                    while ((line = getLine(br)) != null) {
                        setBitStringFromRlePattern(line);
                    }
                } catch (PatternFormatException e) {
                    FileHandling.alert("An error occured");
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

    private String findXYandRulesLine(BufferedReader br) throws IOException, PatternFormatException {
        Boolean xNotFound = true;
        while (xNotFound) {
            String line = br.readLine();
            if (line.startsWith("x")) {
                return line;
            }
            if (line == null) {
                throw new IOException("Unable to read new line from file.");
            }
        }
        throw new PatternFormatException("X, Y or Rules not found in file.");
    }

    private void findXY(String line) throws IOException, PatternFormatException {
        String test = "x ?= ?(\\d+),? ?y ?= ?(\\d+)";
        Matcher matcher = Pattern.compile(test, Pattern.CASE_INSENSITIVE).matcher(line);
        matcher.find();
        this.x = Integer.parseInt(matcher.group(1));
        this.y = Integer.parseInt(matcher.group(2));
        if (this.x == 0 || this.y == 0) {
            throw new PatternFormatException("Couldn't find X or/and Y-value.");
        }
    }

    private void findRules(String line) /*throws Exception*/ {
        String test = "rule ?= ?b?(\\d+)/s?(\\d+)";
        Matcher matcher = Pattern.compile(test, Pattern.CASE_INSENSITIVE).matcher(line);
        matcher.find();
        //throw new Exception();
        born = convertRuleToArray(Integer.parseInt(matcher.group(1)));
        survive = convertRuleToArray(Integer.parseInt(matcher.group(2)));
    }

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
    private void setBitStringFromRlePattern(String rlePattern) {
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


    private Boolean checkForRleEnding(String line, int i) {
        if (line.substring(i, i + 1) == "!") {
            return true;
        } else {
            return false;
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
                         yPlacement++;
                         xPlacement = 0;
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
            if(xPlacement != x) {
                arr[yPlacement][xPlacement] = b;
                xPlacement++;
            }

        }
    }



    /**
     * Returns the X value of the Board from the RLE-file loaded into the given RLEParser-object
     */

    public int getX() {
        return x;
    }

    /**
     * Returns the Y value of the Board from the RLE-file loaded into the given RLEParser-object
     */
    public int getY() {
        return y;
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
        } else if (numberOfDigits == 3) {
            return 2;
        } else {
            return 0;
        }
    }

    private String addToBitString(int runCount, char cellType) {
        String updateBitString = "";
        for (int i = 0; i < runCount; i++) {
            updateBitString += cellType;
        }
        return updateBitString;
    }

    private boolean prevCharIsNotInt(String rlePattern, int i) {
        char cha = rlePattern.charAt(i - 1);
        if (Character.isLetter(cha)) {
            return true;
        } else {
            return false;
        }
    }

    private byte[][] stringToByteArray(String bitString) {
        byte[][] byteArray = new byte[y][x];
        int count = 0;
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                char charBit = bitString.charAt(count);
                byteArray[i][j] = (byte) Character.getNumericValue(charBit);
                count++;
            }
        }
        return byteArray;
    }

    /**
     * Test function that prints the array that is loaded through the RLEParser-object.
     *
     * @param array - the array that the parser extracts from the .rle file.
     */
    public void printArray(byte[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println("");
        }
    }
    public int[] getBorn() {
        return born;
    }

    public int[] getSurvive() {
        return survive;
    }
}


